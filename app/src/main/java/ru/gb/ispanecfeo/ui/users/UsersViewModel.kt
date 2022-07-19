package ru.gb.ispanecfeo.ui.users

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.gb.ispanecfeo.domain.entities.UserEntity
import ru.gb.ispanecfeo.domain.repos.UserRepo
import ru.gb.ispanecfeo.ui.utils.SingleEventLiveData
import ru.gb.ispanecfeo.ui.utils.mutable

class UsersViewModel(private val userRepo: UserRepo) : ViewModel() {

    val usersLiveData: LiveData<List<UserEntity>> = MutableLiveData<List<UserEntity>>()
    val errorLiveData: LiveData<Throwable> = SingleEventLiveData<Throwable>()
    val progressLiveData: LiveData<Boolean> = MutableLiveData<Boolean>()

    fun onRefresh() {
        loadData()
    }

    private fun loadData() {

        progressLiveData.mutable().postValue(true)

        userRepo.getUsers(object : Callback<List<UserEntity>> {
            override fun onResponse(
                call: Call<List<UserEntity>>,
                response: Response<List<UserEntity>>
            ) {
                progressLiveData.mutable().postValue(false)
                val userList = response.body() as List<UserEntity>
                if (response.isSuccessful) {
                    usersLiveData.mutable().postValue(userList)
                }
            }

            override fun onFailure(call: Call<List<UserEntity>>, t: Throwable) {
                progressLiveData.mutable().postValue(false)
                errorLiveData.mutable().value = t
            }

        })
    }



}

class UserViewModelFactory(private val userRepo: UserRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = UsersViewModel(userRepo) as T
}

