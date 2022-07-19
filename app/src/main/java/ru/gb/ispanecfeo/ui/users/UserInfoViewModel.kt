package ru.gb.ispanecfeo.ui.users

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.gb.ispanecfeo.domain.entities.UserInfoEntity
import ru.gb.ispanecfeo.domain.repos.UserRepo
import ru.gb.ispanecfeo.ui.utils.SingleEventLiveData
import ru.gb.ispanecfeo.ui.utils.mutable

class UserInfoViewModel(private val userRepo: UserRepo, private val login: String) : ViewModel() {

    val userInfoLiveData : LiveData<UserInfoEntity> = MutableLiveData<UserInfoEntity>()
    val userInfoErrorLiveData : LiveData<Throwable> = SingleEventLiveData<Throwable>()
    val userInfoProgressLiveData: LiveData<Boolean> = MutableLiveData<Boolean>()

    fun refresh() {
        loadData()
    }

    private fun loadData() {
        userInfoProgressLiveData.mutable().postValue(true)

        userRepo.getInfoUser(login, object : Callback<UserInfoEntity> {
            override fun onResponse(
                call: Call<UserInfoEntity>,
                response: Response<UserInfoEntity>
            ) {
                userInfoProgressLiveData.mutable().postValue(false)
                val userInfoData = response.body() as UserInfoEntity

                if (response.isSuccessful) {
                    userInfoLiveData.mutable().postValue(userInfoData)
                }
            }

            override fun onFailure(call: Call<UserInfoEntity>, t: Throwable) {
                userInfoErrorLiveData.mutable().value = t
            }

        })
    }

}

class UserInfoViewModelFactory(private val userRepo: UserRepo, private val login: String) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        UserInfoViewModel(userRepo, login) as T

}

