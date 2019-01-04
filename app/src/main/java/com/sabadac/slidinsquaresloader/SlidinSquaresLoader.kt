package com.sabadac.slidinsquaresloader

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.view.animation.LinearInterpolator

class SlidinSquaresLoader @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val squareSide = 40
    private val squareDistance = 8
    private val squareCornerRadius = 10
    private val numberOfSquares = 6
    private val duration = 400L
    private val canvasSide = numberOfSquares * dpToPx(squareSide * 2 + squareDistance, context)
    private val bitmap = Bitmap.createBitmap(canvasSide.toInt(), canvasSide.toInt(), Bitmap.Config.ARGB_8888)
    private val bitmapCanvas = Canvas(bitmap)
    private val bitmapPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val positions = Array(12) { RectF() }
    private val squares = Array(12) { RectF() }

    init {
        initColorsAndPositions()
        restartSquaresPositions()


        val animatorSet = AnimatorSet()
        animatorSet.playSequentially(
            moveFromTo(intArrayOf(1), intArrayOf(2), intArrayOf(4)),
            moveFromTo(intArrayOf(2, 6), intArrayOf(6, 10), intArrayOf(4, 2)),
            moveFromTo(intArrayOf(10), intArrayOf(11), intArrayOf(2)),
            moveFromTo(intArrayOf(11, 7), intArrayOf(7, 3), intArrayOf(2, 3)),
            moveFromTo(intArrayOf(3), intArrayOf(2), intArrayOf(3)),
            moveFromTo(intArrayOf(2, 6), intArrayOf(6, 10), intArrayOf(3, 4)),
            moveFromTo(intArrayOf(10), intArrayOf(9), intArrayOf(4)),
            moveFromTo(intArrayOf(9, 5), intArrayOf(5, 1), intArrayOf(4, 1)),
            moveFromTo(intArrayOf(1), intArrayOf(0), intArrayOf(1)),
            moveFromTo(intArrayOf(0, 4), intArrayOf(4, 8), intArrayOf(1, 0)),
            moveFromTo(intArrayOf(8), intArrayOf(9), intArrayOf(0)),
            moveFromTo(intArrayOf(9, 5), intArrayOf(5, 1), intArrayOf(0, 4))
        )

        animatorSet.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                restartSquaresPositions()
                animatorSet.start()
            }
        })
        animatorSet.start()
        paint.color = Color.RED
    }

    private fun initColorsAndPositions() {
        positions[0].left = bitmap.width / 2f - dpToPx(squareSide, context) - dpToPx(squareDistance, context) / 2f -
                dpToPx(squareDistance, context) - dpToPx(squareSide, context)
        positions[0].right = bitmap.width / 2f - dpToPx(squareDistance, context) / 2f -
                dpToPx(squareDistance, context) - dpToPx(squareSide, context)
        positions[0].top = (bitmap.height - dpToPx(squareSide, context)) / 2f - dpToPx(squareSide, context) -
                dpToPx(squareDistance, context)
        positions[0].bottom = (bitmap.height - dpToPx(squareSide, context)) / 2f - dpToPx(squareDistance, context)

        positions[1].left = bitmap.width / 2f - dpToPx(squareSide, context) - dpToPx(squareDistance, context) / 2f
        positions[1].right = bitmap.width / 2f - dpToPx(squareDistance, context) / 2f
        positions[1].top = (bitmap.height - dpToPx(squareSide, context)) / 2f - dpToPx(squareSide, context) -
                dpToPx(squareDistance, context)
        positions[1].bottom = (bitmap.height - dpToPx(squareSide, context)) / 2f - dpToPx(squareDistance, context)

        positions[2].left = bitmap.width / 2f + dpToPx(squareDistance, context) / 2f
        positions[2].right = bitmap.width / 2f + dpToPx(squareDistance, context) / 2f + dpToPx(squareSide, context)
        positions[2].top = (bitmap.height - dpToPx(squareSide, context)) / 2f - dpToPx(squareSide, context) -
                dpToPx(squareDistance, context)
        positions[2].bottom = (bitmap.height - dpToPx(squareSide, context)) / 2f - dpToPx(squareDistance, context)

        positions[3].left = bitmap.width / 2f + dpToPx(squareDistance, context) / 2f + dpToPx(squareDistance, context) +
                dpToPx(squareSide, context)
        positions[3].right = bitmap.width / 2f + dpToPx(squareDistance, context) / 2f +
                dpToPx(squareSide, context) + dpToPx(squareDistance, context) / 2f + dpToPx(squareDistance, context) +
                dpToPx(squareSide, context)
        positions[3].top = (bitmap.height - dpToPx(squareSide, context)) / 2f - dpToPx(squareSide, context) -
                dpToPx(squareDistance, context)
        positions[3].bottom = (bitmap.height - dpToPx(squareSide, context)) / 2f - dpToPx(squareDistance, context)

        positions[4].left = bitmap.width / 2f - dpToPx(squareSide, context) - dpToPx(squareDistance, context) / 2f -
                dpToPx(squareDistance, context) - dpToPx(squareSide, context)
        positions[4].right = bitmap.width / 2f - dpToPx(squareDistance, context) / 2f -
                dpToPx(squareDistance, context) - dpToPx(squareSide, context)
        positions[4].top = (bitmap.height - dpToPx(squareSide, context)) / 2f
        positions[4].bottom = (bitmap.height + dpToPx(squareSide, context)) / 2f

        positions[5].left = bitmap.width / 2f - dpToPx(squareSide, context) - dpToPx(squareDistance, context) / 2f
        positions[5].right = bitmap.width / 2f - dpToPx(squareDistance, context) / 2f
        positions[5].top = (bitmap.height - dpToPx(squareSide, context)) / 2f
        positions[5].bottom = (bitmap.height + dpToPx(squareSide, context)) / 2f

        positions[6].left = bitmap.width / 2f + dpToPx(squareDistance, context) / 2f
        positions[6].right = bitmap.width / 2f + dpToPx(squareDistance, context) / 2f + dpToPx(squareSide, context)
        positions[6].top = (bitmap.height - dpToPx(squareSide, context)) / 2f
        positions[6].bottom = (bitmap.height + dpToPx(squareSide, context)) / 2f

        positions[7].left = bitmap.width / 2f + dpToPx(squareDistance, context) / 2f + dpToPx(squareDistance, context) +
                dpToPx(squareSide, context)
        positions[7].right = bitmap.width / 2f + dpToPx(squareDistance, context) / 2f +
                dpToPx(squareSide, context) + dpToPx(squareDistance, context) / 2f + dpToPx(squareDistance, context) +
                dpToPx(squareSide, context)
        positions[7].top = (bitmap.height - dpToPx(squareSide, context)) / 2f
        positions[7].bottom = (bitmap.height + dpToPx(squareSide, context)) / 2f

        positions[8].left = bitmap.width / 2f - dpToPx(squareSide, context) - dpToPx(squareDistance, context) / 2f -
                dpToPx(squareDistance, context) - dpToPx(squareSide, context)
        positions[8].right = bitmap.width / 2f - dpToPx(squareDistance, context) / 2f -
                dpToPx(squareDistance, context) - dpToPx(squareSide, context)
        positions[8].top = (bitmap.height + dpToPx(squareSide, context)) / 2f + dpToPx(squareDistance, context)
        positions[8].bottom = (bitmap.height + dpToPx(squareSide, context)) / 2f + dpToPx(squareSide, context) +
                dpToPx(squareDistance, context)

        positions[9].left = bitmap.width / 2f - dpToPx(squareSide, context) - dpToPx(squareDistance, context) / 2f
        positions[9].right = bitmap.width / 2f - dpToPx(squareDistance, context) / 2f
        positions[9].top = (bitmap.height + dpToPx(squareSide, context)) / 2f + dpToPx(squareDistance, context)
        positions[9].bottom = (bitmap.height + dpToPx(squareSide, context)) / 2f + dpToPx(squareSide, context) +
                dpToPx(squareDistance, context)

        positions[10].left = bitmap.width / 2f + dpToPx(squareDistance, context) / 2f
        positions[10].right = bitmap.width / 2f + dpToPx(squareDistance, context) / 2f + dpToPx(squareSide, context)
        positions[10].top = (bitmap.height + dpToPx(squareSide, context)) / 2f + dpToPx(squareDistance, context)
        positions[10].bottom = (bitmap.height + dpToPx(squareSide, context)) / 2f + dpToPx(squareSide, context) +
                dpToPx(squareDistance, context)

        positions[11].left = bitmap.width / 2f + dpToPx(squareDistance, context) / 2f +
                dpToPx(squareDistance, context) + dpToPx(squareSide, context)
        positions[11].right = bitmap.width / 2f + dpToPx(squareDistance, context) / 2f +
                dpToPx(squareSide, context) + dpToPx(squareDistance, context) / 2f + dpToPx(squareDistance, context) +
                dpToPx(squareSide, context)
        positions[11].top = (bitmap.height + dpToPx(squareSide, context)) / 2f + dpToPx(squareDistance, context)
        positions[11].bottom = (bitmap.height + dpToPx(squareSide, context)) / 2f + dpToPx(squareSide, context) +
                dpToPx(squareDistance, context)
    }

    private fun restartSquaresPositions() {
        for (i in 4..7) {
            squares[i - 4].set(positions[i])
        }
        squares[4].set(positions[1])
    }

    private fun moveFromTo(from: IntArray, to: IntArray, which: IntArray, withDelay: Long = 0L): ValueAnimator {
        val valueAnimator = ValueAnimator.ofFloat(0f, 1f)
        valueAnimator.duration = duration
        valueAnimator.startDelay = withDelay
        valueAnimator.interpolator = LinearInterpolator()
        valueAnimator.addUpdateListener(object : ValueAnimator.AnimatorUpdateListener {
            override fun onAnimationUpdate(animation: ValueAnimator?) {
                val fraction = animation!!.animatedFraction

                from.forEachIndexed { index, fromValue ->
                    val i = which[index]
                    val toValue = to[index]

                    squares[i].left = positions[fromValue].left + fraction *
                            (positions[toValue].left - positions[fromValue].left)
                    squares[i].right = positions[fromValue].right + fraction *
                            (positions[toValue].right - positions[fromValue].right)
                    squares[i].top = positions[fromValue].top + fraction *
                            (positions[toValue].top - positions[fromValue].top)
                    squares[i].bottom = positions[fromValue].bottom + fraction *
                            (positions[toValue].bottom - positions[fromValue].bottom)
                }

                invalidate()
            }
        })
        return valueAnimator
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        bitmapCanvas.save()
        bitmapCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)
        bitmapCanvas.drawColor(Color.WHITE)
        bitmapCanvas.restore()

        squares.forEachIndexed { index, position ->
            bitmapCanvas.drawRoundRect(
                position,
                dpToPx(squareCornerRadius / 2, context),
                dpToPx(squareCornerRadius / 2, context),
                paint
            )
        }



        canvas?.drawBitmap(bitmap, (width - bitmap.width) / 2f, (height - bitmap.height) / 2f, bitmapPaint)
    }

    private fun dpToPx(dp: Int, context: Context): Float =
        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), context.resources.displayMetrics)

}