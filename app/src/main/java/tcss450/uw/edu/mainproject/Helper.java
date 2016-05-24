/*
 * Slick pick app
  * Mehgan Cook and Tony Zullo
  * Mobile apps TCSS450
 * */
package tcss450.uw.edu.mainproject;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.widget.TextView;

/**
 * Helper class for style
 * @author Tony Zullo
 */
public class Helper {

    /** The font style for the entire project. */
    private Typeface mFontStyle;
    private AssetManager mManager;

    public Helper() {

    }

    /**The constructor for helper class
     * @param manager the asset manager*/
    public Helper(AssetManager manager) {
        mManager = manager;
        mFontStyle = Typeface.createFromAsset(manager, "fonts/Oswald-Regular.ttf");
    }

    /**
     * Sets the Font Style for the given element.
     *
     * @param textObject the text
     */
    public void setFontStyle(TextView textObject) {
        textObject.setTypeface(mFontStyle);
    }
}
