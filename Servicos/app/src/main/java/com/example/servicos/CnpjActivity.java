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
import com.example.servicos.model.Cnpj; // Certifique-se que esta classe Cnpj está correta

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CnpjActivity extends AppCompatActivity {

    // Declarar os componentes da UI como membros da classe
    private EditText etCnpj;
    private Button btBuscarCnpj;
    private TextView tvInfoCnpj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cnpj);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inicializar os componentes da UI
        etCnpj = findViewById(R.id.etCnpj);
        btBuscarCnpj = findViewById(R.id.btBuscarCnpj);
        tvInfoCnpj = findViewById(R.id.tvInfoCnpj);

        btBuscarCnpj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String numeroCnpj = etCnpj.getText().toString().trim(); // Adicionado trim()
                if (numeroCnpj.isEmpty()) {
                    Toast.makeText(CnpjActivity.this, "Por favor, insira um CNPJ.", Toast.LENGTH_SHORT).show();
                    return;
                }
                // Mostrar "Carregando..." e desabilitar o botão
                tvInfoCnpj.setText("Carregando...");
                btBuscarCnpj.setEnabled(false);
                consultarCnpj(numeroCnpj);
            }
        });
    }

    private void consultarCnpj(String numeroCnpj) {
        // Remover a formatação do CNPJ (pontos, barras, traços) se a API esperar apenas números
        // String cnpjFormatado = numeroCnpj.replaceAll("[^0-9]", "");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constantes.URL) // Certifique-se que Constantes.URL está definida
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        InvertextoApi invertextoApi = retrofit.create(InvertextoApi.class);

        // Certifique-se que Constantes.TOKEN está definida
        // e que o método getCnpj na sua InvertextoApi está correto
        Call<Cnpj> call = invertextoApi.getCnpj(numeroCnpj, Constantes.TOKEN); // ou cnpjFormatado se necessário

        call.enqueue(new Callback<Cnpj>() {
            @Override
            public void onResponse(Call<Cnpj> call, Response<Cnpj> response) {
                // Reabilitar o botão
                btBuscarCnpj.setEnabled(true);

                if (response.isSuccessful() && response.body() != null) {
                    Cnpj cnpj = response.body();

                    // Construa a string com as informações do CNPJ
                    // Adapte de acordo com os campos disponíveis no seu objeto Cnpj
                    StringBuilder infoBuilder = new StringBuilder();
                    // Exemplo de campos comuns (verifique os nomes exatos na sua classe Cnpj
                    // e no JSON retornado pela API Invertexto)
                    infoBuilder.append("CNPJ: ").append(cnpj.getCnpj() != null ? cnpj.getCnpj() : numeroCnpj).append("\n");
                    infoBuilder.append("Razão Social: ").append(cnpj.getRazao_social()).append("\n");
                    infoBuilder.append("Nome Fantasia: ").append(cnpj.getNome_fantasia()).append("\n");
                    infoBuilder.append("Telefone: ").append(cnpj.getTelefone1()).append("\n");
                    infoBuilder.append("E-mail: ").append(cnpj.getEmail()).append("\n");


                    tvInfoCnpj.setText(infoBuilder.toString());

                } else {
                    tvInfoCnpj.setText(""); // Limpar o texto de carregando
                    String errorMessage = "Erro ao buscar informações.";
                    if (response.code() == 400) { // Exemplo de tratamento de erro específico
                        errorMessage = "CNPJ inválido ou não encontrado.";
                    } else if (response.code() == 404) {
                        errorMessage = "CNPJ não encontrado.";
                    } else if (response.code() == 401) { // Exemplo
                        errorMessage = "Token inválido ou não autorizado.";
                    }
                    Toast.makeText(CnpjActivity.this, errorMessage + " (Código: " + response.code() + ")",
                            Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Cnpj> call, Throwable throwable) {
                // Reabilitar o botão
                btBuscarCnpj.setEnabled(true);
                tvInfoCnpj.setText(""); // Limpar o texto de carregando
                Toast.makeText(CnpjActivity.this, "Falha na comunicação: " + throwable.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}
