package com.arsoft.base.util

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.app.ShareCompat


fun shareChooser(activity: Activity, title: String, data: String) {
    ShareCompat.IntentBuilder.from(activity)
        .setType("text/plain")
        .setChooserTitle(title)
        .setText(data)
        .startChooser()
}

fun shareToFacebook(context: Context, id: String) {
    context.startActivity(getOpenFacebookIntent(context, id))
}

private fun getOpenFacebookIntent(context: Context, id: String) = try {
    context.packageManager.getPackageInfo("com.facebook.katana", 0)
    Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/$id"))
} catch (e: Exception) {
    Intent(
        Intent.ACTION_VIEW,
        Uri.parse("https://www.facebook.com/$id")
    )
}

fun shareToInstagram(context: Context, ig: String) {
    try {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse("http://instagram.com/$ig")
        intent.setPackage("com.instagram.android")
        context.startActivity(intent)
    } catch (anfe: ActivityNotFoundException) {
        context.startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("http://instagram.com/_u/$ig")
            )
        )
    }
}

fun shareToYoutube(context: Context, name: String) {
    try {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setPackage("com.google.android.youtube")
        intent.data = Uri.parse(name)
        context.startActivity(intent)
    } catch (anfe: ActivityNotFoundException) {
        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(name)))
    }
}