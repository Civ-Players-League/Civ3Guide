package com.sixbynine.civ3guide.shared.home

import com.sixbynine.civ3guide.shared.MR.images
import com.sixbynine.civ3guide.shared.MR.strings
import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.StringResource

enum class HomeDestination(
  val image: ImageResource,
  val title: StringResource,
  val summary: StringResource? = null
) {
  QUIZ(
    images.worker,
    strings.home_label_quiz_title,
    strings.home_label_quiz_summary
  ),
  WORKER_PUZZLE(
    images.worker,
    strings.home_label_worker_action_title,
    strings.home_label_worker_action_summary
  );

  companion object {
    fun getAll() = values().toList()
  }
}