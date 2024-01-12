package com.example.funbook

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import com.example.funbook.databinding.FragmentRegistrationBinding


class RegistrationFragment :BaseFragment<FragmentRegistrationBinding>(FragmentRegistrationBinding::inflate){


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.buttonRegistration.setOnClickListener {
            var name= binding.editFullUsername.text.toString().trim()
           var email= binding.editEmail.text.toString().trim()
            var password= binding.editPassword.text.toString().trim()

            userRegistration(name,email,password)

        }
        binding.textViewLogin.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun userRegistration(name: String, email: String, password: String) {

        mAuth.createUserWithEmailAndPassword(email,password)
            .addOnSuccessListener { result ->

                val user= User(

                 name= name,
                 emil= email,
                 password= password,
                 profileImg= "",
                 userId= result.user!!.uid,
                )
                mRef.child("User").child(user.userId).setValue(user).addOnSuccessListener {

                    mAuth.signOut()
                    findNavController().popBackStack()
                }.addOnFailureListener {
                        error->
                    var alertDialog = AlertDialog.Builder(requireActivity()).setTitle("Error")
                        .setMessage(error.message)
                    alertDialog.create().show()

                }


        }.addOnFailureListener {
                    error->
                var alertDialog = AlertDialog.Builder(requireActivity()).setTitle("Error")
                    .setMessage(error.message)
                alertDialog.create().show()
            }

    }

}