package com.lemonde.lettrine.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.lemonde.lettrine.LettrineTextView;

/**
 * Lettrine sample activity
 *
 * @author Rémi Pradal
 */
public class SampleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);

        LettrineTextView lettrine2TextView = (LettrineTextView) findViewById(R.id.simpleTwoLettrineTextView);
        if (lettrine2TextView != null) {
            lettrine2TextView.setBodyText(getString(R.string.lorem_ipsum));
        }

        LettrineTextView lettrine3TextView = (LettrineTextView) findViewById(R.id.simpleThreeLettrineTextView);
        if (lettrine3TextView != null) {
            lettrine3TextView.setBodyText(getString(R.string.lorem_ipsum));
        }

        LettrineTextView fontLettrineTextView = (LettrineTextView) findViewById(R.id.fontLettrineTextView);
        if (fontLettrineTextView != null) {
            fontLettrineTextView.setBodyText(getString(R.string.lorem_ipsum));
        }

        LettrineTextView htmlLettrineTextView = (LettrineTextView) findViewById(R.id.htmlLettrineTextView);
        if (htmlLettrineTextView != null) {
            htmlLettrineTextView.setBodyText(getString(R.string.lorem_ipsum_styled));
        }
    }
}
