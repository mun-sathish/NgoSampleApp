package sathish.ngosampleapp.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.github.florent37.materialtextfield.MaterialTextField;
import com.rilixtech.materialfancybutton.MaterialFancyButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sathish.ngosampleapp.R;
import sathish.ngosampleapp.dto.UserDetailModel;
import sathish.ngosampleapp.util.Const;
import sathish.ngosampleapp.util.PDialog;
import sathish.ngosampleapp.util.Util;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ForgotPasswordFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ForgotPasswordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ForgotPasswordFragment extends Fragment {

    @BindView(R.id.fp_username)
    EditText mFpUserNameView;
    @BindView(R.id.fp_sq_answer)
    EditText mFpSqAnswerView;
    @BindView(R.id.fp_sq_question)
    TextView mFpSqQuestionView;
    @BindView(R.id.fp_password1)
    EditText mFpPasswordView;
    @BindView(R.id.fp_password2)
    EditText mFpRepeatPasswordView;
    @BindView(R.id.fp_btn)
    MaterialFancyButton mFpBtnView;

    @BindView(R.id.fp_username_layout)
    MaterialTextField mFpUserNameLayoutView;
    @BindView(R.id.fp_sq_answer_layout)
    MaterialTextField mFpSqAnswerLayoutView;
    @BindView(R.id.fp_password1_layout)
    MaterialTextField mFpPasswordLayoutView;
    @BindView(R.id.fp_password2_layout)
    MaterialTextField mFpRepeatPasswordLayoutView;

    private UserDetailModel userDetailModel;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ForgotPasswordFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ForgotPasswordFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ForgotPasswordFragment newInstance(String param1, String param2) {
        ForgotPasswordFragment fragment = new ForgotPasswordFragment();
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
        View view = inflater.inflate(R.layout.fragment_forgot_password, container, false);
        ButterKnife.bind(this, view);

        return view;

    }

    public void handleUsernameResponse(UserDetailModel userDetailModel, boolean isFound) {
        if(!isFound) {
            mFpUserNameView.setError("Not found");
            mFpUserNameView.requestFocus();
        } else  {
            this.userDetailModel = userDetailModel;
            mFpSqQuestionView.setText(userDetailModel.getSecurityQuestion());
            updateView(mFpBtnView.getText().toString());
        }
    }

    public void updateView(String s){
        if(s.equals(getString(R.string.fp_btn_text_check))) {
            mFpBtnView.setText(getString(R.string.fp_btn_text_confirm));
            mFpUserNameLayoutView.setVisibility(View.INVISIBLE);
            mFpPasswordLayoutView.setVisibility(View.INVISIBLE);
            mFpRepeatPasswordLayoutView.setVisibility(View.INVISIBLE);
            mFpSqQuestionView.setVisibility(View.VISIBLE);
            mFpSqAnswerLayoutView.setVisibility(View.VISIBLE);
        } else if(s.equals(getString(R.string.fp_btn_text_confirm))) {
            mFpBtnView.setText(getString(R.string.fp_btn_text_change_password));
            mFpUserNameLayoutView.setVisibility(View.INVISIBLE);
            mFpSqQuestionView.setVisibility(View.INVISIBLE);
            mFpSqAnswerLayoutView.setVisibility(View.INVISIBLE);
            mFpPasswordLayoutView.setVisibility(View.VISIBLE);
            mFpRepeatPasswordLayoutView.setVisibility(View.VISIBLE);
        } else {
            mFpBtnView.setText(getString(R.string.fp_btn_text_check));
            mFpUserNameLayoutView.setVisibility(View.VISIBLE);
            mFpSqQuestionView.setVisibility(View.INVISIBLE);
            mFpSqAnswerLayoutView.setVisibility(View.INVISIBLE);
            mFpPasswordLayoutView.setVisibility(View.INVISIBLE);
            mFpRepeatPasswordLayoutView.setVisibility(View.INVISIBLE);
        }
    }

    @OnClick(R.id.fp_btn)
    public void handleBtnClicked() {
        String btnText = mFpBtnView.getText().toString();
        if(btnText.equals(getString(R.string.fp_btn_text_check))) {
            String username = mFpUserNameView.getText().toString();
            if(Util.isNull(username)) {
                mFpUserNameView.setError(getString(R.string.required));
                mFpUserNameView.requestFocus();
            }
            else {
                if(mListener != null)
                    mListener.onUsernameBtnClicked(username);
            }
        } else if(btnText.equals(getString(R.string.fp_btn_text_confirm))) {
            String sqQuestion = mFpSqQuestionView.getText().toString();
            String sqAnswer = mFpSqAnswerView.getText().toString().trim();
            if(Util.isNull(sqQuestion))
                PDialog.showStyleableToast(getActivity(), getString(R.string.fp_sq_not_loaded), Const.TOAST_PRIMARY, null);
            else if(Util.isNull(sqAnswer)) {
                mFpSqAnswerView.setError(getString(R.string.required));
                mFpSqAnswerView.requestFocus();
            }
            else {
                if(userDetailModel.getSecurityQuestionAnswer().equals(sqAnswer)) {
                    updateView(btnText);
                }
                else {
                    mFpSqAnswerView.setError(getString(R.string.fp_invalid_answer));
                    mFpSqAnswerView.requestFocus();
                }
            }
        } else if(btnText.equals(getString(R.string.fp_btn_text_change_password))) {
            String password = mFpPasswordView.getText().toString().trim();
            String repeatPassword = mFpRepeatPasswordView.getText().toString().trim();
            if(Util.isNull(password)) {
                mFpPasswordView.setError(getString(R.string.required));
                mFpPasswordView.requestFocus();
            } else if(Util.isNull(repeatPassword)) {
                mFpRepeatPasswordView.setError(getString(R.string.required));
                mFpRepeatPasswordView.requestFocus();
            } else if(!password.equals(repeatPassword)) {
                PDialog.showStyleableToast(getActivity(), getString(R.string.fp_password_mismatch), Const.TOAST_DANGER, null);
            } else {
                Log.i(Const.TAG, "Coming jhree");
                if(mListener != null)
                    mListener.onChangePasswordClicked(userDetailModel.getUsername(), password);
            }
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
        if (activity instanceof OnFragmentInteractionListener) {
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
        void onUsernameBtnClicked(String username);
        void onChangePasswordClicked(String username, String password);
    }
}
