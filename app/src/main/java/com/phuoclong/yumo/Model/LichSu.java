package com.phuoclong.yumo.Model;

public class LichSu {
    private String IDPhieuXuatChoCTV;
    private String MAPhieuXuatChoCTV;
    private String NgayLap;
    private String GhiChu;
    private String iDCTV;
    private float GiamGia;
    private String TenNguoiNhanHang;
    private String SDTNguoiNhanHang;
    private String EmailNguoiNhan;
    private int idTinhNhan;
    private int idQuanHuyen;
    private int idPhuongXa;
    private String DiaChiNhan;
    private float PhiVanChuyen;
    private String MaTrangThai;
    private String ThuKhac;
    private float ThanhToanTienMat;
    private float ThanhToanChuyenKhoan;
    private String idNguoiTao;
    private String Loai;
    private boolean isDoiSoat;
    private float TienDoiSoat;
    private boolean isNhanHang;
    private String DonViVanChuyen;
    private String MaVanDon;
    private float PhiTraVanChuyen;
    private String NgayXuatHang;
    private String TrangThaiVanDon;
    private String NgayTrangThaiVanDon;
    private String lyDoKhongThanhCong;
    private String MaDonHangDoi;
    private String idTraHang;
    private String MaGiamGiaCoDinh;
    private String MaGiamGiaPhiShip;
    private String NgayChamSoc;
    private boolean isChamSocXong;
    private String NgayChamSocLai;
    private String idNguoiSua;
    private String NgaySua;
    private String idLyDoHuy;
    private float TongTienDonHang;
    private float TongTienHoaHongCTV;
    private boolean isDoiSoatHoaHongCTV;
    private String idGianHang;
    private String idGianHangCTV;
    private boolean isWeb;
    private String QuyenCTV;
    private boolean isDuyetSoQuy;
    private String idNguoiDuyet;
    private String NgayDuyet;
    private float TongTienThanhToan;
    private String HinhAnhSp;

    public int getSoluong() {
        return Soluong;
    }

    public void setSoluong(int soluong) {
        Soluong = soluong;
    }

    private String TenSanPham;
    private int Soluong;
    private float GiaBanSanPham;
    private float GiaBanSi;
    private float GiaBanLe;
    private String ThuongHieu;
    private String idSanPham;
    private String MoTaNgan;
    private String MoTaChiTiet;

