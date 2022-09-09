package com.example.test;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class createPetFragment extends DialogFragment {

    Button add;
    EditText nombre, edad, color;
    private FirebaseFirestore mfirestore;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_create_pet, container, false);
        mfirestore = FirebaseFirestore.getInstance();

        nombre = v.findViewById(R.id.nombre);
        edad = v.findViewById(R.id.edad);
        color = v.findViewById(R.id.color);
        add = v.findViewById(R.id.add);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombrePet = nombre.getText().toString().trim();
                String edadPet = edad.getText().toString().trim();
                String colorPet = color.getText().toString().trim();

                if(nombrePet.isEmpty() && edadPet.isEmpty() && colorPet.isEmpty()){
                    Toast.makeText(getContext(), "Inregar los datos", Toast.LENGTH_SHORT).show();
                }else{
                    postPet(nombrePet, edadPet, colorPet);
                }
            }
        });

        return  v;
    }

    private void postPet(String nombrePet, String edadPet, String colorPet) {

        Map<String, Object> map = new HashMap<>();
        map.put("nombre", nombrePet);
        map.put("edad", edadPet);
        map.put("color", colorPet);

        mfirestore.collection("pet").add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(getContext(), "Datos Creados", Toast.LENGTH_SHORT).show();
                getDialog().dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Error al ingresar los datos", Toast.LENGTH_SHORT).show();
            }
        });

    }
}