package test.dev.importantpeople.common.utils

import android.content.Context
import android.util.TypedValue
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

infix fun ImageView.load(imageUrl: String?) = Glide
    .with(context)
    .load(imageUrl)
    .fitCenter()
    .transition(DrawableTransitionOptions.withCrossFade())
    .into(this)

fun Int.toPx(context: Context): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        context.resources.displayMetrics
    ).toInt()
}