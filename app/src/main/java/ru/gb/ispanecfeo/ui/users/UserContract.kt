package ru.gb.ispanecfeo.ui.users

import ru.gb.ispanecfeo.domain.entities.UserEntity

interface UserContract {

    interface View {
        fun showUsers(users: List<UserEntity>)
        fun showError(throwable: Throwable)
        fun showProgress(inProgress: Boolean)
    }

    interface Presenter{
        fun attach(view:View)
        fun detach()
        fun onRefresh()
    }

}