package co.dust.smspasuruan.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by irsal on 6/6/17.
 */

public class User {

    @SerializedName("UserLoginID")
    private int userLoginID;

    @SerializedName("NamaLogin")
    private String namaLogin;

    @SerializedName("NamaUser")
    private String namaUser;

    @SerializedName("NipUser")
    private String nipUser;

    @SerializedName("SatKerID")
    private String satKerID;

    @SerializedName("SatKer")
    private String satKer;

    @SerializedName("Jenis")
    private int jenis;

    public int getUserLoginID() {
        return userLoginID;
    }

    public void setUserLoginID(int userLoginID) {
        this.userLoginID = userLoginID;
    }

    public String getNamaLogin() {
        return namaLogin;
    }

    public void setNamaLogin(String namaLogin) {
        this.namaLogin = namaLogin;
    }

    public String getNamaUser() {
        return namaUser;
    }

    public void setNamaUser(String namaUser) {
        this.namaUser = namaUser;
    }

    public String getNipUser() {
        return nipUser;
    }

    public void setNipUser(String nipUser) {
        this.nipUser = nipUser;
    }

    public String getSatKerID() {
        return satKerID;
    }

    public void setSatKerID(String satKerID) {
        this.satKerID = satKerID;
    }

    public String getSatKer() {
        return satKer;
    }

    public void setSatKer(String satKer) {
        this.satKer = satKer;
    }

    public int getJenis() {
        return jenis;
    }

    public void setJenis(int jenis) {
        this.jenis = jenis;
    }
}
