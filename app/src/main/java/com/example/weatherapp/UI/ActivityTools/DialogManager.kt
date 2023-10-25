package com.example.weatherapp.UI.ActivityTools

import android.app.AlertDialog
import android.content.Context
import android.widget.EditText
import com.example.weatherapp.R
import com.example.weatherapp.Repository.Application.App


object DialogManager {
   val app = App.applicationContext
    fun locationSetting(context: Context, dialogListener: DialogListener) {
        val builder = AlertDialog.Builder(context)
        val dialog = builder.create()
        dialog.setTitle(app.getString(R.string.enable_location))
        dialog.setMessage(app.getString(R.string.location_on_answer))
        dialog.setButton(AlertDialog.BUTTON_POSITIVE, app.getString(R.string.OK)) { _, _ ->
            dialogListener.onClick(null)
            dialog.dismiss()
        }
        dialog.setButton(AlertDialog.BUTTON_NEGATIVE, app.getString(R.string.Cancel)) { _, _ ->
            dialog.dismiss()
        }
        dialog.show()
    }
    fun cityTake(context: Context, dialogListener: DialogListener) {
        val builder = AlertDialog.Builder(context)
        val edName = EditText(context)
        builder.setView(edName)
        val dialog = builder.create()

        dialog.setTitle(app.getString(R.string.City_name))
        dialog.setButton(AlertDialog.BUTTON_POSITIVE,app.getString(R.string.OK)){ _,_->
            dialogListener.onClick(edName.text.toString())
            dialog.dismiss()
        }
        dialog.setButton(AlertDialog.BUTTON_NEGATIVE, app.getString(R.string.Cancel)){ _, _->
            dialog.dismiss()
        }

        dialog.show()
    }
        interface DialogListener {
            fun onClick(name:String?)
        }

}