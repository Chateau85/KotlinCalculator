package com.cheolwoo.kotlincalculator

import java.math.BigDecimal
import java.math.MathContext

enum class Operation {
    ADD,
    SUBTRACT,
    MULTIPLY,
    DIVIDE,
}

data class CalculatorState(
    val display: String,
    val storedValue: String?,
    val pendingOperation: String?,
    val replaceDisplay: Boolean,
)

class Calculator private constructor(
    initialDisplay: String,
    private var storedValue: BigDecimal?,
    private var pendingOperation: Operation?,
    private var replaceDisplay: Boolean,
) {
    constructor() : this("0", null, null, true)

    var display: String = initialDisplay.ifBlank { "0" }
        private set

    fun inputDigit(digit: Int) {
        require(digit in 0..9) { "digit must be between 0 and 9" }
        display = when {
            replaceDisplay || display == ERROR -> digit.toString()
            display == "0" -> digit.toString()
            display == "-0" -> "-$digit"
            else -> display + digit
        }
        replaceDisplay = false
    }

    fun inputDecimal() {
        if (replaceDisplay || display == ERROR) {
            display = "0."
            replaceDisplay = false
        } else if ('.' !in display) {
            display += "."
        }
    }

    fun toggleSign() {
        if (display == ERROR) return
        display = when {
            display.startsWith('-') -> display.drop(1)
            display.toBigDecimalOrNull()?.compareTo(BigDecimal.ZERO) == 0 -> display
            else -> "-$display"
        }
    }

    fun percent() {
        val value = currentValue() ?: return
        display = format(value.movePointLeft(2))
        replaceDisplay = true
    }

    fun clear() {
        display = "0"
        storedValue = null
        pendingOperation = null
        replaceDisplay = true
    }

    fun selectOperation(operation: Operation) {
        val current = currentValue() ?: return
        if (pendingOperation != null && !replaceDisplay) {
            if (!evaluate(current)) return
        } else {
            storedValue = current
        }
        pendingOperation = operation
        replaceDisplay = true
    }

    fun calculate() {
        val current = currentValue() ?: return
        if (pendingOperation != null) {
            evaluate(current)
        }
        pendingOperation = null
        storedValue = null
        replaceDisplay = true
    }

    fun snapshot() = CalculatorState(
        display = display,
        storedValue = storedValue?.toPlainString(),
        pendingOperation = pendingOperation?.name,
        replaceDisplay = replaceDisplay,
    )

    private fun currentValue(): BigDecimal? = display.toBigDecimalOrNull()

    private fun evaluate(right: BigDecimal): Boolean {
        val left = storedValue ?: return false
        val result = when (pendingOperation) {
            Operation.ADD -> left.add(right)
            Operation.SUBTRACT -> left.subtract(right)
            Operation.MULTIPLY -> left.multiply(right)
            Operation.DIVIDE -> {
                if (right.compareTo(BigDecimal.ZERO) == 0) {
                    showError()
                    return false
                }
                left.divide(right, MathContext.DECIMAL64)
            }
            null -> return false
        }
        storedValue = result
        display = format(result)
        return true
    }

    private fun showError() {
        display = ERROR
        storedValue = null
        pendingOperation = null
        replaceDisplay = true
    }

    companion object {
        const val ERROR = "Error"

        fun restore(
            display: String,
            storedValue: String?,
            pendingOperation: String?,
            replaceDisplay: Boolean,
        ) = Calculator(
            initialDisplay = display,
            storedValue = storedValue?.toBigDecimalOrNull(),
            pendingOperation = pendingOperation?.let {
                runCatching { Operation.valueOf(it) }.getOrNull()
            },
            replaceDisplay = replaceDisplay,
        )

        private fun format(value: BigDecimal): String =
            value.stripTrailingZeros().toPlainString().let { if (it == "-0") "0" else it }
    }
}
