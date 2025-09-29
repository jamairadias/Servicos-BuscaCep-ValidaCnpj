package com.example.servicos.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Cnpj {

    private String cnpj;
    @SerializedName("RAZAO_SOCIAL")
    private String razao_social;
    private String nome_fantasia;
    private String natureza_juridica;
    private String capital_social;
    private String data_inicio;
    private String porte;
    private String tipo;
    private String telefone1;
    private String telefone2;
    private String email;

    private Situacao situacao;
    private Endereco endereco;
    private Simples simples;
    private Mei mei;
    private List<Socio> socios;
    private Atividade atividade_principal;
    private List<Atividade> atividades_secundarias;

    public String getCnpj() { return cnpj; }
    public String getRazao_social() { return razao_social; }
    public String getNome_fantasia() { return nome_fantasia; }
    public String getNatureza_juridica() { return natureza_juridica; }
    public String getCapital_social() { return capital_social; }
    public String getData_inicio() { return data_inicio; }
    public String getPorte() { return porte; }
    public String getTipo() { return tipo; }
    public String getTelefone1() { return telefone1; }
    public String getTelefone2() { return telefone2; }
    public String getEmail() { return email; }

    public Situacao getSituacao() { return situacao; }
    public Endereco getEndereco() { return endereco; }
    public Simples getSimples() { return simples; }
    public Mei getMei() { return mei; }
    public List<Socio> getSocios() { return socios; }
    public Atividade getAtividade_principal() { return atividade_principal; }
    public List<Atividade> getAtividades_secundarias() { return atividades_secundarias; }

    public String formatar() {
        return this.razao_social.concat("\n")
                .concat(this.nome_fantasia)
                .concat("\n")
                .concat(this.natureza_juridica)
                .concat("\n")
                .concat(this.telefone1)
                .concat("\n")
                .concat(this.email);
    }

    public static class Situacao {
        private String nome;
        private String data;
        private String motivo;

        public String getNome() { return nome; }
        public String getData() { return data; }
        public String getMotivo() { return motivo; }
    }

    public static class Endereco {
        private String tipo_logradouro;
        private String logradouro;
        private String numero;
        private String complemento;
        private String bairro;
        private String cep;
        private String uf;
        private String municipio;

        public String getTipo_logradouro() { return tipo_logradouro; }
        public String getLogradouro() { return logradouro; }
        public String getNumero() { return numero; }
        public String getComplemento() { return complemento; }
        public String getBairro() { return bairro; }
        public String getCep() { return cep; }
        public String getUf() { return uf; }
        public String getMunicipio() { return municipio; }
    }

    public static class Simples {
        private String optante_simples;
        private String data_opcao;
        private String data_exclusao;

        public String getOptante_simples() { return optante_simples; }
        public String getData_opcao() { return data_opcao; }
        public String getData_exclusao() { return data_exclusao; }
    }

    public static class Mei {
        private String optante_mei;
        private String data_opcao;
        private String data_exclusao;

        public String getOptante_mei() { return optante_mei; }
        public String getData_opcao() { return data_opcao; }
        public String getData_exclusao() { return data_exclusao; }
    }

    public static class Socio {
        private String nome;
        private String cpf_cnpj;
        private String data_entrada;
        private String qualificacao;

        public String getNome() { return nome; }
        public String getCpf_cnpj() { return cpf_cnpj; }
        public String getData_entrada() { return data_entrada; }
        public String getQualificacao() { return qualificacao; }
    }

    public static class Atividade {
        private String codigo;
        private String descricao;

        public String getCodigo() { return codigo; }
        public String getDescricao() { return descricao; }
    }
}
