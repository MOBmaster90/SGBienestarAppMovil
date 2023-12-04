package com.mobmasterp.bienestarapp.Modelos;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class AuthRequest implements Parcelable {
    private String token;

    protected AuthRequest(Parcel in) {
        token = in.readString();
    }

    public static final Creator<AuthRequest> CREATOR = new Creator<AuthRequest>() {
        @Override
        public AuthRequest createFromParcel(Parcel in) {
            return new AuthRequest(in);
        }

        @Override
        public AuthRequest[] newArray(int size) {
            return new AuthRequest[size];
        }
    };

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(token);
    }

    public class TokenModel implements Parcelable{
        private String id;
        private String nombre;
        private String apellidos;
        private String correo_inst;
        private String rol;
        private int privilegio;
        private long fecha;
        private String n_doc;
        private boolean sanciones;
        private long iat;

        protected TokenModel(Parcel in) {
            id = in.readString();
            nombre = in.readString();
            apellidos = in.readString();
            correo_inst = in.readString();
            rol = in.readString();
            privilegio = in.readInt();
            fecha = in.readLong();
            n_doc = in.readString();
            sanciones = in.readByte() != 0;
            iat = in.readLong();
        }

        public final Creator<TokenModel> CREATOR = new Creator<TokenModel>() {
            @Override
            public TokenModel createFromParcel(Parcel in) {
                return new TokenModel(in);
            }

            @Override
            public TokenModel[] newArray(int size) {
                return new TokenModel[size];
            }
        };

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getApellidos() {
            return apellidos;
        }

        public void setApellidos(String apellidos) {
            this.apellidos = apellidos;
        }

        public String getCorreo_inst() {
            return correo_inst;
        }

        public void setCorreo_inst(String correo_inst) {
            this.correo_inst = correo_inst;
        }

        public String getRol() {
            return rol;
        }

        public void setRol(String rol) {
            this.rol = rol;
        }

        public int getPrivilegio() {
            return privilegio;
        }

        public void setPrivilegio(int privilegio) {
            this.privilegio = privilegio;
        }

        public long getFecha() {
            return fecha;
        }

        public void setFecha(long fecha) {
            this.fecha = fecha;
        }

        public String getN_doc() {
            return n_doc;
        }

        public void setN_doc(String n_doc) {
            this.n_doc = n_doc;
        }

        public boolean isSanciones() {
            return sanciones;
        }

        public void setSanciones(boolean sanciones) {
            this.sanciones = sanciones;
        }

        public long getIat() {
            return iat;
        }

        public void setIat(long iat) {
            this.iat = iat;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(@NonNull Parcel dest, int flags) {
            dest.writeString(id);
            dest.writeString(nombre);
            dest.writeString(apellidos);
            dest.writeString(correo_inst);
            dest.writeString(rol);
            dest.writeInt(privilegio);
            dest.writeLong(fecha);
            dest.writeString(n_doc);
            dest.writeByte((byte) (sanciones ? 1 : 0));
            dest.writeLong(iat);
        }
    }
}
