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

    private EditText etCnpj;
    private Button btBuscarCnpj;
    private TextView tvInfoCnpj;

    public static final String EXTRA_CNPJ_RESULT = "com.example.servicos.EXTRA_CNPJ_RESULT";

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

        etCnpj = findViewById(R.id.etCnpj);
        btBuscarCnpj = findViewById(R.id.btBuscarCnpj);
        tvInfoCnpj = findViewById(R.id.tvInfoCnpj);

        btBuscarCnpj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String numeroCnpj = etCnpj.getText().toString().trim();
                if (numeroCnpj.isEmpty()) {
                    Toast.makeText(CnpjActivity.this, "Por favor, insira um CNPJ.", Toast.LENGTH_SHORT).show();
                    return;
                }
                String cnpjApenasNumeros = numeroCnpj.replaceAll("[^0-9]", "");

                tvInfoCnpj.setText("Carregando...");
                btBuscarCnpj.setEnabled(false);
                consultarCnpj(cnpjApenasNumeros);
            }
        });
    }

    private void consultarCnpj(String numeroCnpjFormatado) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constantes.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        InvertextoApi invertextoApi = retrofit.create(InvertextoApi.class);
        Call<Cnpj> call = invertextoApi.getCnpj(numeroCnpjFormatado, Constantes.TOKEN_CNPJ);

        call.enqueue(new Callback<Cnpj>() {
            @Override
            public void onResponse(Call<Cnpj> call, Response<Cnpj> response) {
                btBuscarCnpj.setEnabled(true);

                if (response.isSuccessful() && response.body() != null) {
                    Cnpj cnpjDetalhado = response.body();
                    StringBuilder infoBuilder = new StringBuilder();
                    infoBuilder.append("CNPJ: ").append(valorOuNaoDisponivel(cnpjDetalhado.getCnpj())).append("\n");
                    infoBuilder.append("Razão Social: ").append(valorOuNaoDisponivel(cnpjDetalhado.getRazao_social())).append("\n");
                    infoBuilder.append("Nome Fantasia: ").append(valorOuNaoDisponivel(cnpjDetalhado.getNome_fantasia())).append("\n");
                    infoBuilder.append("Natureza Jurídica: ").append(valorOuNaoDisponivel(cnpjDetalhado.getNatureza_juridica())).append("\n");
                    infoBuilder.append("Capital Social: ").append(valorOuNaoDisponivel(cnpjDetalhado.getCapital_social())).append("\n");
                    infoBuilder.append("Data Início: ").append(valorOuNaoDisponivel(cnpjDetalhado.getData_inicio())).append("\n");
                    infoBuilder.append("Porte: ").append(valorOuNaoDisponivel(cnpjDetalhado.getPorte())).append("\n");
                    infoBuilder.append("Tipo: ").append(valorOuNaoDisponivel(cnpjDetalhado.getTipo())).append("\n");
                    infoBuilder.append("Telefone 1: ").append(valorOuNaoDisponivel(cnpjDetalhado.getTelefone1())).append("\n");
                    if (cnpjDetalhado.getTelefone2() != null && !cnpjDetalhado.getTelefone2().isEmpty()) {
                        infoBuilder.append("Telefone 2: ").append(cnpjDetalhado.getTelefone2()).append("\n");
                    }
                    infoBuilder.append("E-mail: ").append(valorOuNaoDisponivel(cnpjDetalhado.getEmail())).append("\n\n");
                    if (cnpjDetalhado.getSituacao() != null) {
                        Cnpj.Situacao situacao = cnpjDetalhado.getSituacao();
                        infoBuilder.append("--- Situação ---\n");
                        infoBuilder.append("Nome: ").append(valorOuNaoDisponivel(situacao.getNome())).append("\n");
                        infoBuilder.append("Data: ").append(valorOuNaoDisponivel(situacao.getData())).append("\n");
                        infoBuilder.append("Motivo: ").append(valorOuNaoDisponivel(situacao.getMotivo())).append("\n\n");
                    }
                    if (cnpjDetalhado.getEndereco() != null) {
                        Cnpj.Endereco endereco = cnpjDetalhado.getEndereco();
                        infoBuilder.append("--- Endereço ---\n");
                        infoBuilder.append(valorOuNaoDisponivel(endereco.getTipo_logradouro())).append(" ")
                                .append(valorOuNaoDisponivel(endereco.getLogradouro())).append(", ")
                                .append(valorOuNaoDisponivel(endereco.getNumero())).append("\n");
                        if (endereco.getComplemento() != null && !endereco.getComplemento().isEmpty()) {
                            infoBuilder.append("Complemento: ").append(endereco.getComplemento()).append("\n");
                        }
                        infoBuilder.append("Bairro: ").append(valorOuNaoDisponivel(endereco.getBairro())).append("\n");
                        infoBuilder.append("CEP: ").append(valorOuNaoDisponivel(endereco.getCep())).append("\n");
                        infoBuilder.append("Município: ").append(valorOuNaoDisponivel(endereco.getMunicipio()))
                                .append(" - ").append(valorOuNaoDisponivel(endereco.getUf())).append("\n\n");
                    }
                    if (cnpjDetalhado.getAtividade_principal() != null) {
                        Cnpj.Atividade principal = cnpjDetalhado.getAtividade_principal();
                        infoBuilder.append("--- Atividade Principal ---\n");
                        infoBuilder.append("Código: ").append(valorOuNaoDisponivel(principal.getCodigo())).append("\n");
                        infoBuilder.append("Descrição: ").append(valorOuNaoDisponivel(principal.getDescricao())).append("\n\n");
                    }
                    if (cnpjDetalhado.getAtividades_secundarias() != null && !cnpjDetalhado.getAtividades_secundarias().isEmpty()) {
                        infoBuilder.append("--- Atividades Secundárias ---\n");
                        for (Cnpj.Atividade secundaria : cnpjDetalhado.getAtividades_secundarias()) {
                            infoBuilder.append("- ").append(valorOuNaoDisponivel(secundaria.getDescricao()))
                                    .append(" (Cód: ").append(valorOuNaoDisponivel(secundaria.getCodigo())).append(")\n");
                        }
                        infoBuilder.append("\n");
                    }
                    if (cnpjDetalhado.getSocios() != null && !cnpjDetalhado.getSocios().isEmpty()) {
                        infoBuilder.append("--- Sócios ---\n");
                        for (Cnpj.Socio socio : cnpjDetalhado.getSocios()) {
                            infoBuilder.append("- Nome: ").append(valorOuNaoDisponivel(socio.getNome())).append("\n");
                            infoBuilder.append("  CPF/CNPJ: ").append(valorOuNaoDisponivel(socio.getCpf_cnpj())).append("\n");
                        }
                        infoBuilder.append("\n");
                    }
                    if (cnpjDetalhado.getSimples() != null) {
                        Cnpj.Simples simples = cnpjDetalhado.getSimples();
                        infoBuilder.append("--- Simples Nacional ---\n");
                        infoBuilder.append("Optante: ").append(valorOuNaoDisponivel(simples.getOptante_simples())).append("\n");
                        infoBuilder.append("\n");
                    }
                    if (cnpjDetalhado.getMei() != null) {
                        Cnpj.Mei mei = cnpjDetalhado.getMei();
                        infoBuilder.append("--- MEI ---\n");
                        infoBuilder.append("Optante: ").append(valorOuNaoDisponivel(mei.getOptante_mei())).append("\n");
                        infoBuilder.append("\n");
                    }


                    tvInfoCnpj.setText(infoBuilder.toString());
                } else {
                    tvInfoCnpj.setText("");
                    String errorMessage = "Erro ao buscar informações.";
                    if (response.code() == 400 || response.code() == 404) {
                        errorMessage = "CNPJ inválido ou não encontrado.";
                    } else if (response.code() == 401) {
                        errorMessage = "Token inválido ou não autorizado.";
                    } else if (response.errorBody() != null) {
                        try {
                            String errorBody = response.errorBody().string();
                            androidx.media3.common.util.Log.e("CnpjActivity", "Erro da API: " + errorBody);
                        } catch (Exception e) {
                            androidx.media3.common.util.Log.e("CnpjActivity", "Erro ao ler errorBody", e);
                        }
                    }
                    Toast.makeText(CnpjActivity.this, errorMessage + " (Código: " + response.code() + ")",
                            Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Cnpj> call, Throwable throwable) {
                btBuscarCnpj.setEnabled(true);
                tvInfoCnpj.setText("");
                Toast.makeText(CnpjActivity.this, "Falha na comunicação: " + throwable.getMessage(),
                        Toast.LENGTH_LONG).show();
                androidx.media3.common.util.Log.e("CnpjActivity", "Falha na chamada da API", throwable);
            }
        });
    }
    private String valorOuNaoDisponivel(String valor) {
        if (valor == null || valor.trim().isEmpty()) {
            return "N/D";
        }
        return valor;
    }
}
