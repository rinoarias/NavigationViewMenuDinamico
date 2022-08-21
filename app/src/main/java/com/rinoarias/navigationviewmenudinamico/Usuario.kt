package com.rinoarias.navigationviewmenudinamico

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.Serializable

class Usuario (a: JSONObject) : Serializable {
    var username : String
    var password : String
    var names : String
    var img : String
    var opciones : ArrayList<String>
    var rol : String


    companion object {
        @Throws(JSONException::class)
        fun JsonObjectsBuild(datos: JSONArray): ArrayList<Usuario> {
            val users : ArrayList<Usuario> =  ArrayList<Usuario>()
            for(i in 0 until datos.length()) {
                users.add(Usuario(datos.getJSONObject(i)))
            }
            return users
        }
    }

    init {
        username = a.getString("username").toString()
        password = a.getString("password").toString()
        names = a.getString("names").toString()
        img = a.getString("img").toString()
        opciones = ArrayList<String>()
        val arrayOptions = a.getJSONArray("opciones")
        for (i in 0 until arrayOptions.length()){
            opciones.add(arrayOptions.getJSONObject(i).getString("opt"))
        }
        rol = a.getString("rol").toString()
    }
}