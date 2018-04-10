package br.ufpb.dcx.sisalfapp;

import android.content.Context;

import java.util.Properties;

import br.ufpb.dcx.sisalfapp.sisalfapi.SisalfaService;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mathe on 05/04/2018.
 */

public class ServiceGenerator {

    public static SisalfaService SISALFASERVICE = null;
    private Util propertyReader;
    private Properties properties;

    public SisalfaService loadApiCt(Context context){
        propertyReader = new Util(context);
        properties = propertyReader.getProperties();
        String url = properties.getProperty("apiurlteste");
        System.out.println("URL AQUI: " + url);
        Retrofit retrofit = new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build();
        return retrofit.create(SisalfaService.class);
    }

}
