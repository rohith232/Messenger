 package com.example.messenger


 import android.content.Intent
 import android.os.Bundle
 import android.widget.Button
 import android.widget.EditText
 import android.widget.Toast
 import androidx.activity.enableEdgeToEdge
 import androidx.appcompat.app.AppCompatActivity
 import androidx.core.view.ViewCompat
 import androidx.core.view.WindowInsetsCompat
 import com.google.firebase.auth.FirebaseAuth
 import com.google.firebase.database.DatabaseReference
 import com.google.firebase.database.FirebaseDatabase

 class SignUp : AppCompatActivity() {

     private lateinit var edtName : EditText
     private lateinit var edtEmail : EditText
     private lateinit var edtPassword : EditText
     private lateinit var btnSignUp: Button
     private lateinit var mAuth : FirebaseAuth
     private lateinit var mDbRef:DatabaseReference




     override fun onCreate(savedInstanceState: Bundle?) {
         super.onCreate(savedInstanceState)
         enableEdgeToEdge()
         setContentView(R.layout.activity_sign_up)
         supportActionBar?.hide()
         ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
             val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
             v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
             insets
         }
         mAuth =FirebaseAuth.getInstance()
         edtName =findViewById(R.id.edt_name)
         edtEmail =findViewById(R.id.edt_email)
         edtPassword =findViewById(R.id.edt_password)
         btnSignUp =findViewById(R.id.btn_signup)




         btnSignUp.setOnClickListener {
             val name =edtName.text.toString()
             val email =edtEmail.text.toString()
             val password =edtPassword.text.toString()

             signUp(name,email,password)
         }
     }

     private fun signUp(name:String, email: String, password: String) {
         //logic of creating user

         mAuth.createUserWithEmailAndPassword(email, password)
             .addOnCompleteListener(this) { task ->
                 if (task.isSuccessful) {

                     //code for jumping to home

                     addUserToDatabase(name,email,mAuth.currentUser?.uid!!)

                     val intent = Intent (this@SignUp,MainActivity::class.java)
                     finish()
                     startActivity(intent)

                 } else {
                     Toast.makeText(this@SignUp, "some error occured",Toast.LENGTH_SHORT).show()

                 }
             }


     }

     private fun addUserToDatabase(name: String, email: String, uid: String?) {

         mDbRef =FirebaseDatabase.getInstance().getReference()
        mDbRef.child("user").child(uid.toString()).setValue(User(name,email,uid))

     }
 }