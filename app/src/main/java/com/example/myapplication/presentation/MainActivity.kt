package com.example.myapplication.presentation

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import com.example.myapplication.R
import com.example.myapplication.presentation.adapter.RestaurantAdapter
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.domain.entity.MenuItemEntity
import com.example.myapplication.domain.entity.RestaurantEntity
import com.example.myapplication.domain.helper.JsonEntityHelper
import com.example.myapplication.domain.util.FileUtil.getJsonFromAssets
import java.util.stream.Collectors


class MainActivity : AppCompatActivity() {
    private lateinit var restaurants: ArrayList<RestaurantEntity>
    private lateinit var binding: ActivityMainBinding
    private val adapter by lazy { RestaurantAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        restaurants = JsonEntityHelper
            .getRestaurants(
                getJsonFromAssets(this, "restaurant.json"),
                JsonEntityHelper.getMenus(getJsonFromAssets(this, "menu.json"))
            )

        binding.recyclerView.adapter = adapter
        adapter.setRestaurants(restaurants)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)

        val menuItem: MenuItem? = menu?.findItem(R.id.action_search)
        val searchView = menuItem?.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                searchQueryInRestaurants(query)
                return true
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    private fun searchQueryInRestaurants(query: String?) {
        if (query.isNullOrEmpty()) { //query is null or empty then set original list into the adapter
            setSearchResultViewsVisibility(View.GONE, View.VISIBLE)
            adapter.setRestaurants(restaurants)
        } else {
            val foundRestaurants = getRestaurantsByQuery(query)
            adapter.setRestaurants(foundRestaurants)
            if (foundRestaurants.size > 0) {
                setSearchResultViewsVisibility(View.GONE, View.VISIBLE)
                adapter.setRestaurants(foundRestaurants)
            } else {
                setSearchResultViewsVisibility(View.VISIBLE, View.GONE)
            }
        }
    }

    /**
     * @param query search text query
     * @return found restaurant list
     */
    private fun getRestaurantsByQuery(query: String): ArrayList<RestaurantEntity> {
        val foundRestaurants = restaurants.stream()
            .filter {
                (it.name.contains(query, ignoreCase = true)
                        || it.cuisineType.contains(query, ignoreCase = true)
                        || it.address.contains(query, ignoreCase = true)
                        || isSearchQueryInMenuList(query, it.menuList ?: return@filter false))
            }.collect(Collectors.toList())
        return foundRestaurants as ArrayList<RestaurantEntity>
    }

    /**
     * If search search present in restaurant menu list then returns true otherwise false
     * @param query search text query
     * @param menuItems restaurant menuItems list
     * @return Boolean
     */
    private fun isSearchQueryInMenuList(query: String,
                                        menuItems: ArrayList<MenuItemEntity>): Boolean {
        for (menu in menuItems) {
            if (menu.name.contains(query, ignoreCase = true)) {
                return true
            }
        }
        return false
    }

    /**
     * @param visibility1 View VISIBILITY
     * @param visibility2 View VISIBILITY
     */
    private fun setSearchResultViewsVisibility(visibility1: Int, visibility2: Int) {
        if (binding.recyclerView.visibility == visibility1) binding.recyclerView.visibility =
            visibility2
        if (binding.noResult.visibility == visibility2) binding.noResult.visibility = visibility1
    }
}
