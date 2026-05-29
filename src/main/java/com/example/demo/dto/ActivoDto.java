package com.example.demo.dto;

public class ActivoDto {

    private String tit;
    private String tip;
    private String ser;
    private String est;
    private String respon;
    private String descrip;
    private String img;

    public ActivoDto() {}

    public String getTitulo() { return tit; }
    public void setTitulo(String titulo) { this.tit = titulo; }
    public String getTipo() { return tip; }
    public void setTipo(String tipo) { this.tip = tipo; }
    public String getSerie() { return ser; }
    public void setSerie(String serie) { this.ser = serie; }
    public String getEstado() { return est; }
    public void setEstado(String estado) { this.est = estado; }
    public String getResponsable() { return respon; }
    public void setResponsable(String responsable) { this.respon = responsable; }
    public String getDescripcion() { return descrip; }
    public void setDescripcion(String descripcion) { this.descrip = descripcion; }
    public String getImg() { return img; }
    public void setImg(String img) { this.img = img; }
}
