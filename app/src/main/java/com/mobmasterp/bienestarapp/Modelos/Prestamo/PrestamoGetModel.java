package com.mobmasterp.bienestarapp.Modelos.Prestamo;

import com.mobmasterp.bienestarapp.Modelos.ImplementosModel;
import com.mobmasterp.bienestarapp.Modelos.UsuarioModel;

import java.util.List;

public class PrestamoGetModel {
    private String _id;
    private List<ImplementosModel> implementos;
    private List<Integer> cantidad_implementos;
    private UsuarioModel usuario;
    private String fecha_inicio;
    private String fecha_fin;
    private EstadoPrestamo estado;
    private String createdAt;
    private String updatedAt;
    private int __v;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public List<ImplementosModel> getImplementos() {
        return implementos;
    }

    public void setImplementos(List<ImplementosModel> implementos) {
        this.implementos = implementos;
    }

    public List<Integer> getCantidad_implementos() {
        return cantidad_implementos;
    }

    public void setCantidad_implementos(List<Integer> cantidad_implementos) {
        this.cantidad_implementos = cantidad_implementos;
    }

    public UsuarioModel getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioModel usuario) {
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

    public EstadoPrestamo getEstado() {
        return estado;
    }

    public void setEstado(EstadoPrestamo estado) {
        this.estado = estado;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int get__v() {
        return __v;
    }

    public void set__v(int __v) {
        this.__v = __v;
    }

    public class EstadoPrestamo{
        private String _id;
        private String nombre;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }
    }
}
