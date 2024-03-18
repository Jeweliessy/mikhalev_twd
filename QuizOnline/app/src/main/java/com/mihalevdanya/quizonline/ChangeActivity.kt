package com.mihalevdanya.quizonline

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.mihalevdanya.quizonline.databinding.ActivityChangeBinding
import com.mihalevdanya.quizonline.databinding.ActivitySettingsBinding

class ChangeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChangeBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        firebaseAuth = FirebaseAuth.getInstance()
        super.onCreate(savedInstanceState)
        binding = ActivityChangeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backToSettings.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }
        binding.updatePass.setOnClickListener {
            val currentUser = firebaseAuth.currentUser
            val pass = binding.passET.text.toString()
            val confirmPass = binding.confirmPassEt.text.toString()
            if (pass.length >= 6) {
                if (pass.isNotEmpty() && confirmPass.isNotEmpty()) {
                    if (pass == confirmPass) {
                        currentUser?.updatePassword(pass)?.addOnCompleteListener {
                            if (it.isSuccessful) {
                                Toast.makeText(this, "Пароли изменены", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(this, it.exception!!.message, Toast.LENGTH_SHORT).show()
                            }
                        }
                    } else {
                        Toast.makeText(this, "Пароли не совпадают", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Поля не заполнены", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Пароль должен содержать более 6 симвовлов", Toast.LENGTH_SHORT).show()
            }
        }
    }
}