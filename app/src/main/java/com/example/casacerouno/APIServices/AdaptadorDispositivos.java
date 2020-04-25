package com.example.casacerouno.APIServices;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.casacerouno.Modelos.Dispositivos;
import com.example.casacerouno.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdaptadorDispositivos  extends RecyclerView.Adapter<AdaptadorDispositivos.ViewHolder> {

    private List<Dispositivos> dispositivosList;
    private int layout;
    private Dispositivos dispositivo;
    private OnItemClickListener itemClickListener;


    private Context context;

    public AdaptadorDispositivos(List<Dispositivos> dispositivosList, int layout, OnItemClickListener itemClickListener) {
        this.dispositivosList = dispositivosList;
        this.layout = layout;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(layout,parent,false);
        context = parent.getContext();
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(dispositivosList.get(position),itemClickListener);
    }

    @Override
    public int getItemCount() {
        return dispositivosList.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder{
        private TextView textViewName;
        private ImageView imageViewPoster;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewDispositivo);
            imageViewPoster = itemView.findViewById(R.id.imageViewDispositivo);
        }

        public void bind(final Dispositivos dispositivo, final OnItemClickListener listener){
            String tipoDispositivo = dispositivo.getTipo();
            String statusDispositivo = dispositivo.getStatus();
            textViewName.setText(dispositivo.getNombre());
            Picasso.with(context).load(poster(tipoDispositivo, statusDispositivo)).fit().into(imageViewPoster);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(dispositivo, getAdapterPosition());
                }
            });
        }

    }
    private int poster(String tipoDispositivo, String status) {
        switch (tipoDispositivo) {
            case "GP":
                if(status.equals("off"))
                    return R.drawable.foco_apagado;
                else
                    return R.drawable.foco;
            case "TV":
                return R.drawable.televisor;
            case "TM":
                return R.drawable.termostato;
            case "PR":
                return R.drawable.persiana_abierta;
            default:
                return R.drawable.sin_icono;
        }
    }
    public interface OnItemClickListener{
        void onItemClick(Dispositivos dispositivos, int posicion);
    }

}
