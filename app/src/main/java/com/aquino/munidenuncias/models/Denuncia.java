package com.aquino.munidenuncias.models;

/**
 * Created by Alumno on 13/11/2017.
 */

public class Denuncia {

    private Integer id;
    private Integer usuarios_id;
    private String titulo;
    private String comentario;
    private double latitud;
    private double longitud;
    private String imagen;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUsuarios_id() {
        return usuarios_id;
    }

    public void setUsuarios_id(Integer usuarios_id) {
        this.usuarios_id = usuarios_id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    @Override
    public String toString() {
        return "Denuncia{" +
                "id=" + id +
                ", usuarios_id=" + usuarios_id +
                ", titulo='" + titulo + '\'' +
                ", comentario='" + comentario + '\'' +
                ", latitud=" + latitud +
                ", longitud=" + longitud +
                ", imagen='" + imagen + '\'' +
                '}';
    }
}
