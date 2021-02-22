package app.andika.coroutines_pokemonpage

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*


@Entity
data class Pokemon constructor(val name: String, val imageURL: String, @PrimaryKey val id: Int = 0)

@Dao
interface PokemonDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemon(pokemon: Pokemon)

    @get:Query("select * from Pokemon where id = 0")
    val pokemonLiveData: LiveData<Pokemon?>
}

@Database(entities = [Pokemon::class], version = 1, exportSchema = false)
abstract class PokemonDatabase : RoomDatabase() {
    abstract val pokemonDao: PokemonDao
}

private lateinit var INSTANCE: PokemonDatabase

fun getDatabase(context: Context): PokemonDatabase {
    synchronized(PokemonDatabase::class) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room
                .databaseBuilder(
                    context.applicationContext,
                    PokemonDatabase::class.java,
                    "pokemon_db"
                )
                .fallbackToDestructiveMigration()
                .build()
        }
    }
    return INSTANCE
}
