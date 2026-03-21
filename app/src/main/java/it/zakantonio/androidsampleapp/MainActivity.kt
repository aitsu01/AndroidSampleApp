package it.zakantonio.androidsampleapp

import android.os.Bundle
import androidx.navigation.ui.AppBarConfiguration
import it.zakantonio.androidsampleapp.core.BaseActivity
import it.zakantonio.androidsampleapp.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

    }
}