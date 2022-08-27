package com.udacity

import android.animation.AnimatorInflater
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import kotlin.properties.Delegates


private const val TAG = "LoadingButton"
class LoadingButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private var widthSize = 0
    private var heightSize = 0
    private var btnBgColor: Int? = null
    private var btnTxtColor: Int? = null
    private var loadingValue: Double = 0.0
    private var valueAnimator = ValueAnimator()

    private var buttonState: ButtonState by Delegates.observable<ButtonState>(ButtonState.Completed) { p, old, new ->
        Log.d(TAG, "buttonState: p: $p")
        Log.d(TAG, "buttonState: old: $old")
        Log.d(TAG, "buttonState: new: $new")
       /* when(new){
            ButtonState.Idel -> {}
            ButtonState.Clicked -> {
                buttonState = ButtonState.Loading
            }
            ButtonState.Loading -> {
                valueAnimator.start()
            }
            ButtonState.Completed -> {
                //hasCompletedDownload()
            }
        }*/
    }
    private val updateListener = ValueAnimator.AnimatorUpdateListener {
        loadingValue = (it.animatedValue as Float).toDouble()
        invalidate()
        requestLayout()
       // Log.d(TAG, "updateListener: loadingValue: $loadingValue")
    }

    init {
        isClickable = true
        valueAnimator = AnimatorInflater.loadAnimator(
            context,
            R.animator.loading
        ) as ValueAnimator

        valueAnimator.addUpdateListener(updateListener)
        val attr = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.LoadingButton,
            0,
            0
        )
        try {
            btnTxtColor = attr.getColor(
                R.styleable.LoadingButton_btnTxtColor,
                ContextCompat.getColor(context, R.color.white)
            )
            btnBgColor = attr.getColor(
                R.styleable.LoadingButton_btnBgColor,
                ContextCompat.getColor(context, R.color.colorPrimary)
            )

        } finally {
            attr.recycle()
        }
    }

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        textAlign = Paint.Align.CENTER
        textSize = 55.0f
        typeface = Typeface.create("", Typeface.BOLD)
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        paint.strokeWidth = 0f
        paint.color = btnBgColor!!
        canvas!!.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paint)

        if (buttonState == ButtonState.Loading) {
            paint.color = Color.parseColor("#004349")
            canvas.drawRect(
                0f, 0f,
                (width * (loadingValue / 100)).toFloat(), height.toFloat(), paint
            )
        }
        val buttonText = if (buttonState == ButtonState.Loading)
            resources.getString(R.string.button_loading)
        else resources.getString(R.string.button_name)

        paint.color = btnTxtColor!!
        canvas.drawText(buttonText, (width / 2).toFloat(), ((height + 30) / 2).toFloat(), paint)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val minw: Int = paddingLeft + paddingRight + suggestedMinimumWidth
        val w: Int = resolveSizeAndState(minw, widthMeasureSpec, 1)
        val h: Int = resolveSizeAndState(
            MeasureSpec.getSize(w),
            heightMeasureSpec,
            0
        )
        widthSize = w
        heightSize = h
        setMeasuredDimension(w, h)
    }

    fun hasCompletedDownload() {
        valueAnimator.cancel()
        buttonState = ButtonState.Completed
        invalidate()
        requestLayout()
    }

    override fun performClick(): Boolean {
        super.performClick()
      // buttonState = ButtonState.Clicked
        if (buttonState == ButtonState.Completed)
            buttonState = ButtonState.Loading
        valueAnimator.start()

        return true
    }
}