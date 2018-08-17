package br.com.gestaodeeventos.ui.view;

import java.util.List;

import br.com.gestaodeeventos.api.model.Evento;

public interface EventoListView extends ListView {

    void showAddedList(List<Evento> eventos);

}
