package br.ufpb.dcx.sisalfapp;

import android.content.Context;
import android.content.res.AssetManager;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by  Matheus on 03/04/2018.
 */

public class Util {

    private Context context;
    private Properties properties;

    public Util(Context context){
        this.context = context;
        this.properties = new Properties();
    }

    public Properties getProperties(){
        try{
            AssetManager assetManager = context.getAssets();
            InputStream inputStream = assetManager.open("config.properties");
            properties.load(inputStream);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return properties;
    }
}
