package sathish.ngosampleapp.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AudioFeedbackModel implements Serializable {

    @SerializedName("audio_feedback_id")
    @Expose
    private String audioFeedbackId;
    @SerializedName("audio_id")
    @Expose
    private String audioId;
    @SerializedName("user_login_id")
    @Expose
    private String userLoginId;
    private String feedback;
    private String rating;


    public String getAudioFeedbackId() {
        return audioFeedbackId;
    }

    public void setAudioFeedbackId(String audioFeedbackId) {
        this.audioFeedbackId = audioFeedbackId;
    }

    public String getAudioId() {
        return audioId;
    }

    public void setAudioId(String audioId) {
        this.audioId = audioId;
    }

    public String getUserLoginId() {
        return userLoginId;
    }

    public void setUserLoginId(String userLoginId) {
        this.userLoginId = userLoginId;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