    public LichSu(String IDPhieuXuatChoCTV, String MAPhieuXuatChoCTV, String ngayLap, String ghiChu, String iDCTV, float giamGia, String tenNguoiNhanHang, String SDTNguoiNhanHang, String emailNguoiNhan, int idTinhNhan, int idQuanHuyen, int idPhuongXa, String diaChiNhan, float phiVanChuyen, String maTrangThai, String thuKhac, float thanhToanTienMat, float thanhToanChuyenKhoan, String idNguoiTao, String loai, boolean isDoiSoat, float tienDoiSoat, boolean isNhanHang, String donViVanChuyen, String maVanDon, float phiTraVanChuyen, String ngayXuatHang, String trangThaiVanDon, String ngayTrangThaiVanDon, String lyDoKhongThanhCong, String maDonHangDoi, String idTraHang, String maGiamGiaCoDinh, String maGiamGiaPhiShip, String ngayChamSoc, boolean isChamSocXong, String ngayChamSocLai, String idNguoiSua, String ngaySua, String idLyDoHuy, float tongTienDonHang, float tongTienHoaHongCTV, boolean isDoiSoatHoaHongCTV, String idGianHang, String idGianHangCTV, boolean isWeb, String quyenCTV, boolean isDuyetSoQuy, String idNguoiDuyet, String ngayDuyet, float tongTienThanhToan, String hinhAnhSp, String tenSanPham, int soluong, float giaBanSanPham, float giaBanSi, float giaBanLe, String thuongHieu, String idSanPham, String moTaNgan, String moTaChiTiet) {
        this.IDPhieuXuatChoCTV = IDPhieuXuatChoCTV;
        this.MAPhieuXuatChoCTV = MAPhieuXuatChoCTV;
        NgayLap = ngayLap;
        GhiChu = ghiChu;
        this.iDCTV = iDCTV;
        GiamGia = giamGia;
        TenNguoiNhanHang = tenNguoiNhanHang;
        this.SDTNguoiNhanHang = SDTNguoiNhanHang;
        EmailNguoiNhan = emailNguoiNhan;
        this.idTinhNhan = idTinhNhan;
        this.idQuanHuyen = idQuanHuyen;
        this.idPhuongXa = idPhuongXa;
        DiaChiNhan = diaChiNhan;
        PhiVanChuyen = phiVanChuyen;
        MaTrangThai = maTrangThai;
        ThuKhac = thuKhac;
        ThanhToanTienMat = thanhToanTienMat;
        ThanhToanChuyenKhoan = thanhToanChuyenKhoan;
        this.idNguoiTao = idNguoiTao;
        Loai = loai;
        this.isDoiSoat = isDoiSoat;
        TienDoiSoat = tienDoiSoat;
        this.isNhanHang = isNhanHang;
        DonViVanChuyen = donViVanChuyen;
        MaVanDon = maVanDon;
        PhiTraVanChuyen = phiTraVanChuyen;
        NgayXuatHang = ngayXuatHang;
        TrangThaiVanDon = trangThaiVanDon;
        NgayTrangThaiVanDon = ngayTrangThaiVanDon;
        this.lyDoKhongThanhCong = lyDoKhongThanhCong;
        MaDonHangDoi = maDonHangDoi;
        this.idTraHang = idTraHang;
        MaGiamGiaCoDinh = maGiamGiaCoDinh;
        MaGiamGiaPhiShip = maGiamGiaPhiShip;
        NgayChamSoc = ngayChamSoc;
        this.isChamSocXong = isChamSocXong;
        NgayChamSocLai = ngayChamSocLai;
        this.idNguoiSua = idNguoiSua;
        NgaySua = ngaySua;
        this.idLyDoHuy = idLyDoHuy;
        TongTienDonHang = tongTienDonHang;
        TongTienHoaHongCTV = tongTienHoaHongCTV;
        this.isDoiSoatHoaHongCTV = isDoiSoatHoaHongCTV;
        this.idGianHang = idGianHang;
        this.idGianHangCTV = idGianHangCTV;
        this.isWeb = isWeb;
        QuyenCTV = quyenCTV;
        this.isDuyetSoQuy = isDuyetSoQuy;
        this.idNguoiDuyet = idNguoiDuyet;
        NgayDuyet = ngayDuyet;
        TongTienThanhToan = tongTienThanhToan;
        HinhAnhSp = hinhAnhSp;
        TenSanPham = tenSanPham;
        Soluong = soluong;
        GiaBanSanPham = giaBanSanPham;
        GiaBanSi = giaBanSi;
        GiaBanLe = giaBanLe;
        ThuongHieu = thuongHieu;
        this.idSanPham = idSanPham;
        MoTaNgan = moTaNgan;
        MoTaChiTiet = moTaChiTiet;
    }

    public String getIDPhieuXuatChoCTV() {
        return IDPhieuXuatChoCTV;
    }

    public void setIDPhieuXuatChoCTV(String IDPhieuXuatChoCTV) {
        this.IDPhieuXuatChoCTV = IDPhieuXuatChoCTV;
    }

    public String getMAPhieuXuatChoCTV() {
        return MAPhieuXuatChoCTV;
    }

    public void setMAPhieuXuatChoCTV(String MAPhieuXuatChoCTV) {
        this.MAPhieuXuatChoCTV = MAPhieuXuatChoCTV;
    }

    public String getNgayLap() {
        return NgayLap;
    }

    public void setNgayLap(String ngayLap) {
        NgayLap = ngayLap;
    }

    public String getGhiChu() {
        return GhiChu;
    }

    public void setGhiChu(String ghiChu) {
        GhiChu = ghiChu;
    }

    public String getiDCTV() {
        return iDCTV;
    }

    public void setiDCTV(String iDCTV) {
        this.iDCTV = iDCTV;
    }

    public float getGiamGia() {
        return GiamGia;
    }

    public void setGiamGia(float giamGia) {
        GiamGia = giamGia;
    }

    public String getTenNguoiNhanHang() {
        return TenNguoiNhanHang;
    }

    public void setTenNguoiNhanHang(String tenNguoiNhanHang) {
        TenNguoiNhanHang = tenNguoiNhanHang;
    }

    public String getSDTNguoiNhanHang() {
        return SDTNguoiNhanHang;
    }

    public void setSDTNguoiNhanHang(String SDTNguoiNhanHang) {
        this.SDTNguoiNhanHang = SDTNguoiNhanHang;
    }

    public String getEmailNguoiNhan() {
        return EmailNguoiNhan;
    }

    public void setEmailNguoiNhan(String emailNguoiNhan) {
        EmailNguoiNhan = emailNguoiNhan;
    }

    public int getIdTinhNhan() {
        return idTinhNhan;
    }

    public void setIdTinhNhan(int idTinhNhan) {
        this.idTinhNhan = idTinhNhan;
    }

    public int getIdQuanHuyen() {
        return idQuanHuyen;
    }

