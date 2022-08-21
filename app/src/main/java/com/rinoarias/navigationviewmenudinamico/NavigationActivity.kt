package com.rinoarias.navigationviewmenudinamico

import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.rinoarias.navigationviewmenudinamico.databinding.ActivityNavigationBinding
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class NavigationActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityNavigationBinding

    lateinit var txtNom :TextView
    lateinit var img : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNavigationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarNavigation.toolbar)

        binding.appBarNavigation.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_navigation)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        // Recogiendo datos de act anterior
        var usuario: Usuario = intent.getSerializableExtra("usuario") as Usuario


        //Se hace referencia al Navigation View
        val navi = findViewById<View>(R.id.nav_view) as NavigationView
        val hview = navi.getHeaderView(0)


        //Se vinculan los componentes
        txtNom = hview.findViewById<View>(R.id.txtNombreNav) as TextView
        img = hview.findViewById<View>(R.id.imgViewPerfil) as ImageView

        //Se cargan los elementos en el navigation View
        cargarElementosDelUsuario(txtNom, img, usuario, usuario.opciones, navi)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.navigation, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_navigation)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }


    private fun cargarElementosDelUsuario(
        nom: TextView, img: ImageView, usu: Usuario, opt: ArrayList<String>,
        navi: NavigationView
    ) {
        //Ubicamos el nombre
        nom.setText(usu.names)

        //Cargamos la imagen
        Glide.with(this).load(usu.img.toString()).into(img)

        //Carga las opciones según su Rol: admin o user
        if (usu.rol.equals("admin")) {
            navi.menu.setGroupVisible(R.id.groupEstandar, true)
            navi.menu.setGroupVisible(R.id.groupAdmin, true)
        } else {
            navi.menu.setGroupVisible(R.id.groupEstandar, true)
        }
    }
}