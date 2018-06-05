package sathish.ngosampleapp.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
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
import sathish.ngosampleapp.adapter.BookAdapter;
import sathish.ngosampleapp.dto.BookModel;

import static sathish.ngosampleapp.util.Const.TAG;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FeedBookFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FeedBookFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FeedBookFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private final Gson gson = new Gson();
    @BindView(R.id.recycler_view)
    public RecyclerView recyclerView;
    @BindView(R.id.shimmer_view_container)
    public ShimmerFrameLayout mShimmerViewContainer;
    private BookAdapter adapter;
    private List<BookModel> bookList;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public FeedBookFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FeedBookFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FeedBookFragment newInstance(String param1, String param2) {
        FeedBookFragment fragment = new FeedBookFragment();
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
        View view = inflater.inflate(R.layout.fragment_feed_book, container, false);
        ButterKnife.bind(this, view);

        bookList = new ArrayList<>();
        adapter = new BookAdapter(getContext(), bookList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
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
            bookList.clear();
            for (int i = 0; i < response.length(); i++) {
                BookModel bookModel = gson.fromJson(response.getJSONObject(i).toString(), BookModel.class);
                bookList.add(bookModel);
            }
            Log.i(TAG, "TOTAL: " + gson.toJson(bookList));
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
