package com.example.ds3t_api_livraria

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.google.gson.JsonObject

class CadastroLivro : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.cadastro_livro)

        //Recuperando os elementos de view
        val txtTitulo = findViewById<EditText>(R.id.txtTitulo)
        val txtPreco = findViewById<EditText>(R.id.txtPreco)
        val txtCategoria = findViewById<EditText>(R.id.txtCategoria)
        val txtTDescricao = findViewById<EditText>(R.id.txtLivroDescricao)
        val btnCadastrar = findViewById<Button>(R.id.btnCadastrarLivro)

        //Tratamento da ação de click no botão cadastrar


        btnCadastrar.setOnClickListener{
            //Recebendo os dados digitados

            val titulo = txtTitulo.text.toString()
            val preco = txtPreco.text.toString()
            val categoria = txtCategoria.text.toString()
            val descricao = txtTDescricao.text.toString()

            //Construção do corpo de dados em JSON
            val body = JsonObject().apply {
                addProperty("titulo", titulo)
                addProperty("preco", preco)
                addProperty("categoria", categoria)
                addProperty("descricao", descricao)
            }

            Log.e("BODY-JSON", body.toString())

            //Navegação para a tela de imagens com corpo de dados json

            val intent = Intent(this, CadastroLivroImagem::class.java).apply {
                putExtra("bodyJSON", body.toString())
            }
            startActivity(intent)
        }
    }
}