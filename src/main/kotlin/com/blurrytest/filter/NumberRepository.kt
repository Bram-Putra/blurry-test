package com.blurrytest.filter

import org.springframework.stereotype.Repository

@Repository
class NumberRepository {
    fun batchUpsert(students: Collection<Int>): Collection<Int> = emptyList()
}