package com.example.db.studentPackage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.db.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class student extends AppCompatActivity {

    DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Students");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
    }

    public void horarioClick(View view){
        showHorario();
    }

    public void showHorario(){
        Intent horarioIntent = new Intent(this, horarioStudent.class);
        startActivity(horarioIntent);
    }
}
