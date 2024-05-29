package ru.mirea.computerclub.core

import android.util.Patterns.EMAIL_ADDRESS

fun CharSequence.isValidEmail() =
    this.isNotEmpty() && EMAIL_ADDRESS.matcher(this).matches()