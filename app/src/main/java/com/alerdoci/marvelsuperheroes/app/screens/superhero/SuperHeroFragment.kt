package com.alerdoci.marvelsuperheroes.app.screens.superhero

import android.content.Intent
import android.content.res.ColorStateList
import android.net.Uri
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
import com.alerdoci.marvelsuperheroes.app.screens.superhero.composable.ComicsList
import com.alerdoci.marvelsuperheroes.app.screens.superhero.viewmodel.SuperHeroViewModel
import com.alerdoci.marvelsuperheroes.databinding.FragmentSuperheroBinding
import com.alerdoci.marvelsuperheroes.domain.models.features.superherocomics.ModelComicsSuperHeroList
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
    private var currentSuperHeroComic: List<ModelComicsSuperHeroList> = emptyList()

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
                            is ResourceState.Loading -> {
                                binding?.progressBar?.visibility = View.VISIBLE
                            }

                            is ResourceState.Success -> {
                                currentSuperHero = superHeroState.data as List<ModelResult>
                                withContext(Dispatchers.Main) {
                                    loadSuperHero()
                                }
                                binding?.progressBar?.visibility = View.GONE
                            }

                            is ResourceState.Error -> {
                                binding?.ivError?.visibility = View.VISIBLE
                                binding?.tvError?.visibility = View.VISIBLE
                            }

                            else -> {}
                        }
                    }
                }

                viewModel.currentSuperHeroComic.collectLatest { superHeroComicState ->
                    when (superHeroComicState) {
                        is ResourceState.Loading -> {
                            binding?.progressBar?.visibility = View.VISIBLE
                        }

                        is ResourceState.Success -> {
                            currentSuperHeroComic =
                                superHeroComicState.data as List<ModelComicsSuperHeroList>
                            withContext(Dispatchers.Main) {
                                loadSuperHeroComics()
                            }
                            binding?.progressBar?.visibility = View.GONE
                        }

                        is ResourceState.Error -> {
                            binding?.ivError?.visibility = View.VISIBLE
                            binding?.tvError?.visibility = View.VISIBLE
                        }

                        else -> {}
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
            this.btWiki.setOnClickListener {
                val url = currentSuperHeroComic[0].data?.results?.get(0)?.urls?.get(1)?.url
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(url)
                startActivity(intent)
            }
            val colorInt: Int? = context?.getColor(R.color.amber_500)
            this.btWiki.strokeColor = colorInt?.let { ColorStateList.valueOf(it) }
        }
    }

    private fun loadSuperHeroComics() {
        this.binding?.apply {
            val comics = binding!!.compviewComics
            comics.setContent {
                ComicsList(currentSuperHeroComic)
            }
            this.tvMarvelAttribution.text = currentSuperHeroComic[0].attributionText

            if (currentSuperHeroComic[0].data?.results?.size == 0) {
                tvNoRecentComics.visibility = View.VISIBLE
            } else {
                tvNoRecentComics.visibility = View.GONE
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        this.binding = null
        this.currentSuperHero = emptyList()
    }
}
