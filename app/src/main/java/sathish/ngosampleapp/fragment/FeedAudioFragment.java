package sathish.ngosampleapp.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import sathish.ngosampleapp.R;
import sathish.ngosampleapp.adapter.AudioAdapter;
import sathish.ngosampleapp.dto.AudioModel;
import sathish.ngosampleapp.util.GridSpacingItemDecoration;
import sathish.ngosampleapp.util.Util;

import static sathish.ngosampleapp.util.Const.TAG;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FeedAudioFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FeedAudioFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FeedAudioFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private final Gson gson = new Gson();
    @BindView(R.id.recycler_view)
    public RecyclerView recyclerView;
    @BindView(R.id.shimmer_view_container)
    public ShimmerFrameLayout mShimmerViewContainer;
    private AudioAdapter adapter;
    private List<AudioModel> audioList;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public FeedAudioFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FeedAudioFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FeedAudioFragment newInstance(String param1, String param2) {
        FeedAudioFragment fragment = new FeedAudioFragment();
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
        View view = inflater.inflate(R.layout.fragment_feed_audio, container, false);
        ButterKnife.bind(this, view);

        audioList = new ArrayList<>();
        adapter = new AudioAdapter(getContext(), audioList);

//        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
//        recyclerView.setLayoutManager(mLayoutManager);
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        recyclerView.setAdapter(adapter);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, Util.dpToPx(getContext(), 10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        mShimmerViewContainer.setVisibility(View.VISIBLE);
        mShimmerViewContainer.startShimmerAnimation();

        return view;
    }


    public String handleJsonResponse(JSONArray response) {

        mShimmerViewContainer.setVisibility(View.GONE);
        if (mShimmerViewContainer.isAnimationStarted())
            mShimmerViewContainer.stopShimmerAnimation();
        try {
            audioList.clear();
            for (int i = 0; i < response.length(); i++) {
                AudioModel model = gson.fromJson(response.getJSONObject(i).toString(), AudioModel.class);
                audioList.add(model);
            }
            Log.i(TAG, "TOTAL: " + gson.toJson(audioList));
            adapter.notifyDataSetChanged();
            return ("success");
        } catch (JSONException ex) {
            Log.e(TAG, "VOLLEY:" + ex.getMessage());
            return ("exception");
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
        void onFragmentInteraction(Uri uri);
    }
}
