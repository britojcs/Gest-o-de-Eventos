package br.com.gestaodeeventos.api;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import br.com.gestaodeeventos.App;
import br.com.gestaodeeventos.api.service.EventoService;
import br.com.gestaodeeventos.api.service.ParticipanteService;
import br.com.gestaodeeventos.util.Constants;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Api {

    private static Api instance;
    private EventoService serviceEvento;
    private ParticipanteService serviceParticipante;

    private Api() {
        File httpCacheDirectory = new File(App.getApplicationInstance().getCacheDir(), "GestaoDeEventosHttpCache");
        int cacheSize = 10 * 1024 * 1024;
        Cache cache = new Cache(httpCacheDirectory, cacheSize);

//        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
//        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
//                .addInterceptor(interceptor)
                .addNetworkInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
                .cache(cache)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.REST_URL)
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        serviceEvento = retrofit.create(EventoService.class);
        serviceParticipante = retrofit.create(ParticipanteService.class);
    }

    static Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {

            Request request = chain.request();
            Response response = chain.proceed(request);

            if (App.hasNetwork()) {
                int maxAge = 60 * 60 * 24 * 2;
                return response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public ,max-age=" + maxAge)
                        .build();
            }

            return response;
        }
    };

    public static Api getInstance() {
        if (instance == null)
            instance = new Api();

        return instance;
    }

    public EventoService getEventoService() {
        return serviceEvento;
    }

    public ParticipanteService getParticipanteService() {
        return serviceParticipante;
    }

}
