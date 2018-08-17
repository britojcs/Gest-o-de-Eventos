package br.com.gestaodeeventos.ui.view;

import java.util.List;

import br.com.gestaodeeventos.api.model.Paginador;
import br.com.gestaodeeventos.api.model.Participante;

public interface ParticipanteListView extends ListView {

    void showAddedList(Paginador paginador, List<Participante> participante);

}
