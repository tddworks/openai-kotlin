package com.tddworks.gemini.api.textGeneration.api

import com.tddworks.di.getInstance

interface Gemini : TextGeneration {
    companion object {
        const val HOST = "generativelanguage.googleapis.com"
        const val BASE_URL = "https://$HOST"


        fun default(): Gemini {
            return object : Gemini, TextGeneration by getInstance() {}
        }
    }
}