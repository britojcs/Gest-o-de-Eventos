package br.com.gestaodeeventos.ui.view;

import br.com.gestaodeeventos.api.model.Participante;

public interface ParticipanteView extends View {

    void showLoading();

    void hideLoading();

    void showData(Participante participante);

    void showError(int message);

}
