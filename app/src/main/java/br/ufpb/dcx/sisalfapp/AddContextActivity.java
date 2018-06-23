package br.ufpb.dcx.sisalfapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
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
import java.io.FileNotFoundException;
import java.io.InputStream;

import br.ufpb.dcx.sisalfapp.model.ContextM;
import br.ufpb.dcx.sisalfapp.sisalfapi.SisalfaService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddContextActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText mNome, mVideoLink;
    private Button mGaleriaBtn, mGravacaoBtn, mEnviarBtn, mPlayBtn;
    private TextView mLabelgravacao;
    private ImageView mImagemContexto;
    private static final String LOG_TAG = "Record_log";
    private String encodeImage;
    private long userEmail;
    private Boolean successRequest;
    private int SELECT_PICTURE = 1;
    private Recorder recorder = new Recorder();
    private EncoderDecoderClass encoderDecoderClass = new EncoderDecoderClass();
    private ServiceGenerator serviceGenerator = new ServiceGenerator();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contexto);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        this.userEmail = Integer.parseInt(user.getUid());

        mNome = findViewById(R.id.nome);
        mVideoLink = findViewById(R.id.video_link);

        mGaleriaBtn = findViewById(R.id.btn_galeria);
        mGravacaoBtn = findViewById(R.id.btn_gravar);
        mEnviarBtn = findViewById(R.id.btn_enviar);
        mPlayBtn = findViewById(R.id.btn_reproduzir);
        mGaleriaBtn.setOnClickListener(this);
        mEnviarBtn.setOnClickListener(this);
        mPlayBtn.setOnClickListener(this);
        mLabelgravacao = findViewById(R.id.label_recorder);
        mImagemContexto = findViewById(R.id.img_galeria);

        successRequest = true;

        mGravacaoBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){

                    recorder.startRecording();
                    mLabelgravacao.setText("Gravando...");

                }else if(motionEvent.getAction() == MotionEvent.ACTION_UP){

                    recorder.stopRecording();
                    mLabelgravacao.setText("Parou de gravar...");
                    encoderDecoderClass.setEncodedAudio(encoderDecoderClass.audioBase64());
                }else if(motionEvent.getAction() == MotionEvent.ACTION_CANCEL){
                    Toast.makeText(getApplicationContext(), "Pressione e segure o botão de gravar!", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btn_enviar:
                addContext();
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
                    mediaPlayer.setDataSource(Environment.getExternalStorageDirectory().getAbsolutePath() + "/recorded_audio.3gp");
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

    public void addContext(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        long author = Long.parseLong(sharedPreferences.getString("author", "defaultValue"));
        String contextName = mNome.getText().toString();
        String videoLink = mVideoLink.getText().toString();
        ContextM c = new ContextM();
        c.setName(contextName);
        c.setSound(encoderDecoderClass.getEncodedAudio());
        c.setImage(encodeImage);
        c.setId(userEmail);
        c.setVideo(videoLink);
        c.setAuthor(author);
        SisalfaService service = serviceGenerator.loadApiCt(this);
        Call<ContextM> request = service.addContext(c);
        request.enqueue(new Callback<ContextM>() {
            @Override
            public void onResponse(Call<ContextM> call, Response<ContextM> response) {
                Toast.makeText(getApplicationContext(), "Cadastrado com Sucesso!",  Toast.LENGTH_LONG).show();
                mNome.setText("");
                mImagemContexto.setImageBitmap(null);
            }

            @Override
            public void onFailure(Call<ContextM> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Erro: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });


    }


}