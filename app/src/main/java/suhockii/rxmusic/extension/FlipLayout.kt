package suhockii.rxmusic.extension

import android.content.Context
import android.graphics.Camera
import android.util.AttributeSet
import android.view.View
import android.view.animation.Animation
import android.view.animation.DecelerateInterpolator
import android.view.animation.Transformation
import android.widget.FrameLayout

class FlipLayout : FrameLayout, Animation.AnimationListener {
    private var listener: OnFlipListener? = null
    private var animator: FlipAnimator? = null
    private var isFlipped: Boolean = false
    var thirdViewEnabled: Boolean = false
    private var direction: Direction? = null
    var view0: View? = null
    var view1: View? = null
    var view2: View? = null

    fun showView(view: View) {
        if (view == view2) thirdViewEnabled = true
        if (view.visibility != View.VISIBLE) toggleUp()
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context) : super(context) {
        init(context)
    }

    private fun init(context: Context) {
        animator = FlipAnimator()
        animator!!.setAnimationListener(this)
        animator!!.interpolator = fDefaultInterpolator
        animator!!.duration = ANIM_DURATION_MILLIS.toLong()
        direction = Direction.DOWN
        isSoundEffectsEnabled = true
    }

    override fun onFinishInflate() {
        super.onFinishInflate()

        if (childCount > 3) {
            throw IllegalStateException("FlipLayout can host only three direct children")
        }

        view0 = getChildAt(0)
        view1 = getChildAt(1)
        view2 = getChildAt(2)
        reset()
    }

    private fun toggleView() {
        if (view0 == null || view1 == null || view2 == null) {
            return
        }

        if (isFlipped && !thirdViewEnabled) {
            view0!!.visibility = View.VISIBLE
            view1!!.visibility = View.GONE
            view2!!.visibility = View.GONE
        } else if (thirdViewEnabled) {
            view0!!.visibility = View.GONE
            view1!!.visibility = View.GONE
            view2!!.visibility = View.VISIBLE
        } else {
            view0!!.visibility = View.GONE
            view1!!.visibility = View.VISIBLE
            view2!!.visibility = View.GONE
        }

        isFlipped = !isFlipped
    }

    fun setOnFlipListener(listener: OnFlipListener) {
        this.listener = listener
    }

    fun reset() {
        isFlipped = false
        direction = Direction.DOWN
        view0!!.visibility = View.VISIBLE
        view1!!.visibility = View.GONE
        view2!!.visibility = View.GONE
    }

    fun toggleUp() {
        direction = Direction.UP
        startAnimation()
    }

    fun toggleDown() {
        direction = Direction.DOWN
        startAnimation()
    }

    fun toggleLeft() {
        direction = Direction.LEFT
        startAnimation()
    }

    fun toggleRight() {
        direction = Direction.RIGHT
        startAnimation()
    }

    fun startAnimation() {
        animator!!.setVisibilitySwapped()
        startAnimation(animator)
    }

    override fun onAnimationStart(animation: Animation) {
        if (listener != null) {
            listener!!.onFlipStart(this)
        }
    }

    override fun onAnimationEnd(animation: Animation) {
        if (listener != null) {
            listener!!.onFlipEnd(this)
        }
        if (direction == Direction.UP) direction = Direction.DOWN
        if (direction == Direction.DOWN) direction = Direction.UP
        if (direction == Direction.LEFT) direction = Direction.RIGHT
        if (direction == Direction.RIGHT) direction = Direction.LEFT
    }

    override fun onAnimationRepeat(animation: Animation) {}

    fun setAnimationListener(listener: Animation.AnimationListener) {
        animator!!.setAnimationListener(listener)
    }

    private enum class Direction {
        UP, DOWN, LEFT, RIGHT
    }

    interface OnFlipListener {

        fun onFlipStart(view: FlipLayout)

        fun onFlipEnd(view: FlipLayout)
    }

    inner class FlipAnimator : Animation() {
        private var camera: Camera? = null
        private var centerX: Float = 0.toFloat()
        private var centerY: Float = 0.toFloat()
        private var visibilitySwapped: Boolean = false

        init {
            fillAfter = true
        }

        fun setVisibilitySwapped() {
            visibilitySwapped = false
        }

        override fun initialize(width: Int, height: Int, parentWidth: Int, parentHeight: Int) {
            super.initialize(width, height, parentWidth, parentHeight)
            camera = Camera()
            this.centerX = (width / 2).toFloat()
            this.centerY = (height / 2).toFloat()
        }

        override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
            // Angle around the y-axis of the rotation at the given time. It is
            // calculated both in radians and in the equivalent degrees.
            val radians = Math.PI * interpolatedTime

            var degrees = (180.0 * radians / Math.PI).toFloat()

            if (direction == Direction.UP || direction == Direction.LEFT) {
                degrees = -degrees
            }

            // Once we reach the midpoint in the animation, we need to hide the
            // source view and show the destination view. We also need to change
            // the angle by 180 degrees so that the destination does not come in
            // flipped around. This is the main problem with SDK sample, it does
            // not
            // do this.
            if (interpolatedTime >= 0.5f) {
                when (direction) {
                    Direction.LEFT, Direction.UP -> degrees += 180f
                    Direction.RIGHT, Direction.DOWN -> degrees -= 180f
                }

                if (!visibilitySwapped) {
                    toggleView()
                    visibilitySwapped = true
                }
            }

            val matrix = t.matrix

            camera!!.save()
            //you can delete this line, it move camera a little far from view and get back
            camera!!.translate(0.0f, 0.0f, (EXPERIMENTAL_VALUE * Math.sin(radians)).toFloat())
            when (direction) {
                Direction.DOWN, Direction.UP -> {
                    camera!!.rotateX(degrees)
                    camera!!.rotateY(0f)
                }
                Direction.LEFT, Direction.RIGHT -> {
                    camera!!.rotateY(degrees)
                    camera!!.rotateX(0f)
                }
            }
            camera!!.rotateZ(0f)
            camera!!.getMatrix(matrix)
            camera!!.restore()

            matrix.preTranslate(-centerX, -centerY)
            matrix.postTranslate(centerX, centerY)
        }
    }

    companion object {
        private val EXPERIMENTAL_VALUE = 50f
        val ANIM_DURATION_MILLIS = 500
        private val fDefaultInterpolator = DecelerateInterpolator()
    }
}