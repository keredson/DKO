Hello Android!
==============

I designed DKOs to be stupidly simple.  Don't get me wrong, I love what bytecode
rewriting has brought to Java.  But it brings pain with it (esp. when overused).  One 
of the perks of this is that DKOs worked on Android almost out of the box.

Introduction
------------

This `helloandroid` app is actually the initial stub of a real app I'm building.  It's
a product database.  There are four tables:

```sql
CREATE TABLE "scraper_product" (
    "id" integer NOT NULL PRIMARY KEY,
    "web_page_id" integer NOT NULL REFERENCES "scraper_webpage" ("id"),
    "manufacturer_id" integer REFERENCES "scraper_manufacturer" ("id"),
    "name" varchar(1000),
    "discontinued" bool NOT NULL,
    "created" datetime NOT NULL
);
CREATE TABLE "scraper_property" (
    "id" integer NOT NULL PRIMARY KEY,
    "name" varchar(1000) NOT NULL,
    "display_name" varchar(1000),
    "parent_id" integer REFERENCES "scraper_property" ("id"),
    "display" bool NOT NULL,
    "created" datetime NOT NULL
);
CREATE TABLE "scraper_fact" (
    "id" integer NOT NULL PRIMARY KEY,
    "web_page_id" integer NOT NULL REFERENCES "scraper_webpage" ("id"),
    "product_id" integer NOT NULL REFERENCES "scraper_product" ("id"),
    "prop_id" integer NOT NULL REFERENCES "scraper_property" ("id"),
    "value" text NOT NULL,
    "created" datetime NOT NULL
);
CREATE TABLE scraper_manufacturer (
    id integer PRIMARY KEY, 
    name varchar(1000), 
    created datetime
);
CREATE INDEX "scraper_fact_445e8cc4" ON "scraper_fact" ("web_page_id");
CREATE INDEX "scraper_fact_7f1b40ad" ON "scraper_fact" ("product_id");
CREATE INDEX "scraper_fact_b0389113" ON "scraper_fact" ("prop_id");
```

More simply:  A Fact is the value between a Product and a Property.  Products
also have a Manufacturer.  (Why isn't the manufacturer just another property?  GOOD QUESTION...)

Dependencies
------------

Android's internal database support is all driven by SQLite3, however they
totally eschewed JDBC.  DKOs support SQLite3, but JDBC is still needed. So:

Dependency: [SQLDroid](https://github.com/SQLDroid/SQLDroid)

A copy is already included in `libs/sqldroid-1.0.0RC1.jar`, but if you want
to build it yourself:

```bash
$ git clone https://github.com/SQLDroid/SQLDroid.git
$ de SQLDroid
$ sudo apt-get install rake
$ rake
```

You'll also need to build DKO.  From this directory:

```bash
$ cd ../..
$ ant
```

*Warning*: Do not try to make SQLDroid (or DKO) dependent/library projects in Eclipse ADT.  I've
never been able to get a deployable APK that way.  Just build SQLDroid with `rake` and DKO 
with `ant`, and copy the jars.

Eclipse ADT
-----------

Import this project into Eclipse.  It should build and run as-is.

Important files:

_`./src/org/kered/contactlensfinder/DB.java`_

Normally you would specify a default data source directly in your `build.xml` such as
`com.company.myproject.Util.getDefaultDataSource()`.  But android is a bit more 
complicated.  Database files need to be written to `/data/data/com.company.myproject`,
the path to which is gotten from the android Context, available from your Activity.  So
`DB.getReady(context)` needs to be called once in the `onCreate()` of your 
`MainActivity`.  This code does two things:

 1. Loads the SQLDroid JDBC driver.
 2. Checks to see if the SQLite3 database file exists, and if it doesn't, copies an initial one from `assets`.
 3. Sets the default datasource for all DKOs in this VM.

_`./src/org/kered/contactlensfinder/ViewManufacturersActivity.java`_

This `Activity` is a usage example.  It displays a list of manufacturers with the `ArrayAdapter`.  Here's 
the critical code:

```java
		// Define the query (note the case-insensitive order-by using the SQL LOWER() function).
		Query<Manufacturer> query = Manufacturer.ALL.orderBy(LOWER(Manufacturer.NAME));
		
		// The method asList() is a terminal operation (it doesn't return another query), 
		// so this is where the database is actually called.  Note that Query objects 
		// are Iterable, so if you're in a loop the asList() is unnecessary (in fact 
		// it's counter-productive, as DKOs will steam when used as an Iterable).
		final List<String> names = query.asList(Manufacturer.NAME);
```

The above code calls are split out to highlight the underlying types.  However it would be 
more common to chain them all together as such:

```java		
		final String[] names = Manufacturer.ALL
				.orderBy(LOWER(Manufacturer.NAME))
				.asList(Manufacturer.NAME);
```

*Note:* The method `LOWER()` is from:

```java
import static org.kered.dko.SQLFunction.LOWER;
```

Other examples exist in `./src/org/kered/contactlensfinder/ViewProductsActivity.java`
and `./src/org/kered/contactlensfinder/ViewPropertiesActivity.java`.

Good luck!
Derek
