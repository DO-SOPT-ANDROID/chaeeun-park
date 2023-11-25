package org.sopt.dosopttemplate.presentation.adapter

import androidx.recyclerview.widget.RecyclerView
import org.sopt.dosopttemplate.databinding.ItemUserBinding
import org.sopt.dosopttemplate.server.user.UserDataResp
import org.sopt.dosopttemplate.util.loadImageUrl

class UserViewHolder(
    private val binding: ItemUserBinding
): RecyclerView.ViewHolder(binding.root) {
    fun bind(item: UserDataResp) {
        binding.tvUserName.text = item.first_name
        binding.tvUserEmail.text = item.email
        binding.ivUserAvatar.loadImageUrl(item.avatar, 500, 300) //확장함수 만들었어요
    }
}