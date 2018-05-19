package sathish.ngosampleapp.util;

import com.android.volley.Request.Method;

import sathish.ngosampleapp.dto.ConstModel;

public class Const {
    private static  final String BASE_URI = "http://awakenyourselfwithinyou.org/ngo/backend/public/api/";

    //TODO: Resource Path
    private static final String BLOB_PATH = "http://awakenyourselfwithinyou.org/ngo/uploads/blob";
    public static final String VIDEO_BANNER_PATH = "http://awakenyourselfwithinyou.org/ngo/uploads/blob/video/banner/";
    public static final String VIDEO_FILE_PATH = "http://awakenyourselfwithinyou.org/ngo/uploads/blob/video/file/";
    public static final String AUDIO_BANNER_PATH = "http://awakenyourselfwithinyou.org/ngo/uploads/blob/audio/banner/";
    public static final String AUDIO_FILE_PATH = "http://awakenyourselfwithinyou.org/ngo/uploads/blob/audio/file/";
    public static final String BOOK_BANNER_PATH = "http://awakenyourselfwithinyou.org/ngo/uploads/blob/book/banner/";

    //TODO: Login URI
    public  static final ConstModel SQ_URI = new ConstModel(BASE_URI + "/security-question", Method.GET);
    public  static final ConstModel SIGNIN_URI = new ConstModel(BASE_URI + "/user/sign-in", Method.POST);
    public  static final ConstModel SIGNUP_URI = new ConstModel(BASE_URI + "/user/sign-up", Method.POST);
    public  static final ConstModel USER_UPDATE_URI = new ConstModel(BASE_URI + "/user/update", Method.POST);

    //TODO: Resource URI
    public  static final ConstModel AUDIO_URI = new ConstModel(BASE_URI + "/resource/audio", Method.GET);
    public  static final ConstModel VIDEO_URI = new ConstModel(BASE_URI + "/resource/video", Method.GET);
    public  static final ConstModel BOOK_URI = new ConstModel(BASE_URI + "/resource/book", Method.GET);

    //TODO: Resource Access URI
    public  static final ConstModel AUDIO_ACCESS_URI = new ConstModel(BASE_URI + "/resource/audio/access", Method.GET);
    public  static final ConstModel VIDEO_ACCESS_URI = new ConstModel(BASE_URI + "/resource/video/access", Method.GET);
    public  static final ConstModel BOOK_ACCESS_URI = new ConstModel(BASE_URI + "/resource/book/access", Method.GET);

}

