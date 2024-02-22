package com.blurrytest.internship

import org.springframework.stereotype.Repository
import java.math.BigDecimal
import java.time.LocalDate

@Repository
class InternshipRepository {

    fun batchUpsert(students: Collection<Student>): Collection<Student> = emptyList()
    fun batchDelete(students: Collection<Student>): Collection<Student> = emptyList()
    fun findByIdAndTerm(id: String, term: Term): Student = Student(
        id = id,
        term = term,
        dateOfCompletion = LocalDate.now(),
        weightedAverageMark = BigDecimal.ZERO,
        totalCredit = BigDecimal.ZERO,
        lowestActualMark = BigDecimal.ZERO,
        isEligible = false,
        isExcluded = false
    )
}
