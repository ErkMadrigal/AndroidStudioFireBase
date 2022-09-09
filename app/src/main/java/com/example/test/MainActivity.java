package com.example.test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.test.adapter.petAdapter;
import com.example.test.model.pet;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class MainActivity extends AppCompatActivity {

    Button btnAdd, addFragment;
    RecyclerView mRecycler;
    petAdapter mAdapter;
    FirebaseFirestore mFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirestore = FirebaseFirestore.getInstance();
        mRecycler = findViewById(R.id.recyclerViewSingel);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        Query query = mFirestore.collection("pet");
        FirestoreRecyclerOptions<pet> firestoreRecyclerOptions
                = new FirestoreRecyclerOptions.Builder<pet>().setQuery(query, pet.class).build();

        mAdapter = new petAdapter(firestoreRecyclerOptions);
        mAdapter.notifyDataSetChanged();
        mRecycler.setAdapter(mAdapter);

        btnAdd = findViewById(R.id.btnAdd);
        addFragment = findViewById(R.id.addFragment);

        addFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPetFragment fm = new createPetFragment();
                fm.show(getSupportFragmentManager(), "Navegar a fragment");
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, createPetActivity.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAdapter.stopListening();
    }
}