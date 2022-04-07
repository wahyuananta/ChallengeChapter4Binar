package com.coder.challengechapter4binar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.coder.challengechapter4binar.databinding.ActivitySplashScreenBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        CoroutineScope(Dispatchers.Main).launch {
            delay(4000L)
            startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
        }

        binding.ivBola.alpha = 0f
        binding.ivBola.animate().setDuration(4000).alpha(1f).withEndAction {
            val intentKeMain = Intent(this, MainActivity::class.java)
            startActivity(intentKeMain)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }
    }
}