package org.sopt.dosopttemplate.people

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import org.sopt.dosopttemplate.databinding.FragmentPeopleBinding
import org.sopt.dosopttemplate.presentation.adapter.UserAdapter
import org.sopt.dosopttemplate.server.ServicePool
import org.sopt.dosopttemplate.server.user.UserApiManage
import org.sopt.dosopttemplate.server.user.UserRepository

class PeopleFragment : Fragment() {

    private var binding: FragmentPeopleBinding? = null
    private var adapter: UserAdapter? = null
    private lateinit var viewModel: PeopleViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPeopleBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // ViewModel 초기화
        viewModel = ViewModelProvider(this, PeopleViewModelFactory(UserRepository(UserApiManage(
            ServicePool.userService)))
        ).get(PeopleViewModel::class.java)

        // RecyclerView에 어댑터 설정
        setupRecyclerView()

        // API 호출 및 데이터 갱신
        viewModel.getUserList(page = 1)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // 뷰 바인딩 해제, 어댑터 해제
        binding = null
        adapter = null
    }

    private fun setupRecyclerView() {
        adapter = UserAdapter()
        binding?.rvPeople?.adapter = adapter
        binding?.rvPeople?.layoutManager = LinearLayoutManager(requireContext())

        viewModel.userList.observe(viewLifecycleOwner, Observer { userList ->
            adapter?.submitList(userList)
        })
    }
}
