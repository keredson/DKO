
Fake Foreign Keys
=================

Usually DKOs use the automatically generated FK relationships extracted from the database.  But sometimes
those foreign keys aren't actually there.  (Almost always for performance reasons.)   Specifying this file lets
you add FK relationships to the generated code regardless (for all the `with(FK)` goodness).  The format is 
JSON, like the following:

```json
{
    "fk_name_1":{
        "reffing": ["my_schema","product"],
        "reffed": ["my_schema","manufacturer"],
        "columns": {"manufacturer_id": "id"}
    },
}
```

This will create a FK relationship from the 'reffing' table's `product.manufacturer_id` to the 'reffed' 
table's `manufacturer.id`.  Compound keys are additional entries in the "columns" map.  

The foreign key names (`fk_name_1`) don't matter, as long as they're unique in the file.

