package com.persistence

import org.apache.jena.rdf.model.Model
import org.apache.jena.rdf.model.ModelFactory
import org.apache.jena.rdf.model.Resource
import java.security.MessageDigest
import java.util.*

/**
 * Converts a string [value] to an IRI prefixed with the given [uri]
 */
fun getResourceFromValue(value: String, uri: String): Resource {
    val m: Model = ModelFactory.createDefaultModel()
    return m.createResource(uri + stringToCamelCase(value))
}

/**
 * Converts a string [s] with spaces to a lower case string separated by _
 */
fun stringToCamelCase(s: String): String {
    return s.decapitalize().replace(" ", "_")
}

/**
 * Calls hash string function on bound string
 */
fun String.sha256(): String {
    return hashString(this, "SHA-256")
}

/**
 * Hashes string to sha256
 */
private fun hashString(input: String, algorithm: String): String {
    return MessageDigest.getInstance(algorithm)
            .digest(input.toByteArray())
            .fold("", { str, it -> str + "%02x".format(it) })
}

/**
 * Returns RDF IRI of simple object [key] such as title
 */
fun getUriFromKey(key: String): String? {

    val items = getItemsMap()

    return items[key]
}

/**
 * Retruns a map from simple object value such as title to associated RDF IRI
 */
fun getItemsMap(): Map<String, String> {
    val items = HashMap<String, String>()

    items["principleText"] = SC + "text"
    items["id"] = SC + "identifier"
    items["topic"] = SC + "category"

    return items
}