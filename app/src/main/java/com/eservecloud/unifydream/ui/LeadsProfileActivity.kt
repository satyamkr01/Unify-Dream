package com.eservecloud.unifydream.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.eservecloud.unifydream.R
import com.eservecloud.unifydream.databinding.ActivityLeadsProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import de.hdodenhof.circleimageview.CircleImageView

class LeadsProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLeadsProfileBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLeadsProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.mainLeadsProfileView) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        firebaseAuth = Firebase.auth

        binding.toolbarLeadsProfile.setTitle("")
        setSupportActionBar(binding.toolbarLeadsProfile)

        binding.tvSort.setOnClickListener {

        }

        binding.tvFilter.setOnClickListener {
            startActivity(Intent(this, LeadDetailsActivity::class.java))
            finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        binding.toolbarLeadsProfile.inflateMenu(R.menu.toolbar_leads_profile_menu)
        val menuItem = menu.findItem(R.id.profile)
        val actionView = menuItem.actionView
        val profileImage = actionView!!.findViewById<CircleImageView>(R.id.toolbar_profile_image)

        Glide.with(this)
            .load("https://media.istockphoto.com/id/1365138283/photo/portrait-of-attractive-pretty-cheerful-brown-haired-girl-calling-client-discussing-project.jpg?s=612x612&w=0&k=20&c=3ssETa7fEQLlAqUEAj7HwHu80wSHpn-Z6VDGqj3-Tbo=")
            .into(profileImage)

        profileImage.setOnClickListener {
            if (firebaseAuth.currentUser != null) {
                firebaseAuth.signOut()
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.notification -> Toast.makeText(
                this,
                "Clicked on notification Icon.",
                Toast.LENGTH_SHORT
            ).show()
        }
        return true
    }
}
