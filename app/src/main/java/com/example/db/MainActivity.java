package com.example.db;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.db.studentPackage.student;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private EditText user, password;
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //View
        user = (EditText) findViewById(R.id.userTxt);
        password = (EditText) findViewById(R.id.passwordTxt);


    }

    public void loginClick(View view) {
        loginMethod();
    }

    public void loginMethod() {
        final String userText = user.getText().toString().trim();
        final String passText = password.getText().toString().trim();
        if (!userText.isEmpty()) {
            if (!passText.isEmpty()) {
                //Usuario y Contraseña validados
                dbRef.child("Students").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            if (dataSnapshot.child(userText).exists()) {
                                String dbEmail = dataSnapshot.child(userText)
                                        .child("email").getValue().toString();
                                String dbPass = dataSnapshot.child(userText)
                                        .child("password").getValue().toString();
                                if (dbPass.equals(passText)) {
                                    auth.signInWithEmailAndPassword(dbEmail, dbPass)
                                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                @Override
                                                public void onComplete(@NonNull Task<AuthResult> task) {
                                                    if (task.isSuccessful()) {
                                                        showStudentHome();
                                                    } else {
                                                        Toast.makeText(MainActivity.this,
                                                                "Error",
                                                                Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                }
                            }else {
                                dbRef.child("Teachers").addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.exists()) {
                                            if (dataSnapshot.child(userText).exists()) {
                                                String dbEmail = dataSnapshot.child(userText)
                                                        .child("email").getValue().toString();
                                                String dbPass = dataSnapshot.child(userText)
                                                        .child("password").getValue().toString();
                                                if (dbPass.equals(passText)) {
                                                    auth.signInWithEmailAndPassword(dbEmail, dbPass)
                                                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<AuthResult> task) {
                                                                    if (task.isSuccessful()) {
                                                                        showTeachertHome();
                                                                    } else {
                                                                        Toast.makeText(MainActivity.this,
                                                                                "Error",
                                                                                Toast.LENGTH_SHORT).show();
                                                                    }
                                                                }
                                                            });
                                                }
                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            } else {
                Toast.makeText(this,
                        "Nombre de Usuario o Contraseña no debe estar vacío",
                        Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this,
                    "Nombre de Usuario o Contraseña no debe estar vacío",
                    Toast.LENGTH_SHORT).show();
        }

    }

    public void showStudentHome() {
        finish();
        Intent studentIntent = new Intent(this, student.class);
        startActivity(studentIntent);
    }

    public void showTeachertHome() {
        finish();

        Intent teacherIntent = new Intent(this, teacher.class);
        teacherIntent.putExtra("usuario", user.getText().toString());
        startActivity(teacherIntent);
    }

}
