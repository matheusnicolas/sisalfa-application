package sisalfa.android.com.appsisalfa.sisalfa.android.com.app.application;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sisalfa.android.com.appsisalfa.R;
import sisalfa.android.com.appsisalfa.sisalfa.android.com.app.connection.UrlRequest;
import sisalfa.android.com.appsisalfa.sisalfa.android.com.app.sisalfapi.DesafioService;
import sisalfa.android.com.appsisalfa.sisalfa.android.com.app.model.Desafio;

public class AddDesafioActivity extends AppCompatActivity implements View.OnClickListener{

    private static UrlRequest urlT = new UrlRequest();
    private static final String BASE_URL = urlT.getTesteLocal();
    private EditText ePalavra, eUsuario;

    private Button mCadastroBtn, mGaleriaBtn, mGravarBtn, mPlayBtn;

    private TextView textViewGravar;

    private ImageView imgGaleria;

    private int SELECT_PICTURE = 1;
    private boolean successRequest;
    private String encodeImage;
    private String encodeAudio;
    private String userEmail;
    private MediaRecorder mRecorder;
    private String mFileName = null;
    private static final String LOG_TAG = "Record_log";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_desafio);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        this.userEmail = user.getEmail();
        ePalavra = (EditText)findViewById(R.id.palavra);

        mGravarBtn = (Button)findViewById(R.id.btn_gravar);
        mCadastroBtn = (Button)findViewById(R.id.btn_enviar);
        mGaleriaBtn = (Button)findViewById(R.id.btn_galeria);
        mPlayBtn = (Button)findViewById(R.id.btn_reproduzir);
        textViewGravar = (TextView) findViewById(R.id.audio_recorder);

        imgGaleria = (ImageView)findViewById(R.id.img_galeria);
        mCadastroBtn.setOnClickListener(this);
        mGaleriaBtn.setOnClickListener(this);
        mPlayBtn.setOnClickListener(this);

        successRequest = true;

        mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
        mFileName += "/recorded_audio.3gp";

        mGravarBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){

                    startRecording();
                    textViewGravar.setText("Gravando...");

                }else if(motionEvent.getAction() == MotionEvent.ACTION_UP){

                    stopRecording();
                    textViewGravar.setText("Parou de gravar...");
                    audioBase64();
                }


                return false;
            }
        });



    }

    private void startRecording() {
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setOutputFile(mFileName);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mRecorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }

        mRecorder.start();
    }

    private void stopRecording() {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
    }

    public void audioBase64(){
        try{
            File file = new File(Environment.getExternalStorageDirectory() + "/recorded_audio.3gp");
            FileInputStream fileInputStream = new FileInputStream(file);
            byte[] bytes = new byte[(int)file.length()];
            fileInputStream.read(bytes);
            this.encodeAudio = Base64.encodeToString(bytes, Base64.DEFAULT);
            Log.i("AUDIOCOD", "áudio codificado com sucesso!");
        }catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btn_enviar:
                final Handler handler = new Handler(Looper.getMainLooper());
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        enviarDesafio();
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                if(successRequest){
                                    ePalavra.setText("");
                                    imgGaleria.setImageBitmap(null);
                                    Toast.makeText(AddDesafioActivity.this, "Desafio enviado com sucesso!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
                thread.start();
                break;
            case R.id.btn_galeria:
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
                break;
            case R.id.btn_reproduzir:
                MediaPlayer mediaPlayer = new MediaPlayer();
                try{
                    mediaPlayer.setDataSource(mFileName);
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                }catch(Exception e){
                    e.printStackTrace();
                }
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == Activity.RESULT_OK){
            if(requestCode == SELECT_PICTURE){

                Uri selectedImageUri = data.getData();
                String selectedImagePath = getPath(selectedImageUri);

                InputStream inputStream;
                Bitmap bitmap;

                try {
                    inputStream = getContentResolver().openInputStream(selectedImageUri);
                    bitmap = BitmapFactory.decodeStream(inputStream);
                    imgGaleria.setImageBitmap(bitmap);
                    ByteArrayOutputStream streamOutput = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 30, streamOutput);
                    byte[] bitMapData = streamOutput.toByteArray();
                    String encodedImage2 = Base64.encodeToString(bitMapData, Base64.DEFAULT);
                    this.encodeImage = encodedImage2;
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String getPath(Uri uri){
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if(cursor == null)return null;
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String s = cursor.getString(column_index);
        return s;
    }

    public void enviarDesafio(){
        String palavra = ePalavra.getText().toString();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        DesafioService service = retrofit.create(DesafioService.class);
        Desafio d = new Desafio();
        d.setPalavra_desafio(palavra);
        d.setAudio(encodeAudio);
        d.setImagem(encodeImage);
        d.setId_usuario(userEmail);

        Call<Boolean> challenge = service.insertDesafio(d);
        challenge.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(response.isSuccessful()){
                    if(response.body()){
                        Toast.makeText(getApplicationContext(), "Cadastrado com Sucesso!",  Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(getApplicationContext(), "Não foi cadastrado!", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Erro: " + response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {

            }
        });
    }


}
