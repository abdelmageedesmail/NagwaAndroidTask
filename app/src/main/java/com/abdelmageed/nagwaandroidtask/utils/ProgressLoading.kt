package com.abdelmageed.nagwaandroidtask.utils

import android.app.Activity
import android.app.Application
import android.app.ProgressDialog
import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.qualifiers.ActivityContext
import java.lang.Exception
import javax.inject.Inject

class ProgressLoading(val context: Context) {
    val show: ProgressDialog = ProgressDialog(context)

    fun showLoading() {
        insertIndex(0, 0)
        show.setCanceledOnTouchOutside(false)
        show.show()
    }

    fun insertIndex(index: Int, totalItems: Int) {
        show.setMessage("Downloading..file: " + index + " from " + totalItems)
    }

    fun cancelLoading() {

        show!!.cancel()

    }
}