package com.example.servicos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button btnAbrirCep = findViewById(R.id.btnAbrirCep);
        btnAbrirCep.setOnClickListener(this);

        Button btnAbrirCnpj = findViewById(R.id.btnAbrirCnpj);
        btnAbrirCnpj.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnAbrirCep) {
            Intent telaBuscaCep = new Intent(MainActivity.this, CepActivity.class);
            startActivity(telaBuscaCep);

        } else if (view.getId() == R.id.btnAbrirCnpj) {
            Intent telaBuscaCnpj = new Intent(MainActivity.this, CnpjActivity.class);
            startActivity(telaBuscaCnpj);

        }




    }
}