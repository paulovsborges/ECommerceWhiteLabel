package com.pvsb.core

import com.pvsb.core.utils.formatDate
import org.junit.Assert
import org.junit.Test

class UtilsTest {

    @Test
    fun `when the date format is success`() {
        val from = "Fri Jul 22 08:25:05 GMT-03:00 2022"
        val expected = "22/07/2022"
        Assert.assertEquals(expected, from.formatDate())
    }
}
