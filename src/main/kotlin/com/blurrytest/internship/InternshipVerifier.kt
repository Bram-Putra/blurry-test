package com.blurrytest.internship

import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class InternshipVerifier {
    fun verify(student: Student): Student {
        student.isEligible = false

        if (student.weightedAverageMark > BigDecimal("75")) {
            if (student.term == Term.SUMMER && student.dateOfCompletion.monthValue < 9) {
                student.isEligible = true
            } else if (student.term == Term.WINTER && student.dateOfCompletion.monthValue < 3) {
                student.isEligible = true
            }
        }

        return student
    }
}
