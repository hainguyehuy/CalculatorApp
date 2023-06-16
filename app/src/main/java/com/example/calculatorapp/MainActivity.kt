package com.example.calculatorapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {
    private var tvInputt:TextView? = null
    var lastNumeric:Boolean = false
    var lastDot:Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvInputt = findViewById(R.id.tvInput)
    }

    fun onDigit(view :View){
        tvInputt?.append((view as Button).text)
        lastNumeric = true
        lastDot = false
    }

    fun onClear(view: View){
        tvInputt?.text=""
    }
    fun onDecimalPointer(view: View){
        if(lastNumeric && !lastDot){
            tvInputt?.append(".")
            lastNumeric = false
            lastDot = true
        }
    }
    fun onOperator(view: View){
        tvInputt?.text?.let {
            if (lastNumeric && !isOperatorAdded(it.toString())){
                tvInputt?.append((view as Button).text)
                lastNumeric = false
                lastDot = false
            }

        }
    }
    fun isOperatorAdded(value:String):Boolean{
        return if(value.startsWith("-")){
            false
        }else{
            value.contains("/")
                    ||value.contains("+")
                    ||value.contains("-")
                    ||value.contains("*")
        }
    }
    private fun removeDot(value: String):String{
        var result = value
        if(value.contains(".0")){
            result = value.substring(0,result.length-2)
        }
        return result
    }
    fun onEqual(view: View){
        if(lastNumeric){
            var inputValue = tvInputt?.text.toString()
            var prefix = ""
            try {
                if(inputValue.startsWith("-")) {
                    prefix = "-"
                    inputValue = inputValue.substring(1)
                }
                if (inputValue.contains("-")) {
                    val splitValue = inputValue.split("-")
                    var one = splitValue[0]
                    var two = splitValue[1]
                    if (prefix.isNotEmpty()) {
                        one = prefix + one
                    }
                        tvInputt?.text = removeDot((one.toDouble() - two.toDouble()).toString())
                }else if (inputValue.contains("+")) {
                    val splitValue = inputValue.split("+")
                    var one = splitValue[0]
                    var two = splitValue[1]
                    if (prefix.isNotEmpty()) {
                        one = prefix + one
                    }
                    tvInputt?.text = removeDot((one.toDouble() + two.toDouble()).toString())
                }else if (inputValue.contains("/")) {
                    val splitValue = inputValue.split("/")
                    var one = splitValue[0]
                    var two = splitValue[1]
                    if (prefix.isNotEmpty()) {
                        one = prefix + one
                    }
                    tvInputt?.text = removeDot((one.toDouble() / two.toDouble()).toString())
                }else if (inputValue.contains("*")) {
                    val splitValue = inputValue.split("*")
                    var one = splitValue[0]
                    var two = splitValue[1]
                    if (prefix.isNotEmpty()) {
                        one = prefix + one
                    }
                    tvInputt?.text = removeDot((one.toDouble() * two.toDouble()).toString())
                }

            }catch (e:ArithmeticException){
                e.printStackTrace()
            }
        }
    }
}


