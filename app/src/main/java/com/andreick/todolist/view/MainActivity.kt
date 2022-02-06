package com.andreick.todolist.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.appcompat.app.AppCompatActivity
import com.andreick.todolist.R
import com.andreick.todolist.databinding.ActivityMainBinding
import com.andreick.todolist.datasource.TodoDataSource

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val listAdapter by lazy { ToDoListAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        setUpRecyclerView()
        setOnClickListeners()
    }

    private fun setUpRecyclerView() {
        binding.contentMain.rvTodo.adapter = listAdapter
        updateTodoList()
    }

    private fun updateTodoList() {
        val todoLst = TodoDataSource.getTodoList()
        listAdapter.submitList(todoLst)
        binding.contentMain.includeEmptyTodoState.clEmptyTodoState.visibility =
            if (todoLst.isEmpty()) View.VISIBLE
            else View.GONE
    }

    private var newTodoLauncher = registerForActivityResult(StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            updateTodoList()
        }
    }

    private fun setOnClickListeners() {
        binding.fabAddTodo.setOnClickListener {
            val intent = Intent(this, NewTodoActivity::class.java)
            newTodoLauncher.launch(intent)
        }
        listAdapter.listenerEdit = { todo ->
            val intent = Intent(this, NewTodoActivity::class.java)
            intent.putExtra(NewTodoActivity.TODO_ID, todo.id)
            newTodoLauncher.launch(intent)
        }
        listAdapter.listenerDelete = { todo ->
            TodoDataSource.deleteTodo(todo)
            updateTodoList()
        }
    }

    override fun onResume() {
        super.onResume()
        val prefs = getSharedPreferences(getString(R.string.shared_pref_name), Context.MODE_PRIVATE)
        val todos = prefs.getStringSet(getString(R.string.todo_strings), setOf())?.toMutableSet()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_delete_all -> {
                val prefs = getSharedPreferences(getString(R.string.shared_pref_name), Context.MODE_PRIVATE)
                prefs.edit().putStringSet(getString(R.string.todo_strings), null).apply()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}