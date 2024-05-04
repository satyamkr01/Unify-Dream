package com.eservecloud.unifydream.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.eservecloud.unifydream.R
import com.eservecloud.unifydream.databinding.ActivityLeadDetailsBinding

class LeadDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLeadDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLeadDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.mainLeadDetailsView) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.toolbarLeadDetails.setTitle("")
        setSupportActionBar(binding.toolbarLeadDetails)

        binding.fabAdd.setOnClickListener {
            startActivity(Intent(this, LogActivity::class.java))
            finish()
        }

        binding.ivArrowBack.setOnClickListener {
            startActivity(Intent(this, LeadsProfileActivity::class.java))
            finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        binding.toolbarLeadDetails.inflateMenu(R.menu.toolbar_lead_details_menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.call -> makeCall("+1 1234567890")
        }
        return true
    }

    private fun makeCall(contactNumber: String?) {
        val callIntent = Intent(Intent.ACTION_DIAL)
        callIntent.data = Uri.parse("tel:$contactNumber")
        callIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        applicationContext.startActivity(callIntent)
    }
}
