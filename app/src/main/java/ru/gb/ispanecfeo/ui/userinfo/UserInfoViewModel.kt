package ru.gb.ispanecfeo.ui.users

import android.util.Log
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import ru.gb.ispanecfeo.domain.entities.UserInfoEntity
import ru.gb.ispanecfeo.domain.repos.UserRepo
import ru.gb.ispanecfeo.ui.utils.mutable

class UserInfoViewModel(
    private val userRepoRemote: UserRepo.Remote,
    private val userRepoLocal: UserRepo.Local,
) : ViewModel() {

    val userInfoLiveData: Observable<UserInfoEntity> = BehaviorSubject.create()
    val userInfoErrorLiveData: Observable<Throwable> = BehaviorSubject.create()
    val userInfoProgressLiveData: Observable<Boolean> = BehaviorSubject.create()

    fun onRefresh(remoteSource: Boolean, login: String) {
        showProgress(true)

        if (remoteSource)
            loadDataRemote(login)
        else
            loadDataLocal(login)
    }

    private fun loadDataRemote(login:String) {

        showProgress(true)

        userRepoRemote.getInfoUser(login)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { userInfoData ->
                    showProgress(false)
                    userInfoLiveData.mutable().onNext(userInfoData)
                    saveDataLocal(userInfoData)
                },
                onError = { error ->
                    showProgress(false)
                    userInfoErrorLiveData.mutable().onNext(error)
                }
            )
    }

    private fun loadDataLocal(login: String) {

        userRepoLocal.getInfoUser(login)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribeBy(
                onSuccess = { userInfoData ->
                    showProgress(false)
                    userInfoLiveData.mutable().onNext(userInfoData)
                },
                onError = { error ->
                    showProgress(false)
                    userInfoErrorLiveData.mutable().onNext(error)
                }
            )

    }

    private fun saveDataLocal(userInfoData: UserInfoEntity) {
        userRepoLocal.addUserInfo(userInfoData)
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
        userInfoProgressLiveData.mutable().onNext(visible)
    }
}

