package org.sopt.dosopttemplate.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.sopt.dosopttemplate.data.DummyFriendsData
import org.sopt.dosopttemplate.databinding.FragmentHomeBinding
import org.sopt.dosopttemplate.presentation.adapter.FriendsSealedAdapter

class HomeFragment : Fragment() {

    // 코드리뷰 반영 어댑터 전역변수로 선언해두고, 뷰 종료시 함께 지워서 메모리 누수 방지하기
    private var _binding: FragmentHomeBinding? = null
    private var _adapter: FriendsSealedAdapter? = null

    private val binding: FragmentHomeBinding
        get() = requireNotNull(_binding) { "바인딩 error" }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // RecyclerView에 어댑터 설정하고 더미 데이터 추가
        setupRecyclerView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // 뷰 바인딩 해제, 어댑터 해제(코드리뷰 반영)
        _binding = null
        _adapter = null
    }

    private fun setupRecyclerView() {
        val friendsSealedAdapter = FriendsSealedAdapter(requireContext())
        binding.rvFriends.adapter = friendsSealedAdapter
        friendsSealedAdapter.setFriendsData(ArrayList(DummyFriendsData.dummyFriendList))
    }
}


