#!/bin/bash

java -jar ../../lib/dko.jar generate-dkos \
	--schemas schemas.json \
	--package com.mycompany.dko \
	--java-output-dir gensrcdko
