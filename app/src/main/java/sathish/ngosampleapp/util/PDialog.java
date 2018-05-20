package sathish.ngosampleapp.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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
