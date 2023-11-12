package org.sopt.dosopttemplate.presentation.adapter

import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import org.sopt.dosopttemplate.data.local.FriendsSealed
import org.sopt.dosopttemplate.databinding.ItemFriendsMyBinding

class FriendsMyViewHolder(private val binding: ItemFriendsMyBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun onBindView(friendsMyData: FriendsSealed.FriendsMy) {
        binding.run {
            ivFriendsMyProfile.load(friendsMyData.profileImage)
            ivFriendsMyProfile.clipToOutline = true
            tvFriendsMyName.text = friendsMyData.name
        }
    }
}
