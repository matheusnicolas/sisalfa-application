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

    private Context context;
    public static SisalfaService SISALFASERVICE = null;

    protected void loadAPI() {
        try{
            String urlAPI = Util.getProperty("urlapi", context.getApplicationContext());
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(urlAPI)
                        .addConverterFactory(GsonConverterFactory
                            .create()).build();

            SisalfaRetrofitClient.SISALFASERVICE = retrofit.create(SisalfaService.class);
        } catch(IOException e){
            e.printStackTrace();
        }

    }

}
