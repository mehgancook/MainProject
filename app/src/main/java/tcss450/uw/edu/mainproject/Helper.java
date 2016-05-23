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

    /**
     * Creates the bottom level toolbar.
     */
//    public void createBottomToolbar() {
//        mManager.
//    }
//    <android.support.v7.widget.Toolbar
//    android:id="@+id/toolbar"
//    android:layout_width="match_parent"
//    android:layout_height="?attr/actionBarSize"
//    android:background="@color/toolbar_main"
//    android:elevation="4dp"
//    android:layout_alignParentLeft="true"
//    android:transitionGroup="false"
//    android:touchscreenBlocksFocus="false"
//    android:orientation="horizontal"
//    android:weightSum="1">
//    <android.support.percent.PercentRelativeLayout
//    xmlns:android="http://schemas.android.com/apk/res/android"
//    xmlns:app="http://schemas.android.com/apk/res-auto"
//    android:layout_width="match_parent"
//    android:layout_height="match_parent">
//    <ImageView
//    app:layout_widthPercent="20%"
//    android:layout_width="0dp"
//    android:layout_height="?attr/actionBarSize"
//    android:src="@drawable/home"
//    app:layout_marginLeftPercent="0%"/>
//    <ImageView
//    app:layout_widthPercent="20%"
//    android:layout_width="0dp"
//    android:layout_height="?attr/actionBarSize"
//    android:src="@drawable/question"
//    app:layout_marginLeftPercent="20%"/>
//    <ImageView
//    app:layout_widthPercent="20%"
//    android:layout_width="0dp"
//    android:layout_height="?attr/actionBarSize"
//    android:src="@drawable/profile"
//    app:layout_marginLeftPercent="40%" />
//    <ImageView
//    app:layout_widthPercent="20%"
//    android:layout_width="0dp"
//    android:layout_height="?attr/actionBarSize"
//    android:src="@drawable/askers"
//    app:layout_marginLeftPercent="60%"
//    android:background="@color/colorPrimaryDark"/>
//    <ImageView
//    app:layout_widthPercent="20%"
//    android:layout_width="0dp"
//    android:layout_height="?attr/actionBarSize"
//    android:src="@drawable/settings"
//    app:layout_marginLeftPercent="80%" />
//    <!--<Button-->
//    <!--android:layout_width="0dp"-->
//    <!--android:layout_height="wrap_content"-->
//    <!--android:text="A"-->
//    <!--app:layout_widthPercent="50%"-->
//    <!--app:layout_marginLeftPercent="50%" />-->
//    <!--<Button-->
//    <!--android:layout_width="0dp"-->
//    <!--app:layout_widthPercent="50%"-->
//    <!--android:layout_height="wrap_content"-->
//    <!--android:text="B" />-->
//    </android.support.percent.PercentRelativeLayout>
//    </android.support.v7.widget.Toolbar>
}
