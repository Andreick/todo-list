package com.andreick.todolist.view

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.andreick.todolist.R

class CompleteTodoActivity : AppCompatActivity() {

    private lateinit var completeTextView: TextView
    private lateinit var completeButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_complete_todo)

        val todo = intent.extras?.getString("todo")

        completeTextView = findViewById(R.id.tv_complete_todo)
        completeButton = findViewById(R.id.btn_complete_todo)

        completeTextView.text = todo

        completeButton.setOnClickListener {
            val prefs = getSharedPreferences(getString(R.string.shared_pref_name), Context.MODE_PRIVATE)
            val todos = prefs.getStringSet(getString(R.string.todo_strings), setOf())?.toMutableSet()

            todos?.remove(todo)

            prefs.edit().putStringSet(getString(R.string.todo_strings), todos).apply()

            finish()
        }
    }
}