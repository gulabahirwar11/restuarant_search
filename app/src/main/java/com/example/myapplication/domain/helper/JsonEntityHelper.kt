package com.example.myapplication.domain.helper

import android.util.Log
import com.example.myapplication.domain.entity.MenuItemEntity
import com.example.myapplication.domain.entity.RestaurantEntity
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

object JsonEntityHelper {
    private val TAG = JsonEntityHelper::class.simpleName

    /**
     * Convert restaurant json string into Restaurant list entity
     * @param jsonString Json String
     * @param menus : hashmap with key as restaurantId and value list of menuItems entity
     * @return List of Restaurant entity
     */
    fun getRestaurants(jsonString: String?,
                       menus: HashMap<String, ArrayList<MenuItemEntity>>): ArrayList<RestaurantEntity> {
        if (jsonString == null) return ArrayList()

        val restaurants = ArrayList<RestaurantEntity>()

        try {
            val jsonObject = JSONObject(jsonString)
            val restaurantArray: JSONArray = jsonObject.getJSONArray("restaurants")

            for (i in 0 until restaurantArray.length()) {
                val restaurantObject = restaurantArray.getJSONObject(i)
                val id: String = restaurantObject.getString("id")
                val name: String = restaurantObject.getString("name")
                val cuisineType: String = restaurantObject.getString("cuisine_type")
                val address: String = restaurantObject.getString("address")
                restaurants.add(RestaurantEntity(id, name, cuisineType, address, menus[id]))
            }
        } catch (e: JSONException) {
            Log.e("$TAG - getRestaurants", e.message.toString())
        }
        return restaurants
    }

    /**
     * Convert menu json string into Hashmap. restaurantId is the key and
     * ArrayList<MenuItem> is value of returning hashmap
     * @param jsonString Json String
     * @return Hashmap with restaurantId as key and value list of menuItems entity
     */
    fun getMenus (jsonString: String?): HashMap<String, ArrayList<MenuItemEntity>> {
        if (jsonString == null) return HashMap()

        val hashmap = HashMap<String, ArrayList<MenuItemEntity>>()
        try {
            val jsonObject = JSONObject(jsonString)
            val menuArray: JSONArray = jsonObject.getJSONArray("menus")

            for (i in 0 until menuArray.length()) {
                val menuObject: JSONObject = menuArray.getJSONObject(i)
                val categoriesArray: JSONArray = menuObject.getJSONArray("categories")
                val menuItems = ArrayList<MenuItemEntity>()

                for (j in 0 until categoriesArray.length()) {
                    val categoriesObject = categoriesArray.getJSONObject(j)
                    val menuItemArray = categoriesObject.getJSONArray("menu-items")

                    for (k in 0 until menuItemArray.length()) {
                        val menuItemObject = menuItemArray.getJSONObject(k)
                        val name = menuItemObject.getString("name")
                        val description = menuItemObject.getString("description")
                        menuItems.add(MenuItemEntity(name, description))
                    }
                }
                hashmap[menuObject.getString("restaurantId")] = menuItems
            }
        } catch (e: JSONException) {
            Log.e("$TAG - getMenus", e.message.toString())
        }
        return hashmap
    }
}