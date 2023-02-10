package com.vkunitsyn.level3.ui

import android.widget.ImageView

interface RecyclerViewInterface {
    fun onItemClick(imageView: ImageView, position: Int)
}