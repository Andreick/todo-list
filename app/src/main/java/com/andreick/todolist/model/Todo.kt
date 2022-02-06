package com.andreick.todolist.model

data class Todo(
    val id: Int = -1,
    val title: String,
    val hour: String,
    val date: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Todo

        if (id != other.id) return false
        if (title != other.title) return false
        if (hour != other.hour) return false
        if (date != other.date) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + title.hashCode()
        result = 31 * result + hour.hashCode()
        result = 31 * result + date.hashCode()
        return result
    }
}
