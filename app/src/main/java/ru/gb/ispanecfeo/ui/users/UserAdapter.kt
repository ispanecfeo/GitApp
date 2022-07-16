package ru.gb.ispanecfeo.ui.users

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.gb.ispanecfeo.domain.entities.UserEntity

class UserAdapter : RecyclerView.Adapter<UserViewHolder>() {

    private val listData = mutableListOf<UserEntity>()

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = UserViewHolder(parent)

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemCount() = listData.size

    private fun getItem(position: Int) = listData.get(position)

    @SuppressLint("NotifyDataSetChanged")
    fun setData(users: List<UserEntity>) {
        listData.clear()
        listData.addAll(users)
        notifyDataSetChanged()
    }

}