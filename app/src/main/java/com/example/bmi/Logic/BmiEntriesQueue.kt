package com.example.bmi.Logic

import java.util.*

class BmiEntriesQueue {

    private val maxSize = 10
    private val entriesQueue:  LinkedList<BmiHistoryEntry> = LinkedList()

    fun add(entry: BmiHistoryEntry) {
        if (entriesQueue.size >= maxSize)
            entriesQueue.removeLast()
        entriesQueue.addFirst(entry)
    }

    fun clear(){
        entriesQueue.clear()
    }

    fun asArrayList(): ArrayList<BmiHistoryEntry>{
        return ArrayList(entriesQueue)
    }
}