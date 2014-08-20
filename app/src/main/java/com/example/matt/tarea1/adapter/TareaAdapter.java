package com.example.matt.tarea1.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.matt.tarea1.R;
import com.example.matt.tarea1.domain.Tarea;

import java.util.List;

/**
 * Created by mfranci on 07/08/2014.
 */
public class TareaAdapter extends ArrayAdapter<Tarea> {
    private Context context;
    private List<Tarea> objects;

    public TareaAdapter(Context context, int resource, List<Tarea> objects) {
        super(context, resource, objects);
        this.context = context;
        this.objects = objects;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        Tarea tarea = objects.get(position);

        if (convertView == null) {
            //traigo la definición de layout customizado (imagen+textview)
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            //transformo el XML del layout a objetos.
            view = layoutInflater.inflate(R.layout.item_tarea, parent, false);

            ViewHolder viewHolder = new ViewHolder();

            TextView txtNombreTarea = (TextView) view.findViewById(R.id.txtNombreTarea);
            TextView txtFechaTarea = (TextView) view.findViewById(R.id.txtFechaTarea);
            TextView txtDescripcionTarea = (TextView) view.findViewById(R.id.txtDescripcionTarea);
            ImageView imgPrioridadTarea = (ImageView) view.findViewById(R.id.imgPrioridadTarea);

            viewHolder.txtNombreTarea = txtNombreTarea;
            viewHolder.txtFechaTarea = txtFechaTarea;
            viewHolder.txtDescripcionTarea = txtDescripcionTarea;
            viewHolder.imgPrioridadTarea = imgPrioridadTarea;

            //toma el valor de la lista
            view.setTag(viewHolder);

            Log.d("AdapterCustom->getView", "Inflando: " + tarea.getNombre());
        } else {
            Log.d("AdapterCustom->getView", "Optimizado: " + tarea.getNombre());
        }

        //asigno valores a los elementos.
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        viewHolder.txtNombreTarea.setText(tarea.getNombre());
        viewHolder.txtFechaTarea.setText(tarea.getFecha());
        viewHolder.txtDescripcionTarea.setText(tarea.getDescripcion(true));
        viewHolder.imgPrioridadTarea.setImageResource(tarea.getPrioridad());
        //TODO ver de hacer que se ponga de color rojo si está vencida la tarea.

        return view;
    }

    /**
     * Clase estática
     */
    static class ViewHolder {
        public TextView txtNombreTarea;
        public TextView txtFechaTarea;
        public TextView txtDescripcionTarea;
        public ImageView imgPrioridadTarea;
    }
}
