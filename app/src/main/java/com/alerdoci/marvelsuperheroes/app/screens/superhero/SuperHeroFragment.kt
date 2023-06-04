package com.alerdoci.marvelsuperheroes.app.screens.superhero

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.alerdoci.marvelsuperheroes.app.screens.superhero.viewmodel.SuperHeroViewModel
import com.alerdoci.marvelsuperheroes.databinding.FragmentSuperheroBinding
import com.alerdoci.marvelsuperheroes.domain.models.features.superheroes.ModelResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SuperHeroFragment : Fragment() {

    companion object {
        fun newInstance(superHeroId: Int) = SuperHeroFragment()
            .apply { this.superHeroId = superHeroId }
    }

    var superHeroId: Int? = null

    private val viewModel: SuperHeroViewModel by viewModels()
    private var binding: FragmentSuperheroBinding? = null

    private var currentSuperHero: ModelResult? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.binding = FragmentSuperheroBinding.inflate(inflater)
        superHeroId?.let { viewModel.loadSuperHero(it) }
        return this.binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}