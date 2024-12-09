package io.captaingaga.airtickets.effective.mobile.main.ui

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Toast

class CyrillicInputValidation(
    private val context: Context,
    private val editText: EditText?,
    private val operation: (String) -> Unit
) : TextWatcher {
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        if (!s.isNullOrEmpty() && start in s.indices) {
            val char = s[start]
            val isCyrillic = char in '\u0400'..'\u04FF'
            if (!isCyrillic) {
                editText?.apply {
                    val editable = text
                    if (editable != null && start in editable.indices) {
                        editable.delete(start, start + 1)
                    }
                }
                Toast.makeText(
                    context,
                    "Пожалуйста, переключите язык на Русский",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun afterTextChanged(s: Editable?) {
        if (!s.isNullOrEmpty()) operation(s.toString())
    }
}