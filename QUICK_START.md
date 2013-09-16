
DKO Quick Start
===============

DKOs are easy to try out.  Here are the steps:

 1. Use DKO to extract your database schema to a json file.
 2. Add and commit this schema file (usually schemas.json) to git/hg/svn/cvs/whatever.
 3. Add the generate ant target to your build.xml.
 4. Run the generate task.
 5. Code!

A working example is in `examples/helloworld` - [README](examples/helloworld/README.md).

Build the DKO Library
---------------------

Run:

```bash
    $ git clone https://github.com/keredson/DKO.git
    $ cd DKO
    $ ant
```

Copy `lib/dko.jar` to your project's `lib` directory.


Extract Your Database Schemas
-----------------------------

First, a question:  Why extract your schema at all?  Why not have the generate task
and the extract task all in one step?  Because you want your builds to be repeatable.  You
can't replicate a build from 6 months ago if your database schema has changed since
then.

To extract your schema, you define an Ant target.  (So it's easy to repeat later when your
schema changes.)  All you need is the `dko.jar`, your JDBC driver jar (ex: `sqlserver.jar`),
and your JDBC URL.  Here's an example:

    <target name="extract-schemas" depends="init">
        <taskdef name="dkoextractschemas" 
                 classname="org.kered.dko.ant.SchemaExtractor" 
                 classpath="lib/dko.jar:lib/sqlserver.jar" />
        <dkoextractschemas
            url="jdbc:sqlserver://192.168.0.234"
            username="username"
            password="shhh..."
            out="schemas.json" />
    </target>

Then run:

    $ ant extract-schemas

This will connect to your database and write a file `schemas.json`.  This should be 
checked into your VCS:

    $ git add schemas.json
    $ git commit schemas.json

Whenever your database schema changes (and it will), just rerun:

    $ ant extract-schemas
    $ git commit schemas.json


Generating Your DKOs
--------------------

**DKOs should always be a build artifact - NEVER checked into source control!**

To generate your DKOs, add another Ant target:

    <target name="dko-pre-compile" depends="init">
        <taskdef name="dkogen" 
                 classname="org.kered.dko.ant.CodeGenerator" 
                 classpath="lib/dko.jar"/>
        <dkogen package="com.mycompany.dko" 
                schemas="schemas.json"
                javaoutputdir="gensrcdko"
                datasource="com.mycompany.Util.getDefaultDS();" />
    </target>

The `package` parameter defines which java package to put your DKOs into.  The `schemas`
parameter is the file generated above.  The `javaoutputdir` parameter is where to write the
Java source files to *(don't mix with your normal hand-written source files)*.  And the
`datasource` parameter needs to reference a static method in your code that returns a 
`javax.sql.DataSource` that offers a connection to your database.

Then add that as a prereq to your compile target:

    <target name="compile" depends="dko-pre-compile">
        [...]
    </target>

And add that `gensrcdko` directory to your `javac` task.


Start Coding!
-------------

That `dkogen` task will write one Java class for every database table in your
database.  If you have a schema `mydata` and tables `people` and `places`, you
will have the following classes:

    gensrcdko/com/mycompany/dko/mydata/People.java
    gensrcdko/com/mycompany/dko/mydata/Places.java

Again, **DO NOT CHECK THESE INTO SOURCE CONTROL!**  You don't have to regen them
literally every time you compile (although it's usually sub-second, so I don't
generally mind), but you should definitely keep the clear line between "build source"
and "build artifact" nice and sharp.

