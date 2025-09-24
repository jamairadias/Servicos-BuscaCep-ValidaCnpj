package com.example.servicos;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


public class MainActivity extends AppCompatActivity {

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

        Button btTela1 = findViewById(R.id.buttonTela1);
        btTela1.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, CepActivity.class);
            startActivity(intent);
        });

        Button btTela2 = findViewById(R.id.buttonTela2);
        btTela2.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, CnpjActivity.class);
            startActivity(intent);
        });
    }
}