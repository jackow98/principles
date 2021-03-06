package com.persistence

import com.beust.klaxon.Klaxon
import com.model.Principle
import org.apache.jena.query.Query
import org.apache.jena.query.QueryExecutionFactory
import org.apache.jena.query.ResultSet
import org.apache.jena.query.ResultSetFactory
import org.apache.jena.sparql.engine.http.QueryEngineHTTP
import org.apache.jena.update.UpdateExecutionFactory
import org.apache.jena.update.UpdateProcessor
import org.apache.jena.update.UpdateRequest
import java.net.HttpURLConnection
import java.net.URL


/**
 * Executes sparql [query] on tripletore and returns the associated result set
 */
fun executeQuery(query: Query): ResultSet? {

    buildIndex()

    // Remote execution.
    return try {
        val qexec = QueryExecutionFactory.sparqlService(SPARQL_REMOTE_ENDPOINT, query)

        // Set the specific timeout.
        (qexec as QueryEngineHTTP).addParam("timeout", "10000")

        // Execute.
        qexec.execSelect()
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

fun buildIndex() {
    // Build text index.
    try {
        val url = URL("$REMOTE_ENDPOINT/namespace/kb/textIndex?force-index-create=true")

        with(url.openConnection() as HttpURLConnection) {
            requestMethod = "POST"  // optional default is GET

            println("\nSent 'POST' request to URL : $url; Response Code : $responseCode")

            inputStream.bufferedReader().use {
                it.lines().forEach { line ->
                    println(line)
                }
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

/**
 * Converts RDF [ResultSet] to an array of the provided [dataClass]
 */
fun rdfToJsonObject(rs: ResultSet): Array<Principle> {

    // Gets a copy of RDF results
    val rdfResults: ResultSet = ResultSetFactory.copyResults(rs);
    var output: Array<Principle> = emptyArray()

    for (r in rdfResults) {
        val subjectStringBuilder = StringBuilder()

        subjectStringBuilder.append("{")

        val literals = mutableListOf<String>()
        literals.add("principleText")
        literals.add("id")
        literals.add("topic")

        for (literal in literals) {
            if (r.contains(literal)) {
                subjectStringBuilder.append("\"${literal}\":\"${r.getLiteral(literal)}\",")
            }
        }

        subjectStringBuilder.append("}")

        var dataClass = Principle()
        dataClass = Klaxon().parse<Principle>("""$subjectStringBuilder""")!!
        output += dataClass
    }

    return output
}

/**
 *  Executes sparql [update] on tripletore
 */
fun executeUpdate(update: UpdateRequest) {
    try {
        val processor: UpdateProcessor = UpdateExecutionFactory.createRemote(update, SPARQL_REMOTE_ENDPOINT)
        processor.execute()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}