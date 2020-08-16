package com.demosample.smsanalyze

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.SwitchCompat
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var messageList: RecyclerView
    private lateinit var showChart: AppCompatButton
    private var messageAdapter: MessageAdapter? = null
    private val incomeMessage = ArrayList<String>()
    private val expenseMessage = ArrayList<String>()
    private var allLIst = ArrayList<String>()
    private lateinit var toggler: SwitchCompat
    var linearLayoutManager = LinearLayoutManager(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkPermissionGiven()
        showChart = findViewById(R.id.showChart)
        showChart.setOnClickListener {

            startActivity(Intent(this,PieActivity::class.java).putExtra("Income",incomeMessage).putExtra("Expense",expenseMessage))
        }
        messageList = findViewById(R.id.messageList)
        messageAdapter = MessageAdapter(this, allLIst)
        messageList.setLayoutManager(linearLayoutManager)
        messageList.setAdapter(messageAdapter)
        toggler = findViewById(R.id.switch1)
        toggler.setOnCheckedChangeListener { _, b ->
            if (b) {
                allLIst = expenseMessage
                messageAdapter!!.notifyChange(allLIst)
            } else {
                allLIst = incomeMessage
                messageAdapter!!.notifyChange(allLIst)
            }
        }
    }

    private fun checkPermissionGiven() {
        ActivityCompat.requestPermissions(this@MainActivity, arrayOf(Manifest.permission.READ_SMS),
                1)
    }

    // must check the result to prevent exception
    private val cursor: Unit
        private get() {
            val cursor = contentResolver.query(Uri.parse("content://sms/inbox"), null, null, null, null)
            if (cursor!!.moveToFirst()) { // must check the result to prevent exception
                do {
                    if (cursor.getString(12).contains("Credited") || cursor.getString(12).contains("credited") ||
                            cursor.getString(12).contains("CREDITED") || cursor.getString(12).contains("credit") ||
                            cursor.getString(12).contains("Credit")) incomeMessage.add(cursor.getString(12)) else if (cursor.getString(12).contains("Debited") || cursor.getString(12).contains("debited") ||
                            cursor.getString(12).contains("DEBITED") || cursor.getString(12).contains("debit") ||
                            cursor.getString(12).contains("Debit")) expenseMessage.add(cursor.getString(12))
                } while (cursor.moveToNext())
            } else {
            }
            showChart.visibility = View.VISIBLE
            allLIst = incomeMessage
            messageAdapter!!.notifyChange(allLIst)
        }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.size > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            cursor
        }
    }
}