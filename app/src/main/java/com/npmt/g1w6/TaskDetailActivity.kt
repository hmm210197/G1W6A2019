package com.npmt.g1w6

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.npmt.g1w6.room.*
import kotlinx.android.synthetic.main.activity_task_detail.*

class TaskDetailActivity : AppCompatActivity() {

    lateinit var selectedTask : Task
    var taskList : ArrayList<Task> = ArrayList()
    var userList : ArrayList<User> = ArrayList()
    lateinit var taskDAO : TaskDAO
    lateinit var userDAO : UserDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_detail)

        //  get transfer data
        val b = getIntent().getExtras()
        selectedTask = b.getParcelable("task")

        //  get database
        val taskDB = TaskDatabase.invoke(applicationContext)
        taskDAO = taskDB.taskDAO()
        val userDB = UserDatabase.invoke(applicationContext)
        userDAO = userDB.userDAO()

        //  set title
        detail_title.text = selectedTask.decription
        //  del task
        btnDelTask.setOnClickListener(View.OnClickListener {
            taskDAO.deleteTask(selectedTask)
            Toast.makeText(this,"Dell success task "+selectedTask.decription,Toast.LENGTH_SHORT).show()
            val goMain = Intent(this,MainActivity::class.java)
            startActivity(goMain)
            finish()
        })

        //  change switch
        swComplete.isChecked = selectedTask.completed
        swComplete.setOnClickListener(View.OnClickListener {
            selectedTask.completed = swComplete.isChecked
            taskDAO.updateTask(selectedTask)
            Toast.makeText(this,if(swComplete.isChecked){"Completed "}else{"Uncompleted "}+selectedTask.decription,Toast.LENGTH_SHORT).show()
        })
        // !!! warning about change data

        //  dropdown
        var arrayUser = ArrayList<String>()
        arrayUser.add("Unassigned")
        userDAO.getAll().forEach { arrayUser.add(it.name) }
        //  make adapter
        var arrayAdapter = ArrayAdapter<String>(this,R.layout.spinner_item,arrayUser)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        assigment.adapter = arrayAdapter
        //  set selected from data
        val selectedTaskUID = selectedTask.user_uid
        for(i in 0..assigment.count-1){
            if(selectedTaskUID!=null){
                if(userDAO.findById(selectedTaskUID).name == assigment.getItemAtPosition(i).toString())
                    assigment.setSelection(i)
            }else{
                assigment.setSelection(0)
            }
        }
        //  catch some thing
        assigment.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                parent?.count?.let{parent.setSelection(it-1)}
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, d: Long) {

                //  tranfer data choose to Database
                if(parent?.getItemAtPosition(position).toString()!="Unassigned") {
                    selectedTask.user_uid = userDAO.findByName(parent?.getItemAtPosition(position).toString()).id
                }else{
                    selectedTask.user_uid = null
                }
                taskDAO.updateTask(selectedTask)

            }
        }

    }

    override fun onBackPressed() {
        val goMain = Intent(this,MainActivity::class.java)
        val b = Bundle()
        b.putBoolean("isBacked",true)
        goMain.putExtras(b)
        startActivity(goMain)
        finish()
    }
}
