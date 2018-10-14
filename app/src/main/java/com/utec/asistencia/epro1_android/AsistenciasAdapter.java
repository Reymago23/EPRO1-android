package com.utec.asistencia.epro1_android;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.utec.asistencia.epro1_android.model.Asistencia;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;


// Create the basic adapter extending from RecyclerView.Adapter
// Note that we specify the custom ViewHolder which gives us access to our views
public class AsistenciasAdapter extends RecyclerView.Adapter<AsistenciasAdapter.ViewHolder> {


    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder {

        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView tvAsignatura;
        public TextView tvSeccion;
        public TextView tvAula;
        public TextView tvFecha;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {

            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            tvAsignatura = itemView.findViewById(R.id.a_tv_asignatura);
            tvSeccion = itemView.findViewById(R.id.a_tv_seccion);
            tvAula = itemView.findViewById(R.id.a_tv_aula);
            tvFecha = itemView.findViewById(R.id.a_tv_fecha);

        }
    }


    // Store a member variable for the Asistencias
    private List<Asistencia> mAsistencia;

    // Pass in the contact array into the constructor
    public AsistenciasAdapter(List<Asistencia> asistencia) {
        mAsistencia = asistencia;
    }


    // Usually involves inflating a layout from XML and returning the holder
    @NonNull
    @Override
    public AsistenciasAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View asistenciasView = inflater.inflate(R.layout.asistencias_item, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(asistenciasView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AsistenciasAdapter.ViewHolder viewHolder, int position) {


        Asistencia asistencia = mAsistencia.get(position);

        // Set item views based on your views and data model
        TextView tvAsignatura = viewHolder.tvAsignatura;
        tvAsignatura.setText(asistencia.getAsignatura().toLowerCase());

        TextView tvSeccion = viewHolder.tvSeccion;
        tvSeccion.setText(asistencia.getSeccion());

        TextView tvAula = viewHolder.tvAula;
        tvAula.setText(asistencia.getAula());

        Locale locale = new Locale("es", "sv");
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss aaa", locale);

        String date = sdf.format(asistencia.getFechaHora());


        TextView tvFecha = viewHolder.tvFecha;
        tvFecha.setText(date);

    }

    @Override
    public int getItemCount() {

        return mAsistencia.size();
    }


}
