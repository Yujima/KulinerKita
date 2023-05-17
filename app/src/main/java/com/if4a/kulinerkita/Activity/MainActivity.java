package com.if4a.kulinerkita.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.if4a.kulinerkita.Model.ModelKuliner;
import com.if4a.kulinerkita.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rvKuliner;
    private FloatingActionButton fabTambah;
    private RecyclerView.Adapter adKuliner;
    private RecyclerView.LayoutManager lmKuliner;
    private List<ModelKuliner> listKuliner = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvKuliner = findViewById(R.id.rv_kuliner);
        fabTambah = findViewById(R.id.fab_tambah);

        lmKuliner = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
    }
}