package org.sopt.dosopttemplate.presentation.mypage

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import org.sopt.dosopttemplate.databinding.FragmentMypageBinding
import org.sopt.dosopttemplate.di.UserSharedPreferences
import org.sopt.dosopttemplate.presentation.auth.LoginActivity


class MypageFragment : Fragment() {

    private var _binding: FragmentMypageBinding? = null
    private val binding: FragmentMypageBinding
        get() = requireNotNull(_binding) { "바인딩 error" }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMypageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val spId = UserSharedPreferences.getUserID(requireContext())
        val spNickname = UserSharedPreferences.getUserNickname(requireContext())
        val spMbti = UserSharedPreferences.getUserMbti(requireContext())

        val bundle = arguments
        val getId = bundle?.getString("userId")
        val getNickname = bundle?.getString("userNickname")
        val getMbti = bundle?.getString("userMbti")

        // 자동 로그인이 된 경우
        if (UserSharedPreferences.getUserID(requireContext()).isNotBlank()
        ) {
            binding.run {
                tvMainId.text = spId
                tvMainNickname.text = spNickname
                tvMainMbti.text = spMbti
            }
        } else {
            binding.run {
                tvMainId.text = getId
                tvMainNickname.text = getNickname
                tvMainMbti.text = getMbti
            }
        }

        // 로그아웃
        binding.btnMainLogout.setOnClickListener {
            UserSharedPreferences.clearUser(requireContext())


            val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
            fragmentManager
                .beginTransaction()
                .remove(this)
                .commit()


            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
        }

        binding.btnShowBottomsheet.setOnClickListener {
            showBottomSheet()
        }
    }
    fun showBottomSheet() {
        val shareFriendsFragment = ShareFriendsFragment()
        shareFriendsFragment.show(childFragmentManager, shareFriendsFragment.tag)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}