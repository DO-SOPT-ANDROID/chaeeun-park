package org.sopt.dosopttemplate.presentation.mypage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.sopt.dosopttemplate.databinding.BottomSheetMypageBinding


class ShareFriendsFragment : BottomSheetDialogFragment() {

    private var _binding: BottomSheetMypageBinding? = null
    private val binding: BottomSheetMypageBinding
        get() = requireNotNull(_binding) { "바인딩 error" }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetMypageBinding.inflate(inflater, container, false) // 바인딩 초기화
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.closeButton.setOnClickListener {
            dismiss() // 바텀시트 닫기
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // 바인딩 해제
    }
}