package co.dust.smspasuruan.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by irsal on 6/6/17.
 */

public class IncomingMail implements Serializable {

    @SerializedName("suratmasukid")
    private String suratMasukID;

    @SerializedName("tahun")
    private String tahun;

    @SerializedName("nomorsurat")
    private String nomorSurat;

    @SerializedName("tglsurat")
    private String tglSurat;

    @SerializedName("tglditeruskan")
    private String tglDiteruskan;

    @SerializedName("tglbatas")
    private String tglBatas;

    @SerializedName("jenis")
    private String jenis;

    @SerializedName("jenistujuan")
    private String jenisTujuan;

    @SerializedName("kepada")
    private String kepada;

    @SerializedName("perihal")
    private String perihal;

    @SerializedName("klasifikasiid")
    private String klasifikasiID;

    @SerializedName("klasifikasi")
    private String klasifikasi;

    @SerializedName("instansiasal")
    private String instansiAsal;

    @SerializedName("alamatasal")
    private String alamatAsal;

    @SerializedName("kotaasal")
    private String kotaAsal;

    @SerializedName("keteranganasal")
    private String keteranganAsal;

    @SerializedName("satkertujuanid")
    private String satkerTujuanID;

    @SerializedName("satkertujuan")
    private String satkerTujuan;

    @SerializedName("isiringkas")
    private String isiRingkas;

    @SerializedName("jumlahlampiran")
    private String jumlahLampiran;

    @SerializedName("catatan")
    private String catatan;

    @SerializedName("terbalas")
    private String terbalas;

    @SerializedName("terdisposisi")
    private String terdisposisi;

    @SerializedName("terbaca")
    private String terbaca;

    @SerializedName("satkerentryid")
    private String satkerEntryID;

    @SerializedName("tglentry")
    private String tglEntry;

    @SerializedName("userloginid")
    private String userLoginID;

    @SerializedName("namauser")
    private String namaUser;

    @SerializedName("tglupdate")
    private String tglUpdate;

    @SerializedName("nourut")
    private String noUrut;

    @SerializedName("nourutsurat")
    private String noUrutSurat;

    @SerializedName("nourutsurat2")
    private String noUrutSurat2;

    @SerializedName("tembusan")
    private String tembusan;

//    @SerializedName("isRadiogram")
//    private String isRadiogram;

    @SerializedName("jenissurat")
    private String jenisSurat;

    @SerializedName("satkerasalid")
    private String satkerAsalID;

    @SerializedName("asaldisposisi")
    private String asalDisposisi;

    @SerializedName("tgldisposisi")
    private String tglDisposisi;

    @SerializedName("lewattglbatas")
    private String lewatTglBatas;
//    @SerializedName("isRead")
//    private String isRead;

    private int color = -1;

    public String getSuratMasukID() {
        return suratMasukID;
    }

    public void setSuratMasukID(String suratMasukID) {
        this.suratMasukID = suratMasukID;
    }

    public String getTahun() {
        return tahun;
    }

    public void setTahun(String tahun) {
        this.tahun = tahun;
    }

    public String getNomorSurat() {
        return nomorSurat;
    }

    public void setNomorSurat(String nomorSurat) {
        this.nomorSurat = nomorSurat;
    }

    public String getTglSurat() {
        return tglSurat;
    }

    public void setTglSurat(String tglSurat) {
        this.tglSurat = tglSurat;
    }

    public String getTglDiteruskan() {
        return tglDiteruskan;
    }

    public void setTglDiteruskan(String tglDiteruskan) {
        this.tglDiteruskan = tglDiteruskan;
    }

    public String getTglBatas() {
        return tglBatas;
    }

