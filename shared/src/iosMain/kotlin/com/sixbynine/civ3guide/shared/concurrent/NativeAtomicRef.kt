package com.sixbynine.civ3guide.shared.concurrent

import kotlin.native.concurrent.AtomicReference
import kotlin.native.concurrent.freeze

actual class AtomicRef<T> actual constructor(value: T){

  private val reference = AtomicReference(value)

  actual fun set(value: T) {
    val frozenValue = value.freeze()
    reference.compareAndSet(frozenValue, frozenValue)
  }

  actual fun get(): T {
    return reference.value
  }
}

actual fun <T> atomicRef(): AtomicRef<T?> {
  return AtomicRef<T?>(value = null).freeze()
}

actual fun <T> atomicRef(initialValue: T): AtomicRef<T> {
  return AtomicRef(initialValue).freeze()
}