package co.dust.smspasuruan.tools;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import co.dust.smspasuruan.model.Disposisi;
import co.dust.smspasuruan.model.IncomingMail;
import co.dust.smspasuruan.model.IncomingMailAttachment;
import co.dust.smspasuruan.model.User;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by irsal on 9/21/16.
 */

public interface APIService {

    @GET("setting/check-version")
    Call<Message> checkVersionCode(
            @Query("version_code") int appVersionCode
    );

    @FormUrlEncoded
    @POST("updateFcm")
    Call<Message> updateFCMToken(
            @Field("UserLoginID") String userLoginID,
            @Field("FcmToken") String fcmToken
    );

    @FormUrlEncoded
    @POST("updateRead")
    Call<Message> updateRead(
            @Field("SuratMasukID") String suratMasukId
    );


//    @FormUrlEncoded
//    @POST("auth/register-info")
//    Call<Message> insertHealthInformation(
//            @Field("user_id") String userId,
//
//            @Field("berat_badan") String weight,
//            @Field("tinggi_badan") String height,
//
//            @Field("jumlah_anak") String sumChildren,
//            @Field("jumlah_kehamilan") String sumPregnant,
//            @Field("pernah_keguguran") String sumMiscarriage,
//
//            @Field("kurang_darah") String anemia,
//            @Field("tekanan_darah_rendah") String lowBloodPressure,
//            @Field("tekanan_darah_tinggi") String highBloodPressure,
//
//            @Field("diabetes") String diabetes,
//            @Field("flek_paru") String lungPosts,
//            @Field("asma") String Asthma,
//            @Field("penyakit_lain") String otherDisease
//    );


//    @FormUrlEncoded
//    @POST("auth/register")
//    Call<Message> insertIdentity(
//            @Field("nama") String name,
//            @Field("username") String username,
//            @Field("email") String email,
//            @Field("password") String password,
//
//            @Field("umur") String age,
//            @Field("pendidikan") String education,
//            @Field("pekerjaan") String job,
//            @Field("no_hp") String number,
//            @Field("alamat") String address,
//
//            // Husband
//            @Field("nama_suami") String nameHusband,
//            @Field("umur_suami") String ageHusband,
//            @Field("pendidikan_suami") String educationHusband,
//            @Field("pekerjaan_suami") String jobHusband,
//            @Field("no_hp_suami") String numberHusband
//    );

    @FormUrlEncoded
    @POST("login")
    Call<UserResponse> login(
            @Field("NamaUser") String username,
            @Field("Pwd") String password
    );

    // 0 -> all_mail
    // 1 -> unread mail
    // 2 -> read mail
    // 3 -> disposition mail

    @FormUrlEncoded
    @POST("list_surat_masuk/{page}")
    Call<IncomingMailsResponse> loadMail(
            // TODO: 6/8/17 do something

            @Path("page") int page,
            @Field("per_page") int perPage,

            @Field("type") int type,

            @Field("satker_id") String satkerId,
            @Field("tahun") String year,

            @Field("m_tgl_disposisi") String from,
            @Field("s_tgl_disposisi") String now,

            @Field("query") String search

    );

    @GET("tahun")
    Call<YearArrResponse> getYear();

    @GET("get_attachment/{surat_masuk_id}")
    Call<IncomingMailAttachmentsResponse> getAttachment(
            @Path("surat_masuk_id") String suratMasukID
    );

    @GET("detail_suratmasuk")
    Call<IncomingMailResponse> getDetailMail(
            @Query("suratmasuk_id") String suratMasukID,
            @Query("satker_id") String satkerId,
            @Query("tahun") String year
    );

    @FormUrlEncoded
    @POST("disposisi_load")
    Call<List<Disposisi>> getDisposisi(
            @Field("surat_id") String suratId
    );

    @FormUrlEncoded
    @POST("disposisi_isi")
    Call<ResponseBody> getIsiDisposisi(
            @Field("surat_id") String suratmasukid,
            @Field("no_urut") String nourut
    );

    @FormUrlEncoded
    @POST("disposisi_attachment")
    Call<Void> getAttachmentDisposisi(

    );

//    @FormUrlEncoded
//    @POST("rekammedik/catat")
//    Call<Message> saveMedicalRecord(
//
//            @Field("id_user") String userId,
//            @Field("minggu_ke") String weekOf,
//
//            @Field("pernafasan") String pernafasan,
//            @Field("kejang") String kejang,
//            @Field("demam") String demam,
//            @Field("kd") String kurangDarah,
//            @Field("nyeri_kepala") String nyeriKepala,
//            @Field("nyeri_perut") String nyeriPerut,
//            @Field("perdarahan") String perdarahan,
//            @Field("masalah_janin") String masalahJanin,
//            @Field("bengkak") String bengkak,
//            @Field("muntah") String muntah,
//            @Field("keluar_cairan") String keluarCairan,
//            @Field("keterangan_keluhan") String keteranganKeluhan,
//
//            @Field("ke_dokter") String keDokter,
//            @Field("ke_bidan") String keBidan,
//            @Field("ke_puskesmas") String kePuskesmas,
//            @Field("ke_RS") String keRS,
//            @Field("minum_obat") String minumObat,
//            @Field("minum_jamu") String minumJamu,
//            @Field("istirahat") String istirahat,
//            @Field("keterangan_tindakan") String keteranganTindakan,
//
//            @Field("status") String status
//    );

//    @GET("rekammedik/mingguke")
//    Call<WeekOf> getWeekOf(
//            @Query("id_user") String userId
//    );


//    @GET("rekammedik/history")
//    Call<HistoryMedicalRecordResponse> getAllHistoryMedicalRecord(
//            @Query("id_user") String userId,
//            @Query("status") String pre
//    );


//    @Multipart
//    @POST("register")
//    Call<UserResponse> register(
//            @Part("name") RequestBody name,
//            @Part("email") RequestBody email,
//            @Part("birthday_date") RequestBody birthdayDate,
//            @Part("gender") RequestBody gender,
//            @Part("phone_number") RequestBody phoneNumber,
//            @Part("password") RequestBody password,
//
//            @Part("img_path_fb") RequestBody imgPathFb,
//            @Part MultipartBody.Part imgPath,
//
//            @Part("fb_id") RequestBody fbId,
//            @Part("role_id") RequestBody roleId,
//
//            @Part("is_subscribe_news_letter") RequestBody isSubscribe
//    );


//    class UserResponse extends Message {
//
//        @SerializedName("user")
//        public User user;
//
//    }

//    class BaseItemResponse extends Message {
//
//        @SerializedName("category_feature")
//        public ArrayList<BaseItem> baseItems;
//
//    }

    class IncomingMailAttachmentsResponse extends Message {

        @SerializedName("attachment")
        public List<IncomingMailAttachment> incomingMailAttachments;

    }

    class IncomingMailResponse extends Message {

        @SerializedName("surat_masuk")
        public IncomingMail incomingMail;
    }

    class IncomingMailsResponse extends Message {

        @SerializedName("surat_masuk")
        public ArrayList<IncomingMail> incomingMails;
    }

    class Message {

        @SerializedName("error")
        public boolean error;

        @SerializedName("message")
        public String message;

        @SerializedName("return_id")
        public int returnId;
    }

    class UserResponse extends Message {

        @SerializedName("user")
        public User user;

    }

    class YearArrResponse extends Message {

        @SerializedName("tahun")
        public List<String> years;

    }


//    class HistoryMedicalRecordResponse extends Message {
//        @SerializedName("history")
//        public ArrayList<MedicalRecord> medicalRecords;
//    }
}
