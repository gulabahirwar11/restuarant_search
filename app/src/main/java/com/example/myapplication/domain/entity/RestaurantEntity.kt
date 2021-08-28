package com.example.myapplication.domain.entity


data class RestaurantEntity(
        val id: String,
        val name: String,
        val cuisineType: String,
        val address: String,
        val menuList: ArrayList<MenuItemEntity>? = null
)

data class MenuItemEntity(
        val name: String, val description: String
)
