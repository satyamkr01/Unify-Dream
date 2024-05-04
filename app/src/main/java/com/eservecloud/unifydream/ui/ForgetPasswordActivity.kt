package com.eservecloud.unifydream.ui

import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.eservecloud.unifydream.databinding.ActivityForgetPasswordBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ForgetPasswordActivity : BaseActivity() {
    private lateinit var binding: ActivityForgetPasswordBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityForgetPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.mainViewForgetPassword) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        firebaseAuth = Firebase.auth

        binding.btnForgotPasswordSubmit.setOnClickListener {
            resetPassword()
        }
    }

    private fun resetPassword() {
        val email = binding.etForgotPassword.text.toString()
        if (validateForm(email)) {
            showProgressBar()
            firebaseAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        binding.tilEmailForgetPassword.visibility = View.GONE
                        binding.tvSubmitMsg.visibility = View.VISIBLE
                        binding.btnForgotPasswordSubmit.visibility = View.GONE
                    } else {
                        showToast(this, "Unable to reset password, Try again later.")
                    }
                    hideProgressBar()
                }
        }
    }

    private fun validateForm(email: String): Boolean {
        return when {
            TextUtils.isEmpty(email) && !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                binding.tilEmailForgetPassword.error = "Enter valid email"
                false
            }

            else -> {
                binding.tilEmailForgetPassword.error = null
                true
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}