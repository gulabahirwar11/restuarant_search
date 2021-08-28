package com.example.myapplication.presentation.view

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import com.example.myapplication.databinding.MenuitemViewBinding
import com.example.myapplication.domain.entity.MenuItemEntity

/** MenuItemView provide Custom View
 *  MenuItemView contain two child textViews title, description
 */
class MenuItemView : LinearLayout {
    private lateinit var binding: MenuitemViewBinding
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun onFinishInflate() {
        super.onFinishInflate()
        binding = MenuitemViewBinding.bind(this)
    }

    /**
     * set menuItem entity data into the view.
     * @param menuItem MenuItem entity
     */
    fun setData(menuItem: MenuItemEntity) {
        binding.menuItem = menuItem
        binding.executePendingBindings()
    }
}