package com.mobmasterp.bienestarapp.Modelos;

import java.util.List;

public class PrestamoModel {
    private List<String> implementos;
    private List<Integer> cantidad_implementos;
    private String usuario;
    private String fecha_inicio;
    private String fecha_fin;
    private String estado;

    public List<String> getImplementos() {
        return implementos;
    }

    public void setImplementos(List<String> implementos) {
        this.implementos = implementos;
    }

    public List<Integer> getCantidad_implementos() {
        return cantidad_implementos;
    }

    public void setCantidad_implementos(List<Integer> cantidad_implementos) {
        this.cantidad_implementos = cantidad_implementos;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getFecha_inicio() {
        return fecha_inicio;
    }

    public void setFecha_inicio(String fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public String getFecha_fin() {
        return fecha_fin;
    }

    public void setFecha_fin(String fecha_fin) {
        this.fecha_fin = fecha_fin;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }


}