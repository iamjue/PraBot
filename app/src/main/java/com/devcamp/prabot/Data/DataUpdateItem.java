package com.devcamp.prabot.Data;

public class DataUpdateItem {
    String id_sandi, huruf, derajat_lengan_x, derajat_lengan_y, gambar;

    public DataUpdateItem(String id_sandi, String huruf, String derajat_lengan_x, String derajat_lengan_y, String gambar) {
        this.id_sandi = id_sandi;
        this.huruf = huruf;
        this.derajat_lengan_x = derajat_lengan_x;
        this.derajat_lengan_y = derajat_lengan_y;
        this.gambar = gambar;
    }

    public String getId_sandi() {
        return id_sandi;
    }

    public void setId_sandi(String id_sandi) {
        this.id_sandi = id_sandi;
    }

    public String getHuruf() {
        return huruf;
    }

    public void setHuruf(String huruf) {
        this.huruf = huruf;
    }

    public String getDerajat_lengan_x() {
        return derajat_lengan_x;
    }

    public void setDerajat_lengan_x(String derajat_lengan_x) {
        this.derajat_lengan_x = derajat_lengan_x;
    }

    public String getDerajat_lengan_y() {
        return derajat_lengan_y;
    }

    public void setDerajat_lengan_y(String derajat_lengan_y) {
        this.derajat_lengan_y = derajat_lengan_y;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }
}
