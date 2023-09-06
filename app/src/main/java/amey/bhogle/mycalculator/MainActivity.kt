package amey.bhogle.mycalculator

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {
    private var input: TextView? = null
    var lastNumeric : Boolean = false
    var lastDot : Boolean = false

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        input = findViewById(R.id.tvInput)
    }

    fun onDigit(view: View){
        input?.append((view as Button).text)
        lastNumeric = true
        lastDot  = false
    }

    fun onClear(view: View){
        input?.text = ""
    }

    fun onDecimal(view: View){
        if(lastNumeric && !lastDot) {
            input?.append(".")
            lastNumeric = false
            lastDot = false
        }
    }

    fun onOperator(view: View){
        input?.text?.let {
            if(lastNumeric && !isOperatorAdded(it.toString())){
                input?.append((view as Button).text)
                lastNumeric = false
                lastDot = false
            }
        }
    }

    private fun isOperatorAdded(value: String): Boolean{
        return if(value.startsWith("-")){
            false
        }
        else{
            value.contains("/")
                    ||value.contains("x")
                    ||value.contains("+")
                    ||value.contains("-")
        }
    }

    fun onEqual(view: View){
        if(lastNumeric){
            var tvValue = input?.text.toString()
            var prefix = ""
            try {
                if(tvValue.startsWith("-")){
                    prefix = "-"
                    tvValue = tvValue.substring(1)
                }
                //Subtraction
                if(tvValue.contains("-")){
                    val splitValue = tvValue.split("-")
                    var one = splitValue[0]
                    var two = splitValue[1]

                    if(prefix.isNotEmpty()){
                        one = prefix + one
                    }

                    input?.text = removeZeroAfterDot((one.toDouble() - two.toDouble()).toString())
                }
                //Addition
                else if(tvValue.contains("+")){
                    val splitValue = tvValue.split("+")
                    var one = splitValue[0]
                    var two = splitValue[1]

                    if(prefix.isNotEmpty()){
                        one = prefix + one
                    }

                    input?.text = removeZeroAfterDot((one.toDouble() + two.toDouble()).toString())
                }
                //Division
                else if(tvValue.contains("/")){
                    val splitValue = tvValue.split("/")
                    var one = splitValue[0]
                    var two = splitValue[1]

                    if(prefix.isNotEmpty()){
                        one = prefix + one
                    }

                    input?.text = removeZeroAfterDot((one.toDouble() / two.toDouble()).toString())
                }
                //Multiplication
                else if(tvValue.contains("x")){
                    val splitValue = tvValue.split("x")
                    var one = splitValue[0]
                    var two = splitValue[1]

                    if(prefix.isNotEmpty()){
                        one = prefix + one
                    }

                    input?.text =removeZeroAfterDot((one.toDouble() * two.toDouble()).toString())
                }
            }
            catch (e: ArithmeticException){
                e.printStackTrace()
            }

        }

    }

    fun removeZeroAfterDot(result: String): String{
        var value = result
        if(result.contains(".0")){
            value = result.substring(0,result.length -2)
        }

        return value
    }
}