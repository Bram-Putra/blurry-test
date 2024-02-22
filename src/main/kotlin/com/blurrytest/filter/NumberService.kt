package com.blurrytest.filter

import com.blurrytest.filter.MathOperations
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class NumberService(
    private val repository: NumberRepository
) {

    @Transactional
    fun storeOddNumbers(numbers: Collection<Int>) {
        val oddNumbers = MathOperations().filterNumbers(numbers)

        repository.batchUpsert(oddNumbers)
    }
}
