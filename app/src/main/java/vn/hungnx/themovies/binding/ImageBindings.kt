package vn.hungnx.themovies.binding

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.signature.ObjectKey

@BindingAdapter(
    value = ["imageUrl", "thumbnailUrl", "placeholder", "errorDrawable", "requestListener", "centerCrop", "fitCenter", "circleCrop", "diskCacheStrategy", "signatureKey"],
    requireAll = false
)
fun ImageView.loadImage(
    imageUrl: String? = null,
    thumbnailUrl: String? = null,
    placeHolder: Drawable? = null,
    errorDrawable: Drawable? = null,
    requestListener: RequestListener<Drawable>? = null,
    centerCrop: Boolean = false,
    fitCenter: Boolean = false,
    circleCrop: Boolean = false,
    diskCacheStrategy: DiskCacheStrategy? = null,
    signatureKey: String? = null
) {
    if (imageUrl.isNullOrBlank()) {
        setImageDrawable(placeHolder)
        return
    }

    val requestOptions = RequestOptions().apply {
        diskCacheStrategy(diskCacheStrategy ?: DiskCacheStrategy.RESOURCE)

        placeholder(placeHolder)
        error(errorDrawable)

        // crop type
        if (centerCrop) centerCrop()
        if (fitCenter) fitCenter()
        if (circleCrop) circleCrop()

        if (!signatureKey.isNullOrBlank()) {
            signature(ObjectKey(signatureKey))
        }
    }

    Glide.with(context).load(imageUrl).apply {
        transition(DrawableTransitionOptions.withCrossFade())
        addListener(requestListener)
        apply(requestOptions)
    }.into(this)
}