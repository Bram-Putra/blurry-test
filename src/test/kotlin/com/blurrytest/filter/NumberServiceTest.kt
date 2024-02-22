package com.blurrytest.filter

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mockito.any
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.capture
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

@ExtendWith(MockitoExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class NumberServiceTest {

    private val repository: NumberRepository = mock()
    private val numberService = NumberService(repository)

    @Captor
    private lateinit var oddNumbers: ArgumentCaptor<Collection<Int>>

    @Test
    fun blurry() {
        val numbers = listOf(1, 2, 3, 4, 5, 6, 7, 8)

        numberService.storeOddNumbers(numbers)

        verify(repository, times(1)).batchUpsert(listOf(any()))
    }

    @Test
    fun precise() {
        val numbers = listOf(1, 2, 3, 4, 5, 6, 7, 8)

        numberService.storeOddNumbers(numbers)

        verify(repository, times(1)).batchUpsert(capture(oddNumbers))
        assertThat(oddNumbers.value).isEqualTo(listOf(1, 3, 5, 7))
    }
}
