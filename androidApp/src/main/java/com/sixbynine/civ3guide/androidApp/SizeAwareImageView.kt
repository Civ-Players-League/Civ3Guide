package com.sixbynine.civ3guide.androidApp

import android.graphics.Matrix
import android.widget.ImageView


fun ImageView.getActualScaleRatio(): Float {
    // Get image dimensions
    // Get image matrix values and place them in an array
    val f = FloatArray(9).also { imageMatrix.getValues(it) }

    // Extract the scale values using the constants (if aspect ratio maintained, scaleX == scaleY)
    val scaleX = f[Matrix.MSCALE_X]
    val scaleY = f[Matrix.MSCALE_Y]

    return minOf(scaleX, scaleY)
}