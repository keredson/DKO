
Type Mappings
=============

DKOs have default Java types for each database column type known.  But sometimes you don't want what's being 
offered.  If you want to change the type of a generated field, you need to create a `type_mapping.json` file.

for instance, if you want all `java.sql.TimeStamp`s changed to `java.util.Date` (because the time components
are always ignored by your app), you need a `class_mappings` entry in your `type_mapping.json`.  An example:

```json
{
    "class_mappings": {
        "java.sql.Timestamp": "java.util.Date",
    },
    "functions": {
      "java.sql.Timestamp java.util.Date" :
          "new java.util.Date((%s).getTime()",
      "java.util.Date java.sql.Timestamp" :
          "new java.sql.Timestamp((%s).getTime()",
    }
}
```

Note the required `functions` section which specifies Java code for conversions between the two.  The first 
entry converts a `Timestamp` to a `Date`.  The second does the reverse.  This will work for your own custom
classes as well.

If you don't want all types to be mapped the same way for all classes, you can specify Java
regex statements (matched against the schema.table.column names) to specify certain classes.
For example, this will do the same as the previous example, but only for fields with names ending in "_date":

```json
{
    "schema_mappings": {
        ".*_date": "java.util.Date",
    },
    "functions": {
      "java.sql.Timestamp java.util.Date" :
          "new java.util.Date((%s).getTime()",
      "java.util.Date java.sql.Timestamp" :
          "new java.sql.Timestamp((%s).getTime()",
    }
}
```

