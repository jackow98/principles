package com.controller

import com.model.Principles
import com.persistence.getPrinciples
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/Principles")
class PrinciplesController() {
    @CrossOrigin
    @GetMapping
    @ResponseBody
    fun find(@RequestParam searchTerm: String): MutableList<List<String>> {
        return getPrinciples(searchTerm)
    }
}
