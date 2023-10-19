package com.oksupermercados.vencimientosok.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.oksupermercados.vencimientosok.R;
import com.oksupermercados.vencimientosok.adapters.VencimientosAdapter;
import com.oksupermercados.vencimientosok.connections.DatabaseManager;
import com.oksupermercados.vencimientosok.controllers.VencimientoController;
import com.oksupermercados.vencimientosok.model.Producto;
import com.oksupermercados.vencimientosok.model.Vencimiento;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PrincipalActivity extends AppCompatActivity {

    RecyclerView recyclerViewVencimiento;
    VencimientoController vencimientoController;
    ColorStateList colorStateList;
    Producto producto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        vencimientoController = new VencimientoController(this);

        recyclerViewVencimiento = findViewById(R.id.recyclerViewVencimientos);
        List<Vencimiento> vencimientos = vencimientoController.list();
        VencimientosAdapter vencimientosAdapter = new VencimientosAdapter(vencimientos,this);
        recyclerViewVencimiento.setAdapter(vencimientosAdapter);


        colorStateList = getResources().getColorStateList(R.color.icon_colors);
    }

    public void showAddDialog(View view) {

        // Inflar el diseño personalizado para el cuadro de diálogo
        LayoutInflater inflater = getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.dialog_add, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogLayout);
        AlertDialog alertDialog = builder.create();

        final EditText code = dialogLayout.findViewById(R.id.busqueda);
        final EditText expDate = dialogLayout.findViewById(R.id.vencimiento);
        final TextInputLayout textInputLayoutCode = dialogLayout.findViewById(R.id.inputLayoutCode);
        final TextInputLayout textInputLayoutExpDate = dialogLayout.findViewById(R.id.inputLayoutExpDate);
        final Button addButton = dialogLayout.findViewById(R.id.addButton);
        final Button cancelAddButton = dialogLayout.findViewById(R.id.cancelAddButton);


        addButton.setOnClickListener(view1 -> {
            String codeSearch = code.getText().toString();
            String expiresDate = expDate.getText().toString();

            if (textInputLayoutCode.isErrorEnabled()) {
                Toast.makeText(this, "Error en codigo", Toast.LENGTH_SHORT).show();
                return;
            }

            if (textInputLayoutExpDate.isErrorEnabled()) {
                Toast.makeText(this, "Error en fecha", Toast.LENGTH_SHORT).show();
                return;
            }
            Toast.makeText(this, codeSearch + "\n" + expiresDate, Toast.LENGTH_SHORT).show();
            alertDialog.dismiss();
        });

        cancelAddButton.setOnClickListener(view1 -> {
            alertDialog.dismiss();
        });

        code.setOnFocusChangeListener((v, hasFocus) -> {

            if (!hasFocus){

                if (code.getText().toString().trim().equals("")){
                    textInputLayoutCode.setError("El campo es obligatorio.");
                    return;
                }

                //Logica de base de datos
                producto = new Producto();
                if (producto == null){
                    textInputLayoutCode.setError("Producto no encontrado.");
                    return;
                }
                textInputLayoutCode.setStartIconDrawable(R.drawable.marcadeverificacion);
                textInputLayoutCode.setStartIconTintList(colorStateList);
                textInputLayoutCode.setHelperText("Producto.getName()");


            }
        });

        code.setOnEditorActionListener((textView, i, keyEvent) -> {
            if (i == EditorInfo.IME_ACTION_NEXT) {

                if (code.getText().toString().trim().equals("")) {
                    textInputLayoutCode.setError("El campo es obligatorio.");
                    code.selectAll();
                    return true;
                }

                //Logica de base de datos
                producto = new Producto();
                if (producto == null) {
                    textInputLayoutCode.setError("Producto no encontrado.");
                    code.selectAll();
                    return true;
                }

            }
            textInputLayoutCode.setStartIconDrawable(R.drawable.marcadeverificacion);
            textInputLayoutCode.setStartIconTintList(colorStateList);
            textInputLayoutCode.setHelperText("Producto.getName()");
            return false;
        });


        code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                textInputLayoutCode.setStartIconDrawable(null);
                textInputLayoutCode.setErrorEnabled(false);
                textInputLayoutCode.setHelperTextEnabled(false);
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });

        expDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                int lenText = expDate.getText().toString().replace("/","").length();

                expDate.setTextColor(Color.BLACK);
                textInputLayoutExpDate.setErrorEnabled(false);
                textInputLayoutExpDate.setStartIconDrawable(null);

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

                    int day = Integer.parseInt(expDate.getText().subSequence(0,2).toString());
                    int month = Integer.parseInt(expDate.getText().subSequence(3,5).toString());
                    int year = Integer.parseInt(expDate.getText().subSequence(6,8).toString()) + 2000;

                    try {

                        LocalDate date = LocalDate.of(year, month,day);
                        textInputLayoutExpDate.setStartIconDrawable(R.drawable.marcadeverificacion);
                        textInputLayoutExpDate.setStartIconTintList(colorStateList);
//                        Toast.makeText(PrincipalActivity.this, date.toString(), Toast.LENGTH_SHORT).show();

                    } catch (Exception e) {

                        textInputLayoutExpDate.setError("Fecha inválida.");
                        expDate.setTextColor(Color.RED);
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        alertDialog.show();
        code.requestFocus();
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
