package com.example.demo.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "activo")
public class Activo {

    @Id
    private String id;
    private String titulo;
    private String tipo;
    private String serie;
    private String estado;
    private String responsable;
    private String descripcion;
    private String img;

    public Activo() {}

    public Activo(String titulo, String tipo, String serie, String estado, String responsable, String descripcion, String img) {
        this.titulo = titulo;
        this.tipo = tipo;
        this.serie = serie;
        this.estado = estado;
        this.responsable = responsable;
        this.descripcion = descripcion;
        this.img = img;
    }

    public String getId() { return id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public String getSerie() { return serie; }
    public void setSerie(String serie) { this.serie = serie; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public String getResponsable() { return responsable; }
    public void setResponsable(String responsable) { this.responsable = responsable; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public String getImg() { return img; }
    public void setImg(String img) { this.img = img; }
}
