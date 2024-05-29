package ru.mirea.computerclub.core

import android.widget.Toast
import androidx.fragment.app.Fragment

fun Fragment.toastLong(text: String?) = Toast.makeText(context, text, Toast.LENGTH_LONG).show()