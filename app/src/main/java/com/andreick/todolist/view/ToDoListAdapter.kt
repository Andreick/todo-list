package com.andreick.todolist.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.andreick.todolist.R
import com.andreick.todolist.databinding.ItemTodoBinding
import com.andreick.todolist.model.Todo

class ToDoListAdapter : ListAdapter<Todo, ToDoListAdapter.TodoViewHolder>(TodoDiffCallback()) {

    var listenerEdit: (Todo) -> Unit = {}
    var listenerDelete: (Todo) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTodoBinding.inflate(inflater, parent, false)
        return TodoViewHolder(binding, parent.context)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class TodoViewHolder(
        private val binding: ItemTodoBinding,
        private val context: Context
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(todo: Todo) {
            binding.tvItemTodoTitle.text = todo.title
            binding.tvItemTodoDate.text =
                context.getString(R.string.date_hour_format, todo.date, todo.hour)
            binding.ivItemTodoMore.setOnClickListener { showPopup(todo) }
        }

        private fun showPopup(todo: Todo) {
            PopupMenu(binding.ivItemTodoMore.context, binding.ivItemTodoMore).apply {
                menuInflater.inflate(R.menu.menu_item_todo_popup, menu)
                setOnMenuItemClickListener {
                    when (it.itemId) {
                        R.id.action_todo_edit -> listenerEdit(todo)
                        R.id.action_todo_delete -> listenerDelete(todo)
                    }
                    return@setOnMenuItemClickListener true
                }
                show()
            }
        }
    }
}

class TodoDiffCallback : DiffUtil.ItemCallback<Todo>() {
    override fun areItemsTheSame(oldItem: Todo, newItem: Todo): Boolean =
        oldItem == newItem
    override fun areContentsTheSame(oldItem: Todo, newItem: Todo): Boolean =
        oldItem == newItem
}