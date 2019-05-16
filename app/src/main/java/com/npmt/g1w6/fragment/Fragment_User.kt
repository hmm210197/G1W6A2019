package com.npmt.g1w6.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.npmt.g1w6.R
import com.npmt.g1w6.adapter.Adapter_User
import com.npmt.g1w6.room.*
import kotlinx.android.synthetic.main.fragment_user.*

class Fragment_User : Fragment() {

    var taskList : ArrayList<Task> = ArrayList()
    var userList : ArrayList<User> = ArrayList()
    lateinit var taskDb : TaskDatabase
    lateinit var userDb : UserDatabase
    lateinit var taskDAO : TaskDAO
    lateinit var userDAO : UserDAO

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_user, container,false)
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
        list_user.layoutManager = LinearLayoutManager(this.activity)
        val adapter = Adapter_User(userList)
        list_user.adapter = adapter

        // click btn add
        add_user_btn.setOnClickListener(View.OnClickListener {
            val nName = name_user.text.toString()
            Log.i("nName:",nName)
            if(nName != null && nName != "Name" && nName != ""){
                Toast.makeText(this.context,"Add success", Toast.LENGTH_SHORT).show()
                userDAO.insert(User(null,nName))
                adapter.notifyDataSetChanged()
            }else{
                Toast.makeText(this.context,"Not edit yet", Toast.LENGTH_SHORT).show()
            }
        })

    }
}