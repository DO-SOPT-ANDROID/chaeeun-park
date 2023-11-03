package org.sopt.dosopttemplate.data

sealed class FriendsSealed {
    data class FriendsMy(
        val name: String,
        val profileImage: Int,
    ) : FriendsSealed()

    data class FriendsNormal(
        val name: String,
        val description: String?,
        val profileImage: Int,
    ) : FriendsSealed()

    data class FriendsMelon(
        val name: String,
        val description: String?,
        val profileImage: Int,
        val musicTitle: String?,
    ) : FriendsSealed()

    data class FriendsBirthday(
        val name: String,
        val description: String?,
        val profileImage: Int,
    ) : FriendsSealed()


}
