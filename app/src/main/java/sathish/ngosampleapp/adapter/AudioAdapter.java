package sathish.ngosampleapp.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import sathish.ngosampleapp.R;
import sathish.ngosampleapp.activity.testing.AudioPlayerActivity;
import sathish.ngosampleapp.dto.AudioModel;
import sathish.ngosampleapp.util.Const;
import sathish.ngosampleapp.util.Util;

public class AudioAdapter extends RecyclerView.Adapter<AudioAdapter.MyViewHolder> {

    private Context mContext;
    private List<AudioModel> audioList;

    public AudioAdapter(Context mContext, List<AudioModel> audioList) {
        this.mContext = mContext;
        this.audioList = audioList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.audio_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final AudioModel audioModel = audioList.get(position);
        holder.title.setText(audioModel.getTitle() + " (" + audioModel.getGenre() + ")");
        holder.author.setText(audioModel.getAuthor());
//        holder.genre.setText("(" + audioModel.getGenre() + ")");
        if (audioModel.getIsPremium().equals("1")) {
            holder.isPremium.setImageResource(R.mipmap.premium);
        } else {
//            holder.isPremium.setImageResource(R.mipmap.free);
        }

        Glide.with(mContext).load(Const.AUDIO_BANNER_PATH + audioModel.getBanner())
                .thumbnail(0.5f)
                .into(holder.banner);
        holder.banner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("model", audioModel);
                Util.startWithBundle(mContext, AudioPlayerActivity.class, bundle);
            }
        });

    }

    @Override
    public int getItemCount() {
        return audioList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.audio_title)
        TextView title;
        @BindView(R.id.audio_author)
        TextView author;
        @BindView(R.id.audio_genre)
        TextView genre;
        @BindView(R.id.audio_premium)
        ImageView isPremium;
        @BindView(R.id.audio_banner)
        ImageView banner;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
