package br.com.gestaodeeventos.api.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public abstract class BaseModel implements Serializable {

    @SerializedName("Id")
    private int id;
    @SerializedName("Nome")
    private String nome;

    public BaseModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return nome;
    }

}