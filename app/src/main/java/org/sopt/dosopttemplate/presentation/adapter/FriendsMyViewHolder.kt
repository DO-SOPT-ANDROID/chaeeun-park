package org.sopt.dosopttemplate.presentation.adapter

import androidx.recyclerview.widget.RecyclerView
import org.sopt.dosopttemplate.data.FriendsSealed
import org.sopt.dosopttemplate.databinding.ItemFriendsMyBinding

class FriendsMyViewHolder(private val binding: ItemFriendsMyBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun onBindView(friendsMyData: FriendsSealed.FriendsMy) {
        binding.run {
            ivFriendsMyProfile.setImageResource(friendsMyData.profileImage)
            ivFriendsMyProfile.clipToOutline = true
            tvFriendsMyName.text = friendsMyData.name
        }
    }
}
