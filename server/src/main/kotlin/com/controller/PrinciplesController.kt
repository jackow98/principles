package com.controller

import com.model.Principle
import com.persistence.addPrinciple
import com.persistence.getPrinciples
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/Principles")
class PrinciplesController {

    @CrossOrigin
    @GetMapping
    @ResponseBody
    fun find(@RequestParam searchTerm: String): Array<Principle> {
        return getPrinciples(searchTerm)
    }

    @CrossOrigin
    @PostMapping
    fun add(@RequestBody principle: Principle) {
        return addPrinciple(principle)
    }
}
