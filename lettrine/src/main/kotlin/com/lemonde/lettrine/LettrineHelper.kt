package com.lemonde.lettrine

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
        var firstChar: Char? = null
        var truncatedFirstBlock: String? = null
        var blockToBeModified: String? = null
        val truncatedSpannedText: String

        try {
            val factory = XmlPullParserFactory.newInstance()
            factory.isNamespaceAware = true
            val xpp = factory.newPullParser()
            xpp.setInput(StringReader(spannedText))
            var eventType = xpp.eventType

            while (eventType != XmlPullParser.END_DOCUMENT) {
                // We look for the first text block
                if (eventType == XmlPullParser.TEXT) {
                    // Only letter character must be "lettrinized"
                    if (xpp.text != null
                            && !(xpp.text as CharSequence).isEmpty()
                            && Character.isLetter(xpp.text[0])) {
                        firstChar = xpp.text[0].toUpperCase()
                        truncatedFirstBlock = xpp.text.substring(1, xpp.text.length)
                        blockToBeModified = xpp.text
                    }

                    // In any cases we lease the xml inspection
                    break
                }
                eventType = xpp.next()
            }
        } catch (e: XmlPullParserException) {
            firstChar = spannedText[0].toUpperCase()
            if (Character.isLetter(firstChar)) {
                truncatedFirstBlock = spannedText.substring(1, spannedText.length)
                blockToBeModified = spannedText
            } else {
                return LettrineData(null, spannedText)
            }
        }

        truncatedSpannedText = getTruncatedFirstBlock(blockToBeModified, spannedText, truncatedFirstBlock)

        return LettrineData(firstChar, truncatedSpannedText)
    }

    // ----------------------------------
    // PRIVATE METHOD
    // ----------------------------------

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
}

/**
 * Data class used to pass parsed data form LettrineHelper to the lettrineView
 */

internal data class LettrineData(val lettrineChar: Char?, val remainingString: String)