package sathish.ngosampleapp.activity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sathish.ngosampleapp.AppController;
import sathish.ngosampleapp.R;
import sathish.ngosampleapp.dto.ResponseModel;
import sathish.ngosampleapp.dto.SharedPref;
import sathish.ngosampleapp.dto.UserDetailModel;
import sathish.ngosampleapp.util.ConnectivityReceiver;
import sathish.ngosampleapp.util.Const;
import sathish.ngosampleapp.util.PDialog;

import static sathish.ngosampleapp.util.Const.SIGNIN_URI;
import static sathish.ngosampleapp.util.Const.TAG;
import static sathish.ngosampleapp.util.Const.keyUser;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener {

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    @BindView(R.id.login_layout)
    LinearLayout loginLayout;

    // UI references.
    @BindView(R.id.sign_in_username)
    EditText mUsernameView;

    @BindView(R.id.sign_in_password)
    EditText mPasswordView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // register connection status listener
        AppController.getInstance().setConnectivityListener(this);
    }


    @OnClick(R.id.sign_in_btn)
    public void btnClicked() {
        attemptLogin();
    }

    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mUsernameView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String username = mUsernameView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;


        // Check for a valid email address.
        if (TextUtils.isEmpty(username)) {
            mUsernameView.setError(getString(R.string.sign_in_error_field_required));
            focusView = mUsernameView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.sign_in_error_field_required));
            focusView = mPasswordView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            mAuthTask = new UserLoginTask(this, username, password);
            mAuthTask.execute((Void) null);
        }
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        if (isConnected)
            PDialog.showSnackBar(loginLayout, "Connected to Internet");
        else
            PDialog.showSnackBar(loginLayout, "Internet Required");
    }

    /**
     * Performs login operation
     */
    public class UserLoginTask extends AsyncTask<Void, Void, String> {

        private final String mUsername;
        private final String mPassword;
        private final Context context;
        private final Gson gson = new Gson();
        private String successRes = "";

        UserLoginTask(Context context, String username, String password) {
            mUsername = username;
            mPassword = password;
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            PDialog.showDialog(context, "Loading....");
        }


        @Override
        protected String doInBackground(Void... params) {

            JSONObject reqParams = new JSONObject();
            try {
                reqParams.put("username", mUsername);
                reqParams.put("password", mPassword);
            } catch (JSONException ex) {
                Log.e(TAG, "VOLLEY: " + ex.getMessage());
            }

            JsonObjectRequest jsonObjReq = new JsonObjectRequest(SIGNIN_URI.getMethod(),
                    SIGNIN_URI.getUri(), reqParams, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        ResponseModel responseModel = gson.fromJson(response.toString(),
                                ResponseModel.class);
                        if (responseModel.getStatusCode() == 0) {
                            UserDetailModel userDetailModel = gson.fromJson(response.get("res")
                                            .toString(),
                                    UserDetailModel.class);
                            successRes = gson.toJson(userDetailModel);
                            Log.i(TAG, "VOLLEY:" + gson.toJson(userDetailModel));
                            onPostExecute("success");
                        } else {
                            Log.i(TAG, "VOLLEY:" + gson.toJson(responseModel));
                            onPostExecute("failure");
                        }

                    } catch (JSONException ex) {
                        PDialog.showToast(context, "Json Exception");
                        Log.e(TAG, "VOLLEY:" + ex.getMessage());
                        onPostExecute("exception");
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    PDialog.showToast(context, "Error Response");
                    Log.e(TAG, "VOLLEY:" + error.getMessage());
                    onPostExecute("error");
                }
            }) {
                /**
                 * Passing some request headers
                 */
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", "application/json");
                    return headers;
                }
            };
            // Adding request to request queue
            AppController.getInstance().addToRequestQueue(jsonObjReq);

            return null;
        }

        @Override
        protected void onPostExecute(final String success) {
            if (TextUtils.isEmpty(success))
                return;

            Log.i(TAG, "Inside onPostExecute = " + success);
            mAuthTask = null;
            PDialog.hidePDialog();

            switch (success) {
                case "success":
                    new SharedPref(context, Const.LoginPref).addString(keyUser, successRes);
                    finish();
                    break;
                case "failure":
                    mUsernameView.setError(getString(R.string.sign_in_error_incorrect_credentials));
                    mPasswordView.setError(getString(R.string.sign_in_error_incorrect_credentials));
                    mUsernameView.requestFocus();
                    break;
                case "error":
                    PDialog.showToast(context, "Error Response");
                    break;
                case "exception":
                    PDialog.showToast(context, "Exception Occured");
                    break;
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
        }
    }
}

