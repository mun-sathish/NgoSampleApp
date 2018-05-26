package sathish.ngosampleapp.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.muddzdev.styleabletoastlibrary.StyleableToast;

import sathish.ngosampleapp.R;

public class PDialog {

    private static ProgressDialog pDialog;

    public static void showDialog(Context context, String message) {
        pDialog = new ProgressDialog(context);
        pDialog.setCancelable(false);
        pDialog.setMessage(message);
        pDialog.show();
    }

    public static void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void showStyleableToast(Context context, String message) {
        new StyleableToast
                .Builder(context)
                .text(message)
                .length(Toast.LENGTH_LONG)
                .textColor(Color.WHITE)
                .backgroundColor(Color.BLACK)
                .solidBackground()
                .show();
    }

    public static void showStyleableToast(Context context, String message, String btnClass, @Nullable Integer iconId) {
        StyleableToast.Builder st = new StyleableToast.Builder(context);
        st.text(message);
        st.length(Toast.LENGTH_LONG);
        st.textColor(Color.WHITE);
        st.solidBackground();
        st.textBold();
        if(iconId != null)
            st.iconStart(iconId);

        switch (btnClass) {
            case Const.TOAST_PRIMARY:
                st.backgroundColor(ContextCompat.getColor(context, R.color.toast_primary));
                break;
            case Const.TOAST_SECONDARY:
                st.backgroundColor(ContextCompat.getColor(context, R.color.toast_secondary));
                break;
            case Const.TOAST_INFO:
                st.backgroundColor(ContextCompat.getColor(context, R.color.toast_info));
                break;
            case Const.TOAST_SUCCESS:
                st.backgroundColor(ContextCompat.getColor(context, R.color.toast_success));
                break;
            case Const.TOAST_DANGER:
                st.backgroundColor(ContextCompat.getColor(context, R.color.toast_danger));
                break;
        }
        st.show();
    }

    public static void showStyleableToast(Context context, String message, int textColor, int backgroundColor, int iconId) {
        new StyleableToast
                .Builder(context)
                .text(message)
                .length(Toast.LENGTH_LONG)
                .textColor(textColor)
                .backgroundColor(backgroundColor)
                .solidBackground()
                .iconStart(iconId)
                .textBold()
                .show();
    }


    public static void showSnackBar(View view, String message) {
        Snackbar snackbar = Snackbar
                .make(view, message, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

    public static Snackbar getSnackBarInstance(View view, String message) {
        Snackbar snackbar = Snackbar
                .make(view, message, Snackbar.LENGTH_SHORT);
        return snackbar;
    }

    public static void showSnackBar(View view, String message, int textColor) {
        Snackbar snackbar = Snackbar
                .make(view, message, Snackbar.LENGTH_SHORT);
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(textColor);
        snackbar.show();
    }

    public static void showSnackBar(View view, String message, int textColor, int actionTextColor) {
        Snackbar snackbar = Snackbar
                .make(view, message, Snackbar.LENGTH_SHORT);
        snackbar.setActionTextColor(actionTextColor);
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(textColor);
        snackbar.show();
    }

}
