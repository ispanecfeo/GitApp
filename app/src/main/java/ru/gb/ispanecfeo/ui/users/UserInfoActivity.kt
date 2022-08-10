package ru.gb.ispanecfeo.ui.users

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import coil.load
import coil.transform.CircleCropTransformation
import ru.gb.ispanecfeo.app
import ru.gb.ispanecfeo.databinding.ActivityInfoBinding
import ru.gb.ispanecfeo.domain.entities.UserInfoEntity

class UserInfoActivity : AppCompatActivity(), UserInfoContract.View {

    companion object {
        const val LOGIN: String = "login"
    }

    private lateinit var binding: ActivityInfoBinding
    private lateinit var login: String

    private val viewModel: UserInfoViewModel by viewModels {
        UserInfoViewModelFactory(app.userRepo, login)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInfoBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val arguments = intent.extras
        arguments?.let {
            login = it.get(LOGIN) as String
        }

        viewModel.userInfoLiveData.observe(this) { showData(it) }
        viewModel.userInfoProgressLiveData.observe(this) { showProgress(it) }
        viewModel.userInfoErrorLiveData.observe(this) { showError(it) }

        viewModel.refresh()
    }


    override fun showData(userInfo: UserInfoEntity) {
        binding.imgUser.load(userInfo.avatarUrl) {
            crossfade(true)
            transformations(CircleCropTransformation())
        }
        binding.userLogin.text = userInfo.login
        binding.userName.text = userInfo.name
        binding.userLocation.text = userInfo.location
    }

    override fun showProgress(inProgress: Boolean) {
        binding.progressInfo.isVisible = inProgress
        binding.userInformationGroup.visibility = if (inProgress) View.GONE else View.VISIBLE
    }

    override fun showError(throwable: Throwable) {
        Toast.makeText(this, throwable.message, Toast.LENGTH_LONG).show()
    }


}