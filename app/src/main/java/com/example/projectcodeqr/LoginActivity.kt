package com.example.projectcodeqr

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.ads.mediationtestsuite.activities.HomeActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var username: EditText
    private lateinit var password: EditText
    private lateinit var loginbtn: Button
    val dbHelper = DBHelper(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        username = findViewById(R.id.username)
        password = findViewById(R.id.password)
        loginbtn = findViewById(R.id.loginbtn)

        loginbtn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                val user = username.text.toString()
                val pass = password.text.toString()

                if (user.isEmpty() || pass.isEmpty()) {
                    Toast.makeText(this@LoginActivity, "Remplissez tous les champs.", Toast.LENGTH_SHORT).show()
                } else {
                    val checkuserpass = dbHelper.checkUsernamePassword(user, pass)
                    if (checkuserpass) {
                        Toast.makeText(this@LoginActivity, "Connexion réussie!", Toast.LENGTH_SHORT).show()
                        val intent = Intent(applicationContext, StudentInt::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this@LoginActivity, "Information de connexion invalide", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })


    }
}