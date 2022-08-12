package com.udacity.asteroidradar.presentation.adapter

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.udacity.asteroidradar.R

@BindingAdapter("statusIcon")
fun bindAsteroidStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.ic_status_potentially_hazardous)
    } else {
        imageView.setImageResource(R.drawable.ic_status_normal)
    }
}

@BindingAdapter("asteroidImageContentDescription")
fun bindPotentiallyHazardousImageContentDescription(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.contentDescription =
            imageView.context.getString(R.string.potentially_hazardous_asteroid_image)
    } else {
        imageView.contentDescription =
            imageView.context.getString(R.string.not_hazardous_asteroid_image)
    }

}

@BindingAdapter("asteroidImageOfTheDayContentDescription")
fun bindImageOfTheDayContentDescription(imageView: ImageView, imageTitle: String?) {
    imageView.contentDescription =
        imageView.context.getString(R.string.nasa_picture_of_day_content_description_format) + imageTitle
}

@BindingAdapter("asteroidImageIsHazardousContentDescription")
fun bindImageIsHazardousContentDescription(imageView: ImageView, string: Boolean) {
    imageView.contentDescription = imageView.context.getString(R.string.is_hazardous_img_desc)
}

@BindingAdapter("asteroidImageHelperContentDescription")
fun bindImageHelperContentDescription(imageView: ImageView, string: Boolean) {
    imageView.contentDescription = imageView.context.getString(R.string.helper_img_desc)
}

@BindingAdapter("asteroidStatusImage")
fun bindDetailsStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.asteroid_hazardous)
    } else {
        imageView.setImageResource(R.drawable.asteroid_safe)
    }
}

@BindingAdapter("astronomicalUnitText")
fun bindTextViewToAstronomicalUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.astronomical_unit_format), number)
}

@BindingAdapter("kmUnitText")
fun bindTextViewToKmUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_unit_format), number)
}

@BindingAdapter("velocityText")
fun bindTextViewToDisplayVelocity(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_s_unit_format), number)
}
