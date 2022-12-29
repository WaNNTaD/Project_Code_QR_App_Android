package com.example.projectcodeqr

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) : SQLiteOpenHelper(context, DBHelper.DATABASE_NAME, null, DBHelper.DATABASE_VERSION) {

    // Nom de la base de données et version
    companion object {
        private const val DATABASE_NAME = "bdd_app_code_qr.db"
        private const val DATABASE_VERSION = 1
    }

    // Requêtes de création des tables
    private val SQL_CREATE_TABLE_APPAREILS = """
        CREATE TABLE appareils (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            modele TEXT NOT NULL,
            marque TEXT NOT NULL,
            type TEXT NOT NULL,
            disponible BOOLEAN NOT NULL
        )
    """
    private val SQL_CREATE_TABLE_EMPRUNTS = """
        CREATE TABLE emprunts (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            id_appareil INTEGER NOT NULL,
            id_utilisateur INTEGER NOT NULL,
            date_debut DATE NOT NULL,
            date_fin_prevue DATE NOT NULL,
            FOREIGN KEY (id_appareil) REFERENCES appareils (id),
            FOREIGN KEY (id_utilisateur) REFERENCES utilisateurs (id)
        )
    """
    private val SQL_CREATE_TABLE_UTILISATEURS = """
        CREATE TABLE utilisateurs (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            nom_utilisateur TEXT NOT NULL UNIQUE,
            mot_de_passe TEXT NOT NULL,
            role TEXT NOT NULL,
            nom_complet TEXT,
            email TEXT
        )
    """


    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_TABLE_APPAREILS)
        db.execSQL(SQL_CREATE_TABLE_EMPRUNTS)
        db.execSQL(SQL_CREATE_TABLE_UTILISATEURS)
    }

    override fun onUpgrade(db: SQLiteDatabase , oldVersion: Int, newVersion: Int) {
        // Supprimer les tables et les recréer
        db.execSQL("DROP TABLE IF EXISTS appareils")
        db.execSQL("DROP TABLE IF EXISTS emprunts")
        db.execSQL("DROP TABLE IF EXISTS utilisateurs")
        onCreate(db)
    }

    fun insertData(username: String, password: String, completeName: String, email: String): Boolean {
        val db = writableDatabase
        val contentValues = ContentValues()
        contentValues.put("nom_utilisateur", username)
        contentValues.put("mot_de_passe", password)
        contentValues.put("role", "Etudiant")
        contentValues.put("nom_complet", completeName)
        contentValues.put("email", email)
        val result = db.insert("utilisateurs", null, contentValues)
        return result != -1L
    }

    fun checkUsername(username: String): Boolean {
        val db = writableDatabase
        val cursor = db.rawQuery("SELECT * FROM utilisateurs WHERE nom_utilisateur = ?", arrayOf(username))
        return cursor.count > 0
    }

    fun checkUsernamePassword(username: String, password: String): Boolean {
        val db = writableDatabase
        val cursor = db.rawQuery("SELECT * FROM utilisateurs WHERE nom_utilisateur = ? AND mot_de_passe = ?", arrayOf(username, password))
        return cursor.count > 0
    }




}
