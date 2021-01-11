package com.phuoclong.yumo.Model;

public class Collection {
    private String Id_collection;
    private String Code_collection;
    private String name_collection;
    private int soLuong;
    private String avatar_collection;
    private boolean An;
    private String create_date;

    public String getId_collection() {
        return Id_collection;
    }

    public void setId_collection(String id_collection) {
        Id_collection = id_collection;
    }

    public String getCode_collection() {
        return Code_collection;
    }

    public void setCode_collection(String code_collection) {
        Code_collection = code_collection;
    }

    public String getName_collection() {
        return name_collection;
    }

    public void setName_collection(String name_collection) {
        this.name_collection = name_collection;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public String getAvatar_collection() {
        return avatar_collection;
    }

    public void setAvatar_collection(String avatar_collection) {
        this.avatar_collection = avatar_collection;
    }

    public boolean getAn() {
        return An;
    }

    public void setAn(boolean an) {
        An = an;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public Collection(String id_collection, String code_collection, String name_collection, int soLuong, String avatar_collection, boolean an, String create_date) {
        Id_collection = id_collection;
        Code_collection = code_collection;
        this.name_collection = name_collection;
        this.soLuong = soLuong;
        this.avatar_collection = avatar_collection;
        An = an;
        this.create_date = create_date;
    }
}
