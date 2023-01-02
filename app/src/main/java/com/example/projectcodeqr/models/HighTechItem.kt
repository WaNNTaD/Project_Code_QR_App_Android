package com.example.projectcodeqr.models

class HighTechItem {

    private val marque: String
    private val model: String
    private val reference: String
    private val type: String
    private val site: String
    private val dispo: Boolean

    constructor(marque: String, model: String, reference: String, type:String, site:String,dispo:Boolean) {
        this.marque = marque
        this.model = model
        this.reference = reference
        this.type=type
        this.site=site
        this.dispo=dispo
    }

    fun getMarque(): String = marque
    fun getModel(): String = model
    fun getReference(): String = reference
    fun getType(): String = type
    fun getSite(): String = site
    fun getDispo(): Boolean = dispo

}
