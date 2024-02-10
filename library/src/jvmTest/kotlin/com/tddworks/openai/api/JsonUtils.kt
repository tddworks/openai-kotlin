package com.tddworks.openai.api

import kotlinx.serialization.json.Json

val prettyJson = Json { // this returns the JsonBuilder
    prettyPrint = true
    // optional: specify indent
    prettyPrintIndent = "  "
}