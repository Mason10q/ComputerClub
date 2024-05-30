package ru.mirea.computerclub.core

import android.view.View

interface AdapterCallbacks<DATA, B> {

    fun bindViews(binding: B, item: DATA, position: Int)

    fun onViewClicked(view: View, item: DATA, position: Int) {}
}