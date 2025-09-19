package com.example.servicos;

import android.app.ProgressDialog;
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

public class CnpjActivity extends AppCompatActivity implements View.OnClickListener {

    private String buscaCnpj = "";

    private ProgressDialog progressDialog;
    private EditText etCnpj;

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

        etCnpj = findViewById(R.id.etCnpj);
        Button btBuscarCnpj = findViewById(R.id.btBuscarCnpj);

        btBuscarCnpj.setOnClickListener(this);

        String titulo = getIntent().getStringExtra("buscaCnpj");
        buscaCnpj = titulo;
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btBuscarCnpj) {
            String numeroCnpj = etCnpj.getText().toString();

            progressDialog = new ProgressDialog(CnpjActivity.this);
            progressDialog.setMessage("Carregando...");
            progressDialog.setCancelable(false);
            progressDialog.show();

            consultarCnpj(numeroCnpj);
        }
    }

    private void consultarCnpj(String numeroCnpj) {
        TextView tvInfo = findViewById(R.id.tvInfoCnpj);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constantes.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        InvertextoApi invertextoApi = retrofit.create(InvertextoApi.class);

        Call<Logradouro> call = invertextoApi.getLogradouro(
                numeroCnpj, Constantes.TOKEN
        );

        call.enqueue(new Callback<Logradouro>() {
            @Override
            public void onResponse(Call<Logradouro> call, Response<Logradouro> response) {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }

                if (response.isSuccessful() && response.body() != null) {
                    Logradouro logradouro = response.body();
                    tvInfo.setText(logradouro.formatar());
                } else {
                    Toast.makeText(
                            CnpjActivity.this,
                            "Erro ao buscar informações, verifique o CNPJ",
                            Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Logradouro> call, Throwable throwable) {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }

                Toast.makeText(
                        CnpjActivity.this,
                        "Verifique a sua conexão com a internet",
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}
    