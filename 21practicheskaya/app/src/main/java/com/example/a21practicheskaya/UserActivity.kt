package com.example.a21practicheskaya

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity

class UserActivity : AppCompatActivity() {

    private lateinit var db: SQLiteDatabase
    private var userId: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sqlHelper = DatabaseHelper(this)
        db = sqlHelper.writableDatabase

        val extras = intent.extras
        if (extras != null) {
            userId = extras.getLong("id", 0)
        }

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
                saveUser(nameEditText.text.toString(), yearEditText.text.toString().toIntOrNull() ?: 0)
            }
        }

        val delButton = Button(this).apply {
            text = "Delete"
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            setOnClickListener {
                deleteUser()
            }
        }

        // Заполняем поля данными, если userId > 0
        if (userId > 0) {
            val cursor = db.rawQuery("SELECT * FROM ${DatabaseHelper.TABLE} WHERE ${DatabaseHelper.COLUMN_ID}=?", arrayOf(userId.toString()))
            cursor.moveToFirst()
            nameEditText.setText(cursor.getString(1))
            yearEditText.setText(cursor.getInt(2).toString())
            cursor.close()
        } else {
            delButton.isEnabled = false
        }

        // Добавляем элементы в корневой контейнер
        rootView.addView(nameEditText)
        rootView.addView(yearEditText)
        rootView.addView(saveButton)
        rootView.addView(delButton)

        // Устанавливаем корневой контейнер как содержимое активности
        setContentView(rootView)
    }

    private fun saveUser(name: String, year: Int) {
        val cv = ContentValues().apply {
            put(DatabaseHelper.COLUMN_NAME, name)
            put(DatabaseHelper.COLUMN_YEAR, year)
        }

        if (userId > 0) {
            db.update(DatabaseHelper.TABLE, cv, "${DatabaseHelper.COLUMN_ID}=?", arrayOf(userId.toString()))
        } else {
            db.insert(DatabaseHelper.TABLE, null, cv)
        }
        finish()
    }

    private fun deleteUser() {
        if (userId > 0) {
            db.delete(DatabaseHelper.TABLE, "${DatabaseHelper.COLUMN_ID}=?", arrayOf(userId.toString()))
        }
        finish()
    }
}