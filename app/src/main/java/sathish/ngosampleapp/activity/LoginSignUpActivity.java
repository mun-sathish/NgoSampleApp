package sathish.ngosampleapp.activity;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import sathish.ngosampleapp.AppController;
import sathish.ngosampleapp.MainActivity;
import sathish.ngosampleapp.R;
import sathish.ngosampleapp.dto.ResponseModel;
import sathish.ngosampleapp.dto.SharedPref;
import sathish.ngosampleapp.dto.UserDetailModel;
import sathish.ngosampleapp.fragment.ForgotPasswordFragment;
import sathish.ngosampleapp.fragment.LoginFragment;
import sathish.ngosampleapp.fragment.SecurityQuestionFragment;
import sathish.ngosampleapp.fragment.SignUpFragment;
import sathish.ngosampleapp.util.Const;
import sathish.ngosampleapp.util.PDialog;
import sathish.ngosampleapp.util.Util;

public class LoginSignUpActivity extends AppCompatActivity implements LoginFragment.OnFragmentInteractionListener, SignUpFragment.OnFragmentInteractionListener, SecurityQuestionFragment.OnFragmentInteractionListener, ForgotPasswordFragment.OnFragmentInteractionListener {


    // Forgot Password UI
    EditText mFpUsernameView;
    TextView mFpSqQuestionView;
    EditText mFpSqAnswerView;
    EditText mFpPassowrdView;
    EditText mFpRepeatPasswordView;

    // Async Tasks
    private UserLoginTask mLoginAuthTask = null;
    private SignUpTask mSignUpAuthTask = null;
    private SqTask mSqAuthTask = null;
    private ForgotPasswordTask mForgotPasswordAuthTask = null;
    private UserNameValidationTask mUserNameValidationTask = null;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    LoginFragment loginFragment = new LoginFragment();
    SignUpFragment signUpFragment = new SignUpFragment();
    SecurityQuestionFragment securityQuestionFragment = new SecurityQuestionFragment();
    ForgotPasswordFragment forgotPasswordFragment = new ForgotPasswordFragment();

