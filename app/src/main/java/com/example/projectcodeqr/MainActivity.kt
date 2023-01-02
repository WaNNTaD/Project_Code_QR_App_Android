package com.example.projectcodeqr

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText

class MainActivity : AppCompatActivity() {

    // Déclarez les vues de l'interface utilisateur en tant que variables membres
    // afin qu'elles puissent être utilisées dans toutes les méthodes de l'activité
    private lateinit var connectbtn: Button
    private lateinit var registerbtn: Button

    private lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Récupérez les vues de l'interface utilisateur une fois que la vue a été attachée à l'activité
        connectbtn = findViewById(R.id.button3)
        registerbtn = findViewById(R.id.button)

        // Initialisez l'objet DBHelper en utilisant l'objet context de l'activité
        dbHelper = DBHelper(this)

        connectbtn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                val intent = Intent(applicationContext, LoginActivity::class.java)
                startActivity(intent)
            }
        })

        registerbtn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                val intent = Intent(applicationContext, RegisterActivity::class.java)
                startActivity(intent)
            }
        })
    }
}