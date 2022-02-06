package com.andreick.todolist.view

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.andreick.todolist.R
import com.andreick.todolist.databinding.ActivityNewTodoBinding
import com.andreick.todolist.datasource.TodoDataSource
import com.andreick.todolist.extension.format
import com.andreick.todolist.extension.text
import com.andreick.todolist.model.Todo
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.*

class NewTodoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewTodoBinding
    private var todoId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewTodoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (intent.hasExtra(TODO_ID)) {
            todoId = intent.getIntExtra(TODO_ID, todoId)
            val todo = TodoDataSource.findTodoById(todoId)
            if (todo != null) {
                binding.tietNewTodoTitle.setText(todo.title)
                binding.tietNewTodoDate.setText(todo.date)
                binding.tietNewTodoHour.setText(todo.hour)
            }
        }
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.tietNewTodoDate.setOnClickListener {
            MaterialDatePicker.Builder.datePicker().build().apply {
                addOnPositiveButtonClickListener { date ->
                    binding.tietNewTodoDate.setText(Date(date).format())
                }
                show(supportFragmentManager, DATE_PICKER_TAG)
            }
        }
        binding.tietNewTodoHour.setOnClickListener {
            val timePicker = MaterialTimePicker.Builder().setTimeFormat(TimeFormat.CLOCK_24H).build()
            timePicker.addOnPositiveButtonClickListener {
                binding.tietNewTodoHour.setText(getString(
                    R.string.hour_format, timePicker.hour, timePicker.minute
                ))
            }
            timePicker.show(supportFragmentManager, TIME_PICKER_TAG)
        }
        binding.btnCancelTodo.setOnClickListener { finish() }
        binding.btnAddTodo.setOnClickListener {
            val todo = Todo(
                id = todoId,
                title = binding.tietNewTodoTitle.text(),
                date = binding.tietNewTodoDate.text(),
                hour = binding.tietNewTodoHour.text()
            )
            TodoDataSource.insertTodo(todo)
            setResult(Activity.RESULT_OK)
            finish()
        }
    }

    companion object {
        const val TODO_ID = "todoId"
        private const val DATE_PICKER_TAG = "Task Date Picker"
        private const val TIME_PICKER_TAG = "Task Time Picker"
    }
}