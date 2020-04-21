package com.example.casacerouno.APIServices;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.casacerouno.Modelos.Cuarto;
import com.example.casacerouno.Modelos.Habitacion;
import com.example.casacerouno.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Adaptador extends RecyclerView.Adapter <Adaptador.ViewHolder>{

    private List<Habitacion> habitacionList;
    private int layout;
    private OnItemClickListener itemClickListener;

    private Context context;

    public Adaptador(List<Habitacion> habitacionList, int layout, OnItemClickListener listener) {
        this.habitacionList = habitacionList;
        this.layout = layout;
        this.itemClickListener = listener;
    }

    public Adaptador() {
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
            textViewName.setText(habitacion.getNombre());
            Picasso.with(context).load(poster(habitacion.getNombre())).fit().into(imageViewPoster);
            // imageViewPoster.setImageResource(movie.getPoster());
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

    public int poster(String nombreHabitacion){
        int imagen;
        switch (nombreHabitacion){
            case "living":
                imagen =  R.drawable.living;
            case "comedor":
                imagen =  R.drawable.comedor;
            case "cocina":
                imagen =  R.drawable.cocina;
            case "dormitorio":
                imagen = R.drawable.dormitorio;
            case "patio":
                imagen = R.drawable.patio;
            case "bano":
                imagen = R.drawable.bano;
            case "entrada":
                imagen = R.drawable.entrada;
            default:
                imagen = R.drawable.sin_icono;
        } return imagen;
    }

    // Declaramos nuestra interfaz con el/los método/s a implementar
    public interface OnItemClickListener {
        void onItemClick(Habitacion habitacion, int position);
    }
}
