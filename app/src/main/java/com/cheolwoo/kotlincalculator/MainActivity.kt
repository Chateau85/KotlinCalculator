package com.cheolwoo.kotlincalculator

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.abs

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val display = findViewById<TextView>(R.id.tvDisplay)
        val builder = StringBuilder()
        var calcStart = true
        var additionCalc = false
        var subtrationCalc = false
        var multiplicationCalc = false
        var divisionCalc = false
        var a = 0.0
        var b: Double
        var result = 0.0

        display.text = "0"

        btnZero.setOnClickListener {
            if (display.text == "" || display.text == "0" || !calcStart) {
                display.text = "0"
                calcStart = true
            } else {
                builder.append(display.text as String).append("0")
                display.text = builder
                builder.clear()
            }
        }

        btnOne.setOnClickListener {
            if (display.text == "" || display.text == "0" || !calcStart) {
                display.text = "1"
                calcStart = true
            } else {
                builder.append(display.text as String).append("1")
                display.text = builder
                builder.clear()
            }
        }

        btnTwo.setOnClickListener {
            if (display.text == "" || display.text == "0" || !calcStart) {
                display.text = "2"
                calcStart = true
            } else {
                builder.append(display.text as String).append("2")
                display.text = builder
                builder.clear()
            }
        }

        btnThree.setOnClickListener {
            if (display.text == "" || display.text == "0" || !calcStart) {
                display.text = "3"
                calcStart = true
            } else {
                builder.append(display.text as String).append("3")
                display.text = builder
                builder.clear()
            }
        }

        btnFour.setOnClickListener {
            if (display.text == "" || display.text == "0" || !calcStart) {
                display.text = "4"
                calcStart = true
            } else {
                builder.append(display.text as String).append("4")
                display.text = builder
                builder.clear()
            }
        }

        btnFive.setOnClickListener {
            if (display.text == "" || display.text == "0" || !calcStart) {
                display.text = "5"
                calcStart = true
            } else {
                builder.append(display.text as String).append("5")
                display.text = builder
                builder.clear()
            }
        }

        btnSix.setOnClickListener {
            if (display.text == "" || display.text == "0" || !calcStart) {
                display.text = "6"
                calcStart = true
            } else {
                builder.append(display.text as String).append("6")
                display.text = builder
                builder.clear()
            }
        }

        btnSeven.setOnClickListener {
            if (display.text == "" || display.text == "0" || !calcStart) {
                display.text = "7"
                calcStart = true
            } else {
                builder.append(display.text as String).append("7")
                display.text = builder
                builder.clear()
            }
        }

        btnEight.setOnClickListener {
            if (display.text == "" || display.text == "0" || !calcStart) {
                display.text = "8"
                calcStart = true
            } else {
                builder.append(display.text as String).append("8")
                display.text = builder
                builder.clear()
            }
        }

        btnNine.setOnClickListener {
            if (display.text == "" || display.text == "0" || !calcStart) {
                display.text = "9"
                calcStart = true
            } else {
                builder.append(display.text as String).append("9")
                display.text = builder
                builder.clear()
            }
        }

        btnDot.setOnClickListener {
            if (display.text != "" && calcStart && !display.text.contains(".")) {
                builder.append(display.text as String).append(".")
                display.text = builder
                builder.clear()
            }
        }

        btnChangeSign.setOnClickListener {
            if (display.text != "") {
                val temp = display.text.toString()
                display.text = (temp.toDouble() * (-1)).toLong().toString()
                calcStart = true
            }
        }

        btnAC.setOnClickListener {
            display.text = "0"
            calcStart = true
            builder.clear()
            a = 0.0
            b = 0.0
        }

        btnAddition.setOnClickListener {
            if (display.text != "" && display.text != "0") {
                val aText = display.text.toString()
                a = aText.toDouble()
                display.text = ""
                additionCalc = true
            }
        }

        btnSubtraction.setOnClickListener {
            if (display.text != "" && display.text != "0") {
                val aText = display.text.toString()
                a = aText.toDouble()
                display.text = ""
                subtrationCalc = true
            }
        }

        btnMultiplication.setOnClickListener {
            if (display.text != "" && display.text != "0") {
                val aText = display.text.toString()
                a = aText.toDouble()
                display.text = ""
                multiplicationCalc = true
            }
        }

        btnDivision.setOnClickListener {
            if (display.text != "" && display.text != "0") {
                val aText = display.text.toString()
                a = aText.toDouble()
                display.text = ""
                divisionCalc = true
            }
        }

        btnEqual.setOnClickListener {
            if (display.text != "") {
                val bText = display.text.toString()
                b = bText.toDouble()
                when {
                    additionCalc -> {
                        result = (a + b)
                        additionCalc = false
                    }
                    subtrationCalc -> {
                        result = (a - b)
                        subtrationCalc = false
                    }
                    multiplicationCalc -> {
                        result = (a * b)
                        multiplicationCalc = false
                    }
                    divisionCalc -> {
                        if ((a != 0.0 && b == 0.0) || (a == 0.0 && b == 0.0)) {
                            display.text = "오류"
                        } else if (!display.text.contains(".")) {
                            result = (a * 1.0 / b * 1.0)
                        }
                        divisionCalc = false
                    }
                }
                if (abs(result - result.toLong()) > 0) {
                    display.text = result.toString()
                } else {
                    display.text = result.toLong().toString()
                }
                a = 0.0
                b = 0.0
                calcStart = false
            }
        }
    }
}