    public void setTglBatas(String tglBatas) {
        this.tglBatas = tglBatas;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public String getJenisTujuan() {
        return jenisTujuan;
    }

    public void setJenisTujuan(String jenisTujuan) {
        this.jenisTujuan = jenisTujuan;
    }

    public String getKepada() {
        return kepada;
    }

    public void setKepada(String kepada) {
        this.kepada = kepada;
    }

    public String getPerihal() {
        return perihal;
    }

    public void setPerihal(String perihal) {
        this.perihal = perihal;
    }

    public String getKlasifikasiID() {
        return klasifikasiID;
    }

    public void setKlasifikasiID(String klasifikasiID) {
        this.klasifikasiID = klasifikasiID;
    }

    public String getKlasifikasi() {
        return klasifikasi;
    }

    public void setKlasifikasi(String klasifikasi) {
        this.klasifikasi = klasifikasi;
    }

    public String getInstansiAsal() {
        return instansiAsal;
    }

    public void setInstansiAsal(String instansiAsal) {
        this.instansiAsal = instansiAsal;
    }

    public String getAlamatAsal() {
        return alamatAsal;
    }

    public void setAlamatAsal(String alamatAsal) {
        this.alamatAsal = alamatAsal;
    }

    public String getKotaAsal() {
        return kotaAsal;
    }

    public void setKotaAsal(String kotaAsal) {
        this.kotaAsal = kotaAsal;
    }

    public String getKeteranganAsal() {
        return keteranganAsal;
    }

    public void setKeteranganAsal(String keteranganAsal) {
        this.keteranganAsal = keteranganAsal;
    }

    public String getSatkerTujuanID() {
        return satkerTujuanID;
    }

    public void setSatkerTujuanID(String satkerTujuanID) {
        this.satkerTujuanID = satkerTujuanID;
    }

    public String getIsiRingkas() {
        return isiRingkas;
    }

    public void setIsiRingkas(String isiRingkas) {
        this.isiRingkas = isiRingkas;
    }

    public String getJumlahLampiran() {
        return jumlahLampiran;
    }

    public void setJumlahLampiran(String jumlahLampiran) {
        this.jumlahLampiran = jumlahLampiran;
    }

    public String getCatatan() {
        return catatan;
    }

    public void setCatatan(String catatan) {
        this.catatan = catatan;
    }

    public String getTerbalas() {
        return terbalas;
    }

    public void setTerbalas(String terbalas) {
        this.terbalas = terbalas;
    }

    public String getTerdisposisi() {
        return terdisposisi;
    }

    public void setTerdisposisi(String terdisposisi) {
        this.terdisposisi = terdisposisi;
    }


    public String getTerbaca() {
        return terbaca;
    }

    public void setTerbaca(String terbaca) {
        this.terbaca = terbaca;
    }

    public String getSatkerEntryID() {
        return satkerEntryID;
    }

    public void setSatkerEntryID(String satkerEntryID) {
        this.satkerEntryID = satkerEntryID;
    }

    public String getTglEntry() {
        return tglEntry;
    }

    public void setTglEntry(String tglEntry) {
        this.tglEntry = tglEntry;
    }

    public String getUserLoginID() {
        return userLoginID;
    }

    public void setUserLoginID(String userLoginID) {
        this.userLoginID = userLoginID;
    }

    public String getNamaUser() {
        return namaUser;
    }

    public void setNamaUser(String namaUser) {
        this.namaUser = namaUser;
    }

    public String getTglUpdate() {
        return tglUpdate;
    }

    public void setTglUpdate(String tglUpdate) {
        this.tglUpdate = tglUpdate;
    }

    public String getNoUrutSurat() {
        return noUrutSurat;
    }

    public void setNoUrutSurat(String noUrutSurat) {
        this.noUrutSurat = noUrutSurat;
    }

    public String getNoUrutSurat2() {
        return noUrutSurat2;
    }

    public void setNoUrutSurat2(String noUrutSurat2) {
        this.noUrutSurat2 = noUrutSurat2;
    }

    public String getTembusan() {
        return tembusan;
    }

    public void setTembusan(String tembusan) {
        this.tembusan = tembusan;
    }

//    public String getIsRadiogram() {
//        return isRadiogram;
//    }

//    public void setIsRadiogram(String isRadiogram) {
//        this.isRadiogram = isRadiogram;
//    }

    public String getSatkerAsalID() {
        return satkerAsalID;
    }

    public void setSatkerAsalID(String satkerAsalID) {
        this.satkerAsalID = satkerAsalID;
    }

//    public String getIsRead() {
//        return isRead;
//    }

//    public void setIsRead(String isRead) {
//        this.isRead = isRead;
//    }

    public String getSatkerTujuan() {
        return satkerTujuan;
    }

    public void setSatkerTujuan(String satkerTujuan) {
        this.satkerTujuan = satkerTujuan;
    }

    public String getNoUrut() {
        return noUrut;
    }

    public void setNoUrut(String noUrut) {
        this.noUrut = noUrut;
    }

    public String getJenisSurat() {
        return jenisSurat;
    }

    public void setJenisSurat(String jenisSurat) {
        this.jenisSurat = jenisSurat;
    }

    public String getAsalDisposisi() {
        return asalDisposisi;
    }

    public void setAsalDisposisi(String asalDisposisi) {
        this.asalDisposisi = asalDisposisi;
    }

    public String getTglDisposisi() {
        return tglDisposisi;
    }

    public void setTglDisposisi(String tglDisposisi) {
        this.tglDisposisi = tglDisposisi;
    }

    public String getLewatTglBatas() {
        return lewatTglBatas;
    }

    public void setLewatTglBatas(String lewatTglBatas) {
        this.lewatTglBatas = lewatTglBatas;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    @Override
    public String toString() {
        String str = "";
        str += "suratMasukID : " + suratMasukID + "\n";
        str += "tahun : " + tahun + "\n";
        str += "nomorSurat : " + nomorSurat + "\n";
        str += "tglSurat : " + tglSurat + "\n";
        str += "tglDiteruskan : " + tglDiteruskan + "\n";
        str += "tglBatas : " + tglBatas + "\n";
        str += "jenis : " + jenis + "\n";
        str += "jenisTujuan : " + jenisTujuan + "\n";
        str += "kepada : " + kepada + "\n";
        str += "perihal : " + perihal + "\n";
        str += "klasifikasiID : " + klasifikasiID + "\n";
        str += "instansiAsal : " + instansiAsal + "\n";
        str += "alamatAsal : " + alamatAsal + "\n";
        str += "kotaAsal : " + kotaAsal + "\n";
        str += "keteranganAsal : " + keteranganAsal + "\n";
        str += "satkerTujuanID : " + satkerTujuanID + "\n";
        str += "isiRingkas : " + isiRingkas + "\n";
        str += "jumlahLampiran : " + jumlahLampiran + "\n";
        str += "catatan : " + catatan + "\n";
        str += "terbalas : " + terbalas + "\n";
        str += "terdisposisi : " + terdisposisi + "\n";
        str += "satkerEntryID : " + satkerEntryID + "\n";
        str += "tglEntry : " + tglEntry + "\n";
        str += "userLoginID : " + userLoginID + "\n";
        str += "namaUser : " + namaUser + "\n";
        str += "tglUpdate : " + tglUpdate + "\n";
        str += "noUrutSurat : " + noUrutSurat + "\n";
        str += "noUrutSurat2 : " + noUrutSurat2 + "\n";
        str += "tembusan : " + tembusan + "\n";
//        str += "isRadiogram : " + isRadiogram + "\n";
        str += "satkerAsalID : " + satkerAsalID + "\n";
//        str += "isRead : " + isRead + "\n";
        return str;
    }
}
