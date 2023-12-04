package com.mobmasterp.bienestarapp.RV;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobmasterp.bienestarapp.Modelos.ImplementosModel;
import com.mobmasterp.bienestarapp.Modelos.Prestamo.PrestamoGetModel;
import com.mobmasterp.bienestarapp.R;
import com.mobmasterp.bienestarapp.StringValues;

import java.util.List;

public class PrestamosRV extends RecyclerView.Adapter<PrestamosRV.PrestamosViewHolder>{

    Context context;
    List<PrestamoGetModel> prestamosGetModel;
    itemOnClick onClick;

    StringValues stringValues = new StringValues();
    public PrestamosRV(Context context, List<PrestamoGetModel> prestamosGetModel, itemOnClick onClick) {
        this.context = context;
        this.prestamosGetModel = prestamosGetModel;
        this.onClick = onClick;
    }

    @NonNull
    @Override
    public PrestamosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fila_historial_prestamo, parent, false);
        return new PrestamosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PrestamosViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.estado.setText(this.prestamosGetModel.get(position).getEstado().getNombre());
        if(this.prestamosGetModel.get(position).getEstado().get_id().equals(stringValues.ID_ESTADO_PRESTAMO_PENDIENTE)){
            holder.estado.setTextColor(0xFFFFD700);
        }else if(this.prestamosGetModel.get(position).getEstado().get_id().equals(stringValues.ID_ESTADO_PRESTAMO_COMPLETADO)){
            holder.estado.setTextColor(0xFF006400);
        }else if(this.prestamosGetModel.get(position).getEstado().get_id().equals(stringValues.ID_ESTADO_PRESTAMO_APROBADO)){
            holder.estado.setTextColor(0xFF000080);
        }else{
            holder.estado.setTextColor(0xFF8B0000);
        }
        holder.fecha_inicio.setText(this.prestamosGetModel.get(position).getFecha_inicio());
        holder.fecha_fin.setText(this.prestamosGetModel.get(position).getFecha_fin());
        holder.lLayout.removeAllViews();
        int index = 0;
        for (ImplementosModel implemento : this.prestamosGetModel.get(position).getImplementos()) {
            View v = LayoutInflater.from(context).inflate(R.layout.data_implemento, null);
            TextView txtNombre = (TextView)v.findViewById(R.id.txtNombre);
            TextView txtMarca = (TextView)v.findViewById(R.id.txtMarca);
            TextView txtCodigo = (TextView)v.findViewById(R.id.txtCodigo);
            TextView txtDesc = (TextView)v.findViewById(R.id.txtDesc);
            TextView txtCategoria = (TextView)v.findViewById(R.id.txtCategoria);
            TextView txtCantidad = (TextView)v.findViewById(R.id.txtCantidad);
            txtNombre.setText(implemento.getNombre());
            txtMarca.setText(implemento.getMarca().getNombre());
            txtCodigo.setText(implemento.getCodigo());
            String Desc = "Peso: " + implemento.getDescripcion().getPeso() + " - "
                    + "Tamaño: " + implemento.getDescripcion().getTamano() + " - "
                    + "Material: " + implemento.getDescripcion().getMaterial() + " - "
                    + "Color: " + implemento.getDescripcion().getColor() + " - "
                    + "Detalles: " + implemento.getDescripcion().getDetalles();
            txtDesc.setText(Desc);
            String Cat = "";
            for (ImplementosModel.CategoriaImplementoModel categoriaImplementoModel : implemento.getCategoria()) {
                Cat += categoriaImplementoModel.getNombre() + ", ";
            }
            try{Cat = Cat.substring(0, Cat.length()-2);}catch (Exception e){}
            txtCategoria.setText(Cat);
            txtCantidad.setText(this.prestamosGetModel.get(position).getCantidad_implementos().get(index) + "");
            index++;
            holder.lLayout.addView(v);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.click(prestamosGetModel.get(position), position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.prestamosGetModel.size();
    }

    public class PrestamosViewHolder extends RecyclerView.ViewHolder{
        TextView estado, fecha_inicio, fecha_fin;
        LinearLayout lLayout;

        public PrestamosViewHolder(@NonNull View itemView) {
            super(itemView);
            estado = (TextView)itemView.findViewById(R.id.estado);
            fecha_inicio = (TextView)itemView.findViewById(R.id.fecha_inicio);
            fecha_fin = (TextView)itemView.findViewById(R.id.fecha_fin);
            lLayout = (LinearLayout)itemView.findViewById(R.id.lLayout);
        }
    }

    public interface itemOnClick{
        void click(PrestamoGetModel pgm, int pos);
    }
}
