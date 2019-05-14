package com.example.calculatoronkotlin

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_list_of_values.*
import android.content.SharedPreferences

class BaseActivity : AppCompatActivity() {
    private lateinit var mSettings: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_of_values)

        mSettings = getSharedPreferences("settings", MODE_PRIVATE)

        save.setOnClickListener(this::onClickSave)
        calculator.setOnClickListener(this::onClickCalculator)

        val arguments = intent.extras

        if (arguments != null)
            currentValue.text = arguments.getString("result")

        if(mSettings.contains("RESULT")) {
            for(int in 5 downTo 1) {
                listOfValues.append(mSettings.getInt("RESULT$int", 0).toString())
                listOfValues.append("\n")
            }
        }
    }

    private fun onClickSave(v: View) {
        listOfValues.append(currentValue.text)
    }

    private fun onClickCalculator(v: View) {
        startActivity(Intent(this, MainActivity::class.java)
            .addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT))

    }
}