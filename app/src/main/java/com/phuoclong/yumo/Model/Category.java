package com.phuoclong.yumo.Model;

public class Category {
    //nhom hàng hóa
    private String Idctgr;
    private String ctgrName;
    private String ctgrImage;
    private Boolean An;
    private String Ghichu;
    private String idCapcha;

    public Boolean getAn() {
        return An;
    }

    public void setAn(Boolean an) {
        An = an;
    }

    public String getGhichu() {
        return Ghichu;
    }

    public void setGhichu(String ghichu) {
        Ghichu = ghichu;
    }

    public String getIdCapcha() {
        return idCapcha;
    }

    public void setIdCapcha(String idCapcha) {
        this.idCapcha = idCapcha;
    }

    public String getIdctgr() {
        return Idctgr;
    }

    public void setIdctgr(String idctgr) {
        Idctgr = idctgr;
    }

    public String getCtgrName() {
        return ctgrName;
    }

    public void setCtgrName(String ctgrName) {
        this.ctgrName = ctgrName;
    }

    public String getCtgrImage() {
        return ctgrImage;
    }

    public void setCtgrImage(String ctgrImage) {
        this.ctgrImage = ctgrImage;
    }

    public Category(String idctgr, String ctgrName, String ctgrImage, Boolean an, String ghichu, String idCapcha) {
        Idctgr = idctgr;
        this.ctgrName = ctgrName;
        this.ctgrImage = ctgrImage;
        An = an;
        Ghichu = ghichu;
        this.idCapcha = idCapcha;
    }
}
