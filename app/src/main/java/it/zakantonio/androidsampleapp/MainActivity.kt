package it.zakantonio.androidsampleapp

import android.os.Bundle
import it.zakantonio.androidsampleapp.core.BaseActivity
import it.zakantonio.androidsampleapp.databinding.ActivityMainBinding

// Activity principale dell'app: contiene la toolbar, il contenitore dei fragment
// e la BottomNavigationView per navigare tra ChatFragment e SettingsFragment.
class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Imposta la toolbar come ActionBar dell'app
        setSupportActionBar(binding.toolbar)

        // Carica il ChatFragment come schermata iniziale,
        // ma solo al primo avvio (savedInstanceState == null).
        // Quando l'app viene ricreata (es. rotazione), Android ripristina
        // automaticamente l'ultimo fragment, quindi non è necessario ricrearlo.
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, ChatFragment())
                .commit()
        }

        // Ascolta i tap sulla BottomNavigationView e sostituisce il fragment nel container
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            val fragment = when (item.itemId) {
                R.id.nav_chat -> ChatFragment()
                R.id.nav_settings -> SettingsFragment()
                else -> return@setOnItemSelectedListener false
            }
            // replace() rimuove il fragment corrente e lo sostituisce con quello nuovo
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit()
            true
        }
    }
}
