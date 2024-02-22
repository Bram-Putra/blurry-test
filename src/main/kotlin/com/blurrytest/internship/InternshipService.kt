package com.blurrytest.internship

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class InternshipService(
    private val exclusionService: InternshipExclusionService,
    private val internshipVerifier: InternshipVerifier,
    private val repository: InternshipRepository
) {

    @Transactional
    fun verifyInternship(students: Collection<ReferenceKey>) {

        exclusionService.filterNotExcludedFromInternship(
            students
        ).takeIf { it.isNotEmpty() }?.map { verifyEligibility(it.id, it.term) }?.let { verifiedStudents ->
            val (eligibleStudents, ineligibleStudents) = verifiedStudents.partition { it.isEligible }

            eligibleStudents.takeIf { it.isNotEmpty() }?.let { repository.batchUpsert(it) }

            ineligibleStudents.takeIf { it.isNotEmpty() }?.let { repository.batchDelete(it) }
        }
    }

    private fun verifyEligibility(
        id: String, term: Term
    ): Student {
        val student = repository.findByIdAndTerm(id, term)
        return internshipVerifier.verify(student)
    }
}
