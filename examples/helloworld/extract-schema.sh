#!/bin/bash

java -cp ../../lib/dko.jar:../../deps/sqlite-jdbc.jar \
	org.kered.dko.Main extract-schema \
	--url "jdbc:sqlite:helloworld.sqlite3" \
	--out schemas.json

