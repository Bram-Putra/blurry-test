package com.blurrytest.filter

class MathOperations {
    fun filterNumbers(numbers: Collection<Int>) =
        numbers.filter { it % 2 != 0 }
}
