package sisalfa.android.com.appsisalfa.sisalfa.android.com.app.application;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import sisalfa.android.com.appsisalfa.sisalfa.android.com.app.model.Contexto;
import sisalfa.android.com.appsisalfa.sisalfa.android.com.app.sisalfapi.ContextoService;

public class AddContextoActivity extends AppCompatActivity implements View.OnClickListener{

    private static UrlRequest urlT = new UrlRequest();
    private static final String BASE_URL = urlT.getTesteLocal();
    private EditText mNome;
    private Button mGaleriaBtn, mGravacaoBtn, mEnviarBtn;
    private TextView mLabelgravacao;
    private ImageView mImagemContexto;
    private MediaRecorder mRecorder;
    private String mFileName;
    private static final String LOG_TAG = "Record_log";
    private String encodeAudio;
    private String encodeImage;
    private String userEmail;
    private Boolean successRequest;
    private int SELECT_PICTURE = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contexto);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        this.userEmail = user.getEmail();

        mNome = (EditText)findViewById(R.id.nome);

        mGaleriaBtn = (Button)findViewById(R.id.btn_galeria);
        mGravacaoBtn = (Button)findViewById(R.id.btn_gravar);
        mEnviarBtn = (Button)findViewById(R.id.btn_enviar);

        mGaleriaBtn.setOnClickListener(this);
        mEnviarBtn.setOnClickListener(this);

        mLabelgravacao = (TextView)findViewById(R.id.label_recorder);
        mImagemContexto = (ImageView)findViewById(R.id.img_galeria);

        mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
        mFileName += "/recorded_audio.3gp";

        successRequest = true;

        mGravacaoBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){

                    startRecording();
                    mLabelgravacao.setText("Gravando...");

                }else if(motionEvent.getAction() == MotionEvent.ACTION_UP){

                    stopRecording();
                    mLabelgravacao.setText("Parou de gravar...");
                    audioBase64();
                }


                return false;
            }
        });
    }

    private void audioBase64() {
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

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btn_enviar:
                final Handler handler = new Handler(Looper.getMainLooper());
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        enviarContexto();
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                if(successRequest){
                                    mNome.setText("");
                                    mImagemContexto.setImageBitmap(null);
                                    Toast.makeText(AddContextoActivity.this, "Contexto enviado com sucesso!", Toast.LENGTH_SHORT).show();
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
                    mImagemContexto.setImageBitmap(bitmap);
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

    public void enviarContexto(){
        String nomeContexto = mNome.getText().toString();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        ContextoService service = retrofit.create(ContextoService.class);
        Contexto c = new Contexto();
        c.setPalavra_contexto(nomeContexto);
        c.setAudio(encodeAudio);
        c.setImagem(encodeImage);
        c.setId_usuario(userEmail);

        Call<Boolean> context = service.insertContexto(c);
        context.enqueue(new Callback<Boolean>() {
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
