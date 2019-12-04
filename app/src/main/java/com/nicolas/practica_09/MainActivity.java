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

    private EditText etCodigo;
    private EditText etNombre;
    private EditText etPrecio;
    private Button btnCrear;
    private Button btnBuscar;
    private Button btnEliminar;
    private Button btnModificar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etCodigo = findViewById(R.id.txtId);
        etNombre = findViewById(R.id.txtNombre);
        etPrecio = findViewById(R.id.txtPrecio);
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

        String codigo = etCodigo.getText().toString();
        String nombre = etNombre.getText().toString();
        String precio = etPrecio.getText().toString();

        if (!codigo.isEmpty() && !nombre.isEmpty() && !precio.isEmpty()){

            ContentValues registro = new ContentValues();

            registro.put("codigo", codigo);
            registro.put("nombre", nombre);
            registro.put("precio", precio);

            db.insert("articulos", null, registro);
            db.close();
            etCodigo.setText("");
            etNombre.setText("");
            etPrecio.setText("");
            Toast.makeText(this,"Registro Exitoso", Toast.LENGTH_LONG).show();

        }else {
            Toast.makeText(this, "Debes completar los campos", Toast.LENGTH_LONG).show();
        }
    }

    public void buscar(View view){
        AdminSqLite admin = new AdminSqLite(this, "administracion", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();


        String codigo = etCodigo.getText().toString();

        if (!codigo.isEmpty()){
            Cursor fila = db.rawQuery("select nombre, precio from articulos where codigo ="+ codigo, null);

            if (fila.moveToFirst()){
                etNombre.setText(fila.getString(0));
                etPrecio.setText(fila.getString(1));
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

        String codigo = etCodigo.getText().toString();

        if (!codigo.isEmpty()){
            int cantidad = db.delete("articulos", "codigo =" + codigo, null);
            db.close();

            etCodigo.setText("");
            etNombre.setText("");
            etPrecio.setText("");

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

        String codigo = etCodigo.getText().toString();
        String nombre = etNombre.getText().toString();
        String precio = etPrecio.getText().toString();

        if (!codigo.isEmpty() && !nombre.isEmpty() && !precio.isEmpty()){

            ContentValues registro = new ContentValues();
            registro.put("codigo", codigo);
            registro.put("nombre", nombre);
            registro.put("precio", precio);

            int cantidad = db.update("articulos", registro, "codigo =" + codigo, null);
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
