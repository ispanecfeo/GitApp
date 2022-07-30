//package ru.gb.ispanecfeo.ui.users
//
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//import ru.gb.ispanecfeo.domain.entities.UserInfoEntity
//import ru.gb.ispanecfeo.domain.repos.UserRepo
//
//class UserInfoPresenter(private val userRepo: UserRepo, private val login: String) :
//    UserInfoContract.Presenter {
//
//    private var view: UserInfoContract.View? = null
//    private var inProgress: Boolean = true
//    private var userInfoData: UserInfoEntity? = null
//
//    override fun attach(view: UserInfoContract.View) {
//        this.view = view
//        this.view?.showProgress(inProgress)
//        userInfoData?.let { view.showData(it) }
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
//        view?.showProgress(inProgress)
//        inProgress = false
//        userRepo.getInfoUser(login, object : Callback<UserInfoEntity> {
//            override fun onResponse(
//                call: Call<UserInfoEntity>,
//                response: Response<UserInfoEntity>
//            ) {
//                view?.showProgress(inProgress)
//                userInfoData = response.body() as UserInfoEntity
//
//                if (response.isSuccessful && userInfoData != null) {
//                    view?.showData(userInfoData!!)
//                }
//            }
//
//            override fun onFailure(call: Call<UserInfoEntity>, t: Throwable) {
//                view?.showError(t)
//            }
//
//        })
//
//    }
//
//}