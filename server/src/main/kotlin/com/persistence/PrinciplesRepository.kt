package com.persistence

import com.google.gson.JsonObject
import dk.dren.hunspell.Hunspell
import org.json.JSONArray
import org.json.JSONObject
import java.util.*
import javax.swing.text.html.parser.Parser


val DICTIONARY: Hunspell.Dictionary = Hunspell.getInstance().getDictionary("src/main/kotlin/com/persistence/en_GB")

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

fun convertEducationalTerm(searchTerm: String): String {
    val map = getEducationalLanguageMap()

    return (if (map.containsKey(searchTerm.decapitalize())) {
        map[searchTerm]
    } else {
        searchTerm
    })!!
}

fun correctSpelling(searchTermRaw: String): String {

    val searchTermMisspelled = DICTIONARY.misspelled(searchTermRaw)
    val suggestedReplacements = DICTIONARY.suggest(searchTermRaw)

    return if (!searchTermMisspelled) {
        searchTermRaw
    } else if (suggestedReplacements.size > 0) {
        suggestedReplacements[0]
    } else {
        searchTermRaw
    }
}

fun getSynonymsOfWord(searchTerm: String): MutableList<String> {

    try {
        val url = "http://api.conceptnet.io/query?start=/c/en/${searchTerm.decapitalize()}&rel[1]=/r/Synonym&rel[1]=/r/RelatedTo&limit=20"

        val response: JSONObject = khttp.get(url).jsonObject
        val edges: JSONArray = response["edges"] as JSONArray

        val synonyms = mutableListOf<String>()

        for (edgeIndex in 0 until edges.length()) {
            val edge: JSONObject = edges[edgeIndex] as JSONObject
            val end: JSONObject = edge["end"] as JSONObject
            val language: String = end["language"] as String

            println(language)

            if(language == "en"){
                val label: String = end["label"] as String
                println(label)
                synonyms.add(label)
            }
        }

        return synonyms
    }catch (e:Exception){
        return mutableListOf<String>()
    }
}


fun getPrinciples(searchTermRawString: String): MutableList<List<String>> {

    val amendedSearchTerms = mutableListOf<List<String>>()
    val searchTermRawArray = searchTermRawString.split(" ").toTypedArray()

    for (searchTermRaw in searchTermRawArray) {

        val allSearchTermVariations = mutableListOf<String>()
        allSearchTermVariations.add(searchTermRaw)

        val convertedSearchTerm = convertEducationalTerm(searchTermRaw)
        allSearchTermVariations.add(convertedSearchTerm)

        if (convertedSearchTerm === searchTermRaw) {
            val spellCheckedSearchTerm = correctSpelling(searchTermRaw)
            allSearchTermVariations.add(spellCheckedSearchTerm)

            val synonymsOfWordJson = getSynonymsOfWord(spellCheckedSearchTerm)

            for (synonym in synonymsOfWordJson) {
                allSearchTermVariations.add(synonym)
            }

        }
        amendedSearchTerms.add(allSearchTermVariations.distinct())
    }
    return amendedSearchTerms
}