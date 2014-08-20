package com.example.matt.tarea1;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * Created by Student on 09/08/2014.
 */
public class MyTimePicker extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //Para poner hora actual
        Calendar calendar = Calendar.getInstance();
        int hora = calendar.get(Calendar.HOUR_OF_DAY);
        int minuto = calendar.get(Calendar.MINUTE);

        //android.text.format.DateFormat.is24HourFormat(getActivity()) -> devuelve booleano que dice que utiliza o no formato 24hs
        return new TimePickerDialog(getActivity(), this, hora, minuto, android.text.format.DateFormat.is24HourFormat(getActivity()));
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        //guardo el timePicker en el textView
        TextView txtHoraTareaNew = (TextView) getActivity().findViewById(R.id.txtHoraTareaNew);
        txtHoraTareaNew.setText(hourOfDay + ":" + minute);
    }
}
