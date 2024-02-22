package com.blurrytest.filter

import com.blurrytest.filter.MathOperations
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class MathOperationsTest {

    @Test
    fun blurry() {
        val actual = MathOperations().filterNumbers(listOf(
            1, 2, 3, 4, 5, 6, 7, 8
        ))

        assertThat(actual).isNotNull()
    }

    @Test
    fun precise() {
        val actual = MathOperations().filterNumbers(listOf(
            1, 2, 3, 4, 5, 6, 7, 8
        ))

        assertThat(actual).isEqualTo(listOf(1, 3, 5, 7))
    }
}
