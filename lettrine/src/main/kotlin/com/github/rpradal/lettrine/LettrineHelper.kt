package com.github.rpradal.lettrine

import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import org.xmlpull.v1.XmlPullParserFactory
import java.io.StringReader


/**
 * Utility class used to extract the char that has to be used as a lettrine
 *
 * @author Rémi Pradal (rpradal@octo.com)
 */
internal object LettrineHelper {

    // ----------------------------------
    // PUBLIC METHOD
    // ----------------------------------

    /**
     * Extract information needed in order to display text with a lettrine style

     * @param spannedText the text to be parsed
     * *
     * @return Data structure containing the char that can be user as a Lettrine and the truncated
     * * spanned text
     */
    fun parseLettrineInformation(spannedText: String): LettrineData {
        val truncatedSpannedText: String
        var parsed: ParsedTextBlock? = null

        try {
            val xpp = getXmlPullParser(spannedText)
            var eventType = xpp.eventType

            while (eventType != XmlPullParser.END_DOCUMENT) {
                // We look for the first text block
                if (eventType == XmlPullParser.TEXT) {
                    // Only letter character must be "lettrinized"
                    if (isTextBlockLettrinzable(xpp)) {
                        val firstChar = xpp.text[0].toUpperCase()
                        val truncatedFirstBlock = xpp.text.substring(1, xpp.text.length)
                        val blockToBeModified = xpp.text
                        parsed = ParsedTextBlock(firstChar, truncatedFirstBlock, blockToBeModified)
                    }

                    // In any cases we leave the xml inspection
                    break
                }
                eventType = xpp.next()
            }
        } catch (e: XmlPullParserException) {
            parsed = getUnparsableModifiedTextBlock(spannedText)
        }

        truncatedSpannedText = getTruncatedFirstBlock(parsed?.blockToBeModified, spannedText, parsed?.truncatedFirstBlock)

        return LettrineData(parsed?.firstChar, truncatedSpannedText)
    }

    // ----------------------------------
    // PRIVATE METHOD
    // ----------------------------------

    private fun getXmlPullParser(spannedText: String): XmlPullParser {
        val factory = XmlPullParserFactory.newInstance()
        factory.isNamespaceAware = true
        val xpp = factory.newPullParser()
        xpp.setInput(StringReader(spannedText))
        return xpp
    }

    private fun getUnparsableModifiedTextBlock(spannedText: String): ParsedTextBlock? {
        var parsed: ParsedTextBlock?
        val firstChar = spannedText[0].toUpperCase()
        if (Character.isLetter(firstChar)) {
            val truncatedFirstBlock = spannedText.substring(1, spannedText.length)
            val blockToBeModified = spannedText
            parsed = ParsedTextBlock(firstChar, truncatedFirstBlock, blockToBeModified)
        } else {
            parsed = null
        }
        return parsed
    }

    private fun getTruncatedFirstBlock(blockToBeModified: String?, spannedText: String, truncatedFirstBlock: String?): String {
        var truncatedSpannedText: String
        if (blockToBeModified != null && truncatedFirstBlock != null) {
            // We create the new remaining spanned text
            truncatedSpannedText = spannedText.replace(blockToBeModified, truncatedFirstBlock)
        } else {
            truncatedSpannedText = spannedText
        }
        return truncatedSpannedText
    }

    private fun isTextBlockLettrinzable(xpp: XmlPullParser): Boolean {
        return (xpp.text != null
                && !(xpp.text as CharSequence).isEmpty()
                && Character.isLetter(xpp.text[0]))
    }

}

/**
 * Data class used to store intermediate parsing information
 */
private data class ParsedTextBlock(val firstChar: Char?, val truncatedFirstBlock: String, val blockToBeModified: String?)

/**
 * Data class used to pass parsed data form LettrineHelper to the lettrineView
 */

internal data class LettrineData(val lettrineChar: Char?, val remainingString: String)