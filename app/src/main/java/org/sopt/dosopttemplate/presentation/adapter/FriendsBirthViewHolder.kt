package org.sopt.dosopttemplate.presentation.adapter

import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import org.sopt.dosopttemplate.data.local.FriendsSealed
import org.sopt.dosopttemplate.databinding.ItemFriendsBirthdayBinding

class FriendsBirthdayViewHolder(private val binding: ItemFriendsBirthdayBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun onBindView(friendsBirthdayData: FriendsSealed.FriendsBirthday) {
        binding.run {
            ivFriendsBirthdayProfile.load(friendsBirthdayData.profileImage) //코드리뷰 반영 coil 라이브러리
            ivFriendsBirthdayProfile.clipToOutline = true
            tvFriendsBirthdayName.text = friendsBirthdayData.name
            tvFriendsBirthdayContent.text = friendsBirthdayData.description
        }
    }
}