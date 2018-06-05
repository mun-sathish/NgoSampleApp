package sathish.ngosampleapp.util;

import com.android.volley.Request;

import sathish.ngosampleapp.dto.ConstModel;

public class Const {

    // SharedPref
    public static final String LoginPref = "login_pref";
    public static final String keyUser = "user";
    public static final String TAG = "NGO";
    private static final String BASE_URI = "http://awakenyourselfwithinyou.org/ngo/backend/public/api";
    // Login URI
    public static final ConstModel SQ_URI = new ConstModel(BASE_URI + "/security-question", Request.Method.GET);
    public static final ConstModel SIGNIN_URI = new ConstModel(BASE_URI + "/user/sign-in", Request.Method.POST);
    public static final ConstModel SIGNUP_URI = new ConstModel(BASE_URI + "/user/sign-up", Request.Method.POST);
    public static final ConstModel SIGNUP_USERCHECK_URI = new ConstModel(BASE_URI + "/user/name", Request.Method.POST);
    public static final ConstModel USER_UPDATE_URI = new ConstModel(BASE_URI + "/user/update", Request.Method.POST);
    // Resource URI
    public static final ConstModel AUDIO_URI = new ConstModel(BASE_URI + "/resource/audio", Request.Method.GET);
    public static final ConstModel VIDEO_URI = new ConstModel(BASE_URI + "/resource/video", Request.Method.GET);
    public static final ConstModel BOOK_URI = new ConstModel(BASE_URI + "/resource/book", Request.Method.GET);
    // Resource Feedback URI
    public static final ConstModel AUDIO_FEEDBACK_URI = new ConstModel(BASE_URI + "/resource/audio/", Request.Method.GET);
    public static final ConstModel AUDIO_FEEDBACK_ADD_URI = new ConstModel(BASE_URI + "/resource/audio/feedback/add", Request.Method.POST);
    public static final ConstModel VIDEO_FEEDBACK_URI = new ConstModel(BASE_URI + "/resource/video/", Request.Method.GET);
    public static final ConstModel VIDEO_FEEDBACK_ADD_URI = new ConstModel(BASE_URI + "/resource/video/feedback/add", Request.Method.POST);
    public static final ConstModel BOOK_FEEDBACK_URI = new ConstModel(BASE_URI + "/resource/book/", Request.Method.GET);
    public static final String FEEDBACK_PARAM = "/feedback";
    // Resource Path
    private static final String BLOB_PATH = "http://awakenyourselfwithinyou.org/ngo/uploads/blob";
    public static final String VIDEO_BANNER_PATH = BLOB_PATH + "/video/banner/";
    public static final String VIDEO_FILE_PATH = BLOB_PATH + "/video/file/";
    public static final String AUDIO_BANNER_PATH = BLOB_PATH + "/audio/banner/";
    public static final String AUDIO_FILE_PATH = BLOB_PATH + "/audio/file/";
    public static final String BOOK_BANNER_PATH = BLOB_PATH + "/book/banner/";



    public static final String TOAST_PRIMARY = "primary";
    public static final String TOAST_DANGER = "danger";
    public static final String TOAST_SECONDARY = "secondary";
    public static final String TOAST_INFO = "info";
    public static final String TOAST_SUCCESS = "success";





}

