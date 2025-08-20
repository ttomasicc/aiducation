package hr.aiducation.data

data class StudentRecord(
    val tjedan: Int,
    val ucenikId: Int,
    val stres: Int,
    val motivacija: Int,
    val zadovoljstvo: Int,
    val ocjenaTest: Double,
    val ocjenaZadaca: Double,
    val biljeska: String
) {
    fun toCSV(): String = "$tjedan,$ucenikId,$stres,$motivacija,$zadovoljstvo,$ocjenaTest,$ocjenaZadaca,$biljeska"
}