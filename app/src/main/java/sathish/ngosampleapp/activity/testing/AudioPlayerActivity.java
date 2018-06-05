package sathish.ngosampleapp.activity.testing;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sathish.ngosampleapp.AppController;
import sathish.ngosampleapp.R;
import sathish.ngosampleapp.adapter.AudioFeedbackAdapter;
import sathish.ngosampleapp.dto.AudioFeedbackModel;
import sathish.ngosampleapp.dto.AudioModel;
import sathish.ngosampleapp.dto.ResponseModel;
import sathish.ngosampleapp.dto.SharedPref;
import sathish.ngosampleapp.dto.UserDetailModel;
import sathish.ngosampleapp.util.Const;
import sathish.ngosampleapp.util.PDialog;
import sathish.ngosampleapp.util.Util;

import static sathish.ngosampleapp.util.Const.AUDIO_FEEDBACK_ADD_URI;
import static sathish.ngosampleapp.util.Const.AUDIO_FEEDBACK_URI;
import static sathish.ngosampleapp.util.Const.FEEDBACK_PARAM;
import static sathish.ngosampleapp.util.Const.TAG;

//Glide.with(context).load(R.drawable.loading).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).crossFade().into(loadingImageView);

public class AudioPlayerActivity extends AppCompatActivity {

