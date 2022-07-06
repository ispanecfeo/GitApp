package ru.gb.ispanecfeo.ui.users

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.gb.ispanecfeo.app
import ru.gb.ispanecfeo.databinding.ActivityMainBinding
import ru.gb.ispanecfeo.domain.entities.UserEntity
import ru.gb.ispanecfeo.domain.repos.UserRepo

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val adapter = UserAdapter()
    private val userRepo: UserRepo by lazy { app.userRepo }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    fun initView() {

        binding.refreshActivityButton.setOnClickListener {
            loadData()
        }

        initRecyclerView()
        showProgress(false)

    }

    private fun loadData() {

        showProgress(true)
        userRepo.getUsers(object : Callback<List<UserEntity>> {
            override fun onResponse(
                call: Call<List<UserEntity>>,
                response: Response<List<UserEntity>>
            ) {
                showProgress(false)
                val list = response.body()
                if (response.isSuccessful && list != null) {
                    adapter.setData(list)
                }
            }

            override fun onFailure(call: Call<List<UserEntity>>, t: Throwable) {

            }

        })
    }

    private fun initRecyclerView() {

        with(binding.usersRecyclerView) {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
        }

    }

    private fun showProgress(visible: Boolean) {

        with(binding) {
            progressBar.isVisible = visible
            usersRecyclerView.isVisible = !visible
        }

    }

}