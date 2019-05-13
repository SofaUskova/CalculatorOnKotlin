package com.example.calculatoronkotlin

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_list_of_values.*
import kotlinx.android.synthetic.main.activity_main.*

class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_of_values)

        save.setOnClickListener(this::onClickSave)
        calculator.setOnClickListener(this::onClicCalculator)

        val arguments = intent.extras

        if (arguments != null)
            currentValue.text = arguments.getString("result")
    }

    private fun onClickSave(v: View) {
        listOfValues.text = currentValue.text
    }

    private fun onClicCalculator(v: View) {
        startActivity(Intent(this, MainActivity::class.java)
            .addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT))

    }
}