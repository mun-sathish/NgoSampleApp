package sathish.ngosampleapp.activity.testing;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.MediaController;
import android.widget.VideoView;

import butterknife.BindView;
import butterknife.ButterKnife;
import sathish.ngosampleapp.R;
import sathish.ngosampleapp.util.Const;
import sathish.ngosampleapp.util.PDialog;

public class VideoPlayerActivity extends AppCompatActivity {

    @BindView(R.id.video_player)
    public VideoView mVideoView;
    private MediaController mMediaController;
    private Uri videoLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);
        ButterKnife.bind(this);

        String  videoFileName = getIntent().getExtras().getString("file");
        videoLocation = Uri.parse(Const.VIDEO_FILE_PATH + videoFileName);
        mMediaController = new MediaController(this);
        setUpMedia();
        setUpListener();
    }

    private void setUpMedia() {
        mMediaController.setAnchorView(mVideoView);
        mVideoView.setMediaController(mMediaController);
        mVideoView.setVideoURI(videoLocation);
        mVideoView.requestFocus();
    }

    private void setUpListener() {
        PDialog.showDialog(this, "Buffering...");
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                PDialog.hidePDialog();
                mediaPlayer.setLooping(true);
                mVideoView.start();
            }
        });
    }
}
