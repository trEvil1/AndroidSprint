package com.example.androidsprint

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.findNavController
import com.example.androidsprint.databinding.ActivityMainBinding
import com.example.androidsprint.model.Category
import com.example.androidsprint.model.Recipe
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val threadPool = Executors.newFixedThreadPool(10)

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        threadPool.execute {
            val interceptor = HttpLoggingInterceptor().apply {
                level =
                    HttpLoggingInterceptor.Level.BODY
            }
            val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
            val request: Request =
                Request.Builder().url("https://recipes.androidsprint.ru/api/category").build()

            client.newCall(request).execute().use { response ->
                val json = response.body.string()

                val categoryList = Gson().fromJson(json, Array<Category>::class.java)
                val idList = categoryList.map { it.id }

                idList.forEach { categoryId ->
                    threadPool.execute {
                        val request: Request =
                            Request.Builder()
                                .url("https://recipes.androidsprint.ru/api/category/$categoryId/recipes")
                                .build()
                        client.newCall(request).execute().use { response ->
                            val json =
                                response.body.string()

                            val recipes = Gson().fromJson(json, Array<Recipe>::class.java)
                            Log.i("!!!!!!!", "Категория $categoryId: ${recipes.size} рецептов\"")
                        }
                    }
                }
            }
        }

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

    override fun onDestroy() {
        super.onDestroy()
        threadPool.shutdown()
    }
}
