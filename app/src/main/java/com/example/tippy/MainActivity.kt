package com.example.tippy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView

private const val TAG = "MainActivity"
private const val INITIAL_TIP_PERCENT = 15

class MainActivity : AppCompatActivity() {

    // propriety
    private lateinit var etBaseAmount: EditText
    private lateinit var seekBarTip: SeekBar
    private lateinit var tvPercentLabel: TextView
    private lateinit var tvTipAmount: TextView
    private lateinit var tvTotalAmount: TextView

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        etBaseAmount = findViewById(R.id.etBaseAmount)
        seekBarTip = findViewById(R.id.seekBarTip)
        tvPercentLabel = findViewById(R.id.tvPercentLabel)
        tvTipAmount = findViewById(R.id.tvTipAmount)
        tvTotalAmount = findViewById(R.id.tvTotalAmount)

        // set tip seekbar to 15%
        seekBarTip.progress = INITIAL_TIP_PERCENT
        //set tip seekbar label to 15%
        tvPercentLabel.text = "$INITIAL_TIP_PERCENT%"

        // listen to change on the seekbar
        // object : anonymous classes, one time use
        seekBarTip.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{

            override fun onProgressChanged(p0: SeekBar?, progress: Int, p2: Boolean) {
                Log.i(TAG, "onProgressChange $progress")

                // change the text to the progress of the seekbar
                tvPercentLabel.text = "$progress%"
                computeTipAndTotal()
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {}

            override fun onStopTrackingTouch(p0: SeekBar?) {}

        })

        // listen for change on the base Amount input
        etBaseAmount.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                Log.i(TAG, "afterTextChange $p0")

                computeTipAndTotal()
            }

        })
    }

    private fun computeTipAndTotal() {

        // if the billAmount is empty then remove the values from the tip and total
        if (etBaseAmount.text.isEmpty()){

            tvTipAmount.text = ""
            tvTotalAmount.text = ""

            return
        }

        // get the value of the base and tip
        val baseAmount = etBaseAmount.text.toString().toDouble()
        val tipPercent = seekBarTip.progress

        // compute the tip and total
        val tipAmount = baseAmount * tipPercent / 100
        val totalAmount = baseAmount + tipAmount

        // update UI
        tvTipAmount.text = "%.2f".format(tipAmount) // format the tipAmount to 2 decimal
        tvTotalAmount.text = "%.2f".format(totalAmount)


    }
}