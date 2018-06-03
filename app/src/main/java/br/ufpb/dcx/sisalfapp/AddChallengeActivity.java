package br.ufpb.dcx.sisalfapp;
    /*
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
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

import br.ufpb.dcx.sisalfapp.model.Challenge;
import br.ufpb.dcx.sisalfapp.model.User;
import br.ufpb.dcx.sisalfapp.sisalfapi.SisalfaService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddChallengeActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText ePalavra;
    private Button mCadastroBtn, mGaleriaBtn, mGravarBtn, mPlayBtn;
    private TextView textViewGravar;
    private ImageView imgGaleria;
    private int SELECT_PICTURE = 1;
    private boolean successRequest;
    private String encodeImage;
    private long userEmail;
    private Recorder recorder = new Recorder();
    private EncoderDecoderClass encoderDecoderClass = new EncoderDecoderClass();
    private ServiceGenerator serviceGenerator = new ServiceGenerator();
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_desafio);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        this.userEmail = Long.parseLong(user.getUid());
        ePalavra = findViewById(R.id.palavra);

        mGravarBtn = findViewById(R.id.btn_gravar);
        mCadastroBtn = findViewById(R.id.btn_enviar);
        mGaleriaBtn = findViewById(R.id.btn_galeria);
        mPlayBtn = findViewById(R.id.btn_reproduzir);
        textViewGravar = findViewById(R.id.audio_recorder);

        imgGaleria = findViewById(R.id.img_galeria);
        mCadastroBtn.setOnClickListener(this);
        mGaleriaBtn.setOnClickListener(this);
        mPlayBtn.setOnClickListener(this);

        successRequest = true;

        mGravarBtn.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    recorder.startRecording();
                    textViewGravar.setText("Gravando...");

                }else if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                    recorder.stopRecording();
                    textViewGravar.setText("Gravação parou...");
                    encoderDecoderClass.setEncodedAudio(encoderDecoderClass.audioBase64());
                }
                return false;
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btn_enviar:
                sendChallenge();
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

    public void sendChallenge(){
        String palavra = ePalavra.getText().toString();
        Challenge d = new Challenge();
        d.setWord(palavra);
        d.setSound(encoderDecoderClass.getEncodedAudio());
        d.setImage(encodeImage);
        d.setId(userEmail);
        SisalfaService service = serviceGenerator.loadApiCt(this);
        Call<Challenge> request = service.addChallenge(d);
        request.enqueue(new Callback<Challenge>() {
            @Override
            public void onResponse(Call<Challenge> call, Response<Challenge> response) {
                if(response.isSuccessful()){
                    if (response.body() != null) {
                        ePalavra.setText("");
                        imgGaleria.setImageBitmap(null);
                        Toast.makeText(getApplicationContext(), "Cadastrado com Sucesso!",  Toast.LENGTH_LONG).show();
                    }
                }


            }

            @Override
            public void onFailure(Call<Challenge> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Erro: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }


}
*/