package com.example.androidsprint

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.findNavController
import com.example.androidsprint.databinding.ActivityMainBinding
import com.google.gson.Gson
import kotlinx.serialization.Serializable
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)


        val thread = Thread {
            val url = URL("https://recipes.androidsprint.ru/api/category")
            val connection = url.openConnection() as? HttpURLConnection
            connection?.connect()
            val json = connection?.getInputStream()?.bufferedReader()?.readText()
            connection?.disconnect()

            val categoryList = Gson().fromJson(json, Array<CategoryItem>::class.java)

            Log.i("!!!", "Выполняю запрос на потоке :${Thread.currentThread().name}")
            Log.i("!!!", "Метод onCreate() выполняется на потоке: :${Thread.currentThread().name}")
            Log.i("!!!", " :${categoryList}")
        }
        thread.start()

        val view = binding.root
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom
            )
            insets
        }
        setContentView(view)

        binding.btnCategory.setOnClickListener {
            findNavController(R.id.nav_host_fragment).navigate(R.id.categoriesListFragment)
        }

        binding.btnFavorite.setOnClickListener {
            findNavController(R.id.nav_host_fragment).navigate(R.id.favoritesFragment)
        }
    }
}

@Serializable
data class CategoryItem(
    val id: Int,
    val title: String,
    val description: String,
    val imageUrl: String
)
