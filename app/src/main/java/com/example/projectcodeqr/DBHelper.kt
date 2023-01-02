package com.example.projectcodeqr

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.projectcodeqr.models.HighTechItem

class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DBHelper.DATABASE_VERSION) {

    // Nom de la base de données et version
    companion object {
        private const val DATABASE_NAME = "bdd_app_code_qr.db"
        private const val DATABASE_VERSION = 1
    }

    val appareils = listOf(
        HighTechItem("Apple", "iPhone 12", "A12B3C", "téléphone","https://www.apple.com/befr/",true),
        HighTechItem("Samsung", "Galaxy S20", "B23C4D", "téléphone","https://www.samsung.com/be_fr/",true),
        HighTechItem("Huawei", "P40 Pro", "C34D5E", "téléphone","https://consumer.huawei.com/be-fr/",true),
        HighTechItem("Google", "Pixel 4", "D45E6F", "téléphone","https://store.google.com/be/?hl=fr-BE&pli=1",true),
        HighTechItem("OnePlus", "8 Pro", "E56F7G", "téléphone","https://www.oneplus.com/be_fr",true),
        HighTechItem("Apple", "iPad Pro", "F67G8H", "tablette","https://www.apple.com/befr/",true),
        HighTechItem("Samsung", "Galaxy Tab S7", "G78H9I", "tablette","https://www.samsung.com/be_fr/",true),
        HighTechItem("Huawei", "MatePad Pro", "H89I0J", "tablette","https://consumer.huawei.com/be-fr/",true),
        HighTechItem("Google", "Pixel Slate", "I0J1K2", "tablette","https://store.google.com/be/?hl=fr-BE&pli=1",true),
        HighTechItem("OnePlus", "Nord Pad", "J1K2L3", "tablette","https://www.oneplus.com/be_fr",true),
        HighTechItem("Apple", "iPhone SE", "K2L3M4", "téléphone","https://www.apple.com/befr/",true),
        HighTechItem("Samsung", "Galaxy A50", "L3M4N5", "téléphone","https://www.samsung.com/be_fr/",true),
        HighTechItem("Huawei", "P30", "M4N5O6", "téléphone","https://consumer.huawei.com/be-fr/",true),
        HighTechItem("Google", "Pixel 3", "N5O6P7", "téléphone","https://store.google.com/be/?hl=fr-BE&pli=1",true),
        HighTechItem("OnePlus", "7T", "O6P7Q8", "téléphone","https://www.oneplus.com/be_fr",true)
    )

    // Requêtes de création des tables
    private val SQL_CREATE_TABLE_APPAREILS = """
        CREATE TABLE appareils (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            modele TEXT NOT NULL,
            marque TEXT NOT NULL,
            reference TEXT NOT NULL,
            site_fabricant TEXT NOT NULL,
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
        for (appareil in appareils) {
            val contentValues = ContentValues()
            contentValues.put("modele", appareil.getModel())
            contentValues.put("marque", appareil.getMarque())
            contentValues.put("reference", appareil.getReference())
            contentValues.put("type", appareil.getType())
            contentValues.put("disponible", 1)
            db.insert("appareils", null, contentValues)
        }
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

    @SuppressLint("Range")
    fun getAllAppareils(): List<HighTechItem> {
        val appareils = mutableListOf<HighTechItem>()

        // Récupération de la base de données en mode lecture
        val db = readableDatabase

        // Exécution de la requête de sélection de tous les appareils
        val cursor = db.query("appareils", null, null, null, null, null, null)

        // Parcours du curseur
        while (cursor.moveToNext()) {
            // Récupération des données de l'appareil
            val id = cursor.getInt(cursor.getColumnIndex("id"))
            val modele = cursor.getString(cursor.getColumnIndex("modele"))
            val marque = cursor.getString(cursor.getColumnIndex("marque"))
            val reference = cursor.getString(cursor.getColumnIndex("reference"))
            val siteFabricant = cursor.getString(cursor.getColumnIndex("site_fabricant"))
            val type = cursor.getString(cursor.getColumnIndex("type"))
            val disponible = cursor.getColumnIndex("disponible")

            // Vérification de l'existence de la colonne "disponible"
            val disponibleValue = if (disponible != -1) {
                cursor.getInt(disponible) == 1
            } else {
                null
            }

            // Cré


            // Création de l'objet Appareil
            val appareil = HighTechItem(marque, modele, reference, type, siteFabricant, disponibleValue!!)

            // Ajout de l'appareil à la liste
            appareils.add(appareil)

        }

        // Fermeture du curseur
        cursor.close()

        // Retour de la liste des appareils
        return appareils
    }


    fun insertDefaultAppareils() {
        val db = writableDatabase
        val contentValues = ContentValues()


    }




}
