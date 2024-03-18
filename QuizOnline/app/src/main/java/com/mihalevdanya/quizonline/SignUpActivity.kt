package com.mihalevdanya.quizonline

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.mihalevdanya.quizonline.databinding.ActivitySignInBinding
import com.mihalevdanya.quizonline.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {
    private lateinit var  binding: ActivitySignUpBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.textView.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }
        binding.button.setOnClickListener {
            val email = binding.emailEt.text.toString()
            val pass = binding.passET.text.toString()
            val confirmPass = binding.confirmPassEt.text.toString()
            if (pass.length >= 6){
                if (email.isNotEmpty() && pass.isNotEmpty() && confirmPass.isNotEmpty()){
                        if (pass == confirmPass){
                            firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    Toast.makeText(this, "Подождите", Toast.LENGTH_SHORT).show()
                                    val intent = Intent(this, MainActivity::class.java)
                                    startActivity(intent)
                                } else {
                                    Toast.makeText(this, task.exception!!.message, Toast.LENGTH_SHORT).show()
                                }
                            }
                        }else{
                            Toast.makeText(this, "Пароли не совпадают", Toast.LENGTH_SHORT).show()
                        }
                }else{
                    Toast.makeText(this, "Поля не заполнены", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this, "Пароль должен содержать более 6 симвовлов", Toast.LENGTH_SHORT).show()
            }
        }
    }
}