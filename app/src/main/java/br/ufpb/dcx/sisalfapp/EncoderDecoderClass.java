package br.ufpb.dcx.sisalfapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;

import com.bumptech.glide.load.model.stream.HttpUriLoader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import retrofit2.http.Url;

/**
 * Created by mathe on 03/04/2018.
 */

public class EncoderDecoderClass {

    private String encodedAudio;

    public EncoderDecoderClass(){

    }

    public String getEncodedAudio() {
        return this.encodedAudio;
    }
    public void setEncodedAudio(String encodedAudio){
        this.encodedAudio = encodedAudio;
    }

    public String audioBase64(){
        try{
            File file = new File(Environment.getExternalStorageDirectory() + "/recorded_audio.3gp");
            FileInputStream fileInputStream = new FileInputStream(file);
            byte[] bytes = new byte[(int)file.length()];
            fileInputStream.read(bytes);
            this.encodedAudio = Base64.encodeToString(bytes, Base64.DEFAULT);
            Log.i("AUDIOCOD", "áudio codificado com sucesso!");
        }catch (FileNotFoundException e){
            Log.i("FILENOTFOUND", "Message: " + e.getMessage());
        } catch (IOException e) {
            Log.i("IOEXCEPTION", "Message: " + e.getMessage());
        }
        return this.encodedAudio;
    }

    public Bitmap getBitmapFromURL(String src){
        try{
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setDoInput(true);
            InputStream input = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(input);
            return bitmap;
        } catch (IOException e) {
            // Log exception
            Log.i("BITMAP",  "Ocorreu um erro no bitmap");
        }
        return null;
    }


}
