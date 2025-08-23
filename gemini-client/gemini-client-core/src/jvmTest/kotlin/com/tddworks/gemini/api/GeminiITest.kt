package com.tddworks.gemini.api

import app.cash.turbine.test
import com.tddworks.di.getInstance
import com.tddworks.gemini.api.textGeneration.api.*
import com.tddworks.gemini.di.initGemini
import java.util.*
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable
import org.koin.test.junit5.AutoCloseKoinTest

@EnabledIfEnvironmentVariable(named = "GEMINI_API_KEY", matches = ".+")
class GeminiITest : AutoCloseKoinTest() {
    fun ByteArray.toBase64() = Base64.getEncoder().encodeToString(this)

    @BeforeEach
    fun setUp() {
        initGemini(
            config =
                GeminiConfig(
                    apiKey = { System.getenv("GEMINI_API_KEY") ?: "CONFIGURE_ME" },
                    baseUrl = { System.getenv("GEMINI_BASE_URL") ?: Gemini.BASE_URL },
                )
        )
    }

    @Test
    fun `should return generateContent response with image`() = runTest {
        val gemini = getInstance<Gemini>()
        val image = javaClass.classLoader.getResource("origami.png")?.readBytes()?.toBase64()
        val response =
            gemini.generateContent(
                GenerateContentRequest(
                    contents =
                        listOf(
                            Content(
                                parts =
                                    listOf(
                                        Part.TextPart(
                                            text =
                                                "Detect items, with no more than 20 items. Output a json list where each entry contains the 2D bounding box in \"box_2d\" and a text label in \"label\"."
                                        ),
                                        Part.InlineDataPart(
                                            inlineData =
                                                Part.InlineDataPart.InlineData("image/png", image!!)
                                        ),
                                    )
                            )
                        ),
                    stream = false,
                )
            )

        println(response)

        assertNotNull(response)
    }

