package com.cheolwoo.kotlincalculator

import org.junit.Assert.assertEquals
import org.junit.Test

class CalculatorTest {
    @Test
    fun `입력한 숫자와 소수를 표시한다`() {
        val calculator = Calculator()

        calculator.inputDigit(1)
        calculator.inputDigit(2)
        calculator.inputDecimal()
        calculator.inputDecimal()
        calculator.inputDigit(5)

        assertEquals("12.5", calculator.display)
    }

    @Test
    fun `사칙연산 결과를 계산한다`() {
        assertCalculation("7", Operation.ADD, "5", "12")
        assertCalculation("7", Operation.SUBTRACT, "5", "2")
        assertCalculation("7", Operation.MULTIPLY, "5", "35")
        assertCalculation("7", Operation.DIVIDE, "2", "3.5")
    }

    @Test
    fun `소수 계산에서 이진 부동소수점 오차가 노출되지 않는다`() {
        val calculator = Calculator()
        enter(calculator, "0.1")
        calculator.selectOperation(Operation.ADD)
        enter(calculator, "0.2")
        calculator.calculate()

        assertEquals("0.3", calculator.display)
    }

    @Test
    fun `연속 연산은 앞선 결과를 사용한다`() {
        val calculator = Calculator()
        enter(calculator, "2")
        calculator.selectOperation(Operation.ADD)
        enter(calculator, "3")
        calculator.selectOperation(Operation.MULTIPLY)
        enter(calculator, "4")
        calculator.calculate()

        assertEquals("20", calculator.display)
    }

    @Test
    fun `0으로 나누면 오류를 표시하고 다음 숫자 입력으로 복구한다`() {
        val calculator = Calculator()
        enter(calculator, "5")
        calculator.selectOperation(Operation.DIVIDE)
        calculator.inputDigit(0)
        calculator.calculate()

        assertEquals(Calculator.ERROR, calculator.display)

        calculator.inputDigit(7)

        assertEquals("7", calculator.display)
    }

    @Test
    fun `부호와 퍼센트를 변경한다`() {
        val calculator = Calculator()
        enter(calculator, "25")

        calculator.toggleSign()
        assertEquals("-25", calculator.display)

        calculator.percent()
        assertEquals("-0.25", calculator.display)
    }

    @Test
    fun `초기화하면 진행 중인 연산을 모두 지운다`() {
        val calculator = Calculator()
        enter(calculator, "9")
        calculator.selectOperation(Operation.ADD)
        calculator.clear()
        calculator.inputDigit(1)
        calculator.calculate()

        assertEquals("1", calculator.display)
    }

    @Test
    fun `저장한 상태를 복원한다`() {
        val calculator = Calculator()
        enter(calculator, "12")
        calculator.selectOperation(Operation.ADD)
        val state = calculator.snapshot()

        val restored = Calculator.restore(
            state.display,
            state.storedValue,
            state.pendingOperation,
            state.replaceDisplay,
        )
        enter(restored, "8")
        restored.calculate()

        assertEquals("20", restored.display)
    }

    private fun assertCalculation(
        left: String,
        operation: Operation,
        right: String,
        expected: String,
    ) {
        val calculator = Calculator()
        enter(calculator, left)
        calculator.selectOperation(operation)
        enter(calculator, right)
        calculator.calculate()
        assertEquals(expected, calculator.display)
    }

    private fun enter(calculator: Calculator, value: String) {
        value.forEach {
            when (it) {
                '.' -> calculator.inputDecimal()
                else -> calculator.inputDigit(it.digitToInt())
            }
        }
    }
}
