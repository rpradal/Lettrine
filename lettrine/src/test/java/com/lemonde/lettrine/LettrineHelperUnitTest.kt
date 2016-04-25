package com.lemonde.lettrine

import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricGradleTestRunner
import org.robolectric.annotation.Config

/**
 * @author Rémi Pradal (rpradal@octo.com)
 */
@RunWith(RobolectricGradleTestRunner::class)
@Config(constants = BuildConfig::class, sdk = intArrayOf(18))
class LettrineHelperUnitTest {

    @Test
    fun parseLettrineInformation_WhenFirstLetterIsAlphaNumerical_ShouldLettrinizeFirstChar() {
        // Given
        val fullText = "Lorem ipsum"

        // When
        val lettrineData = LettrineHelper.parseLettrineInformation(fullText)

        // Then
        val lettrine = lettrineData.lettrineChar
        val remainingBody = lettrineData.remainingString
        Assert.assertEquals("orem ipsum", remainingBody)
        Assert.assertEquals('L', lettrine)
    }

    @Test
    fun parseLettrineInformation_WhenFirstLetterIsNotCapitalize_ShouldLettrinizeCapitalizedFirstChar() {
        // Given
        val fullText = "lorem ipsum"

        // When
        val lettrineData = LettrineHelper.parseLettrineInformation(fullText)

        // Then
        val lettrine = lettrineData.lettrineChar
        val remainingBody = lettrineData.remainingString
        Assert.assertEquals("orem ipsum", remainingBody)
        Assert.assertEquals('L', lettrine)
    }

    @Test
    fun parseLettrineInformation_WhenFirstLetterIsNotAlphaNumerical_ShouldNotLettrinizeFirstChar() {
        // Given
        val fullText = "\"To be or not to be\" William Shakespeare"

        // When
        val lettrineData = LettrineHelper.parseLettrineInformation(fullText)

        // Then
        val lettrine = lettrineData.lettrineChar
        val remainingBody = lettrineData.remainingString
        Assert.assertEquals("\"To be or not to be\" William Shakespeare", remainingBody)
        Assert.assertEquals(null, lettrine)
    }

    @Test
    fun parseLettrineInformation_WhenTextIsUnderTags_ShouldLettrinizeRealFirstLetter() {
        // Given
        val fullText = "<p><b>To</b> be or not to <b>be</b></p>"

        // When
        val lettrineData = LettrineHelper.parseLettrineInformation(fullText)

        // Then
        val lettrine = lettrineData.lettrineChar
        val remainingBody = lettrineData.remainingString
        Assert.assertEquals("<p><b>o</b> be or not to <b>be</b></p>", remainingBody)
        Assert.assertEquals('T', lettrine)
    }

    @Test
    fun parseLettrineInformation_WhenTextIsUnderTagsAndFirstLetterIsNotAlphaNumerical_ShouldNotLettrinizeFirstLetter() {

        // Given
        val fullText = "<p>\"To\" be or not to be</p>"

        // When
        val lettrineData = LettrineHelper.parseLettrineInformation(fullText)

        // Then
        val lettrine = lettrineData.lettrineChar
        val remainingBody = lettrineData.remainingString
        Assert.assertEquals("<p>\"To\" be or not to be</p>", remainingBody)
        Assert.assertEquals(null, lettrine)
    }

    @Test
    fun parseLettrineInformation_WhenTagsAreNotClosed_ShouldCapitalizeRealFirstLetter() {

        // Given
        val fullText = "<p>To be <b>or not to be<p>"

        // When
        val lettrineData = LettrineHelper.parseLettrineInformation(fullText)

        // Then
        val lettrine = lettrineData.lettrineChar
        val remainingBody = lettrineData.remainingString
        Assert.assertEquals("<p>o be <b>or not to be<p>", remainingBody)
        Assert.assertEquals('T', lettrine)
    }

    @Test
    fun parseLettrineInformation_WhenTagsAreNotClosed() {

        // Given
        val fullText = "<p><b></b>Qsdqsd</p>"

        // When
        val lettrineData = LettrineHelper.parseLettrineInformation(fullText)

        // Then
        val lettrine = lettrineData.lettrineChar
        val remainingBody = lettrineData.remainingString
        Assert.assertEquals("<p><b></b>sdqsd</p>", remainingBody)
        Assert.assertEquals('Q', lettrine)
    }

}