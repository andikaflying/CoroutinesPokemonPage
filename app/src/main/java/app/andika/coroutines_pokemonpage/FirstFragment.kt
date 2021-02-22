package app.andika.coroutines_pokemonpage

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import app.andika.coroutines_pokemonpage.model.PokemonData
import com.google.gson.internal.bind.ArrayTypeAdapter.FACTORY
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {
    private val TAG = FirstFragment::class.java.name

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Get MainViewModel by passing a database to the factory
        val database = getDatabase(requireContext())
        val repository = MainRepository(getNetworkService(), database.pokemonDao)
        val viewModel = ViewModelProviders
            .of(this, MainViewModel.FACTORY(repository))
            .get(MainViewModel::class.java)
        val tvResult: TextView = view.findViewById(R.id.tvResult)

        Log.e(TAG, "onViewCreated")

        viewModel.pokemonName.observe(viewLifecycleOwner) { value ->
            tvResult.setText(value)
        }

        viewModel.displayPokemonData()
    }
}