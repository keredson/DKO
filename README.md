DKOs - Data Knowledge Objects
=============================
(Or Derek's Objects, depending on who you ask...)

Introduction
------------
DKOs are an ORM for people who hate ORMs.

ORMs are a pain.  They promise a world of being free from your database where you just work with good ol' Java.
Your database server doesn't matter.  Your schema doesn't matter.  Tables and relationships can be abstracted away
and changing them doesn't have to affect your code.  All you have are some object references and you can modify
them willy-nilly and it'll all just work.

Well that's just nonsense.  It may work for a 100 entry blog implementation, but you're not going to process
millions of new rows daily with it (or at least not without a world of pain getting there).  Your database is a shared
resource on a different
machine, not an in-memory entity.  And ignoring your schema is a great way to accidentally DOS your database with
millions of "`select * from x where id=35476753`" style queries.

Plus: **SQL is not the devil!**  It's one of computer science's most successful languages!  The devil 
is SQL built by string concatenation.  And string identifiers.  And a lack of typing.  And a lack of streaming.

DKOs addresses all these issues:

 - It's fully typed.
 - It's streaming (by default).
 - It embraces SQL (rather than replaces it).
 - It doesn't use string identifiers for tables/columns/etc.
 - It doesn't hide from you the fact that it's hitting a database (or what SQL it's running).

Want to get started right now?  Try the [Quick Start Guide](QUICK_START.md).

The Nickel Tour
---------------
Hello world...

    for (Bug bug : Bug.ALL.limit(100)) {
      System.out.println(bug.getId() +" "+ bug.getTitle());
    }

The constant `Bug.ALL` is of type `Query<Bug>`, as is `Bug.ALL.limit(100)`.  And `Query<Bug>` implements `Iterable<Bug>`
(hence it working in a for loop).

The class `Bug` is generated (please don't freak out) direct from your database schema.  Is generated code 
evil?  Usually yes, but not always.  It's evil if you generate it, modify it and check it into your VCS.  But if it's a 
build artifact (like your `.class` files) that is never touched by human hands (or git)... then it's OK.  And that's
what we do here.  (In fact the ONLY way to generate code with DKO is through an Ant task.)

Another example:

    for (Bug bug : Bug.ALL.where(Bug.REPORTER.eq(Bug.ASSIGNEE))) {
      // people work on their own bugs a lot
    }

`Bug.REPORTER` is static constant `Field<Integer>`.  Fields have methods for all normal SQL comparisons.  Here we're
comparing it to another field of the same table.

For simplicity, let's leave out the for loop going forward...

    Bug.ALL.where(Bug.CREATE_DATE.between(new Date(2010,06,30), new Date(2013,09,04)));

    Bug.ALL.where(
        Bug.CREATE_DATE.lt(new Date(2013,03,05))
        .and(Bug.CLOSE_DATE.isNull())
    );

Remember when I said DKOs embrace SQL?

Joins on foreign keys are easy too:

    Query<Bug> q = Bug.ALL
        .with(Bug.FK_REPORTER_USER)
        .where(User.NAME.like("%Derek%"));
    // this only runs one query - no O(n) database ops!
    for (Bug bug : q) {
        System.out.println(bug.getReporterFK().getName());
    }

BTW, that works both ways:

    Query<User> q = User.ALL
        .with(Bug.FK_REPORTER_USER)
        .where(Bug.ESTIMATED_TIME.gt(4.5)) // hours
    // only one joined query (streaming over User) happens here
    for (User user : q) {
        // no second query here!
        for (Bug bug : user.getReporterUserSet()) {
        }
    }

Foreign key relationships aren't limited to one join:

    // joins to two tables
    Bug.ALL
        .with(Bug.FK_REPORTER_USER)
        .with(Bug.FK_PRODUCT);

Nor are they limited to one level deep:

    Bug bug = Bug.ALL.with(Bug.FK_REPORTER_USER, User.FK_MANAGER).first();
    System.out.println(bug.getReporterFK().getManagerFK().getName());

Server-side updates are supported of course:

    long count = Bug.ALL
        .where(Bug.LAST_UPDATED.lt(new Date(2010,06,30)))
        .set(Bug.PRIORITY, 0)
        .update();

As are deletes, inserts, etc.  You get the drill.  Virtually all SQL operations you care about are supported.  Here 
are some highlights:

 - Transactions
 - SQL Functions
 - Joins (FK based and non-FK based)
 - Inner Queries
 - Temp Tables
 - Aggregation / Counting
 - Sum / Average
 - Order By
 - Distinct
 - Top / Limit
 - Aliases

Plus some nice touches:

 - Reads (outside transactions) default to database mirrors (if you have them).
 - Selected columns that go unused are optimized away in future runs.
 - DKOs warn you if you're needlessly killing your database (like when you forget a `.with(FK)`).
 - Lots of helper functions... (Want a `Map<Integer,Bug>` from some field to your object?  Can do!)

And DKOs scale very well.  Measured performance is >98% of raw JDBC for the largest SQL Server databases money can 
buy.  And they're small enough to power Android apps (using SQLite).

Plus, and I can't stress this enough:  The code is **boringly simple**.  Plain objects with plain variables.  No 
byte-code rewriting.  No twenty-layers-of-reflection
hell.  When you want to know what's going on you don't need a PhD.  Only a debugger.

To learn more, see [DKOs - The Book](http://nosco.googlecode.com/hg/doc/dkos-the-book/dkos-the-book.html).



History
-------
The idea of DKO started in 2006 when I first used [Django](https://www.djangoproject.com), 
liked their query API and thought "jeez I wish
Java had this".  (I had spent the previous six years as a corporate Java developer)
But I quickly fell in love with Python, so I never pursued it.
Then in 2010 I was thrown back into the Java world when I started at 
[REDACTED].  I was appalled by our internal use of Hibernate
(35k lines of XML config files alone!), so I implemented DKO as an external open-source project to prove out the 
concept.  It worked, I brought it in, and it has been in widespread internal use for greater than two years now.  It 
powers hundreds of daily 
production-critical jobs and processes, the vast majority *not* written by me (and without any sort of internal mandate
for its use), and is the preferred go-to API for database access in the office by developers and data analysts alike.

The original name of DKOs was [Nosco](https://code.google.com/p/nosco) (Latin for "get to know", hence 
"Data Knowledge Objects"), but someone pointed out that that rhymed with "bosco", and I couldn't have that.  :)

Note: *[REDACTED] in no way endorses DKOs.  Opinions stated here are mine and mine alone.*

