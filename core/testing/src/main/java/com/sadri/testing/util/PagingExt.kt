package com.sadri.testing.util

import android.annotation.SuppressLint
import androidx.paging.CombinedLoadStates
import androidx.paging.DifferCallback
import androidx.paging.NullPaddedList
import androidx.paging.PagingData
import androidx.paging.PagingDataDiffer
import kotlinx.coroutines.test.TestDispatcher

@SuppressLint("RestrictedApi")
suspend fun <T : Any> PagingData<T>.collectDataForTest(
  dispatcher: TestDispatcher,
  onDone: (List<T>) -> Unit
): List<T> {
  val dcb = object : DifferCallback {
    override fun onChanged(position: Int, count: Int) {}
    override fun onInserted(position: Int, count: Int) {}
    override fun onRemoved(position: Int, count: Int) {}
  }
  val items = mutableListOf<T>()
  val dif = object : PagingDataDiffer<T>(dcb, dispatcher) {
    override suspend fun presentNewList(
      previousList: NullPaddedList<T>,
      newList: NullPaddedList<T>,
      lastAccessedIndex: Int,
      onListPresentable: () -> Unit
    ): Int? {
      for (idx in 0 until newList.size)
        items.add(newList.getFromStorage(idx))
      onDone.invoke(items)
      onListPresentable.invoke()
      return null
    }
  }
  dif.collectFrom(this)
  return items
}

@SuppressLint("RestrictedApi")
suspend fun <T : Any> PagingData<T>.collectStateForTest(
  dispatcher: TestDispatcher,
  onStateChange: (CombinedLoadStates) -> Unit
) {
  val dcb = object : DifferCallback {
    override fun onChanged(position: Int, count: Int) {}
    override fun onInserted(position: Int, count: Int) {}
    override fun onRemoved(position: Int, count: Int) {}
  }
  val dif = object : PagingDataDiffer<T>(dcb, dispatcher) {
    override suspend fun presentNewList(
      previousList: NullPaddedList<T>,
      newList: NullPaddedList<T>,
      lastAccessedIndex: Int,
      onListPresentable: () -> Unit
    ): Int? {
      onListPresentable.invoke()
      return null
    }
  }
  dif.addLoadStateListener {
    onStateChange.invoke(it)
  }
  dif.collectFrom(this)
}