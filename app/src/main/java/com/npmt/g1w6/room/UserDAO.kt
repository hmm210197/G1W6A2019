package com.npmt.g1w6.room

import android.arch.persistence.room.*

@Dao
interface UserDAO {
    @Query("SELECT * FROM User")
    fun getAll():List<User>

    @Query("SELECT * FROM User WHERE id =:id")
    fun findById(id:Int):User

    @Query("SELECT * FROM User WHERE name =:name")
    fun findByName(name:String):User

    @Insert
    fun insertAll(vararg todo:User):List<Long>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(task: User):Long

    @Delete
    fun deleteUser(task: User)

    @Query("DELETE FROM User")
    fun deleteAll()

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateUser(user: User)

}