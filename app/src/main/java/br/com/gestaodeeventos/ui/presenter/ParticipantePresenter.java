package br.com.gestaodeeventos.ui.presenter;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.HashSet;
import java.util.Set;

import br.com.gestaodeeventos.R;
import br.com.gestaodeeventos.api.Api;
import br.com.gestaodeeventos.api.model.Participante;
import br.com.gestaodeeventos.ui.view.ParticipanteView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ParticipantePresenter implements Presenter<ParticipanteView> {

    private ParticipanteView view;
    private Set<Call<Participante>> eventoCalls;

    @Override
    public void attachView(ParticipanteView view) {
        this.view = view;
        this.eventoCalls = new HashSet<>();
    }

    public void subscribe() {
        view.showData(null);
    }

    public void update(int id) {
        view.showLoading();

        final Call<Participante> call = Api.getInstance()
                .getParticipanteService()
                .getParticipante(id);

        eventoCalls.add(call);
        call.enqueue(new Callback<Participante>() {
            @Override
            public void onResponse(@NonNull Call<Participante> call, @NonNull Response<Participante> response) {
                if (response.isSuccessful()) {
                    Participante participante = response.body();
                    Log.d("Presenter", "ResponseBody --> " + participante);
                    view.hideLoading();
                    view.showData(participante);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Participante> call, @NonNull Throwable t) {
                view.showError(R.string.falha_carregar_participante);
            }
        });
    }

}
