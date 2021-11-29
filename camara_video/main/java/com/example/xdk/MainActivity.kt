package com.example.xdk

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    //Identificacion del permiso
    private val REQUEST_GALERIA = 1001
    private val REQUEST_CAMARA = 1002
    private val REQUEST_VIDEO = 101
    var imagen: Uri? = null


    @SuppressLint("WrongViewCast")
    val btnAbrirGaleria: Button = findViewById(R.id.btnAbrirGaleria)
    val btnAbrirCamara: Button = findViewById(R.id.btnAbrirCamara)
    val btnGrabarVideo: Button = findViewById(R.id.btnGrabarVideo)

    var foto: ImageView = findViewById(R.id.foto)
    var video: VideoView = findViewById(R.id.video)



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        abrirGaleria()
        abrirCamara()
        grabarVideo()
    }



    //btnAbrirGaleria
    private fun abrirGaleria(){
        btnAbrirGaleria.setOnClickListener(){
            //Verificar version Android
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                //Permiso
                if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                    //Pide permiso a Usuario
                    val permisoArchivos = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                    requestPermissions(permisoArchivos, REQUEST_GALERIA)

                }else
                //Tiene permiso
                    mostrarGaleria()
            }else
            //Tiene version de Android 8.1 Oreo hacia abajo
                mostrarGaleria()
        }
    }

    private fun mostrarGaleria(){
        //Mostrar una imagen de la galeria
        val intentGaleria = Intent(Intent.ACTION_PICK)
        intentGaleria.type = "image/*"
        startActivityForResult(intentGaleria,REQUEST_GALERIA)
    }


    //Respuesta del Usaio a solicitud de permiso
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            REQUEST_GALERIA ->{
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    mostrarGaleria()
                else
                    Toast.makeText(applicationContext, "No tiene acceso a las imagenes", Toast.LENGTH_SHORT).show()
            }
            REQUEST_CAMARA ->{
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    abrirCamara()
                else
                    Toast.makeText(applicationContext, "No tiene acceso a la camara", Toast.LENGTH_SHORT).show()
            }
        }
    }


    //Recuperacion de imagen seleccionada
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_GALERIA){
            foto.setImageURI(data?.data)
        }
        if(resultCode == Activity.RESULT_OK && requestCode == REQUEST_CAMARA){
            foto.setImageURI(imagen!!)
        }
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_VIDEO) {
            video.setVideoURI(data?.data)
            video.start()

        }
    }


    //btnAbrirCamara
    private fun abrirCamara(){
        btnAbrirCamara.setOnClickListener(){
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if(checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED
                    || checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                    //Aviso de permiso a Usuario
                    val permisosCamara = arrayOf(Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    requestPermissions(permisosCamara,REQUEST_CAMARA)
                }else
                    abrirCamara2()
            }else
                abrirCamara2()
        }
    }

    //Abrir camara y tomar foto
    private fun abrirCamara2(){
        val value = ContentValues()
        //Imagen de tipo Media
        value.put(MediaStore.Images.Media.TITLE, "Imagen Nueva")
        imagen = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,value)
        val camaraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        camaraIntent.putExtra(MediaStore.EXTRA_OUTPUT,imagen)
        startActivityForResult(camaraIntent,REQUEST_CAMARA)
    }


    //btnGrabarVideo
    private fun grabarVideo(){
        btnGrabarVideo.setOnClickListener(){
            val intentVideo = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
            startActivityForResult(intentVideo,REQUEST_VIDEO)

        }
    }

}