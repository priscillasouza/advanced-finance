package com.advancedfinance.core.extensions

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import java.text.NumberFormat

fun EditText.addCurrencyFormatter() {

    this.addTextChangedListener(object : TextWatcher {

        private var current = ""

        override fun afterTextChanged(s: Editable?) {}

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            if (s.toString() != current) {
                this@addCurrencyFormatter.removeTextChangedListener(this)

                val cleanString = s.toString().replace("\\D".toRegex(), "")
                val parsed = if (cleanString.isBlank()) 0.0 else cleanString.toDouble()
                val formated = NumberFormat.getCurrencyInstance()
                    .format(parsed / 100.0f)

                current = formated
                this@addCurrencyFormatter.setText(formated)
                this@addCurrencyFormatter.setSelection(formated.length)
                this@addCurrencyFormatter.addTextChangedListener(this)
            }
        }
    })
}