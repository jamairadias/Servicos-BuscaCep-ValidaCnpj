package com.example.servicos.model;

public class Cnpj {

    private Boolean valid;
    private String formatted;

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public String getFormatted() {
        return formatted;
    }

    public void setFormatted(String formatted) {
        this.formatted = formatted;
    }

    public String getInformation() {
        if (this.valid != null && this.valid) {
            return "O CNPJ é válido" + (this.formatted != null ? "\n" + this.formatted : "");
        } else {
            return "O CNPJ é inválido";
        }
    }
}
