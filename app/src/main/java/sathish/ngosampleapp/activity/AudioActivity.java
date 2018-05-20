package sathish.ngosampleapp.activity;

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

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import sathish.ngosampleapp.AppController;
import sathish.ngosampleapp.R;
import sathish.ngosampleapp.adapter.AudioAdapter;
import sathish.ngosampleapp.dto.AudioModel;
import sathish.ngosampleapp.util.PDialog;

import static sathish.ngosampleapp.util.Const.AUDIO_URI;
import static sathish.ngosampleapp.util.Const.TAG;

public class AudioActivity extends AppCompatActivity {

    @BindView(R.id.recycler_view)
    public RecyclerView recyclerView;
    @BindView(R.id.shimmer_view_container)
    public ShimmerFrameLayout mShimmerViewContainer;
    private AudioAdapter adapter;
    private List<AudioModel> audioList;
    private AudioTask mAuthTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);
        ButterKnife.bind(this);

        audioList = new ArrayList<>();
        adapter = new AudioAdapter(this, audioList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        mShimmerViewContainer.setVisibility(View.VISIBLE);
        mShimmerViewContainer.startShimmerAnimation();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mAuthTask != null)
            return;

        mAuthTask = new AudioTask(this);
        mAuthTask.execute((Void) null);
    }

    /**
     * Performs Audio GET operation
     */
    public class AudioTask extends AsyncTask<Void, Void, String> {

        private final Context context;
        private final Gson gson = new Gson();

        AudioTask(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(Void... params) {

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(AUDIO_URI.getMethod(), AUDIO_URI.getUri(), null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            try {
                                audioList.clear();
                                for (int i = 0; i < response.length(); i++) {
                                    AudioModel model = gson.fromJson(response.getJSONObject(i).toString(), AudioModel.class);
                                    audioList.add(model);
                                }
                                Log.i(TAG, "TOTAL: " + gson.toJson(audioList));
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
            mAuthTask = null;
            mShimmerViewContainer.setVisibility(View.GONE);
            if (mShimmerViewContainer.isAnimationStarted())
                mShimmerViewContainer.stopShimmerAnimation();

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
            mAuthTask = null;
        }
    }
}
