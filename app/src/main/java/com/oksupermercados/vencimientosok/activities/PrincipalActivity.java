package com.oksupermercados.vencimientosok.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.oksupermercados.vencimientosok.R;
import com.oksupermercados.vencimientosok.connections.DatabaseManager;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class PrincipalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        DatabaseManager.getConn(getApplicationContext());
    }

    public void showAddDialog(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Agregar registro");

        // Inflar el diseño personalizado para el cuadro de diálogo
        LayoutInflater inflater = getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.dialog_add, null);
        builder.setView(dialogLayout);

        final EditText code = dialogLayout.findViewById(R.id.busqueda);
        final EditText expDate = dialogLayout.findViewById(R.id.vencimiento);

        builder.setPositiveButton("Agregar", (dialog, which) -> {
            // Manejar el texto ingresado por el usuario
            String textoIngresado = code.getText().toString();
            // Haz algo con el texto ingresado
            Toast.makeText(this, textoIngresado, Toast.LENGTH_SHORT).show();
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        expDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                int lenText = expDate.getText().toString().replace("/","").length();
                if ((lenText == 3 || lenText == 5) && i2 == 1){
                    String segOne = expDate.getText().subSequence(0,expDate.getText().length()-1).toString();
                    String segTwo = String.valueOf(expDate.getText().charAt(expDate.getText().length()-1));
                    expDate.setText(segOne + "/" + segTwo);
                    expDate.setSelection(expDate.getText().length());
                }

                if ((lenText == 2 || lenText == 4) && i1 == 1){
                    String segOne = expDate.getText().subSequence(0,expDate.getText().length()-1).toString();
                    expDate.setText(segOne);
                    expDate.setSelection(expDate.getText().length());
                }

                if (lenText == 6 && i2 == 1){
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yy");

                    try {
                        LocalDate ld = LocalDate.parse(expDate.getText(),dtf);
                        Date dateValidate = Date.valueOf(ld.toString());
                        Toast.makeText(PrincipalActivity.this, dateValidate.toString(), Toast.LENGTH_SHORT).show();

                    } catch (Exception e) {
                        expDate.setBackgroundColor(Color.RED);
                        expDate.selectAll();
                        Toast.makeText(PrincipalActivity.this, "Fecha inválida.", Toast.LENGTH_SHORT).show();
                    }


                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        builder.show();
    }

    public void CalendarSelector(View view) {
        DatePickerDialog dialog = new DatePickerDialog(this, android.R.style.Theme_Material_Dialog, (datePicker, year, month, day) -> {
            //Todo your work here
            Toast.makeText(this, year + "/" + month + "/" + day, Toast.LENGTH_SHORT).show();
        }, 2023, 11, 23);

        //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        //dialog.getWindow().setGravity(Gravity.CENTER); // Cambia BOTTOM según tus necesidades
        //dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);

        //DatePicker datePicker = dialog.getDatePicker();
        //datePicker.setCalendarViewShown(false); // Desactivar el calendario para mostrar solo los controles de día, mes y año
        //datePicker.setSpinnersShown(true); // Mostrar los controles de día, mes y año

        dialog.show();
    }

}