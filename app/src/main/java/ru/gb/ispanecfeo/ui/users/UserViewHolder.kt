package ru.gb.ispanecfeo.ui.users

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import ru.gb.ispanecfeo.R
import ru.gb.ispanecfeo.databinding.ItemUserBinding
import ru.gb.ispanecfeo.domain.entities.UserEntity

class UserViewHolder(parent: ViewGroup, private val onClickUserCard: (String) -> Unit) :
    RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
    ) {

    private val binding = ItemUserBinding.bind(itemView)

    fun bind(userEntity: UserEntity) {

        binding.avatarImageView.load(userEntity.avatarUrl)
        binding.loginTextView.text = userEntity.login
        binding.uidTextView.text = userEntity.id.toString()
        binding.userCardItem.setOnClickListener{
            onClickUserCard.invoke(userEntity.login)
        }



    }

}