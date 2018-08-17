package br.com.gestaodeeventos.api.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class EventoParticipanteResponse implements Serializable {

    @SerializedName("Paginador")
    private Paginador paginador;
    @SerializedName("Lista")
    private List<Participante> participantes;

    public EventoParticipanteResponse() {
    }

    public Paginador getPaginador() {
        return paginador;
    }

    public void setPaginador(Paginador paginador) {
        this.paginador = paginador;
    }

    public List<Participante> getParticipantes() {
        return participantes;
    }

    public void setParticipantes(List<Participante> participantes) {
        this.participantes = participantes;
    }

}