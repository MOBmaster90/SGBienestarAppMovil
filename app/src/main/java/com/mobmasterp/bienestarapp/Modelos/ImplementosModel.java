package com.mobmasterp.bienestarapp.Modelos;

import java.util.List;

public class ImplementosModel {
    private String _id;
    private String codigo;
    private String nombre;
    private MarcaImplementoModel marca;
    private DescripcionImplementoModel descripcion;
    private List<CategoriaImplementoModel> categoria;
    private int cantidad;
    private String img;
    private List<EstadoImplementoModel> estado;
    private String createdAt;
    private String updatedAt;
    private int cantidad_prestados;
    private int cantidad_disponible;
    private int cantidad_seleccionada;

    public int getCantidad_seleccionada() {
        return cantidad_seleccionada;
    }

    public void setCantidad_seleccionada(int cantidad_seleccionada) {
        this.cantidad_seleccionada = cantidad_seleccionada;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public MarcaImplementoModel getMarca() {
        return marca;
    }

    public void setMarca(MarcaImplementoModel marca) {
        this.marca = marca;
    }

    public DescripcionImplementoModel getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(DescripcionImplementoModel descripcion) {
        this.descripcion = descripcion;
    }

    public List<CategoriaImplementoModel> getCategoria() {
        return categoria;
    }

    public void setCategoria(List<CategoriaImplementoModel> categoria) {
        this.categoria = categoria;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public List<EstadoImplementoModel> getEstado() {
        return estado;
    }

    public void setEstado(List<EstadoImplementoModel> estado) {
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

    public int getCantidad_prestados() {
        return cantidad_prestados;
    }

    public void setCantidad_prestados(int cantidad_prestados) {
        this.cantidad_prestados = cantidad_prestados;
    }

    public int getCantidad_disponible() {
        return cantidad_disponible;
    }

    public void setCantidad_disponible(int cantidad_disponible) {
        this.cantidad_disponible = cantidad_disponible;
    }

    public class MarcaImplementoModel{
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

    public class DescripcionImplementoModel{
        private String peso;
        private String color;
        private String material;
        private String detalles;
        private String tamano;

        public String getPeso() {
            return peso;
        }

        public void setPeso(String peso) {
            this.peso = peso;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String getMaterial() {
            return material;
        }

        public void setMaterial(String material) {
            this.material = material;
        }

        public String getDetalles() {
            return detalles;
        }

        public void setDetalles(String detalles) {
            this.detalles = detalles;
        }

        public String getTamano() {
            return tamano;
        }

        public void setTamano(String tamano) {
            this.tamano = tamano;
        }
    }

    public class CategoriaImplementoModel{
        private String _id;
        private String nombre;
        private String img;

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

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }
    }

    public class EstadoImplementoModel{
        private List<EstadoEstadoImplementoModel> estado;
        private int cantidad;
        private boolean apto;

        public List<EstadoEstadoImplementoModel> getEstado() {
            return estado;
        }

        public void setEstado(List<EstadoEstadoImplementoModel> estado) {
            this.estado = estado;
        }

        public int getCantidad() {
            return cantidad;
        }

        public void setCantidad(int cantidad) {
            this.cantidad = cantidad;
        }

        public boolean isApto() {
            return apto;
        }

        public void setApto(boolean apto) {
            this.apto = apto;
        }

        public class EstadoEstadoImplementoModel{
            private String _id;
            private String estado;

            public String get_id() {
                return _id;
            }

            public void set_id(String _id) {
                this._id = _id;
            }

            public String getEstado() {
                return estado;
            }

            public void setEstado(String estado) {
                this.estado = estado;
            }
        }
    }
}
