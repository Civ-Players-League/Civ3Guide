package com.sixbynine.civ3guide.shared.concurrent

expect class AtomicRef<T> constructor(value: T) {
  fun set(value: T)

  fun get(): T
}

expect fun <T> atomicRef(): AtomicRef<T?>

expect fun <T> atomicRef(initialValue: T): AtomicRef<T>

