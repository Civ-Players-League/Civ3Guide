package com.sixbynine.civ3guide.android.cityplacement

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sixbynine.civ3guide.android.R
import com.sixbynine.civ3guide.android.cityplacement.CityPlacementPagerAdapter.ViewHolder
import com.sixbynine.civ3guide.android.cityplacement.CityPlacementPuzzleController.OnPuzzleStatesChangedObserver

class CityPlacementPagerAdapter(
  private val controller: CityPlacementPuzzleController,
  private val onPuzzleNextDoneClicked: () -> Unit
) : RecyclerView.Adapter<ViewHolder>() {

  init {
    setHasStableIds(true)
  }

  private val recyclerViews = mutableSetOf<RecyclerView>()

  private val observer = object : OnPuzzleStatesChangedObserver {
    override fun onPuzzleStatesChanged() {
      recyclerViews.firstOrNull()?.post {
        notifyDataSetChanged()
      }
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val view =
      LayoutInflater.from(parent.context)
        .inflate(R.layout.view_city_placement_puzzle, parent, false) as CityPlacementPuzzleView
    return ViewHolder(view)
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.view.bind(controller.getPuzzle(position), controller.isLastPuzzle(position))
    holder.view.onTileClickListener = {
      controller.setSelectedTile(position, it)
    }
    holder.view.onNextDoneClickListener = {
      onPuzzleNextDoneClicked()
    }
  }

  override fun getItemId(position: Int): Long {
    return position.toLong()
  }

  override fun getItemCount(): Int {
    return controller.getNumPuzzlesToDisplay()
  }

  override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
    super.onAttachedToRecyclerView(recyclerView)
    recyclerViews.add(recyclerView)
    controller.addObserver(observer)
  }

  override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
    super.onDetachedFromRecyclerView(recyclerView)
    recyclerViews.remove(recyclerView)
    if (recyclerViews.isEmpty()) {
      controller.removeObserver(observer)
    }
  }

  class ViewHolder(val view: CityPlacementPuzzleView) : RecyclerView.ViewHolder(view)
}