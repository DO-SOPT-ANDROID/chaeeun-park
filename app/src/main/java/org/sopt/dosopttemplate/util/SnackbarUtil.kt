package org.sopt.dosopttemplate.util

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun showShortSnackBar(view: View, text: String) {
    Snackbar.make(view, text, Snackbar.LENGTH_SHORT).show()
}