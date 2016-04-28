package tcss450.uw.edu.mainproject;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.widget.TextView;

/**
 * Created by anthonyzullo on 4/27/16.
 */
public class Helper {

    /** The font style for the entire project. */
    private Typeface fontStyle;

    public Helper(AssetManager manager) {
        Typeface fontStyle = Typeface.createFromAsset(manager, "fonts/Oswald-Regular.ttf");

    }

    /**
     * Sets the Font Style for the given element.
     *
     * @param textObject
     */
    public void setFontStyle(TextView textObject) {
        textObject.setTypeface(fontStyle);
    }
}
