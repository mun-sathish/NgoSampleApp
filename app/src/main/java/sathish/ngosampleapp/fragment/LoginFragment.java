package sathish.ngosampleapp.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sathish.ngosampleapp.R;
import sathish.ngosampleapp.util.Const;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LoginFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {

    // Login UI
    @BindView(R.id.login_username)
    EditText mUsernameView;
    @BindView(R.id.login_password)
    EditText mPasswordView;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, view);

        return view;
    }


    public void postExecuteLoginErrorHandler() {
        mUsernameView.setError(getString(R.string.sign_in_error_incorrect_credentials));
        mPasswordView.setError(getString(R.string.sign_in_error_incorrect_credentials));
        mUsernameView.requestFocus();
    }

    @OnClick(R.id.login_btn)
    public void handleLoginBtn() {
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
            if (mListener != null) {
                mListener.onLoginBtnClicked(username, password);
            }
        }
    }

    @OnClick(R.id.login_register_btn)
    public void handleRegisterBtn() {
        if(mListener != null) {
            mListener.onRegisterBtnClicked();
        }
    }

    @OnClick(R.id.login_fp_btn)
    public void handleForgotPasswordBtn() {
        if(mListener != null) {
            mListener.onForgotPasswordBtnClicked();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.i(Const.TAG, "Inside onAttach");
        if (context instanceof OnFragmentInteractionListener) {
            Log.i(Const.TAG, "listener initialized");
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.i(Const.TAG, "Inside onAttach activity");
        if (activity instanceof OnFragmentInteractionListener) {
            Log.i(Const.TAG, "listener initialized activity");
            mListener = (OnFragmentInteractionListener) activity;
        } else {
            throw new RuntimeException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onLoginBtnClicked(String username, String password);
        void onRegisterBtnClicked();
        void onForgotPasswordBtnClicked();
    }
}
