package com.example.ds3t_api_livraria

import android.app.Activity
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class CadastroLivroImagem : AppCompatActivity() {

    //Atributos para manipulação de imagens ()objetos de uri
    private var imageURD: Uri? = null
    private var imagePEQURD: Uri? = null

    //atributo para acesso de manipulação do storage
    private lateinit var storageRef: StorageReference

    //Atributo para acesso e manipulação do firestore database
    private lateinit var fireBaseFireStore: FirebaseFirestore

    //Atributos de image view
    private var btnImgGRD: ImageView? = null
    private var btnImgPEQGRD: ImageView? = null

    //Atributo de button
    private var btnnUpload: Button? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cadastro_livro_imagem)

        initVars()

        //teste de recebimento de corpo de json

        val body = intent.getStringExtra("bodyJSON")

        Log.e("TESTE_JSON", body.toString())

        //Recupera os objetos de views das imagens
        btnImgGRD = findViewById<ImageView>(R.id.imgGRD)
        btnImgPEQGRD = findViewById<ImageView>(R.id.imgPEQ)

        //  Recupera o objeto de button para realizar upload
        btnnUpload = findViewById<Button>(R.id.btnCadastrarLivro)

        //Recupera a imagem grande da galeria
        btnImgGRD?.setOnClickListener {
            resultLauncherGRD.launch("image/*")
        }

        //Recupera a imagem pequena da galeria
        btnImgPEQGRD?.setOnClickListener {
            resultLauncherPEQ.launch("image/*")
        }

        btnnUpload?.setOnClickListener {
            upload()
        }

    }

    //Inicializa os atributos referentes ao firebase
    private fun initVars(){
        //Inicializa o storage com a pasta de images
        storageRef = FirebaseStorage.getInstance().reference.child("imagens")
        fireBaseFireStore = FirebaseFirestore.getInstance()
    }

    //Lançador para imagens grandes da galeria
    private val resultLauncherGRD = registerForActivityResult(ActivityResultContracts.GetContent()){
        imageURD = it
        btnImgGRD?.setImageURI(it)

        Log.e("IMG-GRD", it.toString())
    }

    private val resultLauncherPEQ = registerForActivityResult(ActivityResultContracts.GetContent()){
        imagePEQURD = it
        btnImgPEQGRD?.setImageURI(it)

        Log.e("IMG-PEQ", it.toString())
    }

    //upload das imagens
    private fun upload(){
        imageURD?.let {
            val riversRef = storageRef.child("${it.lastPathSegment}-${System.currentTimeMillis()}.jpg")
            val uploadTask = riversRef.putFile(it)
            uploadTask.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    riversRef.downloadUrl.addOnSuccessListener { uri ->
                        val map = HashMap<String, Any>()
                        map["pic"] = uri.toString()
                        fireBaseFireStore.collection("images").add(map).addOnCompleteListener { firestoreTask ->
                            if (firestoreTask.isSuccessful){
                                Toast.makeText(this, "UPLOAD IMAGEM GRANDE OK!", Toast.LENGTH_SHORT).show()
                            }else{
                                Toast.makeText(this, firestoreTask.exception?.message, Toast.LENGTH_SHORT).show()
                            }
                            btnImgGRD?.setImageResource(R.drawable.upload)
                        }
                    }
                } else {
                    Toast.makeText(this, task.exception?.message, Toast.LENGTH_SHORT).show()
                    btnImgGRD?.setImageResource(R.drawable.upload)
                }
            }
        }

        imagePEQURD?.let {
            val riversRef = storageRef.child("${it.lastPathSegment}-${System.currentTimeMillis()}.jpg")
            val uploadTask = riversRef.putFile(it)
            uploadTask.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    riversRef.downloadUrl.addOnSuccessListener { uri ->
                        val map = HashMap<String, Any>()
                        map["pic"] = uri.toString()
                        fireBaseFireStore.collection("images").add(map).addOnCompleteListener { firestoreTask ->
                            if (firestoreTask.isSuccessful){
                                Toast.makeText(this, "UPLOAD IMAGEM PEQUENA OK!", Toast.LENGTH_SHORT).show()
                            }else{
                                Toast.makeText(this, firestoreTask.exception?.message, Toast.LENGTH_SHORT).show()
                            }
                            btnImgPEQGRD?.setImageResource(R.drawable.upload)
                        }
                    }
                } else {
                    Toast.makeText(this, task.exception?.message, Toast.LENGTH_SHORT).show()
                    btnImgPEQGRD?.setImageResource(R.drawable.upload)
                }
            }
        }
    }
}