package com.mobmasterp.bienestarapp.RV;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobmasterp.bienestarapp.Modelos.ImplementosModel;
import com.mobmasterp.bienestarapp.Modelos.Prestamo.PrestamoGetModel;
import com.mobmasterp.bienestarapp.R;
import com.mobmasterp.bienestarapp.StringValues;

import java.util.List;

public class AprobarPrestamosRV extends RecyclerView.Adapter<AprobarPrestamosRV.AprobarPrestamosViewHolder>{

    Context context;
    List<PrestamoGetModel> prestamoGetModels;
    onClick onclick;
    StringValues stringValues = new StringValues();
    public AprobarPrestamosRV(Context context, List<PrestamoGetModel> prestamoGetModels, onClick onclick) {
        this.context = context;
        this.prestamoGetModels = prestamoGetModels;
        this.onclick = onclick;
    }

    @NonNull
    @Override
    public AprobarPrestamosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fila_prestamos, parent, false);
        return new AprobarPrestamosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AprobarPrestamosViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.txtId.setText(this.prestamoGetModels.get(position).get_id());
        holder.txtEstado.setText(this.prestamoGetModels.get(position).getEstado().getNombre());
        if(this.prestamoGetModels.get(position).getEstado().get_id().equals(stringValues.ID_ESTADO_PRESTAMO_PENDIENTE)){
            holder.txtEstado.setTextColor(0xFFFFD700);
            holder.img2.setEnabled(false);
        }else if(this.prestamoGetModels.get(position).getEstado().get_id().equals(stringValues.ID_ESTADO_PRESTAMO_COMPLETADO)){
            holder.txtEstado.setTextColor(0xFF006400);
            holder.img1.setEnabled(false);
            holder.img2.setEnabled(false);
            holder.img3.setEnabled(false);
        }else if(this.prestamoGetModels.get(position).getEstado().get_id().equals(stringValues.ID_ESTADO_PRESTAMO_APROBADO)){
            holder.txtEstado.setTextColor(0xFF000080);
            holder.img1.setEnabled(false);
            holder.img3.setEnabled(false);
        }else{
            holder.txtEstado.setTextColor(0xFF8B0000);
            holder.img1.setEnabled(false);
            holder.img2.setEnabled(false);
            holder.img3.setEnabled(false);
        }
        holder.txtFechaSol.setText(this.prestamoGetModels.get(position).getCreatedAt());
        holder.lLayout.removeAllViews();
        int index = 0;
        for (ImplementosModel implemento : this.prestamoGetModels.get(position).getImplementos()) {
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
            txtCantidad.setText(this.prestamoGetModels.get(position).getCantidad_implementos().get(index) + "");
            index++;
            holder.lLayout.addView(v);
        }
        holder.img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onclick.click(prestamoGetModels.get(position), position, 1);
            }
        });
        holder.img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onclick.click(prestamoGetModels.get(position), position, 2);
            }
        });
        holder.img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onclick.click(prestamoGetModels.get(position), position, 3);
            }
        });
    }

    @Override
    public int getItemCount() {
        return prestamoGetModels.size();
    }

    public class AprobarPrestamosViewHolder extends RecyclerView.ViewHolder{

        TextView txtId, txtEstado, txtFechaSol;
        LinearLayout lLayout;
        ImageView img1, img2, img3;

        public AprobarPrestamosViewHolder(@NonNull View itemView) {
            super(itemView);
            txtId = (TextView)itemView.findViewById(R.id.txtId);
            txtEstado = (TextView)itemView.findViewById(R.id.txtEstado);
            txtFechaSol = (TextView)itemView.findViewById(R.id.txtFechaSol);
            lLayout = (LinearLayout)itemView.findViewById(R.id.lLayout);
            img1 = (ImageView)itemView.findViewById(R.id.img1);
            img2 = (ImageView)itemView.findViewById(R.id.img2);
            img3 = (ImageView)itemView.findViewById(R.id.img3);
        }
    }

    public interface onClick{
        void click(PrestamoGetModel pgm, int pos, int accion);
    }
}
