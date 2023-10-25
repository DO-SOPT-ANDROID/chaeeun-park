package org.sopt.dosopttemplate

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.sopt.dosopttemplate.databinding.ActivityMainBinding
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        readDataAndPopulateViews()
    }

    private fun readDataAndPopulateViews() {
        val receivedIntent = intent
        val nickname = receivedIntent.getStringExtra("nickname") // "nickname" key
        val id = receivedIntent.getStringExtra("id") // "id" key
        val mbti = receivedIntent.getStringExtra("mbti") // "mbti" key

        binding.tvMainNickname.text = "$nickname"
        binding.tvMainId.text = "$id"
        binding.tvMainMbti.text = "$mbti"
    }
}
