package com.example.tarea09;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.tarea09.modelo.Aula;
import com.example.tarea09.modelo.Estudiante;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class SecondActivity extends AppCompatActivity {

    Toolbar toolbar;
    Spinner sp_aula;
    EditText et_nombre, et_practica, et_trabajos, et_evaluacion;
    Estudiante estudiante;
    DecimalFormat df = new DecimalFormat("###.#");
    int codigo, a, b, c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        et_nombre = findViewById(R.id.et_nombre);
        sp_aula = findViewById(R.id.sp_aula);
        et_practica = findViewById(R.id.et_practica);
        et_trabajos = findViewById(R.id.et_trabajos);
        et_evaluacion = findViewById(R.id.et_evaluacion);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Aula[] aulas = {Aula.A, Aula.B, Aula.C};
        ArrayAdapter<Aula> adapter = new ArrayAdapter<Aula>(getApplicationContext(), android.R.layout.simple_spinner_item, aulas);
        sp_aula.setAdapter(adapter);

        codigo = getIntent().getIntExtra("codigo", 0);
        a = getIntent().getIntExtra("A", 0);
        b = getIntent().getIntExtra("B", 0);
        c = getIntent().getIntExtra("C", 0);
        estudiante = (Estudiante) getIntent().getSerializableExtra("estudiante");
        if (estudiante != null && codigo == 0){
            et_nombre.setText(estudiante.getNombre());
            switch (estudiante.getAula()){
                case A:
                    sp_aula.setSelection(0);
                    break;
                case B:
                    sp_aula.setSelection(1);
                    break;
                case C:
                    sp_aula.setSelection(2);
            }
            et_practica.setText(estudiante.getPractica() + "");
            et_trabajos.setText(estudiante.getTrabajos() + "");
            et_evaluacion.setText(estudiante.getEvaluacion() + "");
        }

        findViewById(R.id.btn_aceptar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent data = new Intent();
                switch ((Aula)sp_aula.getSelectedItem()){
                    case A:
                        if (a == 20) {
                            Toast.makeText(getApplicationContext(),"Ya hay 20 alumnos en el aula \"A\"", Toast.LENGTH_LONG).show();
                            return;
                        } break;
                    case B:
                        if (b == 20) {
                            Toast.makeText(getApplicationContext(),"Ya hay 20 alumnos en el aula \"B\"", Toast.LENGTH_LONG).show();
                            return;
                        } break;
                    case C:
                        if (c == 20) {
                            Toast.makeText(getApplicationContext(),"Ya hay 20 alumnos en el aula \"C\"", Toast.LENGTH_LONG).show();
                            return;
                        }
                }
                if (estudiante == null && codigo != 0) {
                    estudiante = new Estudiante();
                    estudiante.setCodigo(codigo);
                }
                estudiante.setNombre(et_nombre.getText().toString());
                estudiante.setAula((Aula) sp_aula.getSelectedItem());
                estudiante.setPractica(Double.parseDouble(et_practica.getText().toString()));
                estudiante.setTrabajos(Double.parseDouble(et_trabajos.getText().toString()));
                estudiante.setEvaluacion(Double.parseDouble(et_evaluacion.getText().toString()));
                estudiante.setPromedio(Double.parseDouble(df.format((estudiante.getPractica()+estudiante.getTrabajos()+estudiante.getEvaluacion())/3)));
                data.putExtra("estudiante", estudiante);
                setResult(RESULT_OK, data);
                finish();
            }
        });

        findViewById(R.id.btn_cancelar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu2, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem){
        switch (menuItem.getItemId()){
            case R.id.check:
                Intent data = new Intent();
                switch ((Aula)sp_aula.getSelectedItem()){
                    case A:
                        if (a == 20) {
                            Toast.makeText(getApplicationContext(),"Ya hay 20 alumnos en el aula \"A\"", Toast.LENGTH_LONG).show();
                            return false;
                        } break;
                    case B:
                        if (b == 20) {
                            Toast.makeText(getApplicationContext(),"Ya hay 20 alumnos en el aula \"B\"", Toast.LENGTH_LONG).show();
                            return false;
                        } break;
                    case C:
                        if (c == 20) {
                            Toast.makeText(getApplicationContext(),"Ya hay 20 alumnos en el aula \"C\"", Toast.LENGTH_LONG).show();
                            return false;
                        }
                }
                if (estudiante == null && codigo != 0) {
                    estudiante = new Estudiante();
                    estudiante.setCodigo(codigo);
                }
                estudiante.setNombre(et_nombre.getText().toString());
                estudiante.setAula((Aula) sp_aula.getSelectedItem());
                estudiante.setPractica(Double.parseDouble(et_practica.getText().toString()));
                estudiante.setTrabajos(Double.parseDouble(et_trabajos.getText().toString()));
                estudiante.setEvaluacion(Double.parseDouble(et_evaluacion.getText().toString()));
                estudiante.setPromedio(Double.parseDouble(df.format((estudiante.getPractica()+estudiante.getTrabajos()+estudiante.getEvaluacion())/3)));
                data.putExtra("estudiante", estudiante);
                setResult(RESULT_OK, data);
                finish();
                break;
            case R.id.cancel:
                setResult(RESULT_CANCELED);
                finish();
        }
        return true;
    }
}