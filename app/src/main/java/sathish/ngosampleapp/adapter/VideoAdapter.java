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
import sathish.ngosampleapp.activity.testing.VideoPlayerActivity;
import sathish.ngosampleapp.dto.VideoModel;
import sathish.ngosampleapp.util.Const;
import sathish.ngosampleapp.util.Util;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.MyViewHolder> {

    private Context mContext;
    private List<VideoModel> videoList;

    public VideoAdapter(Context mContext, List<VideoModel> videoList) {
        this.mContext = mContext;
        this.videoList = videoList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.video_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final VideoModel videoModel = videoList.get(position);
        holder.title.setText(videoModel.getTitle() + " (" + videoModel.getGenre() + ")");
        holder.author.setText(videoModel.getAuthor());
//        holder.genre.setText("(" + videoModel.getGenre() + ")");
        if (videoModel.getIsPremium().equals("1")) {
            holder.isPremium.setImageResource(R.mipmap.premium);
        } else {
//            holder.isPremium.setImageResource(R.mipmap.free);
        }

        Glide.with(mContext).load(Const.VIDEO_BANNER_PATH + videoModel.getBanner())
                .thumbnail(0.5f)
                .into(holder.banner);

        holder.banner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("file", videoModel.getFile());
                Util.startWithBundle(mContext, VideoPlayerActivity.class, bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.video_title)
        TextView title;
        @BindView(R.id.video_author)
        TextView author;
        @BindView(R.id.video_genre)
        TextView genre;
        @BindView(R.id.video_premium)
        ImageView isPremium;
        @BindView(R.id.video_banner)
        ImageView banner;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
