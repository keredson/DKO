
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

```bash
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
