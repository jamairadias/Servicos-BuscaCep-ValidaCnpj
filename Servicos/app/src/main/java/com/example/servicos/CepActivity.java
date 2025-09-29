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

    private EditText etCep;
    private Button btBuscar;
    private TextView tvInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cep);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etCep = findViewById(R.id.etCep);
        btBuscar = findViewById(R.id.btBuscar);
        tvInfo = findViewById(R.id.tvInfo);

        btBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String numeroCep = etCep.getText().toString().trim();
                if (numeroCep.isEmpty()) {
                    Toast.makeText(CepActivity.this, "Por favor, insira um CEP.", Toast.LENGTH_SHORT).show();
                    return;
                }
                tvInfo.setText("Carregando...");
                btBuscar.setEnabled(false);
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
                btBuscar.setEnabled(true);

                if (response.isSuccessful() && response.body() != null) {
                    Logradouro logradouro = response.body();

                    StringBuilder infoBuilder = new StringBuilder();
                    infoBuilder.append("CEP: ").append(logradouro.getCep()).append("\n");
                    infoBuilder.append("Logradouro: ").append(logradouro.getStreet()).append("\n");
                    infoBuilder.append("Bairro: ").append(logradouro.getNeighborhood()).append("\n");

                    tvInfo.setText(infoBuilder.toString());

                } else {
                    tvInfo.setText("");
                    String errorMessage = "Erro ao buscar informações.";
                    if (response.code() == 400) {
                        errorMessage = "CEP inválido ou não encontrado.";
                    } else if (response.code() == 401) {
                        errorMessage = "Token inválido ou não autorizado.";
                    }
                    Toast.makeText(CepActivity.this, errorMessage + " (Código: " + response.code() + ")",
                            Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Logradouro> call, Throwable throwable) {
                btBuscar.setEnabled(true);
                tvInfo.setText("");
                Toast.makeText(CepActivity.this, "Falha na comunicação: " + throwable.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}
