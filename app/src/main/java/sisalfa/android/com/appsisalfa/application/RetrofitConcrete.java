package sisalfa.android.com.appsisalfa.application;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sisalfa.android.com.appsisalfa.retrofinterface.RetrofitInterface;
import sisalfa.android.com.appsisalfa.sisalfapi.SisalfaService;

/**
 * Created by Pichau on 11/03/2018.
 */

public class RetrofitConcrete implements RetrofitInterface {

    private static final String BASE_URL = "http://192.168.0.100:8080/meuProjetoWeb/webapi/";
    private Retrofit retrofit;

    @Override
    public SisalfaService CallSisalfaApi() {
        retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        SisalfaService sisalfaService = retrofit.create(SisalfaService.class);
        return sisalfaService;
    }

}
