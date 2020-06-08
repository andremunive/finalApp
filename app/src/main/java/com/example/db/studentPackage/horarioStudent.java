package com.example.db.studentPackage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.db.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class horarioStudent extends AppCompatActivity {

    private DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    private ArrayList<String> materiasArray;
    private ArrayAdapter adapter;
    private ListView horario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horario_student);

        materiasArray = new ArrayList<>();
        horario = findViewById(R.id.horarioListView);

        searchCourseID();

    }

    public void searchCourseID(){
        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        Query query = ref.child("Students").orderByChild("email").equalTo(email);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot data: dataSnapshot.getChildren()){
                    String courseID = data.getValue().toString()
                            .split(",")[3]
                            .split("=")[1];
                    System.out.println(courseID);
                    searchHorario(courseID);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void searchHorario(final String courseID){
        Query query2 = ref.child("Asignaturas").orderByChild("courseID").equalTo(courseID);
        query2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                System.out.println("CourseID: "+courseID);
                for(DataSnapshot data: dataSnapshot.getChildren()){
                    String materia = data.getValue().toString()
                            .split(",")[3]
                            .split("=")[1];

                    materiasArray.add(data.getValue().toString()
                            .split(",")[3]
                            .split("=")[1].substring(0,materia.length()-1));

                    adapter = new ArrayAdapter<String>(horarioStudent.this,
                            android.R.layout.simple_list_item_1,
                            materiasArray);

                    horario.setAdapter(adapter);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
