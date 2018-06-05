package sathish.ngosampleapp.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class VideoFeedbackModel implements Serializable {

    @SerializedName("video_feedback_id")
    @Expose
    private String videoFeedbackId;
    @SerializedName("video_id")
    @Expose
    private String videoId;
    @SerializedName("user_login_id")
    @Expose
    private String userLoginId;
    private String feedback;
    private String rating;

    public String getVideoFeedbackId() {
        return videoFeedbackId;
    }

    public void setVideoFeedbackId(String videoFeedbackId) {
        this.videoFeedbackId = videoFeedbackId;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
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
