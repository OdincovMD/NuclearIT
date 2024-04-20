package com.dmiiy.wayapp;
import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RecycleViewSmoke extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ImageAdapter imageAdapter;
    private List<ImageModel> imageList;
    private FirebaseStorage storage;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_view_smoke);

        recyclerView = findViewById(R.id.recycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        imageList = new ArrayList<>();
        imageAdapter = new ImageAdapter(this, imageList);
        recyclerView.setAdapter(imageAdapter);

        storage = FirebaseStorage.getInstance();

        loadImagesFromFirebase();
    }

    private void loadImagesFromFirebase() {
        StorageReference listRef = storage.getReference().child("smoke"); // Замените "images" на имя вашей папки в Storage
        listRef.listAll().addOnSuccessListener(listResult -> {
            for (StorageReference item : listResult.getItems()) {
                item.getDownloadUrl().addOnSuccessListener(uri -> {
                    imageList.add(new ImageModel(uri.toString()));
                    imageAdapter.notifyDataSetChanged();
                });
            }
        });
    }
}

