package ru.netology.nmedia.util

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder

object DialogManager {

    fun SignInDialog(context: Context, listener: Listener) {
        val builder = MaterialAlertDialogBuilder(context)
        val dialog = builder.create()
        dialog.setTitle("Please sign in")
        dialog.setMessage("Go to sign in page")
        dialog.setButton(AlertDialog.BUTTON_POSITIVE, "Sign in") { _, _ ->
            listener.onClick()
            dialog.dismiss()
        }
        dialog.show()
    }

    fun SignOutDialog(context: Context, listener: Listener) {
        val builder = MaterialAlertDialogBuilder(context)
        val dialog = builder.create()
        dialog.setTitle("Are you sure you want to sign out?")
        dialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes") { _, _ ->
            listener.onClick()
            dialog.dismiss()
        }
        dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No") { _, _ ->
            dialog.dismiss()
        }
        dialog.show()
    }

    interface Listener {
        fun onClick()
    }

}