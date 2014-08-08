package com.example.matt.tarea1;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

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

         viewHolder.txtNombreTarea = txtNombreTarea;
         viewHolder.txtFechaTarea = txtFechaTarea;

         //toma el valor de la lista
         view.setTag(viewHolder);

         Log.d("AdapterCustom->getView", "Inflando: " + tarea.getNombre());
      } else {
         Log.d("AdapterCustom->getView", "Optimizado: " + tarea.getNombre());
      }

      ViewHolder viewHolder = (ViewHolder) view.getTag();
      viewHolder.txtNombreTarea.setText(tarea.getNombre());
      viewHolder.txtFechaTarea.setText(tarea.getFecha());

      return view;
   }

   /**
    * Clase estática
    */
   static class ViewHolder {
      public TextView txtNombreTarea;
      public TextView txtFechaTarea;
   }
}
