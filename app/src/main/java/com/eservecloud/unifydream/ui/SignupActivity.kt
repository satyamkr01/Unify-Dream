package com.eservecloud.unifydream.ui

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.eservecloud.unifydream.databinding.ActivitySignupBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignupActivity : BaseActivity() {
    private lateinit var binding: ActivitySignupBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.mainSignupView) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        firebaseAuth = Firebase.auth

        binding.btnRegister.setOnClickListener {
            registerUser()
        }

        binding.tvLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun registerUser() {
        val email = binding.etSignupEmail.text.toString()
        val password = binding.etSignupPassword.text.toString()
        val confirmPassword = binding.etConfirmPassword.text.toString()
        if (validateForm(email, password, confirmPassword)) {
            showProgressBar()
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        startActivity(Intent(this, LoginActivity::class.java))
                        finish()
                    } else {
                        showToast(this, "Oops! Something went wrong")
                    }
                    hideProgressBar()
                }
        }
    }

    private fun validateForm(email: String, password: String, confirmPassword: String): Boolean {
        return when {
            TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                binding.tilSignupEmail.error = "Enter valid email"
                false
            }

            TextUtils.isEmpty(password) -> {
                binding.tilSignupPassword.error = "Enter password"
                false
            }

            TextUtils.isEmpty(confirmPassword) || (confirmPassword != password) -> {
                binding.tilConfirmPassword.error = "Password does not matched"
                false
            }
            else -> {
                binding.tilSignupEmail.error = null
                binding.tilSignupPassword.error = null
                binding.tilConfirmPassword.error = null
                true
            }
        }
    }

}