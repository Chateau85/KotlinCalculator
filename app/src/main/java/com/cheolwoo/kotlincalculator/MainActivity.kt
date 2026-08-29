package com.cheolwoo.kotlincalculator

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cheolwoo.kotlincalculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var calculator: Calculator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        calculator = savedInstanceState?.let {
            Calculator.restore(
                display = it.getString(STATE_DISPLAY).orEmpty(),
                storedValue = it.getString(STATE_STORED_VALUE),
                pendingOperation = it.getString(STATE_PENDING_OPERATION),
                replaceDisplay = it.getBoolean(STATE_REPLACE_DISPLAY),
            )
        } ?: Calculator()

        bindButtons()
        render()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        val state = calculator.snapshot()
        outState.putString(STATE_DISPLAY, state.display)
        outState.putString(STATE_STORED_VALUE, state.storedValue)
        outState.putString(STATE_PENDING_OPERATION, state.pendingOperation)
        outState.putBoolean(STATE_REPLACE_DISPLAY, state.replaceDisplay)
        super.onSaveInstanceState(outState)
    }

    private fun bindButtons() = with(binding) {
        listOf(
            btnZero to 0,
            btnOne to 1,
            btnTwo to 2,
            btnThree to 3,
            btnFour to 4,
            btnFive to 5,
            btnSix to 6,
            btnSeven to 7,
            btnEight to 8,
            btnNine to 9,
        ).forEach { (button, digit) ->
            button.setOnClickListener { update { inputDigit(digit) } }
        }

        btnDot.setOnClickListener { update(Calculator::inputDecimal) }
        btnChangeSign.setOnClickListener { update(Calculator::toggleSign) }
        btnPercent.setOnClickListener { update(Calculator::percent) }
        btnAC.setOnClickListener { update(Calculator::clear) }
        btnAddition.setOnClickListener { update { selectOperation(Operation.ADD) } }
        btnSubtraction.setOnClickListener { update { selectOperation(Operation.SUBTRACT) } }
        btnMultiplication.setOnClickListener { update { selectOperation(Operation.MULTIPLY) } }
        btnDivision.setOnClickListener { update { selectOperation(Operation.DIVIDE) } }
        btnEqual.setOnClickListener { update(Calculator::calculate) }
    }

    private fun update(action: Calculator.() -> Unit) {
        calculator.action()
        render()
    }

    private fun render() {
        binding.tvDisplay.text = calculator.display
    }

    private companion object {
        const val STATE_DISPLAY = "display"
        const val STATE_STORED_VALUE = "stored_value"
        const val STATE_PENDING_OPERATION = "pending_operation"
        const val STATE_REPLACE_DISPLAY = "replace_display"
    }
}
