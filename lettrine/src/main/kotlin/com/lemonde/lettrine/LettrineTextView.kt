package com.lemonde.lettrine

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.Typeface
import android.text.Html
import android.text.Layout
import android.text.SpannableString
import android.text.TextUtils
import android.text.style.LeadingMarginSpan
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import org.jetbrains.anko.textView

/**
 * LettrineTextView can be used to display a text with a lettrine
 *
 * @author Rémi Pradal (rpradal@octo.com)
 */
class LettrineTextView : FrameLayout {

    // ---------------------------------
    // CONSTANTS
    // ---------------------------------

    companion object {
        private val LETTRINE_LINES_SIZE_EQUIVALENT = 2
        private val DEFAULT_TEXT_SIZE_SP = 14
    }

    // ---------------------------------
    // CONSTRUCTOR
    // ---------------------------------

    constructor(ctx: Context) : super(ctx) {
        initView(null)
    }

    constructor(ctx: Context, attrs: AttributeSet) : super(ctx, attrs) {
        initView(attrs)
    }

    constructor(ctx: Context, attrs: AttributeSet, defStyleAttribute: Int) : super(ctx, attrs, defStyleAttribute) {
        initView(attrs)
    }

    // ---------------------------------
    // ATTRIBUTES
    // ---------------------------------

    private var lettrineLinesSizeEquivalent = LETTRINE_LINES_SIZE_EQUIVALENT
    private var bodyTextSize = DEFAULT_TEXT_SIZE_SP
    private var fontPath: String? = null

    // ---------------------------------
    // INTERNAL VIEWS
    // ---------------------------------

    private val bodyTextView = textView {
        textSize = DEFAULT_TEXT_SIZE_SP.toFloat()
    }
    private val lettrineTextView = textView()

    // ---------------------------------
    // PUBLIC METHODS
    // ---------------------------------

    fun setBodyText(articleBody: String) {
        if (!TextUtils.isEmpty(articleBody)) {
            val lettrineData = LettrineHelper.parseLettrineInformation(articleBody)
            val spanned = Html.fromHtml(lettrineData.remainingString)
            val spannableString = SpannableString(spanned)

            if (lettrineData.lettrineChar != null) {
                lettrineTextView.visibility = View.VISIBLE
                lettrineTextView.text = Character.toString(lettrineData.lettrineChar)
                spannableString.setSpan(LettrineLeadingMarginSpan2(lettrineLinesSizeEquivalent, getTextViewWidth(lettrineTextView)), 0, spannableString.length, 0)
            } else {
                lettrineTextView.visibility = View.GONE
            }

            bodyTextView.visibility = View.VISIBLE
            bodyTextView.text = spannableString
        } else {
            lettrineTextView.visibility = View.GONE
            bodyTextView.visibility = View.GONE
        }
    }

    // ---------------------------------
    // PRIVATE METHODS
    // ---------------------------------

    private fun initView(attrs: AttributeSet?) {
        var bodyText: String = ""

        attrs?.let {
            val typedArray = context.theme.obtainStyledAttributes(
                    it,
                    R.styleable.LettrineTextView,
                    0, 0);
            lettrineLinesSizeEquivalent = typedArray.getInteger(R.styleable.LettrineTextView_lettrine_lettrineSize, LETTRINE_LINES_SIZE_EQUIVALENT)
            bodyTextSize = typedArray.getDimensionPixelSize(R.styleable.LettrineTextView_lettrine_textSize, DEFAULT_TEXT_SIZE_SP)
            fontPath = typedArray.getString(R.styleable.LettrineTextView_lettrine_font)
            bodyText = typedArray.getString(R.styleable.LettrineTextView_lettrine_text) ?: ""
        }

        bodyTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, bodyTextSize.toFloat())
        val lettrineTextSize = (bodyTextView.lineHeight + bodyTextView.lineHeight - bodyTextSize) * lettrineLinesSizeEquivalent
        lettrineTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, lettrineTextSize.toFloat())

        val lettrineSpacing = lettrineTextView.lineHeight - lettrineTextSize

        lettrineTextView.setPadding(0, -lettrineSpacing, bodyTextSize.toInt(), 0)

        fontPath?.let {
            val typeFace = Typeface.createFromAsset(context.assets, fontPath);
            bodyTextView.typeface = typeFace
            lettrineTextView.typeface = typeFace
        }

        setBodyText(bodyText)
    }

    private fun getTextViewWidth(textView: TextView): Int {
        val bounds = Rect()
        textView.paint.getTextBounds(textView.text.toString(), 0, textView.text.length, bounds)
        return bounds.width() + textView.paddingRight + textView.paddingLeft
    }

    // ---------------------------------
    // INNER CLASS
    // ---------------------------------

    /**
     * Custom Margin Span used to put a mMargin only for limited number of mLineNumber
     *
     * @param lineNumber number of line on which we apply the mMargin
     * *
     * @param margin     in pixels
     */
    private inner class LettrineLeadingMarginSpan2(private val mLineNumber: Int, private val mMargin: Int) : LeadingMarginSpan.LeadingMarginSpan2 {


        override fun getLeadingMargin(first: Boolean): Int {
            return if (first) mMargin else 0
        }

        override fun drawLeadingMargin(c: Canvas, p: Paint, x: Int, dir: Int,
                                       top: Int, baseline: Int, bottom: Int, text: CharSequence,
                                       start: Int, end: Int, first: Boolean, layout: Layout) {
        }

        override fun getLeadingMarginLineCount(): Int {
            return mLineNumber
        }
    }
}