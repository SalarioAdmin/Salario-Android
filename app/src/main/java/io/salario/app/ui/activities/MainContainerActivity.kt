package io.salario.app.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.salario.app.databinding.ActivityMainBinding

class MainContainerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}