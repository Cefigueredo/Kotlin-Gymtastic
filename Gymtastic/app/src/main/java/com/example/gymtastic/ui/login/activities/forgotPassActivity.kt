package com.example.gymtastic.ui.login.activities

import android.app.Activity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.*

import com.example.gymtastic.R

/**
 * Author: Carlos Figueredo
 */
class ForgotPassActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_forgot_pass)

        val email = findViewById<EditText>(R.id.editTextEmail)
        val submit = findViewById<Button>(R.id.buttonSubmit)
        val buttonReturn = findViewById<ImageButton>(R.id.imageButtonReturn)
    }
}