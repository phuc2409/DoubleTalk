package com.dualtalk.MainActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.dualtalk.R
import com.dualtalk.fragment.ChatFragment
import com.dualtalk.fragment.SettingFragment
import com.dualtalk.fragment.TinnhanchoFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val chat = ChatFragment()
        val tinnhancho = TinnhanchoFragment()
        val setting = SettingFragment()

        makeCurrentFragment(chat)

        val bottomnavigation = findViewById<BottomNavigationView>(R.id.bottomnavigation)
        bottomnavigation.setOnNavigationItemSelectedListener {
            when(it.itemId)
            {
                R.id.doanchat -> makeCurrentFragment(chat)
                R.id.tincho -> makeCurrentFragment(tinnhancho)
                R.id.settings -> makeCurrentFragment(setting)
            }
            true
        }
    }
    private fun makeCurrentFragment(fragment: Fragment)
    {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.doanchat,fragment)
            commit()
        }
    }
}