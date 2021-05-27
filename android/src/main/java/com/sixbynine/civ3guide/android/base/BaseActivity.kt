package com.sixbynine.civ3guide.android.base

import android.app.Activity
import android.content.ContextWrapper
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase

abstract class BaseActivity : AppCompatActivity() {

  internal lateinit var firebaseAnalytics: FirebaseAnalytics

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    firebaseAnalytics = Firebase.analytics
  }
}

val View.activity: Activity
  get() {
    var curContext = context
    while (curContext !is Activity) {
      check(curContext is ContextWrapper)
      curContext = curContext.baseContext
    }
    return curContext
  }

val View.baseActivity: BaseActivity
  get() = activity as BaseActivity

val View.firebaseAnalytics: FirebaseAnalytics
  get() = baseActivity.firebaseAnalytics