package com.alerdoci.marvelsuperheroes.app.screens.superhero

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alerdoci.marvelsuperheroes.R
import com.alerdoci.marvelsuperheroes.databinding.ActivitySuperheroBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SuperheroActivity : AppCompatActivity() {
    private var binding: ActivitySuperheroBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivitySuperheroBinding.inflate(this.layoutInflater)
        setContentView(this.binding!!.root)

        val superHeroId = this.intent.getIntExtra("superHeroId", 0)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, SuperHeroFragment.newInstance(superHeroId))
                .commitNow()
        }
    }
}
