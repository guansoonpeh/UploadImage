package com.example.uploadimage

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.firebase.storage.FirebaseStorage

class MainActivity : AppCompatActivity() {
    private lateinit var img: ImageView
    private var imgUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        img = findViewById(R.id.imgProfile)

        val btnBrowse: Button = findViewById(R.id.btnBrowse)
        val btnUpload: Button = findViewById(R.id.btnUpload)

        btnBrowse.setOnClickListener() {

            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"


            getImageActivity.launch(intent)
        }

        btnUpload.setOnClickListener() {

            val fName = "sdfsfd"

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