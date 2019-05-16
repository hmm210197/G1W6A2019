package com.npmt.g1w6.room

import android.arch.persistence.room.*

@Dao
interface TaskDAO {

    @Query("SELECT * FROM Task")
    fun getAll():List<Task>

    @Query("SELECT * FROM Task WHERE id =:id")
    fun findById(id:Int):Task

    @Query("SELECT * FROM Task WHERE user_uid=:uid")
    fun getUId(uid:Int):Task

    @Insert
    fun insertAll(vararg todo:Task):List<Long>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(task: Task):Long

    @Delete
    fun deleteTask(task: Task)

    @Query("DELETE FROM Task")
    fun deleteAll()

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateTask(task: Task)

}