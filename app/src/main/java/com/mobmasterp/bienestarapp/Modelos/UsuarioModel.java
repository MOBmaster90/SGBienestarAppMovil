package com.mobmasterp.bienestarapp.Modelos;

public class UsuarioModel {
    private String _id;
    private String nombres;
    private String apellidos;
    private String tipo_doc;
    private String n_doc;
    private String telefono;
    private String correo_inst;
    private String correo_pers;
    private String nacimiento;
    private RolUsuarioModel rol;
    private String direccion;
    private String rh;
    private String eps;
    private boolean pps;
    private String token;
    private String activacion;
    private String codigo;
    private String createdAt;
    private String updatedAt;
    private int __v;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getTipo_doc() {
        return tipo_doc;
    }

    public void setTipo_doc(String tipo_doc) {
        this.tipo_doc = tipo_doc;
    }

    public String getN_doc() {
        return n_doc;
    }

    public void setN_doc(String n_doc) {
        this.n_doc = n_doc;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo_inst() {
        return correo_inst;
    }

    public void setCorreo_inst(String correo_inst) {
        this.correo_inst = correo_inst;
    }

    public String getCorreo_pers() {
        return correo_pers;
    }

    public void setCorreo_pers(String correo_pers) {
        this.correo_pers = correo_pers;
    }

    public String getNacimiento() {
        return nacimiento;
    }

    public void setNacimiento(String nacimiento) {
        this.nacimiento = nacimiento;
    }

    public RolUsuarioModel getRol() {
        return rol;
    }

    public void setRol(RolUsuarioModel rol) {
        this.rol = rol;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getRh() {
        return rh;
    }

    public void setRh(String rh) {
        this.rh = rh;
    }

    public String getEps() {
        return eps;
    }

    public void setEps(String eps) {
        this.eps = eps;
    }

    public boolean isPps() {
        return pps;
    }

    public void setPps(boolean pps) {
        this.pps = pps;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getActivacion() {
        return activacion;
    }

    public void setActivacion(String activacion) {
        this.activacion = activacion;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
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

    public class RolUsuarioModel{
        private String _id;
        private String nombre;
        private int privilegio;
        private int duracion_prestamo;

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

        public int getPrivilegio() {
            return privilegio;
        }

        public void setPrivilegio(int privilegio) {
            this.privilegio = privilegio;
        }

        public int getDuracion_prestamo() {
            return duracion_prestamo;
        }

        public void setDuracion_prestamo(int duracion_prestamo) {
            this.duracion_prestamo = duracion_prestamo;
        }
    }
}
