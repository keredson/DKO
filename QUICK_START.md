
DKO Quick Start
===============

DKOs are easy to try out.  Here are the steps:

 1. Use DKO to extract your database schema to a json file.
 2. Add and commit this schema file (usually schema.json) to git/hg/svn/cvs/whatever.
 3. Add the generate ant target to your build.xml.
 4. Run the generate task.
 5. Code!


Build the DKO Library
---------------------

Run:

    $ git clone https://github.com/keredson/DKO.git
    $ cd DKO
    $ ant

Copy `lib/dko.jar` to your project's `lib` directory.


Extract Your Database Schema
----------------------------

First, a question:  Why extract your schema at all?  Why not have the generate task
and the extract task all in one step?  Because you want your builds to be repeatable.  You
can't replicate a build from 6 months ago if your database schema has changed since
then.

To extract your schema, you define an Ant target.  (So it's easy to repeat later when your
schema changes.)  All you need is the `dko.jar`, your JDBC driver jar (ex: `sqlserver.jar`),
and your JDBC URL.  Here's an example:

    <target name="extract-schema" depends="init">
        <taskdef name="dkoextractschema" 
                 classname="org.kered.dko.ant.SchemaExtractor" 
                 classpath="lib/dko.jar:lib/sqlserver.jar" />
        <dkoextractschema
            url="jdbc:sqlserver://192.168.0.234"
            username="username"
            password="shhh..."
            out="schema.json" />
    </target>

Then run:

    $ ant extract-schema

This will connect to your database and write a file `schema.json`.  This should be 
checked into your VCS:

    $ git add schema.json
    $ git commit schema.json

Whenever your database schema changes (and it will), just rerun:

    $ ant extract-schema
    $ git commit schema.json


Generating Your DKOs
--------------------

**DKOs should always be a build artifact - NEVER checked into source control!**

To generate your DKOs, add another Ant target:

    <target name="dko-pre-compile" depends="init">
        <taskdef name="dkogen" 
                 classname="org.kered.dko.ant.CodeGenerator" 
                 classpath="lib/dko.jar"/>
        <dkogen package="com.mycompany.dko" 
                schemas="schema.json"
                javaoutputdir="gensrcdko" />
    </target>

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

