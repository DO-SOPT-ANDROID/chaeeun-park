package org.sopt.dosopttemplate.presentation.adapter

import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import org.sopt.dosopttemplate.data.local.FriendsSealed
import org.sopt.dosopttemplate.databinding.ItemFriendsNormalBinding

class FriendsNormalViewHolder(private val binding: ItemFriendsNormalBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun onBindView(friendsNormalData: FriendsSealed.FriendsNormal) {
        binding.run {
            ivFriendsNormalProfile.load(friendsNormalData.profileImage)
            ivFriendsNormalProfile.clipToOutline = true
            tvFriendsNormalName.text = friendsNormalData.name
            tvFriendsNormalContent.text = friendsNormalData.description
        }
    }
}
