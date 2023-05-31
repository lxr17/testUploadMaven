package net.lxr.testuploadmaven

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import kotlin.math.min

/**
 * 贪吃蛇
 */
class SnakeView @JvmOverloads constructor(
    context: Context, attributeSet: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attributeSet, defStyleAttr) {
    private var mExactlySize = 0 // 实际大小
    private val mWallPaint = Paint() // 墙壁的画笔
    private val mSnakePaint = Paint() // 蛇的画笔
    private val mFoodPaint = Paint() // 食物的画笔
    private val mGridArray = Array(GRID_SIZE) { Array(GRID_SIZE) { RectF() } }// 每个小方格的矩阵范围
    private var mBoxSize = 0F // 小方格的宽度

    private val mSnakeList = mutableListOf<Point>() // 贪吃蛇
    private val mFoodList = mutableListOf<Point>() // 食物

    init {
        mWallPaint.color = Color.BLACK
        mSnakePaint.color = Color.GRAY
        mFoodPaint.color = Color.RED
    }

    /**
     * 测量布局的宽高
     */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(getSize(widthMeasureSpec), getSize(heightMeasureSpec))
        mExactlySize = min(measuredWidth, measuredHeight)
        mBoxSize = ((mExactlySize - (GRID_SIZE + 1) * LINE_WIDTH) * 1.0 / GRID_SIZE).toFloat()
        initGridArray()
    }

    private fun getSize(measureSpec: Int): Int {
        val specMode = MeasureSpec.getMode(measureSpec)
        val specSize = MeasureSpec.getSize(measureSpec)

        return when (specMode) {
            MeasureSpec.EXACTLY -> {
                specSize
            }
            MeasureSpec.AT_MOST -> {
                min(specSize, MIN_SIZE)
            }
            MeasureSpec.UNSPECIFIED -> {
                resources.displayMetrics.widthPixels
            }
            else -> 0
        }
    }

    /**
     * 初始化网格数组
     */
    private fun initGridArray() {
        mGridArray.forEachIndexed { line, rectFS ->
            rectFS.forEachIndexed { column, rectF ->
                rectF.top = (line + 1) * LINE_WIDTH + mBoxSize * line
                rectF.left = (column + 1) * LINE_WIDTH + mBoxSize * column
                rectF.bottom = rectF.top + mBoxSize
                rectF.right = rectF.left + mBoxSize
            }
        }
    }

    // 网格是LINE_WIDTH的白色线
    // 网格内部是具体的颜色
    // 墙壁也是网格小矩形
    override fun onDraw(canvas: Canvas?) {
        mGridArray.forEach {
            it.forEach { rectF ->
                canvas?.drawRect(rectF, mWallPaint)
            }
        }
    }

    companion object {
        /**
         * 该View的最小大小
         */
        const val MIN_SIZE = 600

        /**
         * 每行或者每列网格数量
         */
        const val GRID_SIZE = 10

        /**
         * 网格线的宽度
         */
        const val LINE_WIDTH = 3
    }
}