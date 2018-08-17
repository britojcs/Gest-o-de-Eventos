package br.com.gestaodeeventos.ui.presenter;

import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import br.com.gestaodeeventos.R;
import br.com.gestaodeeventos.api.Api;
import br.com.gestaodeeventos.api.model.Evento;
import br.com.gestaodeeventos.ui.view.EventoListView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventoListPresenter implements Presenter<EventoListView> {

    private EventoListView view;
    private Set<Call<List<Evento>>> eventoCalls;

    @Override
    public void attachView(EventoListView view) {
        this.view = view;
        this.eventoCalls = new HashSet<>();
    }

    public void subscribe() {
        view.showList();
    }

    public void unsubscribe() {
        for (Call<List<Evento>> call : eventoCalls) {
            if (call != null && call.isExecuted() && !call.isCanceled())
                call.cancel();
        }
        eventoCalls.clear();
    }

    public void update(int id) {
        view.showLoading();

        final Call<List<Evento>> call = Api.getInstance()
                .getEventoService()
                .getEventos(id);

        eventoCalls.add(call);
        call.enqueue(new Callback<List<Evento>>() {
            @Override
            public void onResponse(@NonNull Call<List<Evento>> call, @NonNull Response<List<Evento>> response) {
                if (response.isSuccessful()) {
                    List<Evento> eventos = response.body();
                    Log.d("Presenter", "ResponseBody --> " + eventos);
                    view.hideLoading();
                    view.showAddedList(eventos);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Evento>> call, @NonNull Throwable t) {
                view.showError(R.string.falha_carregar_eventos);
            }
        });
    }

    public void itemClicked(View v, int position) {
        view.navigateToDetailScreen(v, position);
    }

}
