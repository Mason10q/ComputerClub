package ru.mirea.computerclub.core

import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.addItemMargins(horizontal: Int = 0, vertical: Int = 0) {
    addItemDecoration(MarginItemDecorator(vertical, horizontal))
}