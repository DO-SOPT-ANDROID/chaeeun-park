package org.sopt.dosopttemplate.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import org.sopt.dosopttemplate.databinding.ItemUserBinding
import org.sopt.dosopttemplate.server.user.UserDataResp

class UserAdapter : ListAdapter<UserDataResp, UserViewHolder>(UserDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemUserBinding.inflate(inflater, parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private class UserDiffCallback : DiffUtil.ItemCallback<UserDataResp>() {
        override fun areItemsTheSame(oldItem: UserDataResp, newItem: UserDataResp): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: UserDataResp, newItem: UserDataResp): Boolean {
            return oldItem == newItem
        }
    }
}