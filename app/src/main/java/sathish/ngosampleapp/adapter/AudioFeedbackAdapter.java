package sathish.ngosampleapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import sathish.ngosampleapp.R;
import sathish.ngosampleapp.dto.AudioFeedbackModel;

public class AudioFeedbackAdapter extends RecyclerView.Adapter<AudioFeedbackAdapter.MyViewHolder> {

    private Context mContext;
    private List<AudioFeedbackModel> audioFeedbackList;

    public AudioFeedbackAdapter(Context mContext, List<AudioFeedbackModel> audioFeedbackList) {
        this.mContext = mContext;
        this.audioFeedbackList = audioFeedbackList;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.audio_feedback_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final AudioFeedbackModel audioFeedbackModel = audioFeedbackList.get(position);
        holder.username.setText(audioFeedbackModel.getUserLoginId());
        holder.rating.setText(audioFeedbackModel.getRating() + " / 5");
        holder.feedback.setText(audioFeedbackModel.getFeedback());
    }


    @Override
    public int getItemCount() {
        return audioFeedbackList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.audio_feedback_username)
        TextView username;
        @BindView(R.id.audio_feedback_feedback)
        TextView feedback;
        @BindView(R.id.audio_feedback_rating)
        TextView rating;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
