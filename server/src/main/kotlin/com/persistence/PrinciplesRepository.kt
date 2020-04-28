package com.persistence

import com.model.Principle
import dk.dren.hunspell.Hunspell
import org.apache.jena.query.ParameterizedSparqlString
import org.apache.jena.query.QueryFactory
import org.apache.jena.query.ResultSet
import org.apache.jena.update.UpdateFactory
import org.apache.jena.vocabulary.RDF
import org.json.JSONArray
import org.json.JSONObject
import java.util.*


/**
 * Map of common educational terms in full searchable values
 */
fun getEducationalLanguageMap(): Map<String, String> {
    val items = HashMap<String, String>()

    items["nat"] = "National"
    items["nat 5"] = "National 5"
    items["nat 4"] = "National 4"
    items["nat 3"] = "National 3"
    items["nat 2"] = "National 2"
    items["nat 1"] = "National 1"
    items["nat"] = "National"
    items["ah"] = "Advanced Higher"
    items["sqa"] = "Scottish Qualifications Authority"
    items["n5"] = "Scottish Qualifications Authority"
    items["n4"] = "Scottish Qualifications Authority"
    items["n3"] = "Scottish Qualifications Authority"
    items["n2"] = "Scottish Qualifications Authority"
    items["n1"] = "Scottish Qualifications Authority"

    return items
}

/**
 * Converts common educational [searchTerm] into full searchable values
 */
fun convertEducationalTerm(searchTerm: String): String {
    val map = getEducationalLanguageMap()

    return (if (map.containsKey(searchTerm.decapitalize())) {
        map[searchTerm]
    } else {
        searchTerm
    })!!
}

/**
 * Corrects the spelling of [searchTermRaw] provided
 */
fun correctSpelling(searchTermRaw: String): String {

//    TODO: Fix so this works in jar executable - Problem is Hunspell requires a file path however not recommended in jars
    val dictionary: Hunspell.Dictionary = Hunspell.getInstance().getDictionary("./en_GB")

    val searchTermMisspelled = dictionary.misspelled(searchTermRaw)
    val suggestedReplacements = dictionary.suggest(searchTermRaw)

    return if (!searchTermMisspelled) {
        searchTermRaw
    } else if (suggestedReplacements.size > 0) {
        suggestedReplacements[0]
    } else {
        searchTermRaw
    }
}

/**
 * Get synonyms of [searchTerm]
 */
fun getRelationsOfWord(searchTerm: String): List<String> {

    try {
        val url = "http://api.conceptnet.io/query?node=/c/en/${searchTerm.decapitalize()}&other=/c/en&limit=100"

        val response: JSONObject = khttp.get(url).jsonObject
        val edges: JSONArray = response["edges"] as JSONArray

        val relations = mutableListOf<String>()

        for (edgeIndex in 0 until edges.length()) {
            val edge: JSONObject = edges[edgeIndex] as JSONObject

            val end: JSONObject = edge["end"] as JSONObject
            val start: JSONObject = edge["start"] as JSONObject
            val weight: Double = edge["weight"] as Double

            val endLabel: String = end["label"] as String
            val startLabel: String = start["label"] as String

            if (weight > 2) {
                if (startLabel != endLabel) {
                    relations.add(startLabel)
                }
                relations.add(endLabel)
            }
        }

        return relations.distinct()
    } catch (e: Exception) {
        return mutableListOf<String>()
    }
}

/**
 * Retrieves list of principles related to [searchTermRawString]
 */
fun getPrinciples(searchTermRawString: String): Array<Principle> {

    val amendedSearchTerms = mutableListOf<List<String>>()
    val searchTermRawArray = searchTermRawString.split(" ").toTypedArray()

    for (searchTermRaw in searchTermRawArray) {

        val allSearchTermVariations = mutableListOf<String>()
        allSearchTermVariations.add(searchTermRaw)

        val convertedSearchTerm = convertEducationalTerm(searchTermRaw)
        allSearchTermVariations.add(convertedSearchTerm)

        if (convertedSearchTerm === searchTermRaw) {
//            val spellCheckedSearchTerm = correctSpelling(searchTermRaw)
//            allSearchTermVariations.add(spellCheckedSearchTerm)

//            val synonymsOfWordJson = getRelationsOfWord(spellCheckedSearchTerm)
            val synonymsOfWordJson = getRelationsOfWord(searchTermRaw)

            for (synonym in synonymsOfWordJson) {
                allSearchTermVariations.add(synonym)
            }

        }
        amendedSearchTerms.add(allSearchTermVariations.distinct())
    }

    return getPrinciplesFromTriplestore(amendedSearchTerms)
}

fun getPrinciplesFromTriplestore(amendedSearchTerms: MutableList<List<String>>): Array<Principle> {

    val pss = ParameterizedSparqlString()
    // Add a Base URI and define the rdfs prefix

    pss.setNsPrefix("sc", SC);
    pss.setNsPrefix("rdf", RDF.getURI());
    pss.setNsPrefix("bds", BDS);
    pss.setNsPrefix("text", TEXT);

    pss.append("SELECT ?principleText ?id ?rank ?topic ");
    pss.append("WHERE {")
    pss.append("?p rdf:type sc:CreativeWork . ");
    pss.append("?p sc:text ?principleText . ");
    pss.append("?p sc:category ?topic . ");
    pss.append("?p sc:identifier ?id . ");
//    pss.append("?p text:query (sc:text \"")
    pss.append("?principleText bds:search \"");
    for (searchTerm in amendedSearchTerms) {
        for (variation in searchTerm) {
            pss.append("|*$variation*")
        }
    }
    pss.append("\". ");
//    pss.append("?p sc:text ?principleText. ")
    pss.append("?principleText bds:rank ?rank. ");
    pss.append("} ")
    pss.append("GROUP BY ?principleText ?rank ?topic ?id ")
    pss.append("ORDER BY ?rank ")

    println(pss.toString())

    val rs: ResultSet = executeQuery(QueryFactory.create(pss.toString()))!!

    return rdfToJsonObject(rs)
}

fun addPrinciple(p: Principle) {
    println("adding ${p.principleText}")
    return executeUpdate(UpdateFactory.create(jsonToRdfPrinciple(p)))
}

fun jsonToRdfPrinciple(p: Principle): String {

    val pss = ParameterizedSparqlString()

    pss.setNsPrefix("sc", SC);
    pss.setNsPrefix("rdf", RDF.getURI());
    pss.setNsPrefix("bds", BDS);

    pss.append("INSERT DATA{ ")
    pss.append("<${getResourceFromValue(p.principleText.sha256(), PRINCIPLES_GRAPH)}> a <${SC + "CreativeWork"}> ; ")


    //User generated
    pss.append("<${getUriFromKey("principleText")}> \"${p.principleText}\" ; ")
    pss.append("<${getUriFromKey("id")}> \"${p.id}\" ; ")
    pss.append("<${getUriFromKey("topic")}> \"${p.topic}\" ; ")

    pss.append(" }")

    return pss.toString()
}
