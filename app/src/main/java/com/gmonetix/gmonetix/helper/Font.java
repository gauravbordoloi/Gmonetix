package com.gmonetix.gmonetix.helper;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

public class Font {

    public void setFont(Context _context, TextView textView) {
        Typeface roboto = Typeface.createFromAsset(_context.getAssets(), "font/RobotoLight.ttf");
        textView.setTypeface(roboto);
    }
}
