package com.example.casacerouno.APIServices;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.casacerouno.Modelos.Habitacion;
import com.example.casacerouno.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Adaptador extends RecyclerView.Adapter<Adaptador.ViewHolder> {

    private List<Habitacion> habitacionList;
    private int layout;
    private OnItemClickListener itemClickListener;

    private Context context;

    public Adaptador(List<Habitacion> habitacionList, int layout, OnItemClickListener listener) {
        this.habitacionList = habitacionList;
        this.layout = layout;
        this.itemClickListener = listener;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflamos el layout y se lo pasamos al constructor del ViewHolder, donde manejaremos
        // toda la lógica como extraer los datos, referencias...
        View v = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        context = parent.getContext();
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }


    public void onBindViewHolder(ViewHolder holder, int position) {
        // Llamamos al método Bind del ViewHolder pasándole objeto y listener
        holder.bind(habitacionList.get(position), (OnItemClickListener) itemClickListener);
    }


    public int getItemCount() {
        return habitacionList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        // Elementos UI a rellenar
        public TextView textViewName;
        public ImageView imageViewPoster;

        public ViewHolder(View itemView) {
            // Recibe la View completa. La pasa al constructor padre y enlazamos referencias UI
            // con nuestras propiedades ViewHolder declaradas justo arriba.
            super(itemView);
            textViewName = (TextView) itemView.findViewById(R.id.textViewTitle);
            imageViewPoster = (ImageView) itemView.findViewById(R.id.imageViewPoster);
        }

        public void bind(final Habitacion habitacion, final OnItemClickListener listener) {
            // Procesamos los datos a renderizar
            String nombre = habitacion.getNombre();
            textViewName.setText(nombre);
            Picasso.with(context).load(poster(nombre)).fit().into(imageViewPoster);

            // Definimos que por cada elemento de nuestro recycler view, tenemos un click listener
            // que se comporta de la siguiente manera...
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(habitacion, getAdapterPosition());
                }
            });
        }
    }

    public int poster(String nombreHabitacion) {
        switch (nombreHabitacion) {
            case "living":
                return R.drawable.living;
            case "comedor":
                return R.drawable.comedor;
            case "cocina":
                return R.drawable.cocina;
            case "dormitorio":
                return R.drawable.dormitorio;
            case "patio":
                return R.drawable.patio;
            case "bano":
                return R.drawable.bano;
            case "entrada":
                return R.drawable.entrada;
            default:
                return R.drawable.sin_icono;
        }
    }

    // Declaramos nuestra interfaz con el/los método/s a implementar
    public interface OnItemClickListener {
        void onItemClick(Habitacion habitacion, int position);
    }
}
