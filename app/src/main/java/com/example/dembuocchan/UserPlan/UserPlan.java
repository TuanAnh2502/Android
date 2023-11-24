package com.example.dembuocchan.UserPlan;

public class UserPlan {
    private String ngaybatdau;
    private String thoigianthongbao;
    private String ngayluyentap;
    private String capdo;

    public UserPlan(String ngaybatdau, String thoigianthongbao, String ngayluyentap, String capdo) {
        this.ngaybatdau = ngaybatdau;
        this.thoigianthongbao = thoigianthongbao;
        this.ngayluyentap = ngayluyentap;
        this.capdo = capdo;
    }

    public String getNgaybatdau() {
        return ngaybatdau;
    }

    public void setNgaybatdau(String ngaybatdau) {
        this.ngaybatdau = ngaybatdau;
    }

    public String getThoigianthongbao() {
        return thoigianthongbao;
    }

    public void setThoigianthongbao(String thoigianthongbao) {
        this.thoigianthongbao = thoigianthongbao;
    }

    public String getNgayluyentap() {
        return ngayluyentap;
    }

    public void setNgayluyentap(String ngayluyentap) {
        this.ngayluyentap = ngayluyentap;
    }

    public String getCapdo() {
        return capdo;
    }

    public void setCapdo(String capdo) {
        this.capdo = capdo;
    }
}
