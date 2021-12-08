package util

import java.io.File
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class Utils {
    companion object {
        // get file from classpath, resources folder
        private fun getFileFromResources(fileName: String): File {
            val classLoader = javaClass.classLoader
            val resource = classLoader.getResource(fileName)
            return if (resource == null) {
                throw IllegalArgumentException("file $fileName is not found!")
            } else {
                File(resource.file)
            }
        }

        fun readInputByLine(from: String): List<String> {
            return if (from.contains("http")) {
                fromURL(from)
            } else {
                fromFile(from)
            }
        }

        private fun fromURL(urlString: String): List<String> {
            val url = URL(urlString)
            val conn: HttpsURLConnection = url.openConnection() as HttpsURLConnection
            val bufferedReader = conn.inputStream.bufferedReader()
            return bufferedReader.readLines()
        }

        private fun fromFile(fileName: String): List<String> =
                getFileFromResources(fileName).bufferedReader().readLines()
    }

}