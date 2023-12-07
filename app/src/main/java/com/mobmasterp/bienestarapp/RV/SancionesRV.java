package com.mobmasterp.bienestarapp.RV;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobmasterp.bienestarapp.Modelos.SancionesModel;
import com.mobmasterp.bienestarapp.R;

import java.util.List;

public class SancionesRV extends RecyclerView.Adapter<SancionesRV.SancionesViewHolder> {

    Context context;
    List<SancionesModel> sancionesModelList;
    OnClick onClick;
    public SancionesRV(Context context, List<SancionesModel> sancionesModelList, OnClick onClick) {
        this.context = context;
        this.sancionesModelList = sancionesModelList;
        this.onClick = onClick;
    }

    @NonNull
    @Override
    public SancionesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fila_sanciones, parent, false);
        return new SancionesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SancionesViewHolder holder, int position) {
        try{
            holder.txtId.setText(sancionesModelList.get(position).get_id());
            holder.txtnombre.setText(sancionesModelList.get(position).getUsuario().getNombres() + " " + sancionesModelList.get(position).getUsuario().getApellidos());
            holder.txtdoc.setText(sancionesModelList.get(position).getUsuario().getTipo_doc() + " " + sancionesModelList.get(position).getUsuario().getN_doc());
            holder.txtFicha.setText(sancionesModelList.get(position).getUsuario().getFicha().getCodigo());
            holder.txtPrograma.setText(sancionesModelList.get(position).getUsuario().getFicha().getPrograma().getNombre());
            if(sancionesModelList.get(position).isEstado()){
                holder.txtEstado.setText("Activo");
            }else{
                holder.txtEstado.setText("Inactivo");
            }
            holder.txtNduracion.setText(sancionesModelList.get(position).getDuracion() + "");
            holder.txtDescripcion.setText(sancionesModelList.get(position).getDescripcion());
        }catch (Exception e){

        }
    }

    @Override
    public int getItemCount() {
        return sancionesModelList.size();
    }

    public class SancionesViewHolder extends RecyclerView.ViewHolder{

        TextView txtId, txtnombre, txtdoc, txtFicha, txtPrograma, txtEstado, txtNduracion, txtDescripcion;
        public SancionesViewHolder(@NonNull View itemView) {
            super(itemView);
            txtId = (TextView)itemView.findViewById(R.id.txtId);
            txtnombre = (TextView)itemView.findViewById(R.id.txtnombre);
            txtdoc = (TextView)itemView.findViewById(R.id.txtdoc);
            txtFicha = (TextView)itemView.findViewById(R.id.txtFicha);
            txtPrograma = (TextView)itemView.findViewById(R.id.txtPrograma);
            txtEstado = (TextView)itemView.findViewById(R.id.txtEstado);
            txtNduracion = (TextView)itemView.findViewById(R.id.txtNduracion);
            txtDescripcion = (TextView)itemView.findViewById(R.id.txtDescripcion);
        }
    }

    public interface OnClick{
        void click(SancionesModel sancionesModel, int pos);
    }
}
