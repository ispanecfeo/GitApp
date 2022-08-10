package ru.gb.ispanecfeo.ui.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.Subject

fun <T> LiveData<T>.mutable(): MutableLiveData<T> {
    return this as? MutableLiveData<T>
        ?: throw IllegalStateException("It is not MutableLiveData o_O")
}


fun <T : Any>Observable<T>.mutable(): Subject<T> {
    return this as? Subject<T>
        ?: throw IllegalStateException("It is not Subject data type o_O")
}