package com.bizzagi.daytripoptimization.team2.customview

import android.content.Context
import android.graphics.Canvas
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Patterns
import android.view.View
import androidx.appcompat.widget.AppCompatEditText

class CustomEditText : AppCompatEditText {

    private var type: InputType = InputType.TEXT

    enum class InputType {
        TEXT,
        EMAIL,
        USERNAME,
        PASSWORD,
        CONFIRM_PASSWORD
    }

    private var passwordToCompare: String = ""

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        textAlignment = View.TEXT_ALIGNMENT_VIEW_START
    }

    private fun init() {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // Do nothing
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                validateInput(s.toString())
            }

            override fun afterTextChanged(s: Editable) {
                // Do nothing
            }
        })
    }

    fun setInputType(inputType: InputType) {
        type = inputType
    }

    fun setPasswordToCompare(password: String) {
        passwordToCompare = password
    }

    private fun validateInput(text: String) {
        when (type) {
            InputType.EMAIL -> {
                if (text.isNotEmpty() && !Patterns.EMAIL_ADDRESS.matcher(text).matches()) {
                    setError("Format email tidak valid", null)
                } else {
                    error = null
                }
            }
            InputType.USERNAME -> {
                if (text.isNotEmpty() && text.length < 3) {
                    setError("Username minimal 3 karakter", null)
                } else {
                    error = null
                }
            }
            InputType.PASSWORD -> {
                if (text.isNotEmpty() && text.length < 6) {
                    setError("Password minimal 8 karakter", null)
                } else {
                    error = null
                }
            }
            InputType.CONFIRM_PASSWORD -> {
                if (text != passwordToCompare) {
                    setError("Password tidak cocok", null)
                } else {
                    error = null
                }
            }
            InputType.TEXT -> {
                error = null
            }
        }
    }

    fun isValid(): Boolean {
        return error == null
    }
}