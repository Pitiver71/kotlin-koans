package iii_conventions

import iii_conventions.TimeInterval.DAY
import org.junit.Assert.assertEquals
import org.junit.Test

class MyDateTest {

    @Test
    fun addDayTest() {
        assertEquals("Add 1 year", MyDate(2016, 7, 14), MyDate(2015, 7, 14) + TimeInterval.YEAR)
        assertEquals("Add 1 day", MyDate(2015, 7, 15), MyDate(2015, 7, 14) + DAY)
        assertEquals("Add one day and change to next month", MyDate(2015, 8, 1), MyDate(2015, 7, 31) + DAY)
        assertEquals("Year is bisextile", MyDate(2016, 1, 29), MyDate(2016, 1, 28) + DAY)
        assertEquals("Year is not bisextile", MyDate(2015, 2, 1), MyDate(2015, 1, 28) + DAY)
        assertEquals("Happy new year!!!", MyDate(2017, 0, 1), MyDate(2016, 11, 31) + DAY)
        assertEquals("Add 5 years", MyDate(2020, 7, 14), MyDate(2015, 7, 14) + TimeInterval.YEAR * 5)
        assertEquals("Add 5 days", MyDate(2015, 7, 19), MyDate(2015, 7, 14) + DAY * 5)
        assertEquals("Add 10 days and change to next month", MyDate(2015, 8, 4), MyDate(2015, 7, 25) + DAY * 10)
    }
}