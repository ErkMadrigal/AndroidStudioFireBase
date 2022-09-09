package com.example.test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class createPetActivity extends AppCompatActivity {

    Button add;
    EditText nombre, edad, color;
    private FirebaseFirestore mfirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_pet);

        this.setTitle("Crear");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String id = getIntent().getStringExtra("id_pet");
        mfirestore = FirebaseFirestore.getInstance();

        nombre = findViewById(R.id.nombre);
        edad = findViewById(R.id.edad);
        color = findViewById(R.id.color);
        add = findViewById(R.id.add);

        if(id == null || id == ""){
            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String nombrePet = nombre.getText().toString().trim();
                    String edadPet = edad.getText().toString().trim();
                    String colorPet = color.getText().toString().trim();

                    if(nombrePet.isEmpty() && edadPet.isEmpty() && colorPet.isEmpty()){
                        Toast.makeText(getApplicationContext(), "Error: al Ingresar los datos", Toast.LENGTH_SHORT).show();
                    }else{
                        postPet(nombrePet, edadPet, colorPet);
                    }
                }
            });
        }else{
            add.setText("update");
            getPet(id);
            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String nombrePet = nombre.getText().toString().trim();
                    String edadPet = edad.getText().toString().trim();
                    String colorPet = color.getText().toString().trim();

                    if(nombrePet.isEmpty() && edadPet.isEmpty() && colorPet.isEmpty()){
                        Toast.makeText(getApplicationContext(), "Error: al Ingresar los datos", Toast.LENGTH_SHORT).show();
                    }else{
                        updatePet(nombrePet, edadPet, colorPet, id);
                    }
                }
            });
        }
    }

    private void updatePet(String nombrePet, String edadPet, String colorPet, String id) {
        Map<String, Object> map = new HashMap<>();
        map.put("nombre", nombrePet);
        map.put("edad", edadPet);
        map.put("color", colorPet);

        mfirestore.collection("pet").document(id).update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getApplicationContext(), "Datos Actualizados", Toast.LENGTH_SHORT).show();
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Error al actualizar los datos", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void postPet(String nombrePet, String edadPet, String colorPet) {

        Map<String, Object> map = new HashMap<>();
        map.put("nombre", nombrePet);
        map.put("edad", edadPet);
        map.put("color", colorPet);

        mfirestore.collection("pet").add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(getApplicationContext(), "Datos Creados", Toast.LENGTH_SHORT).show();
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Error al ingresar los datos", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private  void getPet(String id){

        mfirestore.collection("pet").document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String nombrePet = documentSnapshot.getString("nombre");
                String edadPet = documentSnapshot.getString("edad");
                String colorPet = documentSnapshot.getString("color");
                nombre.setText(nombrePet);
                edad.setText(edadPet);
                color.setText(colorPet);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Error al obentener los datos", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }
}