    @Test
    fun `should return stream generateContent response`() = runTest {
        val gemini = getInstance<Gemini>()

        gemini
            .streamGenerateContent(
                GenerateContentRequest(
                    contents = listOf(Content(parts = listOf(Part.TextPart(text = "hello")))),
                    stream = true,
                )
            )
            .test(timeout = 10.seconds) {
                assertNotNull(awaitItem())
                assertNotNull(awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
    }

    @Test
    fun `should return generateContent response`() = runTest {
        val gemini = getInstance<Gemini>()

        val response =
            gemini.generateContent(
                GenerateContentRequest(
                    contents = listOf(Content(parts = listOf(Part.TextPart(text = "hello"))))
                )
            )

        assertNotNull(response)
    }
}

// jpg
// [
//  {
//    "box_2d": {
//      "x_min": 346,
//      "y_min": 345,
//      "x_max": 500,
//      "y_max": 507
//    },
//    "name": "origami fox"
//  },
//  {
//    "box_2d": {
//      "x_min": 783,
//      "y_min": 338,
//      "x_max": 999,
//      "y_max": 472
//    },
//    "name": "origami armadillo"
//  }
// ]

/**
 * png
 * [ { "box_2d": { "x_min": 348, "y_min": 343, "x_max": 500, "y_max": 506 }, "label": "origami fox" }, { "box_2d": { "x_min": 506, "y_min": 338, "x_max": 655, "y_max": 473 }, "label": "origami armadillo" }, { "box_2d": { "x_min": 436, "y_min": 474, "x_max": 540, "y_max": 502 }, "label": "window sill" }, { "box_2d": { "x_min": 406, "y_min": 492, "x_max": 999, "y_max": 999 }, "label": "window" }, { "box_2d": { "x_min": 0, "y_min": 0, "x_max": 500, "y_max": 999 }, "label": "wall" }, { "box_2d": { "x_min": 461, "y_min": 0, "x_max": 616, "y_max": 999 }, "label": "window frame" }, { "box_2d": { "x_min": 474, "y_min": 40, "x_max": 670, "y_max": 999 }, "label": "window" }, { "box_2d": { "x_min": 627, "y_min": 645, "x_max": 999, "y_max": 999 }, "label": "house" }, { "box_2d": { "x_min": 660, "y_min": 675, "x_max": 765, "y_max": 770 }, "label": "bush" }, { "box_2d": { "x_min": 770, "y_min": 700, "x_max": 999, "y_max": 999 }, "label": "trees" }, { "box_2d": { "x_min": 640, "y_min": 800, "x_max": 999, "y_max": 999 }, "label": "ground" }, { "box_2d": { "x_min": 450, "y_min": 0, "x_max": 999, "y_max": 999 }, "label": "sky" }, { "box_2d": { "x_min": 0, "y_min": 450, "x_max": 325, "y_max": 700 }, "label": "shadow" } ]
 */

/**
 * [ { "box_2d": { "x_min": 343, "y_min": 345, "x_max": 500, "y_max": 510 }, "label": "origami fox"
 * }, { "box_2d": { "x_min": 525, "y_min": 330, "x_max": 681, "y_max": 472 }, "label": "origami
 * armadillo" }, { "box_2d": { "x_min": 412, "y_min": 435, "x_max": 630, "y_max": 523 }, "label":
 * "windowsill" }, { "box_2d": { "x_min": 460, "y_min": 495, "x_max": 999, "y_max": 999 }, "label":
 * "window" }, { "box_2d": { "x_min": 0, "y_min": 430, "x_max": 385, "y_max": 999 }, "label": "wall"
 * }, { "box_2d": { "x_min": 424, "y_min": 0, "x_max": 673, "y_max": 505 }, "label": "window frame"
 * }, { "box_2d": { "x_min": 440, "y_min": 450, "x_max": 700, "y_max": 490 }, "label": "window
 * ledge" }, { "box_2d": { "x_min": 450, "y_min": 480, "x_max": 999, "y_max": 999 }, "label":
 * "glass" }, { "box_2d": { "x_min": 660, "y_min": 660, "x_max": 850, "y_max": 800 }, "label":
 * "house" }, { "box_2d": { "x_min": 670, "y_min": 750, "x_max": 999, "y_max": 999 }, "label":
 * "yard" }, { "box_2d": { "x_min": 0, "y_min": 400, "x_max": 350, "y_max": 600 }, "label": "shadow"
 * } ]
 */

// [
//  {"box_2d": [474,360,731,624], "name": "lens"},
//  {"box_2d": [12,308,487,643], "name": "lens hood"},
//  {"box_2d": [253,308,487,643], "name": "lens hood"},
//  {"box_2d": [376,358,731,624], "name": "camera lens"},
//  {"box_2d": [539,6,634,164], "name": "clamp"},
//  {"box_2d": [500,781,595,979], "name": "clamp"},
//  {"box_2d": [421,378,653,589], "name": "lens support"},
//  {"box_2d": [725,296,885,767], "name": "camera"},
//  {"box_2d": [743,596,818,656], "name": "dial"},
//  {"box_2d": [757,690,839,754], "name": "dial"},
//  {"box_2d": [725,296,885,571], "name": "camera body"},
//  {"box_2d": [474,360,731,624], "name": "lens barrel"},
//  {"box_2d": [421,378,653,589], "name": "lens collar"},
//  {"box_2d": [725,296,885,767], "name": "camera with lens"},
//  {"box_2d": [329,43,948,1000], "name": "table"},
//  {"box_2d": [725,296,885,767], "name": "camera with lens and accessories"},
//    {"box_2d": [725,296,885,767], "name": "camera with lens and tripod mount"},
//    {"box_2d": [725,296,885,767], "name": "camera with lens and tripod"},
//    {"box_2d": [725,296,885,767], "name": "camera with lens and tripod accessories"},
//    {"box_2d": [725,296,885,767], "name": "camera with lens and tripod and accessories"}
// ]

// [
//  {"box_2d": [474,360,731,624], "name": "镜头"},
//  {"box_2d": [12,308,487,643], "name": "遮光罩"},
//  {"box_2d": [253,308,487,643], "name": "遮光罩"},
//  {"box_2d": [376,358,731,624], "name": "相机镜头"},
//  {"box_2d": [539,6,634,164], "name": "夹子"},
//  {"box_2d": [500,781,595,979], "name": "夹子"},
//  {"box_2d": [421,378,653,589], "name": "镜头支架"},
//  {"box_2d": [725,296,885,767], "name": "相机"},
//  {"box_2d": [743,596,818,656], "name": "拨盘"},
//  {"box_2d": [757,690,839,754], "name": "拨盘"},
//  {"box_2d": [725,296,885,571], "name": "相机机身"},
//  {"box_2d": [474,360,731,624], "name": "镜头筒"},
//  {"box_2d": [421,378,653,589], "name": "镜头环"},
//  {"box_2d": [725,296,885,767], "name": "带镜头的相机"},
//  {"box_2d": [329,43,948,1000], "name": "桌子"},
//  {"box_2d": [725,296,885,767], "name": "带镜头的相机和配件"},
//    {"box_2d": [725,296,885,767], "name": "带镜头的相机和三脚架底座"},
//    {"box_2d": [725,296,885,767], "name": "带镜头的相机和三脚架"},
//    {"box_2d": [725,296,885,767], "name": "带镜头的相机和三脚架配件"},
//    {"box_2d": [725,296,885,767], "name": "带镜头的相机和三脚架及配件"}
// ]
