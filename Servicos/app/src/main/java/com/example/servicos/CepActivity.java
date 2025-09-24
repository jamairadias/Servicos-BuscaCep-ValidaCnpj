package com.example.servicos;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.servicos.api.InvertextoApi;
import com.example.servicos.model.Logradouro;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CepActivity extends AppCompatActivity {

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

        // Initialize UI elements
        EditText etCep = findViewById(R.id.etCep);
        Button btBuscar = findViewById(R.id.btBuscar);
        TextView tvInfo = findViewById(R.id.tvInfo);
        btBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvInfo.setText("Carregando...");
                String numeroCep = etCep.getText().toString();
                consultarCep(numeroCep);
            }
        });

    }

    private void consultarCep(String numeroCep) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constantes.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        InvertextoApi invertextoApi = retrofit.create(InvertextoApi.class);

        Call<Logradouro> call = invertextoApi.getLogradouro(numeroCep, Constantes.TOKEN);

        call.enqueue(new Callback<Logradouro>() {
            @Override
            public void onResponse(Call<Logradouro> call, Response<Logradouro> response) {
                if (response.isSuccessful() ) {
                    Logradouro logradouro = response.body();

                } else {
                    Toast.makeText(CepActivity.this, "Erro ao buscar informações, verifique o CEP",
                            Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Logradouro> call, Throwable throwable) {

                Toast.makeText(CepActivity.this, "Falha na comunicação. Verifique sua conexão com a internet.",
                        Toast.LENGTH_LONG).show();

            }
        });
    }

}