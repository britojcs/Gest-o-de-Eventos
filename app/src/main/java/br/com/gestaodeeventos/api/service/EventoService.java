package br.com.gestaodeeventos.api.service;

import java.util.List;

import br.com.gestaodeeventos.api.model.Evento;
import br.com.gestaodeeventos.api.model.EventoParticipanteResponse;
import br.com.gestaodeeventos.api.model.Paginador;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface EventoService {

    @GET("Evento/EventosAtivosDoCliente")
    Call<List<Evento>> getEventos(@Query("idCliente") Integer id);

    @POST("Evento/ParticipantesDoEvento")
    Call<EventoParticipanteResponse> getParticipantes(
            @Body Paginador paginador,
            @Query("idEvento") Integer id
    );

}