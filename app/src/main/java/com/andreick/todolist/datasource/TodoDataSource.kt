package com.andreick.todolist.datasource

import com.andreick.todolist.model.Todo

object TodoDataSource {
    private val todoLst = arrayListOf<Todo>()

    fun getTodoList() = todoLst.toList()

    fun insertTodo(todo: Todo) {
        if (todo.id == -1) todoLst.add(todo.copy(id = todoLst.size + 1))
        else {
            val indexedTodo = todoLst.withIndex().find { it.value.id == todo.id }
            if (indexedTodo == null) todoLst.add(todo.copy(id = todoLst.size + 1))
            else todoLst[indexedTodo.index] = indexedTodo.value
        }
    }

    fun findTodoById(todoId: Int) = todoLst.find { it.id == todoId }

    fun deleteTodo(todo: Todo) = todoLst.remove(todo)
}