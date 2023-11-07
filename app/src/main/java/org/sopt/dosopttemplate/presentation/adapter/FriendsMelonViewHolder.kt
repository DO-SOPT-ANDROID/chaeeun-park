package org.sopt.dosopttemplate.presentation.adapter

import androidx.recyclerview.widget.RecyclerView
import org.sopt.dosopttemplate.data.local.FriendsSealed
import org.sopt.dosopttemplate.databinding.ItemFriendsMelonBinding

class FriendsMelonViewHolder(private var binding: ItemFriendsMelonBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun onBindView(friendsMelonData: FriendsSealed.FriendsMelon) {
        binding.run {
            ivFriendsMelonProfile.setImageResource(friendsMelonData.profileImage)
            ivFriendsMelonProfile.clipToOutline = true
            tvFriendsMelonName.text = friendsMelonData.name
            tvFriendsMelonContent.text = friendsMelonData.description
            tvFriendsMelonMusic.text = friendsMelonData.musicTitle
        }
    }
}
