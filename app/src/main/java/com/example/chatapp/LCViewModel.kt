package com.example.chatapp

import android.provider.ContactsContract.CommonDataKinds.Email
import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.chatapp.data.Event
import com.example.chatapp.data.USER_NODE
import com.example.chatapp.data.UserData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import dagger.hilt.android.lifecycle.HiltViewModel
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class LCViewModel @Inject constructor(

    val auth :FirebaseAuth,
    var db:FirebaseFirestore
):ViewModel() {

   var inProcess = mutableStateOf(false)
    val eventMutableState = mutableStateOf<Event<String>?>(null)
    var signIn= mutableStateOf(false)
    val userData= mutableStateOf<UserData?>(null)
    init {
        val currentUser =auth.currentUser
        signIn.value =currentUser !=null
        currentUser?.uid?.let{
            getUserData(it)
        }
    }
    fun signUp(name:String, number:String, email: String,password:String){

            inProcess.value=true
        if(name.isEmpty() or number.isEmpty() or email.isEmpty() or password.isEmpty()){
            handleException(customMessage = "Please Fill All Fields")
            return
        }
        inProcess.value=true
        db.collection(USER_NODE).whereEqualTo("number",number).get().addOnSuccessListener {
            if (it.isEmpty){
                auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {
                    if (it.isSuccessful){
                        signIn.value=true
                        createOrUpdateProfile(name,number)
                    }else{
                        handleException(it.exception, customMessage = "Sign Up Failed")
                    }
                }
            }else {
                handleException(customMessage = "number Already Exists")
                inProcess.value=false
            }
        }

    }

    fun LoginIn(email: String,password: String){
        if(email.isEmpty() or password.isEmpty()){
            handleException(customMessage = "Fill All Fields")
        }
        else {
            inProcess.value=true
            auth.signInWithEmailAndPassword(email,password).addOnCompleteListener {
                if (it.isSuccessful){
                signIn.value=true
                    inProcess.value=false

                    auth.currentUser?.uid?.let{
        getUserData(it)
                    }
                }else{
                    handleException(exception = it.exception, customMessage = "Login Failed")
                }
            }
        }
    }

    fun createOrUpdateProfile(name:String?=null,number: String?=null,imageurl:String?=null){
        var uid=auth.currentUser?.uid
        val userData=UserData(
            userId = uid,
            name=name?: userData.value?.name,
            number=number?: userData.value?.number,
           imageUrl = imageurl?: userData.value?.imageUrl,
        )
    uid?.let {
        inProcess.value=true
        db.collection(USER_NODE).document(uid).get().addOnSuccessListener {
            if (it.exists()){
               // update user Data
            }else{
                db.collection(USER_NODE).document(uid).set(userData)
                inProcess.value=false
                getUserData(uid)
            }
        }
            .addOnFailureListener{
                handleException(it,"Cannot Retrive User")
            }
    }
    }
    private fun getUserData(uid:String) {
        inProcess.value=true
        db.collection(USER_NODE).document().addSnapshotListener{
                value,error->
            if(error!=null){
                handleException(error,"Cannot Retrive User")
            }
            if(value!=null){
                var user = value.toObject<UserData>()
                userData.value=user
            }
        }
    }
    fun handleException(exception: Exception?=null,customMessage:String="") {
        Log.e("chatapp", "Chat App Exception : ", exception)
        exception?.printStackTrace()
        val errorMsg = exception?.localizedMessage ?: ""
        val message = if (customMessage.isNullOrEmpty()) errorMsg else
            customMessage
       eventMutableState.value=Event(message)
       inProcess.value= false

    }


}
