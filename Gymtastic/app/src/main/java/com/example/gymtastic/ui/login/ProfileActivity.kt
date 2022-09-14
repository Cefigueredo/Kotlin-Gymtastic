package com.example.gymtastic.ui.login

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

import com.example.gymtastic.R

/**
 * Author: Carlos Figueredo
 */
class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_profile)

        val name = findViewById<TextView>(R.id.nameText)
        val intensity = findViewById<EditText>(R.id.editTextIntensity)
        val trainingDays = findViewById<EditText>(R.id.editTextTrainingDays)
        val goal = findViewById<EditText>(R.id.editTextGoals)
    }
}