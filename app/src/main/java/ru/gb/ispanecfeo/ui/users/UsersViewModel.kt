package ru.gb.ispanecfeo.ui.users

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import ru.gb.ispanecfeo.domain.entities.UserEntity
import ru.gb.ispanecfeo.domain.repos.UserRepo
import ru.gb.ispanecfeo.ui.utils.mutable

class UsersViewModel(
    private val userRepoRemote: UserRepo.Remote,
    private val userRepoLocal: UserRepo.Local

) : ViewModel() {

    val usersLiveData: Observable<List<UserEntity>> = BehaviorSubject.create()
    val errorLiveData: Observable<Throwable> = BehaviorSubject.create()
    val progressLiveData: Observable<Boolean> = BehaviorSubject.create()

    fun onRefresh(remoteSource: Boolean) {
        showProgress(true)

        if (remoteSource)
            loadDataRemote()
        else
            loadDataLocal()
    }


    private fun loadDataRemote() {

        userRepoRemote.getUsers()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { userList ->
                    showProgress(false)
                    usersLiveData.mutable().onNext(userList)
                    saveDataLocal(userList)
                },
                onError = { error ->
                    showProgress(false)
                    errorLiveData.mutable().onNext(error)
                }
            )
    }

    private fun loadDataLocal() {

        userRepoLocal.getUsers()
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribeBy(
                onSuccess = { userList ->
                    showProgress(false)
                    usersLiveData.mutable().onNext(userList)
                },
                onError = { error ->
                    showProgress(false)
                    errorLiveData.mutable().onNext(error)
                }
            )

    }

    private fun saveDataLocal(userList: List<UserEntity>) {
        userRepoLocal.addUsers(userList)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribeBy(
                onComplete = {
                    Log.d("ROOM_DB", "it's ok!!!")
                },
                onError = {
                    Log.d("ROOM_DB", it.message!!)
                }
            )

    }

    private fun showProgress(visible: Boolean) {
        progressLiveData.mutable().onNext(visible)
    }

}

class UserViewModelFactory(
    private val userRepoRemote: UserRepo.Remote,
    private val userRepoLocal: UserRepo.Local
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = UsersViewModel(
        userRepoRemote, userRepoLocal
    ) as T
}

