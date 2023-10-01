package com.example.achats.dialog

import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.achats.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog

fun Fragment.setupBottomSheetDialog(
    onSendClick: (String) -> Unit
){
    val dialog=BottomSheetDialog(requireContext(),R.style.DialogStyle)
    val view= layoutInflater.inflate(R.layout.reset_password_dialogue,null)
    dialog.setContentView(view)
    //to bring dialog above keyboard
    dialog.behavior.state= BottomSheetBehavior.STATE_EXPANDED
    dialog.show()

    val edEmail= view.findViewById<EditText>(R.id.edResetPassword)
    val buttonSend= view.findViewById<Button>(R.id.buttonSendResetPassword)
    val buttonCancel= view.findViewById<Button>(R.id.buttonCancelResetPassword)

    buttonSend.setOnClickListener {
        val email= edEmail.text.toString().trim()
        onSendClick(email)

    }
    buttonCancel.setOnClickListener {
        dialog.dismiss()
    }


    


}