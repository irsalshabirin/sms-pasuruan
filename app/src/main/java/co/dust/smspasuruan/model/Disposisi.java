package co.dust.smspasuruan.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by irsal on 7/18/17.
 */

public class Disposisi {

    @SerializedName("SuratMasukID")
    private String suratMasukId;

    @SerializedName("NoUrut")
    private String nourut;


    @SerializedName("TglDisposisi")
    private String tglDisposisi;

    @SerializedName("SatkerAsalID")
    private String satkerAsalId;

    @SerializedName("SatkerTujuanID")
    private String satkerTujuanId;

    @SerializedName("Terbaca")
    private boolean terbaca;

    @SerializedName("Terbalas")
    private boolean terbalas;

    @SerializedName("Terdisposisi")
    private boolean terdisposisi;

    @SerializedName("SatkerAsal")
    private String satkerAsal;

    @SerializedName("SatkerTujuan")
    private String satkerTujuan;

    @SerializedName("attachment")
    private String attachment;

    public String getSuratMasukId() {
        return suratMasukId;
    }

    public void setSuratMasukId(String suratMasukId) {
        this.suratMasukId = suratMasukId;
    }

    public String getNourut() {
        return nourut;
    }

    public void setNourut(String nourut) {
        this.nourut = nourut;
    }

    public String getTglDisposisi() {
        return tglDisposisi;
    }

    public void setTglDisposisi(String tglDisposisi) {
        this.tglDisposisi = tglDisposisi;
    }

    public String getSatkerAsalId() {
        return satkerAsalId;
    }

    public void setSatkerAsalId(String satkerAsalId) {
        this.satkerAsalId = satkerAsalId;
    }

    public String getSatkerTujuanId() {
        return satkerTujuanId;
    }

    public void setSatkerTujuanId(String satkerTujuanId) {
        this.satkerTujuanId = satkerTujuanId;
    }

    public boolean isTerbaca() {
        return terbaca;
    }

    public void setTerbaca(boolean terbaca) {
        this.terbaca = terbaca;
    }

    public boolean isTerbalas() {
        return terbalas;
    }

    public void setTerbalas(boolean terbalas) {
        this.terbalas = terbalas;
    }

    public boolean isTerdisposisi() {
        return terdisposisi;
    }

    public void setTerdisposisi(boolean terdisposisi) {
        this.terdisposisi = terdisposisi;
    }

    public String getSatkerAsal() {
        return satkerAsal;
    }

    public void setSatkerAsal(String satkerAsal) {
        this.satkerAsal = satkerAsal;
    }

    public String getSatkerTujuan() {
        return satkerTujuan;
    }

    public void setSatkerTujuan(String satkerTujuan) {
        this.satkerTujuan = satkerTujuan;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }
}
