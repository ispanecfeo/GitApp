package ru.gb.ispanecfeo.ui.utils

import android.widget.Button
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject

fun Button.observableClickListener() : Observable<Button> {
    val publishSubject: PublishSubject<Button> = PublishSubject.create()
    this.setOnClickListener { t -> publishSubject.onNext(t as Button) }
    return  publishSubject
}