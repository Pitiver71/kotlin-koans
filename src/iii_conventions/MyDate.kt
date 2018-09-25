package iii_conventions

data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int) {
    fun nextDay(): MyDate {
        val daysInFebruary = when {
            year % 4 == 0 -> 29
            else -> 28
        }

        val newDay = when(month) {
            0,2,4,6,7,9,11 -> if (dayOfMonth < 31) dayOfMonth + 1 else 1
            1 -> if (dayOfMonth < daysInFebruary) dayOfMonth + 1 else 1
            else -> if (dayOfMonth < 30) dayOfMonth + 1 else 1
        }

        val newMonth = when(newDay) {
            1 -> when(month) {
                in 0..10 -> month + 1
                else -> 0
            }
            else -> month
        }
        val newYear = when(newMonth) {
            0 -> year + 1
            else -> year
        }

        return MyDate(newYear, newMonth, newDay)
    }
}

operator fun MyDate.compareTo(other: MyDate): Int {
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

operator fun MyDate.rangeTo(other: MyDate): DateRange {
    return DateRange(this, other)
}

enum class TimeInterval {
    DAY,
    WEEK,
    YEAR
}

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

}

operator fun DateRange.contains(myDate : MyDate) : Boolean {
    return myDate >= start && myDate <= endInclusive
}
