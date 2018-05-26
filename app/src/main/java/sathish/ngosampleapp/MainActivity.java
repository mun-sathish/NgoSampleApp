package sathish.ngosampleapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;
import sathish.ngosampleapp.activity.LoginSignUpActivity;
import sathish.ngosampleapp.activity.testing.AudioActivity;
import sathish.ngosampleapp.activity.testing.BookAcitivty;
import sathish.ngosampleapp.activity.testing.LoginActivity;
import sathish.ngosampleapp.activity.testing.SignUpActivity;
import sathish.ngosampleapp.activity.testing.VideoActivity;
import sathish.ngosampleapp.util.Util;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Util.start(this, LoginSignUpActivity.class);
        finish();
    }

    @OnClick(R.id.main_video)
    public void startVideo() {
        Util.start(this, VideoActivity.class);
    }


    @OnClick(R.id.main_audio)
    public void startAudio() {
        Util.start(this, AudioActivity.class);
    }


    @OnClick(R.id.main_book)
    public void startBook() {
        Util.start(this, BookAcitivty.class);
    }


    @OnClick(R.id.main_sign_in)
    public void startSignIn() {
        Util.start(this, LoginActivity.class);
    }


    @OnClick(R.id.main_sign_up)
    public void startSignUp() {
        Util.start(this, SignUpActivity.class);
    }

}
