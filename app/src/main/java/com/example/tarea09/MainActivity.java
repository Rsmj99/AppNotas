package com.example.tarea09;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tarea09.control.Arreglo_Estudiante;
import com.example.tarea09.modelo.Aula;
import com.example.tarea09.modelo.Estudiante;

import java.io.BufferedReader;
import java.io.CharArrayReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {

    private Spinner sp_ordenar, sp_mostrar;
    private Switch sw_ascendente;
    private EditText et_buscar;
    private TextView tv_a, tv_b, tv_c;
    private ListView lv_estudiantes;
    private Arreglo_Estudiante objArregloEstudiante;
    private String nombre_archivo = "Estudiantes.txt";
    Toolbar toolbar;
    int posicion;
    ArrayAdapter<Estudiante> adaptador_estudiantes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sp_mostrar = findViewById(R.id.sp_mostrar);
        sp_ordenar = findViewById(R.id.sp_ordenar);
        sw_ascendente = findViewById(R.id.sw_ascendente);
        et_buscar = findViewById(R.id.et_buscar);
        tv_a = findViewById(R.id.tv_a);
        tv_b = findViewById(R.id.tv_b);
        tv_c = findViewById(R.id.tv_c);
        lv_estudiantes = findViewById(R.id.lv_estudiantes);

        objArregloEstudiante = new Arreglo_Estudiante();
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        CargarOrdenar();
        CargarMostrar();

        AbrirArchivo();
        adaptador_estudiantes = new ArrayAdapter<Estudiante>(this, android.R.layout.simple_spinner_item, objArregloEstudiante.getLista());
        lv_estudiantes.setAdapter(adaptador_estudiantes);

        et_buscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                lv_estudiantes.setAdapter(adaptador_estudiantes);
                adaptador_estudiantes.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        sp_mostrar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ArrayAdapter<Estudiante> adaptador_estudiantes2 = null;
                switch (i){
                    case 0:
                        adaptador_estudiantes2 = new ArrayAdapter<Estudiante>(getApplicationContext(), android.R.layout.simple_spinner_item, objArregloEstudiante.getLista());
                        break;
                    case 1:
                        adaptador_estudiantes2 = new ArrayAdapter<Estudiante>(getApplicationContext(), android.R.layout.simple_spinner_item, objArregloEstudiante.getEstudiantes(true));
                        break;
                    case 2:
                        adaptador_estudiantes2 = new ArrayAdapter<Estudiante>(getApplicationContext(), android.R.layout.simple_spinner_item, objArregloEstudiante.getEstudiantes(false));
                        break;
                    case 3:
                        adaptador_estudiantes2 = new ArrayAdapter<Estudiante>(getApplicationContext(), android.R.layout.simple_spinner_item, objArregloEstudiante.getEstudiantes(Aula.A));
                        break;
                    case 4:
                        adaptador_estudiantes2 = new ArrayAdapter<Estudiante>(getApplicationContext(), android.R.layout.simple_spinner_item, objArregloEstudiante.getEstudiantes(Aula.B));
                        break;
                    case 5:
                        adaptador_estudiantes2 = new ArrayAdapter<Estudiante>(getApplicationContext(), android.R.layout.simple_spinner_item, objArregloEstudiante.getEstudiantes(Aula.C));
                }
                lv_estudiantes.setAdapter(adaptador_estudiantes2);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        sp_ordenar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                adaptador_estudiantes = new ArrayAdapter<Estudiante>(getApplicationContext(), android.R.layout.simple_spinner_item, objArregloEstudiante.getLista());
                lv_estudiantes.setAdapter(adaptador_estudiantes);
                switch (i){
                    case 0:
                        objArregloEstudiante.OrdenarCodigo(sw_ascendente.isChecked());
                        break;
                    case 1:
                        objArregloEstudiante.OrdenarNombre(sw_ascendente.isChecked());
                        break;
                    case 2:
                        objArregloEstudiante.OrdenarAula(sw_ascendente.isChecked());
                        break;
                    case 3:
                        objArregloEstudiante.OrdenarPromedio(sw_ascendente.isChecked());
                }
                adaptador_estudiantes.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        sw_ascendente.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                adaptador_estudiantes = new ArrayAdapter<Estudiante>(getApplicationContext(), android.R.layout.simple_spinner_item, objArregloEstudiante.getLista());
                lv_estudiantes.setAdapter(adaptador_estudiantes);
                switch (sp_ordenar.getSelectedItemPosition()){
                    case 0:
                        objArregloEstudiante.OrdenarCodigo(b);
                        break;
                    case 1:
                        objArregloEstudiante.OrdenarNombre(b);
                        break;
                    case 2:
                        objArregloEstudiante.OrdenarAula(b);
                        break;
                    case 3:
                        objArregloEstudiante.OrdenarPromedio(b);
                }
                adaptador_estudiantes.notifyDataSetChanged();
            }
        });

        lv_estudiantes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                intent.putExtra("estudiante", (Estudiante) lv_estudiantes.getItemAtPosition(i));
                intent.putExtra("A", objArregloEstudiante.getCantidad(Aula.A));
                intent.putExtra("B", objArregloEstudiante.getCantidad(Aula.B));
                intent.putExtra("C", objArregloEstudiante.getCantidad(Aula.C));
                posicion = i;
                startActivityForResult(intent, 2);
            }
        });

        lv_estudiantes.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(android.R.drawable.ic_delete)
                        .setTitle("¿Estás seguro?")
                        .setMessage("¿Quieres eliminar este registro?")
                        .setPositiveButton("Sí", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                EditElimRegistro(false, (Estudiante) lv_estudiantes.getItemAtPosition(i));
                                objArregloEstudiante.Eliminar((Estudiante) lv_estudiantes.getItemAtPosition(i));
                                onStart();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
                return true;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        adaptador_estudiantes = new ArrayAdapter<Estudiante>(getApplicationContext(), android.R.layout.simple_spinner_item, objArregloEstudiante.getLista());
        lv_estudiantes.setAdapter(adaptador_estudiantes);
        tv_a.setText("A: " + objArregloEstudiante.getCantidad(Aula.A) + " (" + objArregloEstudiante.getPromedio(Aula.A) + ")");
        tv_b.setText("B: " + objArregloEstudiante.getCantidad(Aula.B) + " (" + objArregloEstudiante.getPromedio(Aula.B) + ")");
        tv_c.setText("C: " + objArregloEstudiante.getCantidad(Aula.C) + " (" + objArregloEstudiante.getPromedio(Aula.C) + ")");
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem){
        switch (menuItem.getItemId()){
            case R.id.add:
                Intent intent = new Intent(this, SecondActivity.class);
//                intent.putExtra("codigo", String.format("%03d", objArregloEstudiante.getSize()+1));
                int codigo = objArregloEstudiante.CodigoMayor() + 1;
                intent.putExtra("codigo", codigo);
                intent.putExtra("A", objArregloEstudiante.getCantidad(Aula.A));
                intent.putExtra("B", objArregloEstudiante.getCantidad(Aula.B));
                intent.putExtra("C", objArregloEstudiante.getCantidad(Aula.C));
                startActivityForResult(intent, 1);
                break;
            case R.id.delete:
                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(android.R.drawable.ic_delete)
                        .setTitle("¿Estás seguro?")
                        .setMessage("¿Quieres eliminar todos los registros?")
                        .setPositiveButton("Sí", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                objArregloEstudiante.Eliminar();
                                EliminarArchivo();
                                onStart();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
                return true;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            Estudiante estudiante = (Estudiante) data.getSerializableExtra("estudiante");
            switch (requestCode) {
                case 1:
                    objArregloEstudiante.Registrar(estudiante);
                    GuardarArchivo(objArregloEstudiante.getEstudiante(objArregloEstudiante.getSize() - 1));
                    adaptador_estudiantes.notifyDataSetChanged();
                    break;
                case 2:
                    objArregloEstudiante.Editar(posicion, estudiante);
                    EditElimRegistro(true, objArregloEstudiante.getEstudiante(posicion));
                    adaptador_estudiantes.notifyDataSetChanged();
            }
            Toast.makeText(this, "Lista actualizada", Toast.LENGTH_SHORT).show();
        } else if (resultCode == RESULT_CANCELED) {
            Toast.makeText(this, "Cancelado", Toast.LENGTH_SHORT).show();
        }
    }

//    private void ListarEstudiantes(){
//        ArrayAdapter<String> adaptador_estudiantes = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, objArregloEstudiante.getEstudiantes());
//        lv_estudiantes.setAdapter(adaptador_estudiantes);
//    }

    private void CargarOrdenar(){
        String[] ordenar = {"Por Código", "Por Nombre", "Por Aula", "Por Promedio"};
        ArrayAdapter<String> adaptador_ordenar = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ordenar);
        sp_ordenar.setAdapter(adaptador_ordenar);
    }

    private void CargarMostrar(){
        String[] mostrar = {"Todos", "Aprobados", "Desaprobados", "Del A", "Del B", "Del C"};
        ArrayAdapter<String> adaptador_mostrar = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mostrar);
        sp_mostrar.setAdapter(adaptador_mostrar);
    }

    private boolean existe(String[] archivos, String nombre){
        for (int x = 0; x < archivos.length; x++) {
            if (nombre.equals(archivos[x])) return true;
        }
        return false;
    }

    private void AbrirArchivo(){
        String[] archivos = this.fileList();
        if(existe(archivos, nombre_archivo)){
            try{
                InputStreamReader str= new InputStreamReader(this.openFileInput(nombre_archivo));
                BufferedReader br= new BufferedReader(str);
                String linea;
                while ((linea=br.readLine()) != null){
                    StringTokenizer st= new StringTokenizer(linea, "&");
                    int codigo = Integer.parseInt(st.nextToken());
                    String nombre = st.nextToken();
                    Aula aula = Aula.valueOf(st.nextToken());
                    double practica = Double.parseDouble(st.nextToken());
                    double trabajos = Double.parseDouble(st.nextToken());
                    double evaluacion = Double.parseDouble(st.nextToken());
                    double promedio = Double.parseDouble(st.nextToken());
                    objArregloEstudiante.Registrar(new Estudiante(new Object[]{codigo,nombre,aula,practica,trabajos,evaluacion,promedio}));
                }
                br.close();
                str.close();
            }
            catch (IOException ex){

            }
        }
    }

    private void GuardarArchivo(Estudiante objEstudiante){
        try {
            OutputStreamWriter out = new OutputStreamWriter(this.openFileOutput(nombre_archivo, MODE_APPEND));
            int codigo = objEstudiante.getCodigo();
            String nombre = objEstudiante.getNombre();
            Aula aula = objEstudiante.getAula();
            double practica = objEstudiante.getPractica();
            double trabajos = objEstudiante.getTrabajos();
            double evaluacion = objEstudiante.getEvaluacion();
            double promedio = objEstudiante.getPromedio();
            String linea = codigo + "&" + nombre + "&" + aula + "&" + practica + "&" + trabajos + "&" + evaluacion + "&" + promedio + "\n";
            out.write(linea);
            out.flush();
            out.close();
        } catch (IOException ex){

        }
    }

    private void EditElimRegistro(boolean editar, Estudiante objEstudiante){
        try {
            File inFile = new File(String.valueOf(getFileStreamPath(nombre_archivo)));
            if (!inFile.isFile()) {
                System.out.println("No hay archivo");
                return;
            }

            File tempFile = new File(inFile.getAbsolutePath() + ".tmp");
            BufferedReader br = new BufferedReader(new FileReader(String.valueOf(getFileStreamPath(nombre_archivo))));
            PrintWriter pw = new PrintWriter(new FileWriter(tempFile));
            String line;

            int codigo = objEstudiante.getCodigo();
            String nombre = objEstudiante.getNombre();
            Aula aula = objEstudiante.getAula();
            double practica = objEstudiante.getPractica();
            double trabajos = objEstudiante.getTrabajos();
            double evaluacion = objEstudiante.getEvaluacion();
            double promedio = objEstudiante.getPromedio();
            String linea = codigo + "&" + nombre + "&" + aula + "&" + practica + "&" + trabajos + "&" + evaluacion + "&" + promedio;

            if (editar) {
                while ((line = br.readLine()) != null) {
                    StringTokenizer st= new StringTokenizer(line, "&");
                    int cod = Integer.parseInt(st.nextToken());
                    pw.println((cod == codigo) ? linea : line);
                    pw.flush();
                }
            } else {
                while ((line = br.readLine()) != null) {
                    if (!line.trim().equals(linea)) {
                        pw.println(line);
                        pw.flush();
                    }
                }
            }
            pw.close();
            br.close();

            if (!inFile.delete()) {
                System.out.println("No se puede eliminar el archivo");
                return;
            }

            if (!tempFile.renameTo(inFile)) System.out.println("No se puede renombrar el archivo");

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void EliminarArchivo(){
//        String[] archivos = this.fileList();
//        if(existe(archivos, nombre_archivo)){
//            if (this.deleteFile(nombre_archivo)){
//                Toast.makeText(this,"Eliminado", Toast.LENGTH_LONG);
//            }
//        } else {
//            Toast.makeText(this,"El fichero no existe", Toast.LENGTH_LONG);
//        }
        File file = new File(String.valueOf(getFileStreamPath(nombre_archivo)));
        if(file.exists()){
            if (file.delete()){
                Toast.makeText(this,"Eliminado", Toast.LENGTH_LONG);
            }
        } else {
            Toast.makeText(this,"El fichero no existe", Toast.LENGTH_LONG);
        }
    }
}