    public void setIdQuanHuyen(int idQuanHuyen) {
        this.idQuanHuyen = idQuanHuyen;
    }

    public int getIdPhuongXa() {
        return idPhuongXa;
    }

    public void setIdPhuongXa(int idPhuongXa) {
        this.idPhuongXa = idPhuongXa;
    }

    public String getDiaChiNhan() {
        return DiaChiNhan;
    }

    public void setDiaChiNhan(String diaChiNhan) {
        DiaChiNhan = diaChiNhan;
    }

    public float getPhiVanChuyen() {
        return PhiVanChuyen;
    }

    public void setPhiVanChuyen(float phiVanChuyen) {
        PhiVanChuyen = phiVanChuyen;
    }

    public String getMaTrangThai() {
        return MaTrangThai;
    }

    public void setMaTrangThai(String maTrangThai) {
        MaTrangThai = maTrangThai;
    }

    public String getThuKhac() {
        return ThuKhac;
    }

    public void setThuKhac(String thuKhac) {
        ThuKhac = thuKhac;
    }

    public float getThanhToanTienMat() {
        return ThanhToanTienMat;
    }

    public void setThanhToanTienMat(float thanhToanTienMat) {
        ThanhToanTienMat = thanhToanTienMat;
    }

    public float getThanhToanChuyenKhoan() {
        return ThanhToanChuyenKhoan;
    }

    public void setThanhToanChuyenKhoan(float thanhToanChuyenKhoan) {
        ThanhToanChuyenKhoan = thanhToanChuyenKhoan;
    }

    public String getIdNguoiTao() {
        return idNguoiTao;
    }

    public void setIdNguoiTao(String idNguoiTao) {
        this.idNguoiTao = idNguoiTao;
    }

    public String getLoai() {
        return Loai;
    }

    public void setLoai(String loai) {
        Loai = loai;
    }

    public boolean isDoiSoat() {
        return isDoiSoat;
    }

    public void setDoiSoat(boolean doiSoat) {
        isDoiSoat = doiSoat;
    }

    public float getTienDoiSoat() {
        return TienDoiSoat;
    }

    public void setTienDoiSoat(float tienDoiSoat) {
        TienDoiSoat = tienDoiSoat;
    }

    public boolean isNhanHang() {
        return isNhanHang;
    }

    public void setNhanHang(boolean nhanHang) {
        isNhanHang = nhanHang;
    }

    public String getDonViVanChuyen() {
        return DonViVanChuyen;
    }

    public void setDonViVanChuyen(String donViVanChuyen) {
        DonViVanChuyen = donViVanChuyen;
    }

    public String getMaVanDon() {
        return MaVanDon;
    }

    public void setMaVanDon(String maVanDon) {
        MaVanDon = maVanDon;
    }

    public float getPhiTraVanChuyen() {
        return PhiTraVanChuyen;
    }

    public void setPhiTraVanChuyen(float phiTraVanChuyen) {
        PhiTraVanChuyen = phiTraVanChuyen;
    }

    public String getNgayXuatHang() {
        return NgayXuatHang;
    }

    public void setNgayXuatHang(String ngayXuatHang) {
        NgayXuatHang = ngayXuatHang;
    }

    public String getTrangThaiVanDon() {
        return TrangThaiVanDon;
    }

    public void setTrangThaiVanDon(String trangThaiVanDon) {
        TrangThaiVanDon = trangThaiVanDon;
    }

    public String getNgayTrangThaiVanDon() {
        return NgayTrangThaiVanDon;
    }

    public void setNgayTrangThaiVanDon(String ngayTrangThaiVanDon) {
        NgayTrangThaiVanDon = ngayTrangThaiVanDon;
    }

    public String getLyDoKhongThanhCong() {
        return lyDoKhongThanhCong;
    }

    public void setLyDoKhongThanhCong(String lyDoKhongThanhCong) {
        this.lyDoKhongThanhCong = lyDoKhongThanhCong;
    }

    public String getMaDonHangDoi() {
        return MaDonHangDoi;
    }

    public void setMaDonHangDoi(String maDonHangDoi) {
        MaDonHangDoi = maDonHangDoi;
    }

    public String getIdTraHang() {
        return idTraHang;
    }

    public void setIdTraHang(String idTraHang) {
        this.idTraHang = idTraHang;
    }

    public String getMaGiamGiaCoDinh() {
        return MaGiamGiaCoDinh;
    }

    public void setMaGiamGiaCoDinh(String maGiamGiaCoDinh) {
        MaGiamGiaCoDinh = maGiamGiaCoDinh;
    }

