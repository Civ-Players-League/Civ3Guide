package com.sixbynine.civ3guide.android.level

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.children
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sixbynine.civ3guide.android.R
import com.sixbynine.civ3guide.android.ktx.getColorStateList
import com.sixbynine.civ3guide.android.util.Logger
import com.sixbynine.civ3guide.shared.MR.strings
import com.sixbynine.civ3guide.shared.format
import com.sixbynine.civ3guide.shared.level.LevelManager.PUZZLES_PER_ROW
import com.sixbynine.civ3guide.shared.level.LevelPageData
import com.sixbynine.civ3guide.shared.level.LevelPageRowData

class LevelsList(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {

  init {
    View.inflate(context, R.layout.levels_list_contents, this)
  }

  private val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
  private val adapter = ListAdapter()

  var listener: OnLevelClickListener? = null
  private var data: LevelPageData? = null

  init {
    recyclerView.adapter = adapter
    recyclerView.layoutManager = LinearLayoutManager(context)
  }

  fun setData(data: LevelPageData) {
    this.data = data
    adapter.notifyDataSetChanged()
  }

  private inner class ListAdapter : RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      val inflater = LayoutInflater.from(parent.context)
      val view = inflater.inflate(R.layout.levels_list_row, parent, false)

      val viewHolder = ViewHolder(view)
      repeat(PUZZLES_PER_ROW) {
        inflater.inflate(R.layout.levels_list_checkbox, viewHolder.checkboxContainer)
      }
      return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      val row = data?.rows?.get(position) ?: run {
        Logger.w("Asked to bind level row $position, but did not have data for it")
        return
      }

      holder.itemView.alpha = if (row.isLocked) 0.3f else 1f
      holder.title.text = strings.level_x.format(int = position + 1)
      holder.checkboxContainer.children.forEachIndexed { childIndex, child ->
        check(child is ImageView)
        if (childIndex >= row.total) {
          child.visibility = View.INVISIBLE
          return@forEachIndexed
        }
        child.visibility = View.VISIBLE
        if (childIndex < row.completed) {
          child.setImageResource(R.drawable.ic_circle_check)
          child.imageTintList = getColorStateList(R.color.green)
        } else {
          child.setImageResource(R.drawable.ic_circle)
          child.imageTintList = getColorStateList(R.color.colorTextPrimary)
        }
      }

      if (row.isLocked) {
        holder.itemView.isClickable = false
        holder.lockIcon.visibility = View.VISIBLE
      } else {
        holder.itemView.setOnClickListener {
          listener?.onLevelClick(position, row)
        }
        holder.lockIcon.visibility = View.INVISIBLE
      }
    }

    override fun getItemCount() = data?.rows?.size ?: 0
  }

  private class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val title: TextView = view.findViewById(R.id.title)
    val checkboxContainer: ViewGroup = view.findViewById(R.id.checkbox_container)
    val lockIcon: View = view.findViewById(R.id.lock)
  }

  interface OnLevelClickListener {
    fun onLevelClick(index: Int, level: LevelPageRowData)
  }
}