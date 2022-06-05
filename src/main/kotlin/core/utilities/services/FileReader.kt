package core.utilities.services

import java.io.File

class FileReader {
    companion object {
        fun readFileAsMap(fileName: String): Map<String, String> {
            val map: MutableMap<String, String> = mutableMapOf()
            val readLines = File(fileName).forEachLine {
                val split = it.split("=")
                println("FileReader::split:$split")
                map[split[0]] = split[1]
            }
            println("FileReader::map:$map")

            return map
        }
    }
}