    // For Navigation Tracking
    List<Fragment> navigatedFragmentList = new ArrayList<>();
    UserDetailModel requestSignUpModel = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_sign_up);
        ButterKnife.bind(this);

        // get fragment manager
        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.login_fragment, loginFragment);
        fragmentTransaction.add(R.id.signup_fragment, signUpFragment);
        fragmentTransaction.add(R.id.sq_fragment, securityQuestionFragment);
        fragmentTransaction.add(R.id.fp_fragment, forgotPasswordFragment);
        fragmentTransaction.commit();

        addFragment(loginFragment, false);

    }


    public void addFragment(Fragment fragment, boolean isReverse) {
        fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.hide(loginFragment);
        fragmentTransaction.hide(securityQuestionFragment);
        fragmentTransaction.hide(signUpFragment);
        fragmentTransaction.hide(forgotPasswordFragment);

        fragmentTransaction.show(fragment);
        if (!isReverse)
            navigatedFragmentList.add(fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        navigatedFragmentList.remove(navigatedFragmentList.size() - 1);
        if (navigatedFragmentList.size() == 0)
            finish();
        addFragment(navigatedFragmentList.get(navigatedFragmentList.size() - 1), true);
    }


    @Override
    public void onUsernameBtnClicked(String username) {
        if (mUserNameValidationTask != null)
            mUserNameValidationTask.onCancelled();
        mUserNameValidationTask = new UserNameValidationTask(this, username, "fp");
        mUserNameValidationTask.execute((Void) null);
    }

    @Override
    public void onChangePasswordClicked(String username, String password) {
        if (mForgotPasswordAuthTask != null)
            return;
        mForgotPasswordAuthTask = new ForgotPasswordTask(this, username, password);
        mForgotPasswordAuthTask.execute((Void) null);
    }

    @Override
    public void onUsernameChanged(String username) {
        if (mUserNameValidationTask != null)
            mUserNameValidationTask.onCancelled();
        mUserNameValidationTask = new UserNameValidationTask(this, username, "signup");
        mUserNameValidationTask.execute((Void) null);
    }

    @Override
    public void onCreateAccountBtnClicked(UserDetailModel model) {
        if (mSignUpAuthTask != null)
            return;

        this.requestSignUpModel.setSecurityQuestion(model.getSecurityQuestion());
        this.requestSignUpModel.setSecurityQuestionAnswer(model.getSecurityQuestionAnswer());
        mSignUpAuthTask = new SignUpTask(this, this.requestSignUpModel);
        mSignUpAuthTask.execute((Void) null);
    }

    @Override
    public void onSignUpNextBtnClicked(UserDetailModel model) {
        this.requestSignUpModel = model;
        addFragment(securityQuestionFragment, false);
    }

    @Override
    public void onLoginBtnClicked(String username, String password) {
        if (mLoginAuthTask != null) {
            return;
        }
        mLoginAuthTask = new UserLoginTask(this, username, password);
        mLoginAuthTask.execute((Void) null);
    }


    @Override
    public void onRegisterBtnClicked() {
        addFragment(signUpFragment, false);
        if (mSqAuthTask != null)
            return;
        mSqAuthTask = new SqTask(this);
        mSqAuthTask.execute((Void) null);
    }

    @Override
    public void onForgotPasswordBtnClicked() {
        addFragment(forgotPasswordFragment, false);
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
                Log.e(Const.TAG, "VOLLEY: " + ex.getMessage());
            }

            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Const.SIGNIN_URI.getMethod(),
                    Const.SIGNIN_URI.getUri(), reqParams, new Response.Listener<JSONObject>() {
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
                            Log.i(Const.TAG, "VOLLEY:" + gson.toJson(userDetailModel));
                            onPostExecute("success");
                        } else {
                            Log.i(Const.TAG, "VOLLEY:" + gson.toJson(responseModel));
                            onPostExecute("failure");
                        }

                    } catch (JSONException ex) {
                        PDialog.showToast(context, "Json Exception");
                        Log.e(Const.TAG, "VOLLEY:" + ex.getMessage());
                        onPostExecute("exception");
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    PDialog.showToast(context, "Error Response");
                    Log.e(Const.TAG, "VOLLEY:" + error.getMessage());
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

            Log.i(Const.TAG, "Inside onPostExecute = " + success);
            mLoginAuthTask = null;
            PDialog.hidePDialog();

            switch (success) {
                case "success":
                    new SharedPref(context, Const.LoginPref).addString(Const.keyUser, successRes);
                    PDialog.showStyleableToast(context, "Logged In Successfully");
                    Util.start(context, MainActivity.class);
                    finish();
                    break;
                case "failure":
                    loginFragment.postExecuteLoginErrorHandler();
                    break;
                case "error":
                    PDialog.showStyleableToast(context, "Error Response");
                    break;
                case "exception":
                    PDialog.showStyleableToast(context, "Exception Occured");
                    break;
            }
        }

        @Override
        protected void onCancelled() {
            mLoginAuthTask = null;
        }
    }


    /**
     * Performs Signup operation
     */
    public class SignUpTask extends AsyncTask<Void, Void, String> {

        private final UserDetailModel requestUserModel;
        private final Context context;
        private final Gson gson = new Gson();

        SignUpTask(Context context, UserDetailModel requestUserModel) {
            this.requestUserModel = requestUserModel;
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
                reqParams.put("username", requestUserModel.getUsername());
                reqParams.put("password", requestUserModel.getPassword());
                reqParams.put("name", requestUserModel.getName());
                reqParams.put("securityQuestion", requestUserModel.getSecurityQuestion());
                reqParams.put("securityQuestionAnswer", requestUserModel.getSecurityQuestionAnswer());
                reqParams.put("phone", requestUserModel.getPhone());
                reqParams.put("email", requestUserModel.getEmail());
            } catch (JSONException ex) {
                Log.e(Const.TAG, "VOLLEY: " + ex.getMessage());
            }

            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Const.SIGNUP_URI.getMethod(),
                    Const.SIGNUP_URI.getUri(), reqParams, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        ResponseModel responseModel = gson.fromJson(response.toString(),
                                ResponseModel.class);
                        if (responseModel.getStatusCode() == 0) {
                            Log.i(Const.TAG, "VOLLEY:" + gson.toJson(responseModel));
                            onPostExecute("success");
                        } else {
                            Log.i(Const.TAG, "VOLLEY:" + gson.toJson(responseModel));
                            onPostExecute("failure");
                        }
                    } catch (Exception ex) {
                        PDialog.showStyleableToast(context, "General Exception");
                        Log.e(Const.TAG, "VOLLEY:" + ex.getMessage());
                        onPostExecute("exception");
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    PDialog.showToast(context, "Error Response");
                    Log.e(Const.TAG, "VOLLEY:" + error.getMessage());
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

        @SuppressLint("LogConditional")
        @Override
        protected void onPostExecute(final String success) {
            if (TextUtils.isEmpty(success))
                return;

            Log.i(Const.TAG, "Inside onPostExecute = " + success);
            mSignUpAuthTask = null;
            PDialog.hidePDialog();

            switch (success) {
                case "success":
                    PDialog.showStyleableToast(context, "Created Successfully");
                    Util.start(context, MainActivity.class);
                    finish();
                    break;
                case "failure":
                    PDialog.showStyleableToast(context, "Failure Creating Account");
                    break;
                case "error":
                    PDialog.showStyleableToast(context, "Error Response");
                    break;
                case "exception":
                    PDialog.showStyleableToast(context, "Exception Occured");
                    break;
            }
        }

        @Override
        protected void onCancelled() {
            mSignUpAuthTask = null;
        }
    }


    /**
     * Gets SQ Question operation
     */
    public class SqTask extends AsyncTask<Void, Void, String> {

        private final Context context;
        private final Gson gson = new Gson();

        SqTask(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
        }


        @Override
        protected String doInBackground(Void... params) {

            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Const.SQ_URI.getMethod(), Const.SQ_URI.getUri(), null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {

                    List<String> sqQuestions = new ArrayList<>();
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            sqQuestions.add(response.getJSONObject(i).getString("question"));

                        } catch (JSONException ex) {
                            PDialog.showToast(context, "Json Exception");
                            Log.e(Const.TAG, "VOLLEY:" + ex.getMessage());
                            onPostExecute("exception");
                        }
                    }
                    securityQuestionFragment.onResponseOfSecurityQuestion(sqQuestions);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    PDialog.showStyleableToast(context, "Error Response");
                    Log.e(Const.TAG, "VOLLEY:" + error.getMessage());
                    onPostExecute("error");
                }
            });

            // Adding request to request queue
            AppController.getInstance().addToRequestQueue(jsonArrayRequest);

            return null;
        }

        @SuppressLint("LogConditional")
        @Override
        protected void onPostExecute(final String success) {
            if (TextUtils.isEmpty(success))
                return;

            Log.i(Const.TAG, "Inside onPostExecute = " + success);
            mSqAuthTask = null;
            PDialog.hidePDialog();

            switch (success) {
                case "success":
                    break;
                case "failure":
                    break;
                case "error":
                    PDialog.showStyleableToast(context, "Error Response");
                    break;
                case "exception":
                    PDialog.showStyleableToast(context, "Exception Occured");
                    break;
            }
        }

        @Override
        protected void onCancelled() {
            mSqAuthTask = null;
        }
    }


    /**
     * Performs Forgot Password  operation
     */
    public class ForgotPasswordTask extends AsyncTask<Void, Void, String> {

        private final String mUsername;
        private final String mPassword;
        private final Context context;
        private final Gson gson = new Gson();

        ForgotPasswordTask(Context context, String username, String password) {
            this.context = context;
            mUsername = username;
            mPassword = password;
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
                Log.e(Const.TAG, "VOLLEY: " + ex.getMessage());
            }

            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Const.USER_UPDATE_URI.getMethod(),
                    Const.USER_UPDATE_URI.getUri(), reqParams, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        ResponseModel responseModel = gson.fromJson(response.toString(),
                                ResponseModel.class);
                        if (responseModel.getStatusCode() == 0) {
                            navigatedFragmentList.remove(navigatedFragmentList.size()-1);
                            addFragment(loginFragment, true);
                            onPostExecute("success");
                        } else {
                            onPostExecute("failure");
                        }
                        Log.i(Const.TAG, "VOLLEY:" + gson.toJson(responseModel));
                        PDialog.showStyleableToast(context, responseModel.getStatusMessage());
                    } catch (Exception ex) {
                        PDialog.showStyleableToast(context, "General Exception");
                        Log.e(Const.TAG, "VOLLEY:" + ex.getMessage());
                        onPostExecute("exception");
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    PDialog.showToast(context, "Error Response");
                    Log.e(Const.TAG, "VOLLEY:" + error.getMessage());
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

        @SuppressLint("LogConditional")
        @Override
        protected void onPostExecute(final String success) {
            if (TextUtils.isEmpty(success))
                return;

            Log.i(Const.TAG, "Inside onPostExecute = " + success);
            mForgotPasswordAuthTask = null;
            PDialog.hidePDialog();

            switch (success) {
                case "success":
                    break;
                case "failure":
                    break;
                case "error":
                    PDialog.showStyleableToast(context, "Error Response");
                    break;
                case "exception":
                    PDialog.showStyleableToast(context, "Exception Occured");
                    break;
            }
        }

        @Override
        protected void onCancelled() {
            mForgotPasswordAuthTask = null;
        }
    }


    /**
     * Performs Username checkoperation
     */
    public class UserNameValidationTask extends AsyncTask<Void, Void, String> {

        private final String mUsername;
        private final String mTaskType;
        private final Context context;
        private final Gson gson = new Gson();

        UserNameValidationTask(Context context, String username, String taskType) {
            mUsername = username;
            mTaskType = taskType;
            this.context = context;
        }

        @Override
        protected String doInBackground(Void... params) {

            JSONObject reqParams = new JSONObject();
            try {
                reqParams.put("username", mUsername);
            } catch (JSONException ex) {
                Log.e(Const.TAG, "VOLLEY: " + ex.getMessage());
            }

            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Const.SIGNUP_USERCHECK_URI.getMethod(),
                    Const.SIGNUP_USERCHECK_URI.getUri(), reqParams, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        ResponseModel responseModel = gson.fromJson(response.toString(),
                                ResponseModel.class);
                        if (responseModel.getStatusCode() == 0) {
                            onPostExecute("success");
                            if(mTaskType.equals("fp"))
                                forgotPasswordFragment.handleUsernameResponse(null, false);
                        } else {
                            if (mTaskType.equals("fp")) {
                                UserDetailModel userDetailModel = gson.fromJson(response.get("res")
                                                .toString(),
                                        UserDetailModel.class);
                                forgotPasswordFragment.handleUsernameResponse(userDetailModel, true);
                            } else {
                                onPostExecute("failure");
                            }
                        }

                    } catch (JSONException ex) {
                        PDialog.showToast(context, "JSON Exception");
                        Log.e(Const.TAG, "VOLLEY:" + ex.getMessage());
                        onPostExecute("exception");
                    } catch (Exception ex) {
                        PDialog.showToast(context, "General Exception");
                        Log.e(Const.TAG, "VOLLEY:" + ex.getMessage());
                        onPostExecute("exception");
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    PDialog.showToast(context, "Error Response");
                    Log.e(Const.TAG, "VOLLEY:" + error.getMessage());
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

            Log.i(Const.TAG, "Inside onPostExecute = " + success);
            mUserNameValidationTask = null;
            PDialog.hidePDialog();

            switch (success) {
                case "success":
                    signUpFragment.handleValidUsername(true);
                    break;
                case "failure":
                    signUpFragment.handleValidUsername(false);
                    break;
                case "error":
                    PDialog.showStyleableToast(context, "Error Response");
                    break;
                case "exception":
                    PDialog.showStyleableToast(context, "Exception Occured");
                    break;
            }
        }

        @Override
        protected void onCancelled() {
            mUserNameValidationTask = null;
        }
    }

}
