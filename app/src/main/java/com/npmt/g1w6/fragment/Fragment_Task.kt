package com.npmt.g1w6.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.npmt.g1w6.R
import com.npmt.g1w6.adapter.Adapter_Task
import com.npmt.g1w6.room.*
import kotlinx.android.synthetic.main.fragment_task.*

class Fragment_Task : Fragment() {

    var taskList : ArrayList<Task> = ArrayList()
    var userList : ArrayList<User> = ArrayList()
    lateinit var taskDb : TaskDatabase
    lateinit var userDb : UserDatabase
    lateinit var taskDAO : TaskDAO
    lateinit var userDAO : UserDAO

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_task, container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        //  get database
        this.context?.let{
            //  task
            taskDb = TaskDatabase.invoke(it)
            taskDAO = taskDb.taskDAO()
            taskList.addAll(taskDAO.getAll())
            //  user
            userDb = UserDatabase.invoke(it)
            userDAO = userDb.userDAO()
            userList.addAll(userDAO.getAll())
        }

        //  setup adapter
        list_task.layoutManager = LinearLayoutManager(this.activity)
        val adapter = Adapter_Task(taskList)
        list_task.adapter = adapter

        // click btn
        add_title_btn.setOnClickListener(View.OnClickListener {
            val nTitle = add_title_task.text.toString()
            if(nTitle != null && nTitle != "Title" && nTitle != ""){
                taskDAO.insert(Task(null,nTitle,false,null))
                adapter.notifyDataSetChanged()
            }else{
                Toast.makeText(this.context,"Not edit yet",Toast.LENGTH_SHORT).show()
            }
        })
    }
}