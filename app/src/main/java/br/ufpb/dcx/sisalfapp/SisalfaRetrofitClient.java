package br.ufpb.dcx.sisalfapp;

import android.content.Context;

import java.io.IOException;

import br.ufpb.dcx.sisalfapp.sisalfapi.SisalfaService;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mathe on 05/04/2018.
 */

public class SisalfaRetrofitClient {

    public static SisalfaService SISALFASERVICE = null;

    protected void loadAPI() {
        Retrofit.Builder builder = new Retrofit.Builder().baseUrl("http://192.168.1.107:8080/meuProjetoWeb/webapi/").addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        SisalfaRetrofitClient.SISALFASERVICE = retrofit.create(SisalfaService.class);
    }

}
