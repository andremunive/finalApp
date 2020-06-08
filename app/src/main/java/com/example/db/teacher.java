package com.example.db;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class teacher extends AppCompatActivity {

    private String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);
        user = getIntent().getStringExtra("usuario");

        Toast.makeText(this, "Bienvenido "+user,
                Toast.LENGTH_SHORT).show();

    }

    public void materiasClick(View view){
        showMaterias();
    }

    public void showMaterias(){
        Intent materiasIntent = new Intent(this, asignaturas.class);
        materiasIntent.putExtra("usuario", user);
        startActivity(materiasIntent);
    }
}
