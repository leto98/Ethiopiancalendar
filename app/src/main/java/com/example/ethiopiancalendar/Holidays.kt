package com.example.ethiopiancalendar

import java.time.LocalDate

/**
 * A short list of major public holidays.
 *
 * Not included: the Islamic holidays (Eid al-Fitr, Eid al-Adha, Mawlid), because they follow
 * the moon and need a separate calculation. Holidays set by government announcement may also
 * differ from this list in a given year.
 */
object Holidays {

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

    fun forDate(eth: EthiopianDate, greg: LocalDate): List<String> {
        val result = mutableListOf<String>()
        val m = greg.monthValue
        val d = greg.dayOfMonth

        if (eth.month == 1 && eth.day == 1) result += "Enkutatash (Ethiopian New Year)"
        if (eth.month == 1 && eth.day == 17) result += "Meskel (Finding of the True Cross)"
        if (m == 1 && d == 7) result += "Genna (Ethiopian Christmas)"
        if (m == 1 && d == 19) result += "Timket (Epiphany)"
        if (m == 3 && d == 2) result += "Adwa Victory Day"
        if (m == 5 && d == 1) result += "International Labour Day"
        if (m == 5 && d == 5) result += "Patriots' Victory Day"
        if (m == 5 && d == 28) result += "Downfall of the Derg"

        val easter = orthodoxEaster(greg.year)
        if (greg == easter.minusDays(2)) result += "Siklet (Good Friday)"
        if (greg == easter) result += "Fasika (Ethiopian Easter)"

        return result
    }
}