    public String getMaGiamGiaPhiShip() {
        return MaGiamGiaPhiShip;
    }

    public void setMaGiamGiaPhiShip(String maGiamGiaPhiShip) {
        MaGiamGiaPhiShip = maGiamGiaPhiShip;
    }

    public String getNgayChamSoc() {
        return NgayChamSoc;
    }

    public void setNgayChamSoc(String ngayChamSoc) {
        NgayChamSoc = ngayChamSoc;
    }

    public boolean isChamSocXong() {
        return isChamSocXong;
    }

    public void setChamSocXong(boolean chamSocXong) {
        isChamSocXong = chamSocXong;
    }

    public String getNgayChamSocLai() {
        return NgayChamSocLai;
    }

    public void setNgayChamSocLai(String ngayChamSocLai) {
        NgayChamSocLai = ngayChamSocLai;
    }

    public String getIdNguoiSua() {
        return idNguoiSua;
    }

    public void setIdNguoiSua(String idNguoiSua) {
        this.idNguoiSua = idNguoiSua;
    }

    public String getNgaySua() {
        return NgaySua;
    }

    public void setNgaySua(String ngaySua) {
        NgaySua = ngaySua;
    }

    public String getIdLyDoHuy() {
        return idLyDoHuy;
    }

    public void setIdLyDoHuy(String idLyDoHuy) {
        this.idLyDoHuy = idLyDoHuy;
    }

    public float getTongTienDonHang() {
        return TongTienDonHang;
    }

    public void setTongTienDonHang(float tongTienDonHang) {
        TongTienDonHang = tongTienDonHang;
    }

    public float getTongTienHoaHongCTV() {
        return TongTienHoaHongCTV;
    }

    public void setTongTienHoaHongCTV(float tongTienHoaHongCTV) {
        TongTienHoaHongCTV = tongTienHoaHongCTV;
    }

    public boolean isDoiSoatHoaHongCTV() {
        return isDoiSoatHoaHongCTV;
    }

    public void setDoiSoatHoaHongCTV(boolean doiSoatHoaHongCTV) {
        isDoiSoatHoaHongCTV = doiSoatHoaHongCTV;
    }

    public String getIdGianHang() {
        return idGianHang;
    }

    public void setIdGianHang(String idGianHang) {
        this.idGianHang = idGianHang;
    }

    public String getIdGianHangCTV() {
        return idGianHangCTV;
    }

    public void setIdGianHangCTV(String idGianHangCTV) {
        this.idGianHangCTV = idGianHangCTV;
    }

    public boolean isWeb() {
        return isWeb;
    }

    public void setWeb(boolean web) {
        isWeb = web;
    }

    public String getQuyenCTV() {
        return QuyenCTV;
    }

    public void setQuyenCTV(String quyenCTV) {
        QuyenCTV = quyenCTV;
    }

    public boolean isDuyetSoQuy() {
        return isDuyetSoQuy;
    }

    public void setDuyetSoQuy(boolean duyetSoQuy) {
        isDuyetSoQuy = duyetSoQuy;
    }

    public String getIdNguoiDuyet() {
        return idNguoiDuyet;
    }

    public void setIdNguoiDuyet(String idNguoiDuyet) {
        this.idNguoiDuyet = idNguoiDuyet;
    }

    public String getNgayDuyet() {
        return NgayDuyet;
    }

    public void setNgayDuyet(String ngayDuyet) {
        NgayDuyet = ngayDuyet;
    }

    public float getTongTienThanhToan() {
        return TongTienThanhToan;
    }

    public void setTongTienThanhToan(float tongTienThanhToan) {
        TongTienThanhToan = tongTienThanhToan;
    }

    public String getHinhAnhSp() {
        return HinhAnhSp;
    }

    public void setHinhAnhSp(String hinhAnhSp) {
        HinhAnhSp = hinhAnhSp;
    }

    public String getTenSanPham() {
        return TenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        TenSanPham = tenSanPham;
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

    public String getThuongHieu() {
        return ThuongHieu;
    }

    public void setThuongHieu(String thuongHieu) {
        ThuongHieu = thuongHieu;
    }

    public String getIdSanPham() {
        return idSanPham;
    }

    public void setIdSanPham(String idSanPham) {
        this.idSanPham = idSanPham;
    }

    public String getMoTaNgan() {
        return MoTaNgan;
    }

    public void setMoTaNgan(String moTaNgan) {
        MoTaNgan = moTaNgan;
    }

    public String getMoTaChiTiet() {
        return MoTaChiTiet;
    }

    public void setMoTaChiTiet(String moTaChiTiet) {
        MoTaChiTiet = moTaChiTiet;
    }
}
