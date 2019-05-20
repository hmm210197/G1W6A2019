package com.npmt.g1w6.fragment

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
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

            val imm = activity?.applicationContext?.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(getView()?.windowToken,0)


            val nName = name_user.text.toString()
            if(nName != null && nName != ""){
                Toast.makeText(this.context,"Add success", Toast.LENGTH_SHORT).show()
                val nUser = User(null,nName)
                val id = userDAO.insert(nUser)
                nUser.id = id.toInt()
                userList.add(nUser)
                name_user.text = null
            }else{
                Toast.makeText(this.context,"Not edit yet", Toast.LENGTH_SHORT).show()
            }
            list_user.adapter?.notifyDataSetChanged()
        })

        //  swipe to delete
        val swipeHandler = object : SwipeToDeleteCallback(requireContext()){
            override fun onSwiped(p0: RecyclerView.ViewHolder, p1: Int) {
                val adapter = list_user.adapter as Adapter_User
                adapter.removeAt(p0.adapterPosition)
                val vh = p0 as Adapter_User.User_Holder
                vh.delUser()
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(list_user)

    }
}