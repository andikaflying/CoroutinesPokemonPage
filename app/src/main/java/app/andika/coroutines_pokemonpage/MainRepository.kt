package app.andika.coroutines_pokemonpage

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map

class MainRepository(val network: MainNetwork, val pokemonDao: PokemonDao) {
    private val TAG = MainRepository::class.java.name

    val pokemonName: LiveData<String?> = pokemonDao.pokemonLiveData.map { it?.name }

    suspend fun getPokemon(id : Int) {

        try {
            // Make network request using a blocking call
            Log.e(TAG, "getPokemon")
            val result = network.fetchPokemon("/api/v2/pokemon/9")
            Log.e(TAG, "Name pokemon = " + result.body()!!.name)

            pokemonDao.insertPokemon(Pokemon(result.body()!!.name, ""))
        } catch (cause: Throwable) {
            // If anything throws an exception, inform the caller
            throw TitleRefreshError("Unable to refresh title", cause)
        }
    }
}

/**
 * Thrown when there was a error fetching a new title
 *
 * @property message user ready error message
 * @property cause the original cause of this exception
 */
class TitleRefreshError(message: String, cause: Throwable?) : Throwable(message, cause)

