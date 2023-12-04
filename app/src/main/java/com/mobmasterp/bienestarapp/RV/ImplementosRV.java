package com.mobmasterp.bienestarapp.RV;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobmasterp.bienestarapp.Modelos.ImplementosModel;
import com.mobmasterp.bienestarapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ImplementosRV extends RecyclerView.Adapter<ImplementosRV.ImplementosViewHolder> {


    Context context;
    List<ImplementosModel> implementosModels;
    onItemClick itemClick;

    public ImplementosRV(Context context, List<ImplementosModel> implementosModel, onItemClick itemClick) {
        this.context = context;
        this.implementosModels = implementosModel;
        this.itemClick = itemClick;
    }

    @NonNull
    @Override
    public ImplementosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fila_prestamo_implemento, parent, false);
        return new ImplementosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImplementosViewHolder holder, @SuppressLint("RecyclerView") int position) {
        try {Picasso.get().load(this.implementosModels.get(position).getImg()).into(holder.img);}catch (Exception e){}
        holder.txtNombre.setText(this.implementosModels.get(position).getNombre());
        holder.txtMarca.setText(this.implementosModels.get(position).getMarca().getNombre());
        holder.txtCodigo.setText(this.implementosModels.get(position).getCodigo());
        String Desc = "Peso: " + this.implementosModels.get(position).getDescripcion().getPeso() + " - "
                + "Tamaño: " + this.implementosModels.get(position).getDescripcion().getTamano() + " - "
                + "Material: " + this.implementosModels.get(position).getDescripcion().getMaterial() + " - "
                + "Color: " + this.implementosModels.get(position).getDescripcion().getColor() + " - "
                + "Detalles: " + this.implementosModels.get(position).getDescripcion().getDetalles();
        holder.txtDesc.setText(Desc);
        String Cat = "";
        for (ImplementosModel.CategoriaImplementoModel categoriaImplementoModel : this.implementosModels.get(position).getCategoria()) {
            Cat += categoriaImplementoModel.getNombre() + ", ";
        }
        try{Cat = Cat.substring(0, Cat.length()-2);}catch (Exception e){}
        holder.txtCategoria.setText(Cat);
        List<String> cantidad = new ArrayList<>();
        for (int i=1; i<=this.implementosModels.get(position).getCantidad_disponible(); i++) {
            cantidad.add("" + i);
        }
        holder.spCant.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, cantidad));
        if(this.implementosModels.get(position).getCantidad_seleccionada() >= 0){
            holder.spCant.setSelection(this.implementosModels.get(position).getCantidad_seleccionada());
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClick.onClick(implementosModels.get(position), holder.spCant.getSelectedItemPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.implementosModels.size();
    }

    static class ImplementosViewHolder extends RecyclerView.ViewHolder{
        ImageView img;
        TextView txtNombre;
        TextView txtMarca;
        TextView txtCodigo;
        TextView txtDesc;
        TextView txtCategoria;
        Spinner spCant;

        public ImplementosViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            txtNombre = itemView.findViewById(R.id.txtNombre);
            txtMarca = itemView.findViewById(R.id.txtMarca);
            txtCodigo = itemView.findViewById(R.id.txtCodigo);
            txtDesc = itemView.findViewById(R.id.txtDesc);
            txtCategoria = itemView.findViewById(R.id.txtCategoria);
            spCant = itemView.findViewById(R.id.spCant);
        }
    }

    public interface onItemClick{
        void onClick(ImplementosModel im, int cantidad);
    }
}
