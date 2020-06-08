package com.example.db;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class asignaturas extends AppCompatActivity {

    private DatabaseReference dbRef;

    private ListView materias;
    private ArrayAdapter adapter;

    private String user;

    private ArrayList<String> mat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asignaturas);

        dbRef  = FirebaseDatabase.getInstance().getReference("Asignaturas");
        user = getIntent().getStringExtra("usuario");
        mat = new ArrayList<>();

        //View
        materias = (ListView) findViewById(R.id.asignaturasList);

        buscarMethod();
    }


    public void buscarMethod(){
        Query q = dbRef.orderByChild("teacherID").equalTo(user);

        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mat.clear();
                for(DataSnapshot data: dataSnapshot.getChildren()){
                    //System.out.println(data.getValue());
                    String aux = data.getValue().toString().split(",")[3]
                            .split("=")[1];

                    mat.add(data.getValue().toString().split(",")[3]
                    .split("=")[1].substring(0,aux.length()-1));

                    adapter = new ArrayAdapter<String>(asignaturas.this, android.R.layout.simple_list_item_1, mat);

                    materias.setAdapter(adapter);

                    materias.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        }
                    });

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





    }
}
