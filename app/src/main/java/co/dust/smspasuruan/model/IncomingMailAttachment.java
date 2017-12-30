package co.dust.smspasuruan.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by irsal on 6/6/17.
 */

public class IncomingMailAttachment {

    @SerializedName("SuratMasukID")
    private int suratMasukID;

    @SerializedName("NoUrut")
    private int noUrut;

//    @SerializedName("Attachment")
//    private String attachment;

    @SerializedName("Catatan")
    private String catatan;

    @SerializedName("Ukuran")
    private String ukuran;

//    @SerializedName("Tipe")
//    private String tipe;

    @SerializedName("url")
    private String url;

    public int getSuratMasukID() {
        return suratMasukID;
    }

    public void setSuratMasukID(int suratMasukID) {
        this.suratMasukID = suratMasukID;
    }

    public int getNoUrut() {
        return noUrut;
    }

    public void setNoUrut(int noUrut) {
        this.noUrut = noUrut;
    }

//    public String getAttachment() {
//        return attachment;
//    }

//    public void setAttachment(String attachment) {
//        this.attachment = attachment;
//    }

    public String getCatatan() {
        return catatan;
    }

    public void setCatatan(String catatan) {
        this.catatan = catatan;
    }

    public String getUkuran() {
        return ukuran;
    }

    public void setUkuran(String ukuran) {
        this.ukuran = ukuran;
    }

//    public String getTipe() {
//        return tipe;
//    }

//    public void setTipe(String tipe) {
//        this.tipe = tipe;
//    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
