package org.sopt.dosopttemplate.util

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

fun ImageView.loadImageUrl(url: String?, width: Int, height: Int) {
    Glide.with(context)
        .load(url)
        .apply(RequestOptions().override(width, height))
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .into(this)
}
