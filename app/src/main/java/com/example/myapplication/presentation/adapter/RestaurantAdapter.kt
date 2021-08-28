package com.example.myapplication.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.RestaurantItemBinding
import com.example.myapplication.domain.entity.RestaurantEntity
import com.example.myapplication.presentation.view.MenuItemView

class RestaurantAdapter : RecyclerView.Adapter<RestaurantAdapter.ViewHolder>() {
    private var restaurants: ArrayList<RestaurantEntity>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.restaurant_item, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (restaurants == null) return

        holder.onBind(restaurants!![position])
    }

    override fun getItemCount(): Int {
        return restaurants?.size ?: 0
    }

    fun setRestaurants(restaurants: ArrayList<RestaurantEntity>) {
        this.restaurants = restaurants
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: RestaurantItemBinding) : RecyclerView.ViewHolder(itemView.root) {
        private var binding: RestaurantItemBinding = itemView

        fun onBind(restaurant: RestaurantEntity) {
            binding.restaurant = restaurant

            val menuItemViewHolder = itemView.findViewById<LinearLayout>(R.id.menuitem_holder)
            if (menuItemViewHolder.childCount > 0) menuItemViewHolder.removeAllViews()

            for (item in restaurant.menuList ?: ArrayList()) {
                val menuItemView = LayoutInflater.from(itemView.context)
                    .inflate(R.layout.menuitem_view, null) as MenuItemView
                menuItemView.setData(item)
                menuItemViewHolder.addView(menuItemView)
            }
            binding.executePendingBindings()
        }
    }
}