package com.npmt.g1w6

import android.arch.persistence.room.Room
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.npmt.g1w6.fragment.Fragment_Task
import com.npmt.g1w6.fragment.Fragment_User
import com.npmt.g1w6.room.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.drawer_header.view.*

class MainActivity : AppCompatActivity(){

    var taskList : ArrayList<Task> = ArrayList()
    var userList : ArrayList<User> = ArrayList()
    lateinit var taskDAO : TaskDAO
    lateinit var userDAO : UserDAO
    var inTaskFrag = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val b = getIntent().getExtras()
        repeat(0){
            if(b.getBoolean("isBacked")){
                val currentFragment = supportFragmentManager.findFragmentById(R.id.frame)
                val transaction = supportFragmentManager.beginTransaction()
                currentFragment?.let{
                    transaction.detach(it).attach(it).commit()
                }
            }
        }

        //  show icon menu (replace R.id.home)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_menu)
        }

        //fragment
        switchContent(Fragment_Task())

        //  drawer item listener
        Picasso.get().load(R.drawable.avatar).into(nav_view.getHeaderView(0).logo)
        //  menu selected task or user
        if(inTaskFrag){ nav_view.menu.getItem(0).setChecked(true) }
        else { nav_view.menu.getItem(1).setChecked(true) }
        //  change frag
        nav_view.setNavigationItemSelectedListener{
            when(it.itemId){
                R.id.nav_task->{
                    supportActionBar?.title = "Task"
                    switchContent(Fragment_Task())
                    inTaskFrag = true   //  drawer selected item is task
                }
                R.id.nav_user->{
                    supportActionBar?.title = "User"
                    switchContent(Fragment_User())
                    inTaskFrag = false  // drawe selected item is user
                }
            }
            drawer_layout.closeDrawer(GravityCompat.START)
            true
        }

        //  init database
        initDatabase()

    }

    //  click menu(home) listener
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                drawer_layout.openDrawer(GravityCompat.START)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    //  funtion change fragment
    private fun switchContent(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame,fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    //  initial database
    private fun initDatabase(){
        val taskDb = Room.databaseBuilder(
            applicationContext,
            TaskDatabase::class.java, TASK_DATABASE_NAME
        ).allowMainThreadQueries()
            .build()
        taskDAO = taskDb.taskDAO()

        val userDb = Room.databaseBuilder(
            applicationContext,
            UserDatabase::class.java, USER_DATABASE_NAME
        ).allowMainThreadQueries()
            .build()
        userDAO = userDb.userDAO()
    }

    private fun getTasks() {
        val taskList = taskDAO.getAll() // get Tasks from ROOM database
        this.taskList.clear()           // clear all before add
        this.taskList.addAll(taskList) // add to Tasks list
    }
    private fun getUsers() {
        val userList = userDAO.getAll() // get Users from ROOM database
        this.userList.clear()           // clear all before add
        this.userList.addAll(userList) // add to Users list
    }

}
