package com.model

class Principles(
        var text: String = "",
        var parentPrincipleId: String = "",
        var childPrinciplesId: MutableList<String> = mutableListOf<String>()
)
