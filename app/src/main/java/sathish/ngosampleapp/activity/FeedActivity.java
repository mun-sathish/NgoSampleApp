package sathish.ngosampleapp.activity;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.hugeterry.coordinatortablayout.CoordinatorTabLayout;
import sathish.ngosampleapp.AppController;
import sathish.ngosampleapp.R;
import sathish.ngosampleapp.adapter.FeedPagerAdapter;
import sathish.ngosampleapp.fragment.FeedAudioFragment;
import sathish.ngosampleapp.fragment.FeedBookFragment;
import sathish.ngosampleapp.fragment.FeedTaskFragment;
import sathish.ngosampleapp.fragment.FeedVideoFragment;
import sathish.ngosampleapp.util.PDialog;

import static sathish.ngosampleapp.util.Const.AUDIO_URI;
import static sathish.ngosampleapp.util.Const.BOOK_URI;
import static sathish.ngosampleapp.util.Const.TAG;
import static sathish.ngosampleapp.util.Const.VIDEO_URI;

public class FeedActivity extends AppCompatActivity implements FeedAudioFragment.OnFragmentInteractionListener, FeedVideoFragment.OnFragmentInteractionListener, FeedBookFragment.OnFragmentInteractionListener, FeedTaskFragment.OnFragmentInteractionListener {

    private final String[] mTitles = {"Audio", "Video", "Task", "Book"};
    @BindView(R.id.feed_tab_layout)
    public CoordinatorTabLayout mCoordinatorTabLayout;
    @BindView(R.id.feed_vp)
    public ViewPager mViewPager;
    FeedAudioFragment feedAudioFragment = new FeedAudioFragment();
    FeedVideoFragment feedVideoFragment = new FeedVideoFragment();
    FeedBookFragment feedBookFragment = new FeedBookFragment();
    FeedTaskFragment feedTaskFragment = new FeedTaskFragment();
    private int[] mImageArray, mColorArray;
    private ArrayList<Fragment> mFragments;
    private AudioTask mAudioAuthTask;
    private VideoTask mVideoAuthTask;
    private BookTask mBookAuthTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        ButterKnife.bind(this);
        initFragments();
        initViewPager();
        mImageArray = new int[]{
                R.mipmap.bg_music,
                R.mipmap.bg_video,
                R.mipmap.bg_task,
                R.mipmap.bg_books};
        mColorArray = new int[]{
                R.color.colorPrimary,
                R.color.colorAccent,
                R.color.colorPrimaryDark,
                R.color.colorPrimary};

        mCoordinatorTabLayout.setTranslucentStatusBar(this)
                .setTitle("Awaken Yourself Within You")
                .setImageArray(mImageArray, mColorArray)
                .setupWithViewPager(mViewPager);

        startPopulation();
    }

    private void startPopulation() {
        mAudioAuthTask = new AudioTask(this);
        mAudioAuthTask.execute((Void) null);
        mVideoAuthTask = new VideoTask(this);
        mVideoAuthTask.execute((Void) null);
        mBookAuthTask = new BookTask(this);
        mBookAuthTask.execute((Void) null);
    }

    private void initFragments() {
        mFragments = new ArrayList<>();
        mFragments.add(feedAudioFragment);
        mFragments.add(feedVideoFragment);
        mFragments.add(feedTaskFragment);
        mFragments.add(feedBookFragment);
    }

    private void initViewPager() {
        mViewPager.setOffscreenPageLimit(4);
        mViewPager.setAdapter(new FeedPagerAdapter(getSupportFragmentManager(), mFragments, mTitles));
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        if (mAuthTask != null)
//            return;
//
//        mAuthTask = new AudioTask(this);
//        mAuthTask.execute((Void) null);
//    }


    /**
     * Performs Audio GET operation
     */
    public class AudioTask extends AsyncTask<Void, Void, String> {

        private final Context context;

        AudioTask(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(Void... params) {

            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(AUDIO_URI.getMethod(), AUDIO_URI.getUri(), null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            onPostExecute(feedAudioFragment.handleJsonResponse(response));
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
            mAudioAuthTask = null;

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
            mAudioAuthTask = null;
        }
    }


    /**
     * Performs Video GET operation
     */
    public class VideoTask extends AsyncTask<Void, Void, String> {

        private final Context context;

        VideoTask(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(Void... params) {

            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(VIDEO_URI.getMethod(), VIDEO_URI.getUri(), null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            onPostExecute(feedVideoFragment.handleJsonResponse(response));
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
            mVideoAuthTask = null;

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
            mVideoAuthTask = null;
        }
    }


    /**
     * Performs Book GET operation
     */
    public class BookTask extends AsyncTask<Void, Void, String> {

        private final Context context;

        BookTask(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(Void... params) {

            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(BOOK_URI.getMethod(), BOOK_URI.getUri(), null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            onPostExecute(feedBookFragment.handleJsonResponse(response));
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
            mBookAuthTask = null;

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
            mBookAuthTask = null;
        }
    }
}
