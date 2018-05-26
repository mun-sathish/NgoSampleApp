package sathish.ngosampleapp.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import sathish.ngosampleapp.R;
import sathish.ngosampleapp.dto.UserDetailModel;
import sathish.ngosampleapp.util.Const;
import sathish.ngosampleapp.util.PDialog;
import sathish.ngosampleapp.util.Util;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SignUpFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SignUpFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignUpFragment extends Fragment {

    //SignUp UI
    @BindView(R.id.signup_username)
    EditText mSignUpUsernameView;
    @BindView(R.id.signup_password)
    EditText mSignUpPasswordView;
    @BindView(R.id.signup_name)
    EditText mSignUpNameView;
    @BindView(R.id.signup_phone)
    EditText mSignUpPhoneView;
    @BindView(R.id.signup_email)
    EditText mSignUpEmailView;

    boolean isValidUsername;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public SignUpFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SignUpFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SignUpFragment newInstance(String param1, String param2) {
        SignUpFragment fragment = new SignUpFragment();
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
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnTextChanged(R.id.signup_username)
    public void handleUsernameTextChanged() {
        try{
            Log.i(Const.TAG, mSignUpUsernameView.getText().toString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        String username = mSignUpUsernameView.getText().toString();
        if(Util.isNull(username))
            return;
        if(mListener != null)
            mListener.onUsernameChanged(username);


    }

    public void handleValidUsername(boolean isValidUsername) {
        this.isValidUsername = isValidUsername;
        if(isValidUsername) {
            Drawable customErrorDrawable = getResources().getDrawable(R.drawable.tick);
            customErrorDrawable.setBounds(0, 0, customErrorDrawable.getIntrinsicWidth(), customErrorDrawable.getIntrinsicHeight());
            mSignUpUsernameView.setError(getString(R.string.signup_username_available), customErrorDrawable);
        }
        else {
            mSignUpUsernameView.setError(getString(R.string.signup_username_not_available));
        }
    }

    @OnClick(R.id.signup_next_btn)
    public void handleNextBtn() {
        UserDetailModel model = new UserDetailModel();
        String name = mSignUpNameView.getText().toString();
        String username = mSignUpUsernameView.getText().toString();
        String password = mSignUpPasswordView.getText().toString();
        String email = mSignUpEmailView.getText().toString();
        String phone = mSignUpPhoneView.getText().toString();

        if(Util.isNull(name)) {
            mSignUpNameView.setError(getString(R.string.signup_field_required));
            mSignUpNameView.requestFocus();
        }
        else if(Util.isNull(username)) {
            mSignUpUsernameView.setError(getString(R.string.signup_field_required));
            mSignUpUsernameView.requestFocus();
        }
        else if(!isValidUsername) {
            mSignUpUsernameView.setError(getString(R.string.signup_username_not_available));
            mSignUpUsernameView.requestFocus();
        }
        else if(Util.isNull(password)) {
            mSignUpPasswordView.setError(getString(R.string.signup_field_required));
            mSignUpPasswordView.requestFocus();
        }
        else if(Util.isNull(email)) {
            mSignUpEmailView.setError(getString(R.string.signup_field_required));
            mSignUpEmailView.requestFocus();
        }
        else if(Util.isNull(phone)) {
            mSignUpPhoneView.setError(getString(R.string.signup_field_required));
            mSignUpPhoneView.requestFocus();
        }
        else {
            model.setName(name);
            model.setUsername(username);
            model.setPassword(password);
            model.setEmail(email);
            model.setPhone(phone);

            if(mListener != null)
                mListener.onSignUpNextBtnClicked(model);
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof LoginFragment.OnFragmentInteractionListener) {
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
        // TODO: Update argument type and name
        void onSignUpNextBtnClicked(UserDetailModel model);
        void onUsernameChanged(String username);
    }
}
