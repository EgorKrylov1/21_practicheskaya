package com.example.a21practicheskaya

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Создаем корневой контейнер LinearLayout
        val rootView = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
            )
        }

        // Создаем элементы интерфейса
        val nameEditText = EditText(this).apply {
            hint = "Name"
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        }

        val yearEditText = EditText(this).apply {
            hint = "Year"
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        }

        val saveButton = Button(this).apply {
            text = "Save"
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            setOnClickListener {
                val name = nameEditText.text.toString()
                val year = yearEditText.text.toString().toIntOrNull() ?: 0

                val intent = Intent(this@MainActivity, UserActivity::class.java).apply {
                    putExtra("name", name)
                    putExtra("year", year)
                }
                startActivity(intent)
            }
        }

        // Добавляем элементы в корневой контейнер
        rootView.addView(nameEditText)
        rootView.addView(yearEditText)
        rootView.addView(saveButton)

        // Устанавливаем корневой контейнер как содержимое активности
        setContentView(rootView)
    }
}