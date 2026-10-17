package com.example.ethiopiancalendar

import java.time.LocalDate

/** A date in the Ethiopian calendar. month is 1..13 (13 = Pagume). */
data class EthiopianDate(val year: Int, val month: Int, val day: Int)

/**
 * Conversion between the Gregorian and Ethiopian calendars, using Julian Day Numbers (JDN).
 * These formulas were checked against known dates and against every day from 1950 to 2099.
 */
object EthiopianCalendar {

    private const val JDN_OFFSET = 1723856
    private const val UNIX_EPOCH_JDN = 2440588

    val monthNames = listOf(
        "Meskerem", "Tikimt", "Hidar", "Tahsas", "Tir", "Yekatit", "Megabit",
        "Miazia", "Ginbot", "Sene", "Hamle", "Nehase", "Pagume"
    )

    val monthNamesAmharic = listOf(
        "መስከረም", "ጥቅምት", "ኅዳር", "ታኅሣሥ", "ጥር", "የካቲት", "መጋቢት",
        "ሚያዝያ", "ግንቦት", "ሰኔ", "ሐምሌ", "ነሐሴ", "ጳጉሜን"
    )

    /** Index 0 = Sunday. */
    val weekdayNames = listOf("Ehud", "Segno", "Maksegno", "Rebu", "Hamus", "Arb", "Qidame")

    val weekdayNamesAmharic = listOf("እሑድ", "ሰኞ", "ማክሰኞ", "ረቡዕ", "ሐሙስ", "አርብ", "ቅዳሜ")

    val weekdayShortAmharic = listOf("እሑድ", "ሰኞ", "ማክሰ", "ረቡዕ", "ሐሙስ", "አርብ", "ቅዳሜ")

    val weekdayShortEnglish = listOf("Ehu", "Seg", "Mak", "Reb", "Ham", "Arb", "Qid")

    /** Every 4th year is a leap year (year 3, 7, 11 ...): Pagume then has 6 days. */
    fun isLeapYear(year: Int): Boolean = Math.floorMod(year, 4) == 3

    fun daysInMonth(year: Int, month: Int): Int = when {
        month in 1..12 -> 30
        isLeapYear(year) -> 6
        else -> 5
    }

    fun toJdn(year: Int, month: Int, day: Int): Int =
        JDN_OFFSET + 365 + 365 * (year - 1) + Math.floorDiv(year, 4) + 30 * month + day - 31

    fun toJdn(date: EthiopianDate): Int = toJdn(date.year, date.month, date.day)

    fun fromJdn(jdn: Int): EthiopianDate {
        val shifted = jdn - JDN_OFFSET
        val r = Math.floorMod(shifted, 1461)
        val n = r % 365 + 365 * (r / 1460)
        val year = 4 * Math.floorDiv(shifted, 1461) + r / 365 - r / 1460
        return EthiopianDate(year, n / 30 + 1, n % 30 + 1)
    }

    fun jdnOf(date: LocalDate): Int = (date.toEpochDay() + UNIX_EPOCH_JDN).toInt()

    fun toLocalDate(jdn: Int): LocalDate = LocalDate.ofEpochDay((jdn - UNIX_EPOCH_JDN).toLong())

    fun fromLocalDate(date: LocalDate): EthiopianDate = fromJdn(jdnOf(date))

    fun today(): EthiopianDate = fromLocalDate(LocalDate.now())

    /** 0 = Sunday ... 6 = Saturday. */
    fun weekdayIndex(jdn: Int): Int = Math.floorMod(jdn + 1, 7)
}
