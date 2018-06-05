package sathish.ngosampleapp.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AudioModel implements Serializable {

    @SerializedName("audio_id")
    @Expose
    private String audioId;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("author")
    @Expose
    private String author;
    @SerializedName("genre")
    @Expose
    private String genre;
    @SerializedName("speaker")
    @Expose
    private String speaker;
    @SerializedName("is_premium")
    @Expose
    private String isPremium;
    @SerializedName("banner")
    @Expose
    private String banner;
    @SerializedName("file")
    @Expose
    private String file;

    public String getAudioId() {
        return audioId;
    }

    public void setAudioId(String audioId) {
        this.audioId = audioId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getSpeaker() {
        return speaker;
    }

    public void setSpeaker(String speaker) {
        this.speaker = speaker;
    }

    public String getIsPremium() {
        return isPremium;
    }

    public void setIsPremium(String isPremium) {
        this.isPremium = isPremium;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    @Override
    public String toString() {
        return "AudioModel{" +
                "audioId='" + audioId + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", genre='" + genre + '\'' +
                ", speaker='" + speaker + '\'' +
                ", isPremium='" + isPremium + '\'' +
                ", banner='" + banner + '\'' +
                ", file='" + file + '\'' +
                '}';
    }
}