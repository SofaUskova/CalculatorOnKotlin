package com.example.calculatoronkotlin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

import java.util.*
import android.content.Intent
import android.content.Intent.*
import kotlin.collections.ArrayList
import android.content.SharedPreferences
import android.R.id.edit
import android.widget.Toast
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    private val stackNumber = Stack<Int>()
    private val stackOperand = Stack<Char>()
    private var number: String = ""
    private lateinit var mSettings: SharedPreferences
    private val list: MutableList<Int> = mutableListOf()
    private lateinit var editor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mSettings = getSharedPreferences("settings", MODE_PRIVATE)
        editor = mSettings.edit()

        ok.setOnClickListener(this::onClickOk)
        cancel.setOnClickListener(this::onClickOperand)
        divide.setOnClickListener(this::onClickOperand)
        multiply.setOnClickListener(this::onClickOperand)
        minus.setOnClickListener(this::onClickOperand)
        equally.setOnClickListener(this::onClickOperand)
        plus.setOnClickListener(this::onClickOperand)

        zero.setOnClickListener(this::onClickNumber)
        one.setOnClickListener(this::onClickNumber)
        two.setOnClickListener(this::onClickNumber)
        three.setOnClickListener(this::onClickNumber)
        four.setOnClickListener(this::onClickNumber)
        five.setOnClickListener(this::onClickNumber)
        six.setOnClickListener(this::onClickNumber)
        seven.setOnClickListener(this::onClickNumber)
        eight.setOnClickListener(this::onClickNumber)
        nine.setOnClickListener(this::onClickNumber)
    }

    private fun onClickOk(v: View) {
        val intent = Intent(this, BaseActivity::class.java)
            .addFlags(FLAG_ACTIVITY_CLEAR_TOP)

        intent.putExtra("result", result.text.toString())
        startActivity(intent)
    }

    private fun onClickNumber(v: View) {
        if (expression.text.toString() == "0") {
            expression.text = ""
            result.text = ""
        }

        when (v.id) {
            R.id.zero -> {
                expression.append("0")
                number = number.plus("0")
            }
            R.id.one -> {
                expression.append("1")
                number = number.plus("1")
            }
            R.id.two -> {
                expression.append("2")
                number = number.plus("2")
            }
            R.id.three -> {
                expression.append("3")
                number = number.plus("3")
            }
            R.id.four -> {
                expression.append("4")
                number = number.plus("4")
            }
            R.id.five -> {
                expression.append("5")
                number = number.plus("5")
            }
            R.id.six -> {
                expression.append("6")
                number = number.plus("6")
            }
            R.id.seven -> {
                expression.append("7")
                number = number.plus("7")
            }
            R.id.eight -> {
                expression.append("8")
                number = number.plus("8")
            }
            R.id.nine -> {
                expression.append("9")
                number = number.plus("9")
            }
        }
    }

    private fun onClickOperand(v: View) {
        if (!number.isEmpty()) {
            stackNumber.push(number.toInt())
            number = ""
        }

        when (v.id) {
            R.id.divide -> {
                stackOperand.push('/')
                expression.append("/")
            }
            R.id.multiply -> {
                stackOperand.push('*')
                expression.append("*")
            }
            R.id.minus -> {
                stackOperand.push('-')
                expression.append("-")
            }
            R.id.plus -> {
                stackOperand.push('+')
                expression.append("+")
            }
            R.id.cancel -> {
                expression.text = "0"
                result.text = ""
            }

            R.id.equally -> {
                if (stackNumber.size == 1) {
                    if (list.size < 5) {
                        list.add(stackNumber.peek())
                        editor.putInt("RESULT${list.size}", stackNumber.peek())
                        editor.apply()
                    }
                    result.text = stackNumber.peek().toString()
                    stackNumber.pop()
                } else {
                    solution()
                }
            }
        }
    }

    private fun solution() {
        try {
            var rezult = ""
            while (!stackNumber.isEmpty()) {
                val second = stackNumber.peek()
                stackNumber.pop()
                val first = stackNumber.peek()
                stackNumber.pop()

                rezult = when (stackOperand.peek()) {
                    '/' -> {
                        if (second == 0) {
                            "Error"
                        } else
                            (first / second).toString()
                    }
                    '*' ->
                        (first * second).toString()
                    '-' ->
                        (first - second).toString()
                    '+' ->
                        (first + second).toString()
                    else ->
                        "0"
                }
                stackOperand.pop()
                if (!stackNumber.isEmpty())
                    stackNumber.push(rezult.toInt())
            }
            result.text = rezult
            if (list.size < 5) {
                list.add(rezult.toInt())
                editor.putInt("RESULT${list.size}", rezult.toInt())
                editor.apply()
            }
        } catch (e: Exception) {
            val toast = Toast.makeText(
                applicationContext,
                e.message, Toast.LENGTH_SHORT
            )
            toast.show()
        }
    }
}
