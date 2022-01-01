package com.example.firebaseuygulama.view

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class CustomDecoration(val iSize: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.bottom = iSize
    }
}