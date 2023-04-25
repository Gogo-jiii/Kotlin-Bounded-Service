package com.example.boundedservice

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.widget.Toast
import com.example.boundedservice.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    var boundedServiceIntent: Intent? = null
    var boundedService: BoundedService? = null
    private var isServiceBounded = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        boundedServiceIntent = Intent(this, BoundedService::class.java)

        bindService(boundedServiceIntent, serviceConnection, Context.BIND_AUTO_CREATE)

        Toast.makeText(this, "Service has been bounded", Toast.LENGTH_SHORT).show()

        binding.btnStartService.setOnClickListener {
            if (isServiceBounded) {
                val num = boundedService!!.data
                Toast.makeText(this, "Num: $num", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnStopService.setOnClickListener {
            if (isServiceBounded) {
                unbindService(serviceConnection)
                isServiceBounded = false
            }
        }
    }

    private val serviceConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            val binder = service as BoundedService.LocalBinder
            boundedService = binder.getService()
            isServiceBounded = true
        }

        override fun onServiceDisconnected(name: ComponentName) {
            isServiceBounded = false
        }
    }
}