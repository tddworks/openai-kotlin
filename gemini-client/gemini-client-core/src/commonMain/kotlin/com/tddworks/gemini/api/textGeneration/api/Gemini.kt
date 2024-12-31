package com.tddworks.gemini.api.textGeneration.api

interface Gemini : TextGeneration {
    companion object {
        const val HOST = "generativelanguage.googleapis.com"
        const val BASE_URL = "https://$HOST"
    }
}