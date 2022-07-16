package ru.gb.ispanecfeo.ui.users

import ru.gb.ispanecfeo.domain.entities.UserInfoEntity

interface UserInfoContract {

    interface View {
        fun showData(userInfo: UserInfoEntity)
        fun showProgress(inProgress: Boolean)
        fun showError(throwable: Throwable)
    }

    interface Presenter {
        fun attach(view: View)
        fun detach()
        fun onRefresh()
    }

}