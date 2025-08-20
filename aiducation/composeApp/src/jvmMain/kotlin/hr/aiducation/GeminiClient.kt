package hr.aiducation

import com.google.genai.Client
import hr.aiducation.commons.CSVCommons
import hr.aiducation.data.GlobalSettings
import hr.aiducation.data.StudentRecord

object GeminiClient {

    fun generateAnalysis(studentRecords: List<StudentRecord>): String =
        if (studentRecords.isEmpty()) {
            ""
        } else {
            this.getClient().models.generateContent(
                "gemini-2.5-flash",
                this.buildStudentRequest(studentRecords),
                null
            ).text() ?: throw IllegalStateException("Response text is null")
        }

    fun testApi(): Boolean = try {
        this.getClient().models.generateContent(
            "gemini-2.5-flash",
            "Say Hello world",
            null
        ).text().isNullOrEmpty().not()
    } catch (e: Exception) {
        false
    }

    private fun getClient(): Client = Client.builder()
        .apiKey(GlobalSettings.apiKey)
        .build()

    private fun buildStudentRequest(studentRecords: List<StudentRecord>): String =
        """
    |U nastavku je dano nekoliko tjedanih podataka o studentu. Podaci sadrže količinu stresa, motivacije i
    |zadovoljstva studenta učenjem. Također su dostupni akademski rezultati i bilješke nastavnika o socijalnim
    |interakcijama, sukobima i generalnom ponašanju studenta.
    |
    |Na hrvatskom jeziku napravi vrlo kratku opisnu analizu (dvije do tri rečenice i par nutknica) koja će:
    |- analizirati stres, motivaciju i zadovoljstvo studenta
    |- prepoznati korelaciju podataka, akademskih rezultata i bilješki nastavnika
    |- dati preporuke za poboljšanje studenta
    |- dati preporuke nastavnicima kako pomoći studentu
    |
    |CSV podaci su sljedeći:
    |${CSVCommons.expectedHeaders.joinToString(",")}
    |${studentRecords.joinToString("\n") { it.toCSV() }}
    |
    |Neka odgovor bude kratak i jasan te započinje točno markdown naslovom `# Analiza studenta broj <broj>`
    """.trimMargin()
}