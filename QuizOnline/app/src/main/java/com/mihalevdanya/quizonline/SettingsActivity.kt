package com.mihalevdanya.quizonline

import android.app.AlertDialog
import android.content.ContentValues
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.mihalevdanya.quizonline.databinding.ActivitySettingsBinding


class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signOut.setOnClickListener {
            dialogAlertSignOut()
        }

        binding.changePassword.setOnClickListener {
            val intent = Intent(this, ChangeActivity::class.java)
            startActivity(intent)
        }

        binding.backToTests.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.deleteAcc.setOnClickListener {
            dialogAlertDelete()
        }
    }



    fun dialogAlertDelete(){
        val builder = AlertDialog.Builder(this)

        with(builder)
        {
            setMessage("Вы точно хотите удалить свой аккаунт?")
            setPositiveButton("Да", DialogInterface.OnClickListener(function = positiveButtonClick))
            setNegativeButton("Нет", negativeButtonClick)
            show()
        }
    }
    fun dialogAlertSignOut(){
        val builder = AlertDialog.Builder(this)

        with(builder)
        {
            setMessage("Вы точно хотите выйти?")
            setPositiveButton("Да", DialogInterface.OnClickListener(function = positiveButtonClickSignOut))
            setNegativeButton("Нет", negativeButtonClickSignOut)
            show()
        }
    }

    val positiveButtonClick = { dialog: DialogInterface, which: Int ->
        deleteSuccess()
    }
    val negativeButtonClick = { dialog: DialogInterface, which: Int ->
        dialog.dismiss()
    }
    val positiveButtonClickSignOut = { dialog: DialogInterface, which: Int ->
        signOutSuccess( )
    }
    val negativeButtonClickSignOut = { dialog: DialogInterface, which: Int ->
        dialog.dismiss()
    }

    fun deleteSuccess(){
        firebaseAuth = FirebaseAuth.getInstance()
        val currentUser = firebaseAuth.currentUser
        currentUser!!.delete().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d(ContentValues.TAG, "Происходит удаление")
                startActivity(Intent(this, SignInActivity::class.java))
            }
        }.addOnFailureListener { e ->
            Log.e(ContentValues.TAG, "Что-то пошло не так", e)
        }
    }
    fun signOutSuccess(){
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.signOut()
        startActivity(Intent(this, SignInActivity::class.java))
    }

}