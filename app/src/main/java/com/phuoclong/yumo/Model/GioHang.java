package com.phuoclong.yumo.Model;

public class GioHang {
    private String idSP;
    private String HinhAnhSp;
    private String TenSp;
    private String IDCTV;
    private int SoluongSp;
    private float DonGia;
    private float HoaHong;
    private int Size;
    private int Mau;
    private float TongThanhToan;

    public GioHang(String idSP, String hinhAnhSp, String tenSp, String IDCTV, int soluongSp, float donGia, float hoaHong, int size, int mau, float tongThanhToan) {
        this.idSP = idSP;
        HinhAnhSp = hinhAnhSp;
        TenSp = tenSp;
        this.IDCTV = IDCTV;
        SoluongSp = soluongSp;
        DonGia = donGia;
        HoaHong = hoaHong;
        Size = size;
        Mau = mau;
        TongThanhToan = tongThanhToan;
    }

    public int getSize() {
        return Size;
    }

    public void setSize(int size) {
        Size = size;
    }

    public int getMau() {
        return Mau;
    }

    public void setMau(int mau) {
        Mau = mau;
    }

    public float getTongThanhToan() {
        return TongThanhToan;
    }

    public void setTongThanhToan(float tongThanhToan) {
        TongThanhToan = tongThanhToan;
    }

    public String getIdSP() {
        return idSP;
    }

    public void setIdSP(String idSP) {
        this.idSP = idSP;
    }

    public String getHinhAnhSp() {
        return HinhAnhSp;
    }

    public void setHinhAnhSp(String hinhAnhSp) {
        HinhAnhSp = hinhAnhSp;
    }

    public String getTenSp() {
        return TenSp;
    }

    public void setTenSp(String tenSp) {
        TenSp = tenSp;
    }

    public String getIDCTV() {
        return IDCTV;
    }

    public void setIDCTV(String IDCTV) {
        this.IDCTV = IDCTV;
    }

    public int getSoluongSp() {
        return SoluongSp;
    }

    public void setSoluongSp(int soluongSp) {
        SoluongSp = soluongSp;
    }

    public float getDonGia() {
        return DonGia;
    }

    public void setDonGia(float donGia) {
        DonGia = donGia;
    }

    public float getHoaHong() {
        return HoaHong;
    }

    public void setHoaHong(float hoaHong) {
        HoaHong = hoaHong;
    }

}
