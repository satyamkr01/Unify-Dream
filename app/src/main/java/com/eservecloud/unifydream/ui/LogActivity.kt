package com.eservecloud.unifydream.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.eservecloud.unifydream.databinding.ActivityLogBinding
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class LogActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLogBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLogBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.mainLogView) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.ivUploadRecording.setOnClickListener {
            selectAudio()
        }

        binding.ivPlayRecording.setOnClickListener {

        }

        binding.changeRecording.setOnClickListener {
            selectAudio()
        }

        binding.btnSaveActivity.setOnClickListener {
            binding.rlUploadRecording.visibility = View.VISIBLE
            binding.rlPlayRecording.visibility = View.GONE
            startActivity(Intent(this, LeadDetailsActivity::class.java))
            finish()
        }

        binding.ivClose.setOnClickListener {
            finish()
        }

        binding.meeting.setOnClickListener {

        }

        binding.phoneCall.setOnClickListener {

        }

        binding.siteVisit.setOnClickListener {

        }
    }

    private val fileLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                if (it.data != null) {
                    val ref =
                        Firebase.storage.reference.child("recordings/${System.currentTimeMillis()}")
                    ref.putFile(it.data!!.data!!)
                        .addOnSuccessListener {
                            Toast.makeText(this, "Upload Success.", Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener {
                            Toast.makeText(this, "Upload Failed.", Toast.LENGTH_SHORT).show()
                        }
                }
                binding.rlUploadRecording.visibility = View.GONE
                binding.rlPlayRecording.visibility = View.VISIBLE
            }
        }

    private fun selectAudio() {
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "audio/*"
            action = Intent.ACTION_GET_CONTENT
        }
        fileLauncher.launch(intent)
    }
}