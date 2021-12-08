package com.example.uploadimage

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.graphics.createBitmap
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import java.io.File
import androidx.annotation.NonNull

import com.google.android.gms.tasks.OnCompleteListener

import com.google.firebase.storage.StorageReference


class MainActivity : AppCompatActivity() {
    private lateinit var img: ImageView
    private var imgUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        img = findViewById(R.id.imgProfile)

        val btnBrowse: Button = findViewById(R.id.btnBrowse)
        val btnUpload: Button = findViewById(R.id.btnUpload)
        val btnGet: Button = findViewById(R.id.btnGet)

        btnBrowse.setOnClickListener() {

            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"


            getImageActivity.launch(intent)
        }

        btnUpload.setOnClickListener() {

            val fName = "abc.jpg"

            val storageRef = FirebaseStorage.getInstance().getReference("images/${fName}")


            storageRef.putFile(imgUri!!)
                .addOnSuccessListener {
                    img.setImageURI(null)
                    Toast.makeText(applicationContext, it.toString(), Toast.LENGTH_LONG).show()
                }
                .addOnFailureListener {
                    Toast.makeText(applicationContext, it.toString(), Toast.LENGTH_LONG).show()
                }
        }

        btnGet.setOnClickListener() {

            val fName = "abc.jpg"
//
//            val storageRef = FirebaseStorage.getInstance().getReference("images/${fName}")
//
//            val file = File.createTempFile("temp", "jpg")
//
//            storageRef.getFile(file)
//                .addOnSuccessListener {
//                    val bitmap = BitmapFactory.decodeFile(file.absolutePath)
//                    img.setImageBitmap(bitmap)
//                }
//                .addOnFailureListener {
//                    Toast.makeText(applicationContext, it.toString(), Toast.LENGTH_LONG).show()
//                }


            val ref = FirebaseStorage.getInstance().getReference("images")
                .child("${fName}")

            ref.downloadUrl
                .addOnCompleteListener {

                        val downUri: Uri? = it.getResult()
                        val imageUrl = downUri.toString()

                        Glide.with(img.context)
                            .load(imageUrl)
                            .into(img)

                }.addOnFailureListener{

                }


        }

    }

    var getImageActivity =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data

                imgUri = data?.data
                img.setImageURI(data?.data)
            }
        }
}