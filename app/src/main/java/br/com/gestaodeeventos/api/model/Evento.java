package br.com.gestaodeeventos.api.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Evento extends BaseModel implements Serializable {

    @SerializedName("Imagem")
    private String imagem;
    @SerializedName("ClienteImagem")
    private String clienteImagem;
    @SerializedName("Inicio")
    private String inicio;
    @SerializedName("Local")
    private String local;

    public Evento() {
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public String getClienteImagem() {
        return clienteImagem;
    }

    public void setClienteImagem(String clienteImagem) {
        this.clienteImagem = clienteImagem;
    }

    public String getInicio() {
        return inicio;
    }

    public void setInicio(String inicio) {
        this.inicio = inicio;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

}