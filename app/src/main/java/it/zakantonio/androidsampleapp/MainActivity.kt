package it.zakantonio.androidsampleapp

import android.os.Bundle
import it.zakantonio.androidsampleapp.core.BaseActivity
import it.zakantonio.androidsampleapp.databinding.ActivityMainBinding

// Activity principale dell'app: contiene la toolbar e il contenitore dei fragment.
// Gestisce la navigazione tra CharacterListFragment e CharacterDetailFragment.
class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        // Carica la lista come schermata iniziale.
        // savedInstanceState != null significa che Android sta ripristinando l'app
        // (es. dopo una rotazione), quindi il fragment è già presente nel back stack.
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, CharacterListFragment())
                .commit()
        }
    }

    // Apre il fragment di dettaglio per il personaggio con l'ID indicato.
    // addToBackStack(null) consente di tornare indietro con il tasto Back.
    fun apriDettaglio(id: Int) {
        val fragment = CharacterDetailFragment.newInstance(id)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    // Delegato al sistema di back press quando si preme la freccia "indietro" nella toolbar
    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}
