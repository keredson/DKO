
Commands
========

Originally I refused to implement command line versions of these tools, as I believe they
should not be run manually (only as part of a build process).  Ant tasks were the only
way.  However the world is moving away from Ant, and it's infeasible for me to support all
options.  Plus: I generally don't like forcing choices.  So here are command line verions
of the different tasks.  But *please* don't generate-and-forget.  Make sure these are 
integrated into whatever build system you have.

To list all the commands:

```bash
$ java -jar lib/dko.jar
ERROR: Unrecognized command.
Syntax: java -jar lib/dko.jar <command> [command_option...]

Where <command> is one of the following:
	extract-schema:	extracts your database schema(s) to a JSON file (to be checked into your VCS)

For help on an individual command, run the command with '--help'.
```

The Extract Schema Command
--------------------------

An example (from `examples/helloworld`):

```bash
$ cat extract-schema.sh 
#!/bin/bash
java -cp ../../lib/dko.jar:../../deps/sqlite-jdbc.jar \
	org.kered.dko.Main extract-schema \
	--url "jdbc:sqlite:helloworld.sqlite3" \
	--out schemas.json
```

_Note_: We can't use the `-jar` option here because we need to include `sqlite-jdbc.jar`
in the classpath.

This will write out `schemas.json`, which looks like this:

```json
{
    "version": [
        0,
        2,
        0
    ],
    "schemas": {"": {
        "place": {
            "id": "INTEGER",
            "city": "TEXT",
            "state": "TEXT"
        },
        "person": {
            "id": "INTEGER",
            "name": "TEXT",
            "home_town": "INTEGER"
        }
    }},
    "primary_keys": {"": {
        "place": ["id"],
        "person": ["id"]
    }},
    "foreign_keys": {"FK_1": {
        "reffing": [
            "",
            "person"
        ],
        "reffed": [
            "",
            "place"
        ],
        "columns": {"home_town": "id"}
    }}
```

All available options:

```
$ java -jar lib/dko.jar extract-schema --help

 Help for command: extract-schema 
----------------------------------

--enums <string> (optional)
A comma separated list of 'schema_name.table_name.column_name' values (without the '').
DKOs can ENUMify table values for infrequently changing data.  For example:
'myschema.state.abbr' will turn the entire 'state' table into an enum, with enum entries
matching the values in the 'abbr' column.

--enums-out <filename> (required if --set-enums is used)
The file to write your enum values to.  Usually 'enums.json'.
PLEASE CHECK THIS FILE INTO VERSION CONTROL!

--db-type <sqlserver|mysql|hsql|sqlite3|postgres|oracle> (optional)
Usually this is unnecessary because the type will be auto-detected from the JDBC URL.

--username <database_username> (required for most db-types)
Not needed for SQLite3, HSQL.

--password <password> (required for most db-types)
Not needed for SQLite3, HSQL.

--password-file <filename> (optional)
If your database account's password is saved in a plain-text file, provide the path to it here.

--schemas <string> (optional)
A comma separated list of schema names to extract.  If not set all schemas will be extracted.

--only-tables <string> (optional)
A comma separated list of regex patterns.  If 'schema_name.table_name' matches 
the java pattern, the table will be includes.  Otherwise, it will be excluded.

--exclude-tables <string> (optional)
A comma separated list of regex patterns.  If 'schema_name.table_name' matches 
the java pattern, the table will be excluded.  Otherwise, it will be included.

--out <filename> (required)
The file to write your database schema to.  Usually 'schemas.json'.
PLEASE CHECK THIS FILE INTO VERSION CONTROL!

--url <jdbc_url> (required)
Your database's JDBC URL.  Examples:
  jdbc:mysql://localhost:3306/sakila?profileSQL=true
  jdbc:sqlite://dirA/dirB/dbfile
  jdbc:oracle:thin:@myhost:1521:orcl
```

The Generate DKOs Command
-------------------------

An example (from `examples/helloworld`):

```bash
$ cat examples/helloworld/generate-dkos.sh 
#!/bin/bash

java -jar ../../lib/dko.jar generate-dkos \
	--schemas schemas.json \
	--package com.mycompany.dko \
	--java-output-dir gensrcdko
```

