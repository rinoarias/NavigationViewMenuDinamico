package com.rinoarias.navigationviewmenudinamico

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley

class MainActivity : AppCompatActivity() {

    lateinit var btnLogin: Button
    lateinit var txtUsername: EditText
    lateinit var txtPassword: EditText
    lateinit var requestQueue: RequestQueue
    val url = "https://my-json-server.typicode.com/rinoarias/NavigationViewMenuDinamico/Usuarios"
    var userList: ArrayList<Usuario> = ArrayList<Usuario>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnLogin = findViewById(R.id.btnLogin)
        txtUsername = findViewById(R.id.txtUsername)
        txtPassword = findViewById(R.id.txtPassword)

        obtenerUsuarios()

    }

    private fun obtenerUsuarios() {
        var requestJson : JsonArrayRequest = JsonArrayRequest(
            Request.Method.GET,
            url,
            null,
            { response ->
                try {
                    for (i in 0 until response.length()){
                        var item = response.getJSONObject(i)
                        userList.add(Usuario(item))
                    }
                } catch (e: Exception) {
                    Toast.makeText(this, "Error al obtener los datos: $e", Toast.LENGTH_LONG).show()
                    System.out.println(e.toString())
                }
            },
            {
                Toast.makeText(this, "Error al obtener los datos: $it", Toast.LENGTH_LONG).show()
                System.out.println(it.toString())
            })
        requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(requestJson)
    }

    fun iniciarSesion(view : View?) {
        var userPosition = validarUsuario(txtUsername.text.toString(), txtPassword.text.toString())
        if(userPosition >= 0) {
            val intent : Intent = Intent(this, NavigationActivity::class.java)
            intent.putExtra("usuario", userList.get(userPosition))
            startActivity(intent)
        } else {
            Toast.makeText(this, "Usuario y/o contraseña incorrectos.", Toast.LENGTH_LONG).show()
        }
    }

    private fun validarUsuario(usuario: String, password: String) : Int {
        try {
            for (i in 0 until userList.size) {
                var auxUsuario = userList.get(i)
                if(usuario == auxUsuario.username && password == auxUsuario.password){
                    return i
                }
            }
        } catch (e: Exception) {
            Toast.makeText(this, "Error al validar usuario.", Toast.LENGTH_LONG).show()
        }
        return -1
    }

}