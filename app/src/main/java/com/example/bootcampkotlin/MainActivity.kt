package com.example.bootcampkotlin

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.content.edit
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bootcampkotlin.ContactDetail.Companion.EXTRA_CONTACT
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainActivity : AppCompatActivity(), ClickItemContactListener {

    private val rvList: RecyclerView by lazy {
        findViewById(R.id.rv_list)
    }
    private val adapter = ContactAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.drawer_menu)

        initDrawer()
        fetchListContact()
        bindViews()

    }

    private fun fetchListContact(){
        val list = arrayListOf(
            Contact(
                "Josebias Neto",
                "81 997698793",
                "img.png"
            ),
            Contact(
                "Isabele Lira",
                "81 995930981",
                "img.png"
            )
        )
        getInstanceSharedPreferences().edit{
            val json = Gson().toJson(list)
            putString("contacts", json)
            commit()
        }
    }

    private fun getInstanceSharedPreferences():SharedPreferences{
        return getSharedPreferences("com.exambple.bootcampkotlin.PREFERENCES", Context.MODE_PRIVATE)
    }

    private fun initDrawer(){
        val drawerLayout = findViewById<View>(R.id.drawer_layout) as DrawerLayout
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val toogle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer)
        drawerLayout.addDrawerListener(toogle)
        toogle.syncState()
    }

    private fun bindViews() {
        rvList.adapter = adapter
        rvList.layoutManager = LinearLayoutManager(this)
        updateList()
    }

    private fun getListContacts():List<Contact>{
        val list = getInstanceSharedPreferences().getString("contacts","[]")
        val turnsType = object : TypeToken<List<Contact>>() {}.type
        return Gson().fromJson(list,turnsType)
    }

    private fun updateList() {
        val list = getListContacts()
        adapter.updateList(list)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    private fun showToast(message: String){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.item_menu_1 -> {
                showToast("Exibindo item de menu 1")
                return true
            }
            R.id.item_menu_2 -> {
                showToast("Exibindo item de menu 2")
                return true
            }
                else -> super.onOptionsItemSelected(item)
        }
    }

    override fun clickItemContact(contact: Contact) {
        val intent = Intent(this,ContactDetail::class.java)
        intent.putExtra(EXTRA_CONTACT,contact)
        startActivity(intent)
    }


}