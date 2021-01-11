package com.phuoclong.yumo.Model;

import java.io.Serializable;

public class Product implements Serializable {
    private String IDloaiSanPham;
    private String IdNhomHangHoa;
    private String MaLoaiSanPham;
    private String MaBarcode;
    private String tenLoaiSanPham;
    private String tenLoaiSanPhamKhongDau;
    private String idBoSuuTap;
    private String ThuongHieu;
    private String ChieuCao;
    private float HoaHongCTV;
    private float GHTKMin;
    private float GHTKMax;
    private float GiaBanSanPham;
    private float GiaBanSi;
    private float GiaBanLe;
    private String TinhTrang;
    private String HinhAnh;
    private String MoTaNgan;
    private String MotaChiTiet;

    public String getIDloaiSanPham() {
        return IDloaiSanPham;
    }

    public void setIDloaiSanPham(String IDloaiSanPham) {
        this.IDloaiSanPham = IDloaiSanPham;
    }

    public String getIdNhomHangHoa() {
        return IdNhomHangHoa;
    }

    public void setIdNhomHangHoa(String idNhomHangHoa) {
        IdNhomHangHoa = idNhomHangHoa;
    }

    public String getMaLoaiSanPham() {
        return MaLoaiSanPham;
    }

    public void setMaLoaiSanPham(String maLoaiSanPham) {
        MaLoaiSanPham = maLoaiSanPham;
    }

    public String getMaBarcode() {
        return MaBarcode;
    }

    public void setMaBarcode(String maBarcode) {
        MaBarcode = maBarcode;
    }

    public String getTenLoaiSanPham() {
        return tenLoaiSanPham;
    }

    public void setTenLoaiSanPham(String tenLoaiSanPham) {
        this.tenLoaiSanPham = tenLoaiSanPham;
    }

    public String getTenLoaiSanPhamKhongDau() {
        return tenLoaiSanPhamKhongDau;
    }

    public void setTenLoaiSanPhamKhongDau(String tenLoaiSanPhamKhongDau) {
        this.tenLoaiSanPhamKhongDau = tenLoaiSanPhamKhongDau;
    }

    public String getIdBoSuuTap() {
        return idBoSuuTap;
    }

    public void setIdBoSuuTap(String idBoSuuTap) {
        this.idBoSuuTap = idBoSuuTap;
    }

    public String getThuongHieu() {
        return ThuongHieu;
    }

    public void setIdThuongHieu(String idThuongHieu) {
        this.ThuongHieu = idThuongHieu;
    }

    public String getChieuCao() {
        return ChieuCao;
    }

    public void setChieuCao(String chieuCao) {
        ChieuCao = chieuCao;
    }

    public float getHoaHongCTV() {
        return HoaHongCTV;
    }

    public void setHoaHongCTV(float hoaHongCTV) {
        HoaHongCTV = hoaHongCTV;
    }

    public float getGHTKMin() {
        return GHTKMin;
    }

    public void setGHTKMin(float GHTKMin) {
        this.GHTKMin = GHTKMin;
    }

    public float getGHTKMax() {
        return GHTKMax;
    }

    public void setGHTKMax(float GHTKMax) {
        this.GHTKMax = GHTKMax;
    }

    public float getGiaBanSanPham() {
        return GiaBanSanPham;
    }

    public void setGiaBanSanPham(float giaBanSanPham) {
        GiaBanSanPham = giaBanSanPham;
    }

    public float getGiaBanSi() {
        return GiaBanSi;
    }

    public void setGiaBanSi(float giaBanSi) {
        GiaBanSi = giaBanSi;
    }

    public float getGiaBanLe() {
        return GiaBanLe;
    }

    public void setGiaBanLe(float giaBanLe) {
        GiaBanLe = giaBanLe;
    }

    public String getTinhTrang() {
        return TinhTrang;
    }

    public void setTinhTrang(String tinhTrang) {
        TinhTrang = tinhTrang;
    }

    public String getHinhAnh() {
        return HinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        HinhAnh = hinhAnh;
    }

    public String getMoTaNgan() {
        return MoTaNgan;
    }

    public void setMoTaNgan(String moTaNgan) {
        MoTaNgan = moTaNgan;
    }

    public String getMotaChiTiet() {
        return MotaChiTiet;
    }

    public void setMotaChiTiet(String motaChiTiet) {
        MotaChiTiet = motaChiTiet;
    }

    public Product(String IDloaiSanPham, String idNhomHangHoa, String maLoaiSanPham, String maBarcode, String tenLoaiSanPham, String tenLoaiSanPhamKhongDau, String idBoSuuTap, String ThuongHieu, String chieuCao, float hoaHongCTV, float GHTKMin, float GHTKMax, float giaBanSanPham, float giaBanSi, float giaBanLe, String tinhTrang, String hinhAnh, String moTaNgan, String motaChiTiet) {
        this.IDloaiSanPham = IDloaiSanPham;
        IdNhomHangHoa = idNhomHangHoa;
        MaLoaiSanPham = maLoaiSanPham;
        MaBarcode = maBarcode;
        this.tenLoaiSanPham = tenLoaiSanPham;
        this.tenLoaiSanPhamKhongDau = tenLoaiSanPhamKhongDau;
        this.idBoSuuTap = idBoSuuTap;
        this.ThuongHieu = ThuongHieu;
        ChieuCao = chieuCao;
        HoaHongCTV = hoaHongCTV;
        this.GHTKMin = GHTKMin;
        this.GHTKMax = GHTKMax;
        GiaBanSanPham = giaBanSanPham;
        GiaBanSi = giaBanSi;
        GiaBanLe = giaBanLe;
        TinhTrang = tinhTrang;
        HinhAnh = hinhAnh;
        MoTaNgan = moTaNgan;
        MotaChiTiet = motaChiTiet;
    }
}
