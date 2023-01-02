package com.example.projectcodeqr

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.ads.mediationtestsuite.activities.HomeActivity

class RegisterActivity : AppCompatActivity() {

    private lateinit var username: EditText
    private lateinit var password: EditText
    private lateinit var repassword: EditText
    private lateinit var email: EditText
    private lateinit var completename: EditText
    private lateinit var registerButton: Button
    val dbHelper = DBHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        username = findViewById(R.id.username)
        password = findViewById(R.id.password)
        repassword = findViewById(R.id.repassword)
        email = findViewById(R.id.email)
        completename = findViewById(R.id.completename)
        registerButton = findViewById(R.id.registerbtn)

        registerButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                val user = username.text.toString()
                val pass = password.text.toString()
                val repass = repassword.text.toString()
                val mail = email.text.toString()
                val name = completename.text.toString()

                if (user.isEmpty() || pass.isEmpty() || repass.isEmpty() || mail.isEmpty() || name.isEmpty()) {
                    Toast.makeText(this@RegisterActivity, "Remplissez tous les champs.", Toast.LENGTH_SHORT).show()
                } else {
                    if (pass != repass) {
                        Toast.makeText(this@RegisterActivity, "La verification de mot de passe a échoué, réessayez", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@RegisterActivity, "Information enregistrée avec succès", Toast.LENGTH_SHORT).show()
                        dbHelper.insertData(user,pass,name,mail)
                        val intent = Intent(applicationContext, MainActivity::class.java)
                        startActivity(intent)

                    }
                }
            }
        })

    }
}