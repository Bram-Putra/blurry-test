package com.blurrytest.internship

import org.springframework.stereotype.Service

@Service
class InternshipExclusionService {

    fun filterNotExcludedFromInternship(keys: Collection<ReferenceKey>): List<ReferenceKey> {
        return keys
            .takeIf { keys.isNotEmpty() }
            ?.map { doSomething(it.toStudent()) }
            ?.filter { !it.isExcluded }
            ?.map { it.toReferenceKey() } ?: emptyList()
    }

    private fun doSomething(student: Student): Student {
        return student
    }
}
