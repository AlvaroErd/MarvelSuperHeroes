package com.alerdoci.marvelsuperheroes.app.screens.superhero

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import coil.load
import com.alerdoci.marvelsuperheroes.R
import com.alerdoci.marvelsuperheroes.app.common.states.ResourceState
import com.alerdoci.marvelsuperheroes.app.screens.superhero.viewmodel.SuperHeroViewModel
import com.alerdoci.marvelsuperheroes.databinding.FragmentSuperheroBinding
import com.alerdoci.marvelsuperheroes.domain.models.features.superheroes.ModelResult
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class SuperHeroFragment : Fragment() {

    companion object {
        fun newInstance(superHeroId: Int) = SuperHeroFragment()
            .apply { this.superHeroId = superHeroId }
    }

    var superHeroId: Int = 0

    private val viewModel: SuperHeroViewModel by viewModels()
    private var binding: FragmentSuperheroBinding? = null
    private var currentSuperHero: List<ModelResult> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = FragmentSuperheroBinding.inflate(inflater)
        viewModel.loadSuperHero(superHeroId)
        return this.binding!!.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                launch(Dispatchers.IO) {
                    viewModel.currentSuperHero.collectLatest { superHeroState ->
                        when (superHeroState) {
                            is ResourceState.Loading -> {}
                            is ResourceState.Success -> {
                                currentSuperHero = superHeroState.data as List<ModelResult>
                                withContext(Dispatchers.Main) {
                                    loadSuperHero()
                                }
                            }

                            is ResourceState.Error -> {}
                            else -> {}
                        }

                    }
                }
            }
        }
    }

    private fun loadSuperHero() {
        this.binding?.apply {

            this.tvCharacterName.text = currentSuperHero[0].name
            this.ivCharacterImage.load(currentSuperHero[0].imageFinal)
            if (currentSuperHero[0].description?.isEmpty() == true) {
                tvCharacterDescription.text = getString(R.string.description_not_available)
            } else {
                tvCharacterDescription.text = currentSuperHero[0].description
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        this.binding = null
        this.currentSuperHero = emptyList()
    }
}
