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

        // Initialize UI elements
        etCep = findViewById(R.id.etCep);
        btBuscar = findViewById(R.id.btBuscar);
        tvInfo = findViewById(R.id.tvInfo);

        btBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String numeroCep = etCep.getText().toString().trim(); // Adicionado trim() para remover espaços
                if (numeroCep.isEmpty()) {
                    Toast.makeText(CepActivity.this, "Por favor, insira um CEP.", Toast.LENGTH_SHORT).show();
                    return;
                }
                // Mostrar "Carregando..." e desabilitar o botão para evitar cliques múltiplos
                tvInfo.setText("Carregando...");
                btBuscar.setEnabled(false);
                consultarCep(numeroCep);
            }
        });
    }

    private void consultarCep(String numeroCep) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constantes.URL) // Certifique-se que Constantes.URL está definida corretamente
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        InvertextoApi invertextoApi = retrofit.create(InvertextoApi.class);

        // Certifique-se que Constantes.TOKEN está definida corretamente
        Call<Logradouro> call = invertextoApi.getLogradouro(numeroCep, Constantes.TOKEN);

        call.enqueue(new Callback<Logradouro>() {
            @Override
            public void onResponse(Call<Logradouro> call, Response<Logradouro> response) {
                // Reabilitar o botão
                btBuscar.setEnabled(true);

                if (response.isSuccessful() && response.body() != null) {
                    Logradouro logradouro = response.body();
                    // Construa a string com as informações do logradouro
                    // Adapte de acordo com os campos disponíveis no seu objeto Logradouro
                    StringBuilder infoBuilder = new StringBuilder();
                    infoBuilder.append("CEP: ").append(logradouro.getCep()).append("\n");
                    infoBuilder.append("Logradouro: ").append(logradouro.getStreet()).append("\n");
                    infoBuilder.append("Bairro: ").append(logradouro.getNeighborhood()).append("\n");


                    tvInfo.setText(infoBuilder.toString());

                } else {
                    tvInfo.setText(""); // Limpar o texto de carregando
                    String errorMessage = "Erro ao buscar informações.";
                    if (response.code() == 400) { // Exemplo de tratamento de erro específico
                        errorMessage = "CEP inválido ou não encontrado.";
                    } else if (response.code() == 401) { // Exemplo
                        errorMessage = "Token inválido ou não autorizado.";
                    }
                    Toast.makeText(CepActivity.this, errorMessage + " (Código: " + response.code() + ")",
                            Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Logradouro> call, Throwable throwable) {
                // Reabilitar o botão
                btBuscar.setEnabled(true);
                tvInfo.setText(""); // Limpar o texto de carregando
                Toast.makeText(CepActivity.this, "Falha na comunicação: " + throwable.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}
