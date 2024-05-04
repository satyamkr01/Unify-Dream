package com.eservecloud.unifydream.ui

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.eservecloud.unifydream.R
import com.eservecloud.unifydream.databinding.ActivityBaseBinding

open class BaseActivity : AppCompatActivity() {
    private lateinit var progressBar: Dialog
    private lateinit var binding : ActivityBaseBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityBaseBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainBaseView)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun showProgressBar() {
        progressBar = Dialog(this)
        progressBar.setContentView(R.layout.progress_bar)
        progressBar.setCancelable(false)
        progressBar.show()
    }

    fun hideProgressBar() {
        progressBar.dismiss()
    }

    fun showToast(activity: Activity, msg:String) {
        Toast.makeText(activity,msg, Toast.LENGTH_SHORT).show()
    }
}