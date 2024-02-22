package com.blurrytest.internship

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mockito
import org.mockito.Mockito.any
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.capture
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import java.math.BigDecimal
import java.time.LocalDate

@ExtendWith(MockitoExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class InternshipServiceTest {

    private val exclusionService: InternshipExclusionService = mock()
    private val internshipVerifier: InternshipVerifier = mock()
    private val repository: InternshipRepository = mock()
    private val internshipService = InternshipService(exclusionService, internshipVerifier, repository)

    @Captor
    private lateinit var eligibleStudents: ArgumentCaptor<Collection<Student>>

    @Captor
    private lateinit var ineligibleStudents: ArgumentCaptor<Collection<Student>>

    private val reference1 = ReferenceKey("001", Term.SUMMER)
    private val reference2 = ReferenceKey("002", Term.SUMMER)
    private val reference3 = ReferenceKey("003", Term.SUMMER)
    private val reference4 = ReferenceKey("004", Term.SUMMER)
    private val reference5 = ReferenceKey("005", Term.SUMMER)
    private val reference6 = ReferenceKey("006", Term.SUMMER)
    private val reference7 = ReferenceKey("007", Term.SUMMER)

    private val references = listOf(
        reference1, reference2, reference3, reference4, reference5, reference6, reference7
    )

    private val student1 = Student(
        id = reference1.id,
        term = reference1.term,
        dateOfCompletion = LocalDate.of(2023, 8, 1),
        weightedAverageMark = BigDecimal("78"),
        totalCredit = BigDecimal("25"),
        lowestActualMark = BigDecimal("75"),
        isEligible = false,
        isExcluded = false
    )

    private val student2 = Student(
        id = reference2.id,
        term = reference2.term,
        dateOfCompletion = LocalDate.of(2023, 9, 1),
        weightedAverageMark = BigDecimal("78"),
        totalCredit = BigDecimal("25"),
        lowestActualMark = BigDecimal("75"),
        isEligible = false,
        isExcluded = false
    )

    private val student4 = Student(
        id = reference4.id,
        term = reference4.term,
        dateOfCompletion = LocalDate.of(2023, 8, 1),
        weightedAverageMark = BigDecimal("72"),
        totalCredit = BigDecimal("25"),
        lowestActualMark = BigDecimal("70"),
        isEligible = false,
        isExcluded = false
    )

    private val student7 = Student(
        id = reference7.id,
        term = reference7.term,
        dateOfCompletion = LocalDate.of(2023, 8, 21),
        weightedAverageMark = BigDecimal("77.5"),
        totalCredit = BigDecimal("25"),
        lowestActualMark = BigDecimal("75"),
        isEligible = false,
        isExcluded = false
    )

    @BeforeEach
    fun setup() {
        Mockito.`when`(exclusionService.filterNotExcludedFromInternship(references))
            .thenReturn(listOf(reference1, reference2, reference4, reference7))

        Mockito.`when`(repository.findByIdAndTerm(reference1.id, reference1.term)).thenReturn(student1)

        Mockito.`when`(repository.findByIdAndTerm(reference2.id, reference2.term)).thenReturn(student2)

        Mockito.`when`(repository.findByIdAndTerm(reference4.id, reference4.term)).thenReturn(student4)

        Mockito.`when`(repository.findByIdAndTerm(reference7.id, reference7.term)).thenReturn(student7)

        Mockito.`when`(internshipVerifier.verify(student1)).thenReturn(student1.copy(isEligible = true))

        Mockito.`when`(internshipVerifier.verify(student2)).thenReturn(student2)

        Mockito.`when`(internshipVerifier.verify(student4)).thenReturn(student4)

        Mockito.`when`(internshipVerifier.verify(student7)).thenReturn(student7.copy(isEligible = true))
    }

    @Test
    fun blurry() {

        internshipService.verifyInternship(references)

        verify(repository, times(1)).batchUpsert(listOf(any()))
        verify(repository, times(1)).batchDelete(listOf(any()))
    }

    @Test
    fun precise() {

        internshipService.verifyInternship(references)

        verify(repository).batchUpsert(capture(eligibleStudents))
        verify(repository).batchDelete(capture(ineligibleStudents))

        assertThat(eligibleStudents.value.size).isEqualTo(2)
        assertThat(eligibleStudents.value.map { it.id }).containsOnly("001", "007")
        eligibleStudents.value.forEach { eligibleStudent ->
            assertThat(eligibleStudent.isEligible).isTrue()
        }

        assertThat(ineligibleStudents.value.size).isEqualTo(2)
        assertThat(ineligibleStudents.value.map { it.id }).containsOnly("002", "004")
        ineligibleStudents.value.forEach { ineligibleStudent ->
            assertThat(ineligibleStudent.isEligible).isFalse()
        }
    }
}
