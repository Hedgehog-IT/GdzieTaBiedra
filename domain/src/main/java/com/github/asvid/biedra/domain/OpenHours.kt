package com.github.asvid.biedra.domain

import org.joda.time.DateTimeConstants
import org.joda.time.LocalDate
import java.text.ParseException
import java.text.SimpleDateFormat
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
)

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
        when (LocalDate().dayOfWeek) {
            DateTimeConstants.SUNDAY -> this.sunday ?: this.weekDay
            DateTimeConstants.SATURDAY -> this.saturday ?: this.weekDay
            else -> this.weekDay
        }
