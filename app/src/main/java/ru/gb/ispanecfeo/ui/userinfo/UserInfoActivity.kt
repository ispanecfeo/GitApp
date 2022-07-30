package ru.gb.ispanecfeo.ui.userinfo

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import coil.load
import coil.transform.CircleCropTransformation
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import ru.gb.ispanecfeo.appInstance
import ru.gb.ispanecfeo.databinding.ActivityInfoBinding
import ru.gb.ispanecfeo.domain.entities.UserInfoEntity
import ru.gb.ispanecfeo.domain.repos.UserRepo
import ru.gb.ispanecfeo.ui.users.UserInfoViewModel
import ru.gb.ispanecfeo.ui.users.UserInfoViewModelFactory
import ru.gb.ispanecfeo.ui.utils.NetworkStatus

class UserInfoActivity : AppCompatActivity() {

    companion object {
        const val LOGIN: String = "login"
    }

    private lateinit var binding: ActivityInfoBinding
    private lateinit var login: String
    private val viewModelDisposable = CompositeDisposable()

    private val userRepoRemote: UserRepo.Remote by lazy { appInstance.userRepoRemote }
    private val userRepoLocal: UserRepo.Local by lazy { appInstance.userRepoLocal }

    private val networkStatus: NetworkStatus  by lazy { appInstance.networkStatus }
    private var remoteSource: Boolean = true

    private val viewModel: UserInfoViewModel by viewModels {
        UserInfoViewModelFactory(
            login,
            userRepoRemote,
            userRepoLocal
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityInfoBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val arguments = intent.extras
        arguments?.let {
            login = it.get(LOGIN) as String
        }

        viewModelDisposable.addAll(
            viewModel.userInfoLiveData.observeOn(AndroidSchedulers.mainThread())
                .subscribe { showData(it) },
            viewModel.userInfoProgressLiveData.observeOn(AndroidSchedulers.mainThread())
                .subscribe { showProgress(it) },
            viewModel.userInfoErrorLiveData.observeOn(AndroidSchedulers.mainThread())
                .subscribe { showError(it) },
            networkStatus.getStatusConnected().observeOn(AndroidSchedulers.mainThread())
                .subscribe { onСhangeDataSource(it) }
        )

        remoteSource = networkStatus.isOnline()
        viewModel.onRefresh(remoteSource)
    }


    private fun showData(userInfo: UserInfoEntity) {
        binding.imgUser.load(userInfo.avatarUrl) {
            crossfade(true)
            transformations(CircleCropTransformation())
        }
        binding.userLogin.text = userInfo.login
        binding.userName.text = userInfo.name
        binding.userLocation.text = userInfo.location
    }

    private fun onСhangeDataSource(remote: Boolean) {
        viewModel.onRefresh(remote)
        remoteSource = remote
    }

    private fun showProgress(inProgress: Boolean) {
        binding.progressInfo.isVisible = inProgress
        binding.userInformationGroup.visibility = if (inProgress) View.GONE else View.VISIBLE
    }

    private fun showError(throwable: Throwable) {
        Toast.makeText(this, throwable.message, Toast.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        viewModelDisposable.dispose()
        super.onDestroy()
    }

}