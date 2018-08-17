package br.com.gestaodeeventos.api.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Paginador implements Serializable {

    @SerializedName("Pagina")
    private int pagina;
    @SerializedName("TotalPaginas")
    private int totalPaginas;
    @SerializedName("RegistrosPorPagina")
    private int registrosPorPagina;
    @SerializedName("TotalRegistros")
    private int totalRegistros;

    public Paginador() {
    }

    public int getPagina() {
        return pagina;
    }

    public void setPagina(int pagina) {
        this.pagina = pagina;
    }

    public int getTotalPaginas() {
        return totalPaginas;
    }

    public void setTotalPaginas(int totalPaginas) {
        this.totalPaginas = totalPaginas;
    }

    public int getRegistrosPorPagina() {
        return registrosPorPagina;
    }

    public void setRegistrosPorPagina(int registrosPorPagina) {
        this.registrosPorPagina = registrosPorPagina;
    }

    public int getTotalRegistros() {
        return totalRegistros;
    }

    public void setTotalRegistros(int totalRegistros) {
        this.totalRegistros = totalRegistros;
    }

}