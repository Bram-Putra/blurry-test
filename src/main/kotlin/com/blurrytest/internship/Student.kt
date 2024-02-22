package com.blurrytest.internship

import java.math.BigDecimal
import java.time.LocalDate

data class Student (
    val id: String,
    val term: Term,
    var dateOfCompletion: LocalDate,
    var weightedAverageMark: BigDecimal,
    var totalCredit: BigDecimal,
    var lowestActualMark: BigDecimal,
    var isEligible : Boolean = true,
    var isExcluded : Boolean = false
)

fun Student.toReferenceKey() = ReferenceKey(
    id = id,
    term = term
)
