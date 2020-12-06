package com.github.asvid.biedra.domain

import java.text.DateFormatSymbols
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.time.LocalDate
import java.util.*

/**
 * [Shop] open hours, contains [TimeRange] for weekdays, saturday and sunday
 *
 * @property weekDay opening hours for monday - friday
 * @property saturday opening hours
 * @property sunday opening hours
 * */
data class OpenHours(
        val weekDay: TimeRange?,
        val saturday: TimeRange?,
        val sunday: TimeRange?
) {
    fun prettyPrint(): String {
        val symbols = DateFormatSymbols(Locale.getDefault()).shortWeekdays
        return if (this.sunday == null) {
            """
                ${symbols[2]}-${symbols[6]} : ${this.weekDay}
                ${symbols[7]} : ${this.saturday}
            """.trimIndent()
        } else {
            """
                ${symbols[2]}-${symbols[6]} : ${this.weekDay}
                ${symbols[7]} : ${this.saturday}
                ${symbols[1]} : ${this.sunday}
            """.trimIndent()
        }
    }
}

/**
 * DLS method for [OpenHours]
 * */
fun openHours(block: OpenHoursBuilder.() -> Unit): OpenHours = OpenHoursBuilder().apply(
        block).build()

/**
 * DSL builder for [OpenHours]
 *
 * @property weekDay
 * @property saturday
 * @property sunday
 * */
@ShopDsl
class OpenHoursBuilder {
    var weekDay: String? = null
    var saturday: String? = null
    var sunday: String? = null

    fun build(): OpenHours = OpenHours(weekDay?.toOpenHours(), saturday?.toOpenHours(),
            sunday?.toOpenHours())
}

/**
 * @receiver [String] with range of hours in format: <hh.mm> - <hh.mm>
 *
 * @return [TimeRange] created from [String] range
 * */
fun String.toOpenHours(): TimeRange? {

    val splitted = this.split("-".toRegex())

    if (splitted.size != 2) {
        println("Wrong opening hours format: $this")
        return null
    }
    var startDate = splitted[0].toDate("hh:mm")
    var endDate = splitted[1].toDate("hh:mm")

    if (startDate == null) {
        startDate = splitted[0].toDate("hh.mm")
    }
    if (endDate == null) {
        endDate = splitted[1].toDate("hh.mm")
    }
    if (startDate == null || endDate == null) {
        println("couldn't parse start or end dates of: $this")
        return null
    }

    return TimeRange(startDate, endDate)
}

/**
 * @receiver [String] with date in any possible to process format
 *
 * @param format of date in receiver [String]
 *
 * @return [Date] from parsed [String] with provided [format]
 * */
fun String.toDate(format: String): Date? {
    return try {
        val formatter = SimpleDateFormat(format)
        formatter.parse(this.trim().removePrefix("0"))
    } catch (e: ParseException) {
        println("error parsing time: $this")
        null
    }
}

fun OpenHours.getForToday(): TimeRange? =
        when (LocalDate.now().dayOfWeek) {
            DayOfWeek.SATURDAY -> this.saturday ?: this.weekDay
            DayOfWeek.SUNDAY -> this.sunday ?: this.weekDay
            else -> this.weekDay
        }
