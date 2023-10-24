package com.example.weatherapp.UI.ActivityTools

import android.app.AlertDialog
import android.content.Context
import android.widget.EditText

object DialogManager {
    fun locationSetting(context: Context, dialogListener: DialogListener) {
        val builder = AlertDialog.Builder(context)
        val dialog = builder.create()
        dialog.setTitle("Enable location")
        dialog.setMessage("Вы хотите включить геолокацию?")
        dialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK") { _, _ ->
            dialogListener.onClick(null)
            dialog.dismiss()
        }
        dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel") { _, _ ->
            dialog.dismiss()
        }
        dialog.show()
    }
    fun cityTake(context: Context, dialogListener: DialogListener) {
        val builder = AlertDialog.Builder(context)
        val edName = EditText(context)
        builder.setView(edName)
        val dialog = builder.create()

        dialog.setTitle("Введите название города")
        dialog.setButton(AlertDialog.BUTTON_POSITIVE,"OK"){ _,_->
            dialogListener.onClick(edName.text.toString())
            dialog.dismiss()
        }
        dialog.setButton(AlertDialog.BUTTON_NEGATIVE,"Cancel"){ _,_->
            dialog.dismiss()
        }

        dialog.show()
    }
        interface DialogListener {
            fun onClick(name:String?)
        }

}