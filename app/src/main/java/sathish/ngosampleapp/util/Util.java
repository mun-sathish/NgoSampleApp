package sathish.ngosampleapp.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

public class Util {

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
}
