package com.example.mystage.dialog

import androidx.fragment.app.Fragment


fun Fragment.setupBottomSheetDialog(
    onSendClick: (String) -> Unit
){
    /**Not finishing for now but son :)
     *
     */
//    val dialog = BottomSheetDialog(requireContext(),R.style.DialogStyle)
//    val view = layoutInflater.inflate(R.layout.reset_passowrd_dialog,null)
//    dialog.setContentView(view)
//    dialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
//    dialog.show()
//
//    val edEmail = view.findViewById<EditText>(R.id.edResetPassword)
//    val buttonSend = view.findViewById<Button>(R.id.buttonSendResetPassword)
//    val buttonCancel = view.findViewById<Button>(R.id.buttonCancelResetPassword)
//
//    buttonSend.setOnClickListener {
//        val email = edEmail.text.toString().trim()
//        onSendClick(email)
//        dialog.dismiss()
//    }
//
//    buttonCancel.setOnClickListener {
//        dialog.dismiss()
//    }
}