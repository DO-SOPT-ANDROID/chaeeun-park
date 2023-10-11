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

        // SignUpActivity에서 전달받은 데이터를 읽어옵니다.
        val receivedIntent = intent
        val nickname = receivedIntent.getStringExtra("nickname") // "nickname" 키
        val id = receivedIntent.getStringExtra("id") // "id" 키
        val mbti = receivedIntent.getStringExtra("mbti") // "mbti" 키


        binding.tvMainNickname.text = "$nickname"
        binding.tvMainId.text = "$id"
        binding.tvMainMbti.text = "$mbti"
    }
}
