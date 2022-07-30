package ru.gb.ispanecfeo.ui.users

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import ru.gb.ispanecfeo.appInstance
import ru.gb.ispanecfeo.databinding.ActivityMainBinding
import ru.gb.ispanecfeo.domain.entities.UserEntity
import ru.gb.ispanecfeo.domain.repos.UserRepo
import ru.gb.ispanecfeo.ui.userinfo.UserInfoActivity
import ru.gb.ispanecfeo.ui.utils.NetworkStatus
import ru.gb.ispanecfeo.ui.utils.observableClickListener


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModelDisposable = CompositeDisposable()

    private val networkStatus: NetworkStatus by lazy { appInstance.networkStatus }
    private var remoteSource: Boolean = true

    private val userRepoRemote: UserRepo.Remote by lazy { appInstance.userRepoRemote }
    private val userRepoLocal: UserRepo.Local by lazy { appInstance.userRepoLocal }

    private var refreshPressed: Boolean = false

    private val viewModel: UsersViewModel by viewModels {
        UserViewModelFactory(userRepoRemote, userRepoLocal)
    }

    private val adapter = UserAdapter { login ->
        openInfoUserActivity(login)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    fun initView() {

        viewModelDisposable.addAll(
            viewModel.progressLiveData.observeOn(AndroidSchedulers.mainThread())
                .subscribe { showProgress(it) },
            viewModel.usersLiveData.observeOn(AndroidSchedulers.mainThread())
                .subscribe { showUsers(it) },
            viewModel.errorLiveData.observeOn(AndroidSchedulers.mainThread())
                .subscribe { showError(it) },
            networkStatus.getStatusConnected().observeOn(AndroidSchedulers.mainThread())
                .subscribe { onChangeDataSource(it) }
        )

        binding.refreshActivityButton.observableClickListener()
            .subscribeBy(
                onNext = {
                    refreshPressed = true
                    viewModel.onRefresh(remoteSource)
                },
                onError = {
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG)
                }
            )

        initRecyclerView()
        showProgress(false)
    }

    private fun initRecyclerView() {
        binding.usersRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.usersRecyclerView.adapter = this.adapter
    }

    private fun showUsers(users: List<UserEntity>) {
        adapter.setData(users)
    }

    private fun showError(throwable: Throwable) {
        Toast.makeText(this, throwable.message, Toast.LENGTH_LONG).show()
    }

    private fun showProgress(visible: Boolean) {
        binding.progressBar.isVisible = visible
        binding.usersRecyclerView.isVisible = !visible
    }

    private fun onChangeDataSource(remote: Boolean) {
        if (refreshPressed) {
            viewModel.onRefresh(remote)
        }
        remoteSource = remote
    }

    private fun openInfoUserActivity(login: String) {
        val intent = Intent(this, UserInfoActivity::class.java)
        intent.putExtra(UserInfoActivity.LOGIN, login)
        startActivity(intent)
    }

    override fun onDestroy() {
        viewModelDisposable.dispose()
        super.onDestroy()
    }

}