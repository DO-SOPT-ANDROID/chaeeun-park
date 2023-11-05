package org.sopt.dosopttemplate.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.sopt.dosopttemplate.R
import org.sopt.dosopttemplate.data.local.FriendsSealed
import org.sopt.dosopttemplate.databinding.ItemFriendsBirthdayBinding
import org.sopt.dosopttemplate.databinding.ItemFriendsMelonBinding
import org.sopt.dosopttemplate.databinding.ItemFriendsMyBinding
import org.sopt.dosopttemplate.databinding.ItemFriendsNormalBinding

class FriendsSealedAdapter(context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var friendList: List<FriendsSealed> = emptyList() // ArrayList 대신 List 사용

    // 친구 데이터를 설정
    fun setFriendsData(list: List<FriendsSealed>) {
        friendList = list
        notifyDataSetChanged() // 데이터가 변경되었음
    }

    override fun getItemCount() = friendList.size

    override fun getItemViewType(position: Int) = when (friendList[position]) {
        is FriendsSealed.FriendsMy -> R.layout.item_friends_my
        is FriendsSealed.FriendsNormal -> R.layout.item_friends_normal
        is FriendsSealed.FriendsMelon -> R.layout.item_friends_melon
        is FriendsSealed.FriendsBirthday ->R.layout.item_friends_birthday
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(viewType, parent, false)

        // 타입별 뷰 홀더 생성
        return when (viewType) {
            R.layout.item_friends_my -> FriendsMyViewHolder(
                ItemFriendsMyBinding.bind(view)
            )
            R.layout.item_friends_birthday -> FriendsBirthdayViewHolder(
                ItemFriendsBirthdayBinding.bind(view)
            )
            R.layout.item_friends_melon -> FriendsMelonViewHolder(
                ItemFriendsMelonBinding.bind(view)
            )
            else -> FriendsNormalViewHolder(
                ItemFriendsNormalBinding.bind(view)
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = friendList[position]

        when (holder) {
            is FriendsMyViewHolder -> holder.onBindView(item as FriendsSealed.FriendsMy)
            is FriendsNormalViewHolder -> holder.onBindView(item as FriendsSealed.FriendsNormal)
            is FriendsMelonViewHolder -> holder.onBindView(item as FriendsSealed.FriendsMelon)
            is FriendsBirthdayViewHolder -> holder.onBindView(item as FriendsSealed.FriendsBirthday)

        }
    }
}