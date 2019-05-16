package com.npmt.g1w6.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

@Database(entities = arrayOf(Task::class),version = 1)
abstract class TaskDatabase : RoomDatabase(){

    abstract fun taskDAO():TaskDAO

    companion object {
        @Volatile
        private var instance: TaskDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context,
            TaskDatabase::class.java, "TaskDatabase"
        ).allowMainThreadQueries()
            .build()
    }

}