package com.example.habittracker.Adapters

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView

class SpaceDividerItemDecoration(context: Context?, orientation: Int, private val bottomSpace:Int) : DividerItemDecoration(context, orientation) {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val childAdapterPosition = parent.getChildAdapterPosition(view)
        if(childAdapterPosition == parent.adapter!!.itemCount - 1){
            outRect.bottom = bottomSpace
        }
        else{
            super.getItemOffsets(outRect, view, parent, state)
        }
    }

}
