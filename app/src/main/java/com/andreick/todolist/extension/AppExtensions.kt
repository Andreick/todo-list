package com.andreick.todolist.extension

import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.*

private val LOCALE = Locale("pt", "BR")

fun Date.format() : String {
    val offset = TimeZone.getDefault().getOffset(Date().time)
    this.time -= offset
    return SimpleDateFormat("dd/MM/yyyy", LOCALE).format(this)
}

fun TextInputEditText.text() : String {
    return this.text?.toString() ?: ""
}