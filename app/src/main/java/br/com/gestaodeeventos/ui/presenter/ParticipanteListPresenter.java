package br.com.gestaodeeventos.ui.presenter;

import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import br.com.gestaodeeventos.R;
import br.com.gestaodeeventos.api.Api;
import br.com.gestaodeeventos.api.model.EventoParticipanteResponse;
import br.com.gestaodeeventos.api.model.Paginador;
import br.com.gestaodeeventos.api.model.Participante;
import br.com.gestaodeeventos.ui.view.ParticipanteListView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ParticipanteListPresenter implements Presenter<ParticipanteListView> {

    private ParticipanteListView view;
    private Set<Call<EventoParticipanteResponse>> participanteCalls;

    @Override
    public void attachView(ParticipanteListView view) {
        this.view = view;
        this.participanteCalls = new HashSet<>();
    }

    public void subscribe() {
        view.showList();
    }

    public void unsubscribe() {
        for (Call<EventoParticipanteResponse> call : participanteCalls) {
            if (call != null && call.isExecuted() && !call.isCanceled())
                call.cancel();
        }
        participanteCalls.clear();
    }

    public void update(Paginador paginador, int id) {
        view.showLoading();

        final Call<EventoParticipanteResponse> call = Api.getInstance()
                .getEventoService()
                .getParticipantes(paginador, id);

        participanteCalls.add(call);
        call.enqueue(new Callback<EventoParticipanteResponse>() {
            @Override
            public void onResponse(@NonNull Call<EventoParticipanteResponse> call, @NonNull Response<EventoParticipanteResponse> response) {
                if (response.isSuccessful()) {
                    Paginador p=response.body().getPaginador();
                    List<Participante> participantes = response.body().getParticipantes();
                    Log.d("Presenter", "ResponseBody --> " + participantes);
                    view.hideLoading();
                    view.showAddedList(p,participantes);
                }
            }

            @Override
            public void onFailure(@NonNull Call<EventoParticipanteResponse> call, @NonNull Throwable t) {
                view.showError(R.string.falha_carregar_participantes);
            }
        });
    }

    public void itemClicked(View v, int position) {
        view.navigateToDetailScreen(v, position);
    }

}
