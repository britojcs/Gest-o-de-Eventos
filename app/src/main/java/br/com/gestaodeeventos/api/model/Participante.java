package br.com.gestaodeeventos.api.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Participante extends BaseModel implements Serializable {

    @SerializedName("Email")
    private String email;
    @SerializedName("CheckIn")
    private String checkin;
    @SerializedName("DataCadastro")
    private String dataCadastro;
    @SerializedName("Assinatura")
    private String assinatura;

    public String getEmail() {
        return email;
    }

    public void setEmail(String imagem) {
        this.email = email;
    }

    public String getCheckin() {
        return checkin;
    }

    public void setCheckin(String checkin) {
        this.checkin = checkin;
    }

    public String getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(String dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public String getAssinatura() {
        return assinatura;
    }

    public void setAssinatura(String assinatura) {
        this.assinatura = assinatura;
    }

}