package com.nicolas.practica_09;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.nicolas.practica_09.Data.AdminSqLite;

public class MainActivity extends AppCompatActivity {

    private EditText etid;
    private EditText etNombre;
    private EditText etEdad;
    private Button btnCrear;
    private Button btnBuscar;
    private Button btnEliminar;
    private Button btnModificar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etid = findViewById(R.id.txtId);
        etNombre = findViewById(R.id.txtNombre);
        etEdad = findViewById(R.id.txtEdad);
        btnCrear =findViewById(R.id.btnRegistrar);
        btnBuscar = findViewById(R.id.btnBuscar);
        btnEliminar = findViewById(R.id.btnEliminar);
        btnModificar = findViewById(R.id.btnModificar);


        btnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                crear(view);
            }
        });

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buscar(view);
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eliminar(view);
            }
        });

        btnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editar(view);
            }
        });



    }

    public void crear(View view){
        AdminSqLite  admin = new AdminSqLite(this, "administracion", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();

        String id = etid.getText().toString();
        String nombre = etNombre.getText().toString();
        String edad = etEdad.getText().toString();

        if (!id.isEmpty() && !nombre.isEmpty() && !edad.isEmpty()){

            ContentValues registro = new ContentValues();

            registro.put("id", id);
            registro.put("nombre", nombre);
            registro.put("edad", edad);

            db.insert("personas", null, registro);
            db.close();
            etid.setText("");
            etNombre.setText("");
            etEdad.setText("");
            Toast.makeText(this,"Registro Exitoso", Toast.LENGTH_LONG).show();

        }else {
            Toast.makeText(this, "Debes completar los campos", Toast.LENGTH_LONG).show();
        }
    }

    public void buscar(View view){
        AdminSqLite admin = new AdminSqLite(this, "administracion", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();


        String id = etid.getText().toString();

        if (!id.isEmpty()){
            Cursor fila = db.rawQuery("select nombre, edad from personas where id ="+ id, null);

            if (fila.moveToFirst()){
                etNombre.setText(fila.getString(0));
                etEdad.setText(fila.getString(1));
                db.close();
            }else{
                Toast.makeText(this, "No existe el artículo buscado", Toast.LENGTH_LONG).show();
                db.close();
            }
        }else {
            Toast.makeText(this, "Debes introducir el código del producto a buscar", Toast.LENGTH_LONG).show();
        }
    }

    public void eliminar(View view) {
        AdminSqLite admin = new AdminSqLite(this, "administracion", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();

        String id = etid.getText().toString();

        if (!id.isEmpty()){
            int cantidad = db.delete("personas", "id =" + id, null);
            db.close();

            etid.setText("");
            etNombre.setText("");
            etEdad.setText("");

            if (cantidad == 1){
                Toast.makeText(this, "Producto eliminado exitosamente", Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(this, "El producto no existe", Toast.LENGTH_LONG).show();
            }

        }else {
            Toast.makeText(this, "Debes introducir el código del producto", Toast.LENGTH_LONG).show();
        }
    }

    public void editar(View view) {
        AdminSqLite admin = new AdminSqLite(this, "administracion", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();

        String id = etid.getText().toString();
        String nombre = etNombre.getText().toString();
        String edad = etEdad.getText().toString();

        if (!id.isEmpty() && !nombre.isEmpty() && !edad.isEmpty()){

            ContentValues registro = new ContentValues();
            registro.put("id", id);
            registro.put("nombre", nombre);
            registro.put("edad", edad);

            int cantidad = db.update("personas", registro, "id =" + id, null);
            db.close();

            if (cantidad == 1){
                Toast.makeText(this, "Producto modificado", Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(this, "El producto no existe", Toast.LENGTH_LONG).show();
            }

        }else{
            Toast.makeText(this, "Debes completar los campos", Toast.LENGTH_LONG).show();
        }


    }
}
