package com.example.servicos.model;

public class Cnpj {

    private String cnpj;
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

    public String getCnpj() {
        return cnpj;
    }

    public String getRazao_social() {
        return razao_social;
    }

    public String getNome_fantasia() {
        return nome_fantasia;
    }

    public String getNatureza_juridica() {
        return natureza_juridica;
    }

    public String getCapital_social() {
        return capital_social;
    }

    public String getData_inicio() {
        return data_inicio;
    }

    public String getPorte() {
        return porte;
    }

    public String getTipo() {
        return tipo;
    }

    public String getTelefone1() {
        return telefone1;
    }

    public String getTelefone2() {
        return telefone2;
    }

    public String getEmail() {
        return email;
    }

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

    /**"cnpj": "23035415000104",
     "razao_social": "METROPOLES MIDIA E COMUNICACAO LTDA",
     "nome_fantasia": "METROPOLES",
     "natureza_juridica": "Sociedade Empresária Limitada",
     "capital_social": "50000.00",
     "data_inicio": "2015-08-07",
     "porte": "Empresa de Pequeno Porte",
     "tipo": "Matriz",
     "telefone1": "61 32131700",
     "telefone2": null,
     "email": "financeiro@metropoles.com"
     "*/


}
