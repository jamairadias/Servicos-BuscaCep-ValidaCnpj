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
import com.example.servicos.model.Cnpj;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CnpjActivity extends AppCompatActivity {


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

        EditText etCnpj = findViewById(R.id.etCnpj);
        Button btBuscarCnpj = findViewById(R.id.btBuscarCnpj);
        TextView tvInfoCnpj = findViewById(R.id.tvInfoCnpj);
        btBuscarCnpj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvInfoCnpj.setText("Carregando...");
                String numeroCnpj = etCnpj.getText().toString();
                consultarCnpj(numeroCnpj);
            }
        });
    }
    private void consultarCnpj(String numeroCnpj) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constantes.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        InvertextoApi invertextoApi = retrofit.create(InvertextoApi.class);

        Call<Cnpj> call = invertextoApi.getCnpj(numeroCnpj, Constantes.TOKEN);

        call.enqueue(new Callback<Cnpj>() {
            @Override
            public void onResponse(Call<Cnpj> call, Response<Cnpj> response) {
                if (response.isSuccessful() ) {
                    Cnpj cnpj = response.body();

                } else {
                    Toast.makeText(CnpjActivity.this, "Erro ao buscar informações, verifique o CNPJ",
                            Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Cnpj> call, Throwable throwable) {

                Toast.makeText(CnpjActivity.this, "Falha na comunicação. Verifique sua conexão com a internet.",
                        Toast.LENGTH_LONG).show();

            }
        });
    }
}
