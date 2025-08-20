package hr.aiducation.commons

import hr.aiducation.data.StudentRecord
import java.io.File
import java.text.Normalizer

object CSVCommons {
    val expectedHeaders = listOf(
        "Tjedan",
        "Učenik_ID",
        "Stres",
        "Motivacija",
        "Zadovoljstvo",
        "Ocjena_test",
        "Ocjena_zadaća",
        "Bilješka_nastavnika"
    ).map(::normalizeHeader)

    fun validateCsvFile(path: String): Pair<Boolean, String?> = try {
        val firstLine = File(path).useLines { it.firstOrNull() }
        if (firstLine == null) {
            false to "CSV datoteka je prazna ili nečitljiva."
        } else {
            val headers = firstLine.split(",").map { normalizeHeader(it) }
            if (headers == expectedHeaders) {
                true to null
            } else {
                false to """
                        ❌ CSV zaglavlje nije ispravno!
                        Očekivano: ${expectedHeaders.joinToString(", ")}
                        Pronađeno: ${headers.joinToString(", ")}
                    """.trimIndent()
            }
        }
    } catch (e: Exception) {
        false to "Greška pri čitanju datoteke: ${e.message}"
    }

    fun readAndGroupByStudent(csvFilePath: String): Map<Int, List<StudentRecord>> {
        val file = File(csvFilePath)
        val records = file.readLines()
            .drop(1) // skip header
            .map { line ->
                val tokens = line.split(",")
                StudentRecord(
                    tjedan = tokens[0].toInt(),
                    ucenikId = tokens[1].toInt(),
                    stres = tokens[2].toInt(),
                    motivacija = tokens[3].toInt(),
                    zadovoljstvo = tokens[4].toInt(),
                    ocjenaTest = tokens[5].toDouble(),
                    ocjenaZadaca = tokens[6].toDouble(),
                    biljeska = tokens[7]
                )
            }

        return records.groupBy { it.ucenikId }
    }

    private fun normalizeHeader(s: String): String =
        Normalizer.normalize(s.trim().removePrefix("\uFEFF"), Normalizer.Form.NFC)
}