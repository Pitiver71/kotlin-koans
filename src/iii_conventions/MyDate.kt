package iii_conventions

import iii_conventions.TimeInterval.*

data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int) {
    fun nextDay(): MyDate = this + DAY

    operator fun compareTo(other: MyDate): Int {
        if (this.year == other.year) {
            if (this.month == other.month) {
                return this.dayOfMonth.compareTo(other.dayOfMonth)
            } else {
                return this.month.compareTo(other.month)
            }
        } else {
            return this.year.compareTo(other.year)
        }
    }

    operator fun rangeTo(other: MyDate): DateRange {
        return DateRange(this, other)
    }

    operator fun plus(ti: TimeInterval): MyDate {
        return this.plus(RepeatedTimeInterval(ti))
    }

    operator fun plus(ti: RepeatedTimeInterval): MyDate {

        fun addDays(myDate: MyDate, days: Int): MyDate {
            fun isLeapYear(year: Int): Boolean = year % 4 == 0

            fun loop(md: MyDate, toadd: Int, added: Int): MyDate {
                val remainingDays = when(md.month) {
                    0,2,4,6,7,9,11 -> 31 - md.dayOfMonth
                    1 -> if (isLeapYear(md.year)) 29 - md.dayOfMonth else 28 - md.dayOfMonth
                    else -> 30 - md.dayOfMonth
                }

                return when {
                    toadd == 0 -> md
                    toadd <= remainingDays -> md.copy(dayOfMonth = md.dayOfMonth + toadd)
                    month < 11 -> loop(md.copy(month = md.month + 1, dayOfMonth = 1), toadd - remainingDays - 1, added + remainingDays + 1)
                    else -> loop(md.copy(year = md.year + 1, month = 0, dayOfMonth = 1), toadd - remainingDays - 1, added + remainingDays + 1)
                }
            }

            return loop(myDate, days, 0)
        }

        fun addYear(n: Int): MyDate = this.copy(year = year + 1 * n)

        return when(ti.timeInterval) {
            DAY -> addDays(this, 1 * ti.times)
            WEEK -> addDays(this, 7 * ti.times)
            YEAR -> addYear(ti.times)
        }
    }
}

enum class TimeInterval {
    DAY,
    WEEK,
    YEAR;

    operator fun times(n: Int): RepeatedTimeInterval = RepeatedTimeInterval(this, n)
}

data class RepeatedTimeInterval(val timeInterval: TimeInterval, val times: Int = 1)

class DateRange(val start: MyDate, val endInclusive: MyDate) : Iterable<MyDate> {
    override fun iterator(): Iterator<MyDate> {
        fun loop(collection: Collection<MyDate>, start: MyDate, end: MyDate): Iterator<MyDate> {
            return when {
                start < end -> loop(collection.plus(start), start.nextDay(), end)
                start == end -> collection.plus(start).iterator()
                else -> collection.iterator()
            }
        }

        return loop(ArrayList(), start, endInclusive)
    }

    operator fun contains(myDate : MyDate) : Boolean {
        return myDate >= start && myDate <= endInclusive
    }
}