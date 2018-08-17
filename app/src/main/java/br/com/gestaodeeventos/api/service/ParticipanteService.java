package br.com.gestaodeeventos.api.service;

import br.com.gestaodeeventos.api.model.Participante;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ParticipanteService {

    @GET("Participante/ObterParticipante")
    Call<Participante> getParticipante(@Query("idParticipante") Integer id);

}
