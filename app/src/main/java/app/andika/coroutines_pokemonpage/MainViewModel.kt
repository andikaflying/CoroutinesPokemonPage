package app.andika.coroutines_pokemonpage

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.andika.coroutines_pokemonpage.utilities.singleArgViewModelFactory
import kotlinx.coroutines.launch

class MainViewModel(private val repository: MainRepository) : ViewModel() {
    private val TAG = MainViewModel::class.java.name

    companion object {
        val FACTORY = singleArgViewModelFactory(::MainViewModel)
    }

    private val _textResult = MutableLiveData<String>()
    val textResult: LiveData<String> get() = _textResult
    val pokemonName = repository.pokemonName

    fun displayPokemonData() {
        val id : Int = 9

        Log.e(TAG, "displayPokemonData()")

        viewModelScope.launch {
            try {
//                _spinner.value = true
                _textResult.value = "Berhasil"
                repository.getPokemon(id)
            } catch (error: TitleRefreshError) {
                _textResult.value = "Error masuk catch"
//                _snackBar.value = error.message
            } finally {
                _textResult.value = "Error masuk finally"
//                _spinner.value = false
            }
        }

    }
}