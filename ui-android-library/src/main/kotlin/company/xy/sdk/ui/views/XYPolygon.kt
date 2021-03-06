package company.xy.sdk.ui.views

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import company.xy.sdk.ui.R
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

class XYPolygon : View {

    private var sides = 2
    private var strokeColor = -0x1000000
    private var strokeWidth = 0
    private var fillColor = -0x1
    private var startAngle = -90f
    private var showInscribedCircle = false
    private var fillPercent = 1f
    private var fillBitmapResourceId = -1

    private var alpha = 100
    private var fillPaint: Paint? = null
    private var inscribedCirclePaint: Paint? = null
    private var polyPath: Path? = null

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init(attrs)
    }

    private fun initFromAttributeSet(attrs: AttributeSet) {
        val polyAttributes = context.obtainStyledAttributes(
                attrs,
                R.styleable.XYPolygon)

        sides = polyAttributes.getInt(R.styleable.XYPolygon_sides, sides)
        strokeColor = polyAttributes.getColor(R.styleable.XYPolygon_stroke_color, strokeColor)
        strokeWidth = polyAttributes.getInt(R.styleable.XYPolygon_stroke_width, strokeWidth)
        fillColor = polyAttributes.getColor(R.styleable.XYPolygon_fill_color, fillColor)
        alpha = polyAttributes.getColor(R.styleable.XYPolygon_fill_alpha, alpha)
        startAngle = polyAttributes.getFloat(R.styleable.XYPolygon_start_angle, startAngle)
        showInscribedCircle = polyAttributes.getBoolean(R.styleable.XYPolygon_inscribed_circle, showInscribedCircle)
        fillBitmapResourceId = polyAttributes.getResourceId(R.styleable.XYPolygon_fill_bitmap, fillBitmapResourceId)
        fillPercent = polyAttributes.getFloat(R.styleable.XYPolygon_fill_percent, 100f) / 100f

        polyAttributes.recycle()
    }

    private fun initFillPaint() {
        fillPaint = Paint()
        fillPaint!!.color = fillColor
        fillPaint!!.alpha = alpha
        fillPaint!!.style = Paint.Style.FILL
    }

    private fun initFillBitmap() {
        if (fillBitmapResourceId != -1) {
            val opt = BitmapFactory.Options()
            
            opt.inTempStorage = ByteArray(16 * 1024)
            opt.inSampleSize = 4

            val fillBitmap = BitmapFactory.decodeResource(resources, fillBitmapResourceId, opt)
            val fillShader = BitmapShader(fillBitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT)
            fillPaint!!.shader = fillShader
        }
    }

    private fun initStrokeWidth() {
        if (strokeWidth > 0) {
            val strokePaint = Paint(Paint.ANTI_ALIAS_FLAG)
            strokePaint.color = strokeColor
            strokePaint.strokeWidth = strokeWidth.toFloat()
            strokePaint.style = Paint.Style.STROKE
        }
    }

    private fun initInscribedCircle() {
        if (showInscribedCircle) {
            inscribedCirclePaint = Paint(Paint.ANTI_ALIAS_FLAG)
            inscribedCirclePaint!!.color = -0x1000000
            inscribedCirclePaint!!.strokeWidth = 1f
            inscribedCirclePaint!!.style = Paint.Style.STROKE
        }
    }

    private fun initFillPercent() {
        if (fillPercent < 0 || fillPercent > 1) {
            fillPercent = 1f
        }
    }

    private fun initPolyPath() {
        polyPath = Path()
        polyPath!!.fillType = Path.FillType.WINDING
    }

    private fun init(attrs: AttributeSet) {
        initFromAttributeSet(attrs)
        initFillPaint()
        initFillBitmap()
        initStrokeWidth()
        initInscribedCircle()
        initFillPercent()
        initPolyPath()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val measuredWidth = measureHeightWidth(widthMeasureSpec)
        val measuredHeight = measureHeightWidth(heightMeasureSpec)
        setMeasuredDimension(measuredWidth, measuredHeight)
    }

    private fun measureHeightWidth(measureSpec: Int): Int {
        val specMode = MeasureSpec.getMode(measureSpec)
        val specSize = MeasureSpec.getSize(measureSpec)

        val result: Int

        result = when (specMode) {
            MeasureSpec.AT_MOST -> specSize
            MeasureSpec.EXACTLY -> specSize
            MeasureSpec.UNSPECIFIED ->
                // random size if nothing is specified
                500
            else -> 500
        }
        return result
    }

    private fun updateCanvas(canvas: Canvas, polyPath:Path) {
        canvas.save()
        canvas.translate(x, y)
        canvas.rotate(startAngle)
        canvas.drawPath(polyPath, fillPaint!!)

        canvas.restore()
    }

    private fun addInscribedCircle(canvas: Canvas, radius: Int) {
        if (showInscribedCircle) {
            canvas.drawCircle(x, y, radius.toFloat(), inscribedCirclePaint!!)
        }
    }

    private fun drawArc(workingRadius: Float, radius: Int, a: Float): Float {
        polyPath?.let {
            it.moveTo(workingRadius, 0f)
            for (i in 1 until sides) {
                it.lineTo((workingRadius * cos((a * i).toDouble())).toFloat(),
                        (workingRadius * sin((a * i).toDouble())).toFloat())
            }
            it.close()
        }

        return workingRadius - (radius * fillPercent).toInt()
    }

    // The poly is created as a shape in a path.
    // If there is a hole in the poly, draw a 2nd shape inset from the first
    override fun onDraw(canvas: Canvas) {
        polyPath?.let { polyPath ->
            val x = measuredWidth / 2
            val y = measuredHeight / 2
            val radius = min(x, y)

            if (sides < 3) return

            var a = (Math.PI * 2).toFloat() / sides
            var workingRadius = radius.toFloat()
            polyPath.reset()

            for (j in 0 until if (fillPercent < 1) 2 else 1) {
                workingRadius = drawArc(workingRadius, radius, a)
                a = -a
            }

            updateCanvas(canvas, polyPath)
            addInscribedCircle(canvas, radius)
        }

        super.onDraw(canvas)
    }
}
