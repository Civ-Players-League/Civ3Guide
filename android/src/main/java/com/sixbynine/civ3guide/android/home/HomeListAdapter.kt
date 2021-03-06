package com.sixbynine.civ3guide.android.home

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sixbynine.civ3guide.android.R
import com.sixbynine.civ3guide.android.cityplacement.CityPlacementHomeActivity
import com.sixbynine.civ3guide.android.cityplacement.CityPlacementPuzzleActivity
import com.sixbynine.civ3guide.android.combat.CombatGameActivity
import com.sixbynine.civ3guide.android.ktx.isLtr
import com.sixbynine.civ3guide.android.ktx.isRtl
import com.sixbynine.civ3guide.android.quiz.QuizActivity
import com.sixbynine.civ3guide.android.quiz.QuizHomeActivity
import com.sixbynine.civ3guide.android.workerpuzzle.WorkerPuzzleActivity
import com.sixbynine.civ3guide.android.workerpuzzle.WorkerPuzzleHomeActivity
import com.sixbynine.civ3guide.shared.home.HomeDestination
import com.sixbynine.civ3guide.shared.home.HomeDestination.*
import com.sixbynine.civ3guide.shared.setSharedImageResource
import com.sixbynine.civ3guide.shared.setTextResource
import de.hdodenhof.circleimageview.CircleImageView

class HomeListAdapter : RecyclerView.Adapter<HomeListAdapter.ViewHolder>() {

  private val destinations = HomeDestination.getAll()

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val view = LayoutInflater.from(parent.context)
      .inflate(R.layout.item_home_destination, parent, /* attachToParent= */ false)
    return ViewHolder(view)
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val destination = destinations[position]
    holder.image.setSharedImageResource(destination.image)
    holder.image.scaleX = if (holder.image.isLtr) 1f else -1f
    holder.title.setTextResource(destination.title)
    holder.summary.setTextResource(destination.summary)
    holder.rootView.setOnClickListener { navigateToDestination(it.context, destination) }
  }

  override fun getItemCount() = destinations.size

  private fun navigateToDestination(context: Context, destination: HomeDestination) {
    val intent = when (destination) {
      WORKER_PUZZLE -> Intent(context, WorkerPuzzleHomeActivity::class.java)
      CITY_PLACEMENT -> Intent(context, CityPlacementHomeActivity::class.java)
      COMBAT_ODDS -> Intent(context, CombatGameActivity::class.java)
    }
    context.startActivity(intent)
  }

  class ViewHolder(val rootView: View) : RecyclerView.ViewHolder(rootView) {
    val image: CircleImageView = rootView.findViewById(R.id.image)
    val title: TextView = rootView.findViewById(R.id.title)
    val summary: TextView = rootView.findViewById(R.id.summary)
  }
}