package ru.gb.ispanecfeo.ui.users

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import ru.gb.ispanecfeo.app
import ru.gb.ispanecfeo.databinding.ActivityMainBinding
import ru.gb.ispanecfeo.domain.entities.UserEntity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: UsersViewModel by viewModels {
        UserViewModelFactory(app.userRepo)
    }

    private val adapter = UserAdapter() { login ->
        openInfoUserActivity(login)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    fun initView() {

        viewModel.progressLiveData.observe(this) { showProgress(it) }
        viewModel.usersLiveData.observe(this) { showUsers(it) }
        viewModel.errorLiveData.observe(this) { showError(it) }

        binding.refreshActivityButton.setOnClickListener {
            viewModel.onRefresh()
        }

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

    private fun openInfoUserActivity(login: String) {
        val intent = Intent(this, UserInfoActivity::class.java)
        intent.putExtra(UserInfoActivity.LOGIN, login)
        startActivity(intent)
    }

}