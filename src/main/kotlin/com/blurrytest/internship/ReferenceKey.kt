package com.blurrytest.internship

import java.math.BigDecimal
import java.time.LocalDate

data class ReferenceKey(val id:String, val term: Term)

fun ReferenceKey.toStudent() = Student(
    id = id,
    term = term,
    dateOfCompletion = LocalDate.now(),
    weightedAverageMark = BigDecimal.ZERO,
    totalCredit = BigDecimal.ZERO,
    lowestActualMark = BigDecimal.ZERO,
    isEligible = false,
    isExcluded = false
)
