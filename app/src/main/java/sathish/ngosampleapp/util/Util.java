package sathish.ngosampleapp.util;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.util.TypedValue;

public class Util {

    public static boolean isNull(String s) {
        return (s == null || s.trim().length() == 0);
    }

    public static void start(Context context, java.lang.Class cls) {
        context.startActivity(new Intent(context, cls));
    }

    public static void startWithBundle(Context context, java.lang.Class cls, Bundle bundle) {
        context.startActivity(new Intent(context, cls).putExtras(bundle));
    }

    public static void openURLinBrowser(Context context, String URI) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(URI));
        context.startActivity(browserIntent);
    }

    /**
     * Converting dp to pixel
     */
    public static int dpToPx(Context context, int dp) {
        Resources r = context.getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}
