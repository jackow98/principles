# Principles project
A system to represent my values represented as principles

Based on [Principles](https://www.notion.so/Principles-4356911896ae48a28e7e08c921a45488) , [Weaknesses](https://www.notion.so/Weaknesses-8ad370d2d3f441caa4ef2a5294299407) and [Strengths](https://www.notion.so/Strengths-61d58cb8dc224e7abd224be62bf87ca4)  with a search engine

# Aims
- [x] Storage of learning materials in linked data format
- [x] Search feature that allows users to search by key words
- [ ] Individual URL's for each search result

# Extensions
- Import Amazon kindle notes to be searchable
- Import markdown notes in standard format

# Technologies
- React
- Tailwind
- Springboot
- [Sparql pattern matching](http://vos.openlinksw.com/owiki/wiki/VOS/VirtTipsAndTricksSPARQLPatternMatchFeature)
- [Big Data search](https://blazegraph.com/database/apidocs/com/bigdata/rdf/store/BDS.html)
- [Query expansion](https://queryunderstanding.com/query-expansion-2d68d47cf9c8)
- [LARQ](http://jena.apache.org/documentation/larq/index.html)

- Search
    - Spelling correction - [hunspell](https://github.com/hunspell/hunspell)
    - Canonicalization - [Porter Stemming Algorithm](https://tartarus.org/martin/PorterStemmer/) ([NLT](http://www.nltk.org/), [Stanford](https://nlp.stanford.edu/software/), [snowball](https://snowballstem.org/)) [Wordnet](https://wordnet.princeton.edu/)