This will generate these files:

```bash
$ find examples/helloworld/gensrcdko/
examples/helloworld/gensrcdko/
examples/helloworld/gensrcdko/com
examples/helloworld/gensrcdko/com/mycompany
examples/helloworld/gensrcdko/com/mycompany/dko
examples/helloworld/gensrcdko/com/mycompany/dko/_TableToClassMap.java
examples/helloworld/gensrcdko/com/mycompany/dko/Place.java
examples/helloworld/gensrcdko/com/mycompany/dko/Person.java
examples/helloworld/gensrcdko/.timestamp
```

All available options:

```
$ java -jar lib/dko.jar generate-dkos --help

 Help for command: generate-dkos 
---------------------------------

--enums <path> (optional)
Path to the optional enums JSON file (generated by your extract-schema call, usually 'enums.json').

--schemas <path> (required)
Path to your schemas JSON file (generated by extract-schema, usually 'schemas.json').

--strip-prefixes <prefix_list> (optional)
Set of space-separated prefixes to strip from table names.  So for example, if your table names all 
start with 'wp_' (ie: 'wp_post'), add 'wp_' here to make your class 'Post' instead of 'WpPost'.

--java-output-dir <path> (required)
Directory to write the generated class files to.  Usually 'gensrcdko' or similar.
DO NOT CHECK THESE FILES INTO VERSION CONTROL!  They should be a build artifact.
DO NOT MIX THESE WITH YOUR HAND-WRITTEN CODE!  That will only lead to checking them into VC.
TODO: Your 'build-clean' operation should delete this directory!

--package <package_name> (required)
The base java package of the generated classes.  For instance, with a base package of 
'org.kered.myapp', a schema called 'mydb' and a table 'my_table', the generated class would be
'org.kered.myapp.mydb.MyTable'.

--data-source <static_method> (optional, but highly recommended)
Full path to a static method that will return a DataSource.  For example, 'com.myapp.SomeClass.getDefaultDS()'.
If this is not specified, the datasource needs to be specified some other way.  For example,
'org.kered.dko.Context.getVMContext().setDataSource(someDS).setAutoUndo(false)' will set the default 
for the entire VM.  Alternatively it can be set per query like 'MyTable.ALL.use(someDS)'.

--use-detailed-to-string <true|false> (optional; default:false)
The default toString() implementation wraps toStringSimple() which doesn't show every field, just 
those it thinks are important for identification.  (Mainly the PK + some column name containing 
'name' or 'title'.)  Setting this to true will make toString() wrap toStringDetailed() instead.  
Both methods are available on each generated object.

--schema-aliases <string> (optional)
By default, DKOs use the schema name as the last package name.  If you want to change these to 
your own package name, do so here.  Format is comma separated with 'as'... Like so: 
'schema1 as pkg1, schema2 as pkg2, schema3 as pkg3'.

--type-mappings <path_to_file> (optional, usually 'type_mappings.json')
Please see doc/TYPE_MAPPINGS.md for information on this file.

--fake-fks <path_to_file> (optional, usually 'fake_fks.json')
Please see doc/FAKE_FOREIGN_KEYS.md for information on this file.

--callback-package <package_name> (optional)
DKOs support a variety of pre and post database operations through callbacks.  Specify this parameter 
for where the generated DKOs should look.  For instance, a schema/table `myapp.product` would 
generate a class `org.kered.myapp.Product`.  Which if given a callback package of 
`org.kered.myapp.callbacks` would look for (static) callback methods in 
`org.kered.myapp.callbacks.ProductCB`.  Supported callbacks are (in a file `ProductCB.java`):
  preInsert(Product[] products, DataSource ds)
  postInsert(Product[] products, DataSource ds)
  preUpdate(Product[] products, DataSource ds)
  postUpdate(Product[] products, DataSource ds)
  preDelete(Product[] products, DataSource ds)
  postDelete(Product[] products, DataSource ds)
  toString(Product product)
  hashCode(Product product)
  equals(Product product, Object object)
  compareTo(Product product1, Product product2)
These are useful for data checks, automatic field population, etc...
```

