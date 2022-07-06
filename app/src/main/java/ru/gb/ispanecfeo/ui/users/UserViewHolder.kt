package ru.gb.ispanecfeo.ui.users

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import ru.gb.ispanecfeo.R
import ru.gb.ispanecfeo.databinding.ItemUserBinding
import ru.gb.ispanecfeo.domain.entities.UserEntity

class UserViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
) {

    private val binding = ItemUserBinding.bind(itemView)

    fun bind(userEntity: UserEntity) {
        with(binding) {
            avatarImageView.load(userEntity.avatarUrl)
            loginTextView.text = userEntity.login
            uidTextView.text = userEntity.id.toString()
        }
    }
}