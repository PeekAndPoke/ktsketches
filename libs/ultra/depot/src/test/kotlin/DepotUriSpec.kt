package de.peekandpoke.ultra.depot

import io.kotlintest.shouldBe
import io.kotlintest.specs.FreeSpec

class DepotUriSpec : FreeSpec() {

    init {

        ////  PARSING  /////////////////////////////////////////////////////////////////////////////////////////////////

        "Parsing" - {
            listOf(
                "" to null,
                "repo" to null,
                "repo/bucket" to null,
                "depot://" to null,
                "depot://repo" to null,
                "depot://repo/bucket" to null,
                "depot:///bucket/file" to null,
                "depot://repo//file" to null,
                "depot://repo/bucket/" to null,
                "repo/bucket/file" to DepotUri(repo = "repo", bucket = "bucket", file = "file"),
                "depot://repo/bucket/file" to DepotUri(repo = "repo", bucket = "bucket", file = "file"),
                "depot://repo/bucket/file.jpg" to DepotUri(repo = "repo", bucket = "bucket", file = "file.jpg"),
                "depot://repo/bucket/file/a.jpg" to DepotUri(repo = "repo", bucket = "bucket", file = "file/a.jpg"),
            ).forEach { (input, expected) ->

                "Parsing '$input' must work" {
                    val result = DepotUri.parse(input)

                    result shouldBe expected
                }
            }
        }

        ////  URI  /////////////////////////////////////////////////////////////////////////////////////////////////

        "DepotUri.uri" - {
            listOf(
                DepotUri(repo = "repo", bucket = "bucket", file = "file") to "depot://repo/bucket/file",
                DepotUri(repo = "repo", bucket = "bucket", file = "file/a.jpg") to "depot://repo/bucket/file/a.jpg",
                DepotUri(repo = "/repo/", bucket = "/bucket/", file = "/file/a.jpg/") to "depot://repo/bucket/file/a.jpg",
            ).forEach { (input, expected) ->

                "Uri of '$input' must be correct" {
                    val result = input.uri

                    result shouldBe expected
                }
            }
        }
    }
}
