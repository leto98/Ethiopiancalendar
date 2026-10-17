package com.example.ethiopiancalendar

import java.time.LocalDate

/** A public holiday with its name in both languages. */
data class Holiday(val english: String, val amharic: String) {
    fun name(lang: Language): String = if (lang == Language.ENGLISH) english else amharic
}

/**
 * A short list of major public holidays.
 *
 * Not included: the Islamic holidays (Eid al-Fitr, Eid al-Adha, Mawlid), because they follow
 * the moon and need a separate calculation. Holidays set by government announcement may also
 * differ from this list in a given year.
 */
object Holidays {

    private val enkutatash = Holiday("Enkutatash (Ethiopian New Year)", "እንቁጣጣሽ (አዲስ ዓመት)")
    private val meskel = Holiday("Meskel (Finding of the True Cross)", "መስቀል")
    private val genna = Holiday("Genna (Ethiopian Christmas)", "ገና")
    private val timket = Holiday("Timket (Epiphany)", "ጥምቀት")
    private val adwa = Holiday("Adwa Victory Day", "የዓድዋ ድል በዓል")
    private val labourDay = Holiday("International Labour Day", "የሠራተኞች ቀን")
    private val patriots = Holiday("Patriots' Victory Day", "የአርበኞች የድል ቀን")
    private val derg = Holiday("Downfall of the Derg", "ደርግ የወደቀበት ቀን")
    private val siklet = Holiday("Siklet (Good Friday)", "ስቅለት")
    private val fasika = Holiday("Fasika (Ethiopian Easter)", "ፋሲካ")

    /** Orthodox Easter (Fasika) as a Gregorian date. Valid for the years 1900 to 2099. */
    fun orthodoxEaster(year: Int): LocalDate {
        val a = year % 4
        val b = year % 7
        val c = year % 19
        val d = (19 * c + 15) % 30
        val e = (2 * a + 4 * b - d + 34) % 7
        val t = d + e + 114
        // Julian-calendar month/day, then +13 days to reach the Gregorian date.
        return LocalDate.of(year, t / 31, t % 31 + 1).plusDays(13)
    }

    fun forDate(eth: EthiopianDate, greg: LocalDate): List<Holiday> {
        val result = mutableListOf<Holiday>()
        val m = greg.monthValue
        val d = greg.dayOfMonth

        if (eth.month == 1 && eth.day == 1) result += enkutatash
        if (eth.month == 1 && eth.day == 17) result += meskel
        if (m == 1 && d == 7) result += genna
        if (m == 1 && d == 19) result += timket
        if (m == 3 && d == 2) result += adwa
        if (m == 5 && d == 1) result += labourDay
        if (m == 5 && d == 5) result += patriots
        if (m == 5 && d == 28) result += derg

        val easter = orthodoxEaster(greg.year)
        if (greg == easter.minusDays(2)) result += siklet
        if (greg == easter) result += fasika

        return result
    }
}
