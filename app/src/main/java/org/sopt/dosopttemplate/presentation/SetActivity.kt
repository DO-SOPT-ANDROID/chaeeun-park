package org.sopt.dosopttemplate.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import org.sopt.dosopttemplate.R
import org.sopt.dosopttemplate.databinding.ActivitySetBinding
import org.sopt.dosopttemplate.presentation.android.DoAndroidFragment
import org.sopt.dosopttemplate.presentation.home.HomeFragment
import org.sopt.dosopttemplate.presentation.mypage.MypageFragment
import org.sopt.dosopttemplate.util.BackPressedUtil


class SetActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySetBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializeFragment()
        setupBottomNavigationView()
        handleBackButton()
    }

    private fun initializeFragment() {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.fcv_home)

        if (currentFragment == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fcv_home, HomeFragment())
                .commit()
        }
    }

    private fun setupBottomNavigationView() {
        binding.bnvHome.setOnItemSelectedListener { item ->
            val fragment = when (item.itemId) {
                R.id.menu_home -> HomeFragment()
                R.id.menu_do_android -> DoAndroidFragment()
                R.id.menu_mypage -> createMypageFragmentWithUserInfo()
                else -> return@setOnItemSelectedListener false
            }
            replaceFragment(fragment)
            true
        }
        binding.bnvHome.selectedItemId = R.id.menu_home

    }


    private fun createMypageFragmentWithUserInfo(): Fragment {
        val fragment = MypageFragment()
        val getId = intent.getStringExtra("ID")
        val getNickname = intent.getStringExtra("Nickname")
        val getMbti = intent.getStringExtra("MBTI")

        val bundle = Bundle().apply {
            putString("userId", getId)
            putString("userNickname", getNickname)
            putString("userMbti", getMbti)
        }

        fragment.arguments = bundle
        return fragment
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fcv_home, fragment)
            .commit()
    }

    private fun handleBackButton() {
        val backPressedUtil = BackPressedUtil<ActivitySetBinding>(this)
        backPressedUtil.BackButton()
    }
}

