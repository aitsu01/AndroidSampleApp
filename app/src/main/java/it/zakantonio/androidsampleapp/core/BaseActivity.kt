package it.zakantonio.androidsampleapp.core

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

// Questa è una classe base per le Activity
// che logga i vari eventi del ciclo di vita.
open class BaseActivity : AppCompatActivity() {

    private val TAG = this::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

        Log.d(TAG, "lifecycle activity: onCreate")
    }

    override fun onStart() {
        super.onStart()

        Log.d(TAG, "lifecycle activity: onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "lifecycle activity: onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "lifecycle activity: onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "lifecycle activity: onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "lifecycle activity: onDestroy")
    }
}