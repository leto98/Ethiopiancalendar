package com.example.ethiopiancalendar

import java.time.LocalDate

enum class Language { AMHARIC, ENGLISH }

/** All words shown in the app, in both languages. */
object Labels {

    // Ethiopian months. In English mode these are the usual spellings of the names.
    private val ethiopianMonthsAmharic = listOf(
        "መስከረም", "ጥቅምት", "ኅዳር", "ታኅሣሥ", "ጥር", "የካቲት", "መጋቢት",
        "ሚያዝያ", "ግንቦት", "ሰኔ", "ሐምሌ", "ነሐሴ", "ጳጉሜን"
    )

    private val ethiopianMonthsEnglish = listOf(
        "Meskerem", "Tikimt", "Hidar", "Tahsas", "Tir", "Yekatit", "Megabit",
        "Miazia", "Ginbot", "Sene", "Hamle", "Nehase", "Pagume"
    )

    // Index 0 = Sunday.
    private val weekdaysAmharic = listOf("እሑድ", "ሰኞ", "ማክሰኞ", "ረቡዕ", "ሐሙስ", "አርብ", "ቅዳሜ")
    private val weekdaysAmharicShort = listOf("እሑድ", "ሰኞ", "ማክሰ", "ረቡዕ", "ሐሙስ", "አርብ", "ቅዳሜ")
    private val weekdaysEnglish = listOf(
        "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"
    )
    private val weekdaysEnglishShort = listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")

    private val gregorianMonthsAmharic = listOf(
        "ጃንዋሪ", "ፌብሩዋሪ", "ማርች", "ኤፕሪል", "ሜይ", "ጁን",
        "ጁላይ", "ኦገስት", "ሴፕቴምበር", "ኦክቶበር", "ኖቬምበር", "ዲሴምበር"
    )

    private val gregorianMonthsEnglish = listOf(
        "January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December"
    )

    private fun isEnglish(lang: Language) = lang == Language.ENGLISH

    /** month is 1..13 */
    fun ethiopianMonth(lang: Language, month: Int): String =
        if (isEnglish(lang)) ethiopianMonthsEnglish[month - 1] else ethiopianMonthsAmharic[month - 1]

    /** index 0 = Sunday */
    fun weekday(lang: Language, index: Int): String =
        if (isEnglish(lang)) weekdaysEnglish[index] else weekdaysAmharic[index]

    fun weekdayShort(lang: Language, index: Int): String =
        if (isEnglish(lang)) weekdaysEnglishShort[index] else weekdaysAmharicShort[index]

    /** month is 1..12 */
    private fun gregorianMonth(lang: Language, month: Int): String =
        if (isEnglish(lang)) gregorianMonthsEnglish[month - 1] else gregorianMonthsAmharic[month - 1]

    private fun gregorianMonthShort(lang: Language, month: Int): String =
        if (isEnglish(lang)) gregorianMonthsEnglish[month - 1].take(3) else gregorianMonthsAmharic[month - 1]

    fun todayButton(lang: Language): String = if (isEnglish(lang)) "Today" else "ዛሬ"

    fun gregorianTitle(lang: Language): String = if (isEnglish(lang)) "Gregorian" else "ግሪጎሪያን"

    /** The text on the language button: it names the language you will switch TO. */
    fun languageButton(lang: Language): String = if (isEnglish(lang)) "አማርኛ" else "In English"

    /** For example "Tikimt 7, 2019" with the weekday in front. */
    fun ethiopianLong(lang: Language, date: EthiopianDate, weekdayIndex: Int): String {
        val weekday = weekday(lang, weekdayIndex)
        val month = ethiopianMonth(lang, date.month)
        return if (isEnglish(lang)) {
            "$weekday, $month ${date.day}, ${date.year}"
        } else {
            "${weekday}፣ ${month} ${date.day}፣ ${date.year}"
        }
    }

    fun gregorianLong(lang: Language, date: LocalDate): String {
        val weekday = weekday(lang, EthiopianCalendar.weekdayIndex(EthiopianCalendar.jdnOf(date)))
        val month = gregorianMonth(lang, date.monthValue)
        return if (isEnglish(lang)) {
            "$weekday, $month ${date.dayOfMonth}, ${date.year}"
        } else {
            "${weekday}፣ ${month} ${date.dayOfMonth}፣ ${date.year}"
        }
    }

    /** For example "Sep 11 – Oct 10, 2026". */
    fun gregorianRange(lang: Language, first: LocalDate, last: LocalDate): String {
        fun short(d: LocalDate) = "${gregorianMonthShort(lang, d.monthValue)} ${d.dayOfMonth}"
        fun full(d: LocalDate) = "${short(d)}, ${d.year}"
        return if (first.year == last.year) {
            "${short(first)} – ${full(last)}"
        } else {
            "${full(first)} – ${full(last)}"
        }
    }
}
