package com.sixbynine.civ3guide.shared

import android.widget.ImageView
import android.widget.TextView
import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.desc.StringDesc

actual fun StringDesc.load(): String = this.toString(Civ3GuideApplication.instance)

fun ImageView.setSharedImageResource(resource: ImageResource) {
  setImageResource(resource.drawableResId)
}

fun TextView.setTextResource(resource: StringResource?) {
  if (resource == null) {
    text = ""
  } else {
    setText(resource.resourceId)
  }
}