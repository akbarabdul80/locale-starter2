package com.zero.localestarter

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.zero.localestarter.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.fab.setOnClickListener {
            showHelp()
        }
    }

    private fun showHelp() {
        val helpIntent = Intent(this, HelpActivity::class.java)
        startActivity(helpIntent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id: Int = item.itemId
        if (id == R.id.action_help) {
            val intent = Intent(this, HelpActivity::class.java)
            startActivity(intent)
            return true
        } else if (id == R.id.action_language) {
            val intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(intent)
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}