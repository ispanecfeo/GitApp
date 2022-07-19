package ru.gb.ispanecfeo.ui.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

fun <T> LiveData<T>.mutable(): MutableLiveData<T> {
    return this as? MutableLiveData<T>
        ?: throw IllegalStateException("It is not MutableLiveData o_O")
}