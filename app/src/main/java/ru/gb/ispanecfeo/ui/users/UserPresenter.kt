//package ru.gb.ispanecfeo.ui.users
//
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//import ru.gb.ispanecfeo.domain.entities.UserEntity
//import ru.gb.ispanecfeo.domain.repos.UserRepo
//
//class UserPresenter(
//    private val userRepo: UserRepo
//):UserContract.Presenter {
//
//    private var view: UserContract.View? = null
//    private var userList: List<UserEntity>? = null
//    private var inProgress: Boolean = false
//
//    override fun attach(view: UserContract.View) {
//        this.view = view
//        this.view?.showProgress(inProgress)
//        userList?.let { view.showUsers(it) }
//    }
//
//    override fun detach() {
//        this.view = null
//    }
//
//    override fun onRefresh() {
//        loadData()
//    }
//
//    private fun loadData() {
//
//        view?.showProgress(true)
//        inProgress = true
//
//        userRepo.getUsers(object : Callback<List<UserEntity>> {
//            override fun onResponse(
//                call: Call<List<UserEntity>>,
//                response: Response<List<UserEntity>>
//            ) {
//                view?.showProgress(false)
//                userList = response.body() as List<UserEntity>
//                if (response.isSuccessful && userList != null) {
//                    view?.showUsers(userList!!)
//                    inProgress = false
//                }
//            }
//
//            override fun onFailure(call: Call<List<UserEntity>>, t: Throwable) {
//                view?.showError(t)
//            }
//
//        })
//    }
//
//}