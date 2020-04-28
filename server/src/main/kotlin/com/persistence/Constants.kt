package com.persistence

//Server

//Development
//const val REMOTE_ENDPOINT = "http://localhost:9999/bigdata"

//Production
const val REMOTE_ENDPOINT = "http://triplestore:8080/bigdata"

const val SPARQL_REMOTE_ENDPOINT = "$REMOTE_ENDPOINT/sparql"

//IRIs
const val BASE_URI = "http://example.org/"

const val PRINCIPLES_GRAPH = BASE_URI + "Principles/"

//Prefixes
const val SC = "https://schema.org/"
const val BDS = "http://www.bigdata.com/rdf/search#"
const val TEXT = "http://jena.apache.org/text#"