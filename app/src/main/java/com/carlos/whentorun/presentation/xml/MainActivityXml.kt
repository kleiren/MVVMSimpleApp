package com.carlos.whentorun.presentation.xml

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.carlos.whentorun.R
import com.carlos.whentorun.presentation.compose.MainActivityCompose
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivityXml : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.title = getString(R.string.toolbar_xml_title)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_to_compose) {
            startActivity(Intent(this, MainActivityCompose::class.java))
            this.finish()
        }
        return super.onOptionsItemSelected(item)
    }
}