    @BindView(R.id.audio_player_banner)
    public ImageView banner;
    @BindView(R.id.recycler_view)
    public RecyclerView recyclerView;
    @BindView(R.id.audio_player_play_pause_btn)
    ImageView mPlayPauseBtn;
    @BindView(R.id.audio_player_forward_btn)
    ImageView mForwardBtn;
    @BindView(R.id.audio_player_backward_btn)
    ImageView mBackwardBtn;
    @BindView(R.id.audio_player_feedback)
    EditText mFeedBackView;
    @BindView(R.id.audio_player_rating_input)
    RatingBar ratingBar;
    @BindView(R.id.audio_player_feedback_btn)
    Button feedbackBtn;
    @BindView(R.id.audio_player_rating_layout)
    LinearLayout mRatingBarLayoutView;
    AudioModel audioModel;
    boolean isPlaying = false;
    String latestFeedback = "";
    GetAudioFeedbackTask mGetAudioFeedbackTask;
    PostAudioFeedbackTask mPostAudioFeedbackTask;
    private AudioFeedbackAdapter adapter;
    private List<AudioFeedbackModel> audioFeedbackList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_player);
        ButterKnife.bind(this);

        audioModel = (AudioModel) getIntent().getExtras().getSerializable("model");

        audioFeedbackList = new ArrayList<>();
        adapter = new AudioFeedbackAdapter(this, audioFeedbackList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        Glide.with(this).load(Const.AUDIO_BANNER_PATH + audioModel.getBanner())
                .thumbnail(0.5f)
                .into(banner);

        mGetAudioFeedbackTask = new GetAudioFeedbackTask(this);
        mGetAudioFeedbackTask.execute((Void) null);
    }

    @OnClick(R.id.audio_player_play_pause_btn)
    public void handlePlayPauseBtn() {
        if (isPlaying) {
            mPlayPauseBtn.setImageResource(R.drawable.img_play);
        } else {
            mPlayPauseBtn.setImageResource(R.drawable.img_pause);
        }
        isPlaying = !isPlaying;
    }

    @OnClick(R.id.audio_player_backward_btn)
    public void handleBackwardBtn() {

    }

    @OnClick(R.id.audio_player_forward_btn)
    public void handleForwardBtn() {

    }

    @OnClick(R.id.audio_player_feedback_btn)
    public void handleFeedBackBtn() {
        // when post is clicked post rating
        if (mFeedBackView.getVisibility() == View.GONE) {
            mRatingBarLayoutView.setVisibility(View.GONE);
            mFeedBackView.setVisibility(View.VISIBLE);
            feedbackBtn.setText("NEXT");
            ratingBar.setRating(0.0f);

            PDialog.showStyleableToast(this, "" + (int) ratingBar.getRating(), Const.TOAST_INFO, null);
            if (((int) ratingBar.getRating()) == 0)
                mPostAudioFeedbackTask = new PostAudioFeedbackTask(this, latestFeedback, -1);
            else
                mPostAudioFeedbackTask = new PostAudioFeedbackTask(this, latestFeedback, (int) ratingBar.getRating());
            mPostAudioFeedbackTask.execute((Void) null);
        }
        // when next is clicked post providing comment
        else if (mRatingBarLayoutView.getVisibility() == View.GONE) {
            latestFeedback = mFeedBackView.getText().toString();
            if (Util.isNull(latestFeedback)) {
                PDialog.showStyleableToast(this, "Enter some comment...", Const.TOAST_INFO, null);
                return;
            }
            mRatingBarLayoutView.setVisibility(View.VISIBLE);
            mFeedBackView.setVisibility(View.GONE);
            feedbackBtn.setText("POST");
            mFeedBackView.setText("");

        }
    }


    public class GetAudioFeedbackTask extends AsyncTask<Void, Void, String> {

        private final Context context;
        private final Gson gson = new Gson();

        GetAudioFeedbackTask(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(Void... params) {

            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(AUDIO_FEEDBACK_URI.getMethod(), AUDIO_FEEDBACK_URI.getUri() + audioModel.getAudioId() + FEEDBACK_PARAM, null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            try {
                                audioFeedbackList.clear();
                                for (int i = 0; i < response.length(); i++) {
                                    AudioFeedbackModel audioFeedbackModel = gson.fromJson(response.getJSONObject(i).toString(), AudioFeedbackModel.class);
                                    audioFeedbackList.add(audioFeedbackModel);
                                }
                                Log.i(TAG, "TOTAL: " + gson.toJson(audioFeedbackList));
                                adapter.notifyDataSetChanged();
                                onPostExecute("success");
                            } catch (JSONException ex) {
                                Log.e(TAG, "VOLLEY:" + ex.getMessage());
                                onPostExecute("exception");
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e(TAG, "VOLLEY:" + error.getMessage());
                    onPostExecute("error");
                }
            });

            // Adding request to request queue
            AppController.getInstance().addToRequestQueue(jsonArrayRequest);

            return null;
        }

        @Override
        protected void onPostExecute(final String success) {
            if (TextUtils.isEmpty(success))
                return;

            Log.i(TAG, "Inside onPostExecute = " + success);
            mGetAudioFeedbackTask = null;

            switch (success) {
                case "success":
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
            mGetAudioFeedbackTask = null;
        }
    }

    public class PostAudioFeedbackTask extends AsyncTask<Void, Void, String> {

        private final Context context;
        private final Gson gson = new Gson();
        private final String feedback;
        private final int rating;

        PostAudioFeedbackTask(Context context, String feedback, int rating) {
            this.context = context;
            this.feedback = feedback;
            this.rating = rating;
        }

        @Override
        protected void onPreExecute() {
            PDialog.showDialog(context, "Posting Comment...");
        }

        @Override
        protected String doInBackground(Void... params) {


            String userLoginDetail = new SharedPref(context, Const.LoginPref).getInstance().getString(Const.keyUser, null);
            if (Util.isNull(userLoginDetail)) {
                onPostExecute("LoginException");
                return null;
            }
            UserDetailModel userDetailModel = gson.fromJson(userLoginDetail, UserDetailModel.class);

            JSONObject reqParams = new JSONObject();
            try {
                reqParams.put("audio_id", audioModel.getAudioId());
                reqParams.put("feedback", feedback);
                reqParams.put("rating", rating);
                reqParams.put("user_login_id", userDetailModel.getUserLoginId());
            } catch (JSONException ex) {
                Log.e(Const.TAG, "VOLLEY: " + ex.getMessage());
            }

            Log.i(Const.TAG, "INPUT:" + gson.toJson(reqParams));
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(AUDIO_FEEDBACK_ADD_URI.getMethod(), AUDIO_FEEDBACK_ADD_URI.getUri(), reqParams, new Response.Listener<JSONObject>() {
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
                        PDialog.showStyleableToast(context, responseModel.getStatusMessage(), Const.TOAST_SUCCESS, null);
                    } catch (Exception ex) {
                        Log.e(Const.TAG, "VOLLEY:" + ex.getMessage());
                        onPostExecute("exception");
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
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
            AppController.getInstance().addToRequestQueue(jsonObjectRequest);

            return null;
        }

        @Override
        protected void onPostExecute(final String success) {
            if (TextUtils.isEmpty(success))
                return;

            Log.i(TAG, "Inside onPostExecute = " + success);
            mGetAudioFeedbackTask = null;
            PDialog.hidePDialog();

            switch (success) {
                case "success":
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
            mGetAudioFeedbackTask = null;
        }
    }


}