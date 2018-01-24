package sisalfa.android.com.appsisalfa.sisalfa.android.com.app.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.ArrayList;

import sisalfa.android.com.appsisalfa.R;
import sisalfa.android.com.appsisalfa.sisalfa.android.com.app.model.Desafio;

public class ListaDesafiosAdapter extends RecyclerView.Adapter<ListaDesafiosAdapter.ViewHolder>{

    private ArrayList<Desafio> dataset;
    private Context context;

    public ListaDesafiosAdapter(Context context){
        this.context = context;
        dataset = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_desafio, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Desafio desafio = dataset.get(position);

        if(desafio != null){
            holder.palavraTextView.setText(desafio.getPalavra_desafio());
            //Decodificar a imagem! (O método decodeImageFromBase64ToBitmap converte a imagem de Base64 pra Bitmap)
            Bitmap imagem = decodeImageFromBase64ToBitmap(desafio.getImagem());
            holder.fotoImageView.setImageBitmap(imagem);


            //Reproduzir o áudio que vem do serviço, dentro está o código para realizar a conversão e reproduzir o áudio!
            holder.audioBtnView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int sample = AudioTrack.getNativeOutputSampleRate(AudioManager.STREAM_MUSIC);
                    int size = AudioRecord.getMinBufferSize(sample, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT);
                    AudioTrack audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, sample, AudioFormat.CHANNEL_CONFIGURATION_MONO, AudioFormat.ENCODING_PCM_16BIT, size, AudioTrack.MODE_STREAM);
                    audioTrack.play();
                    byte[] data = Base64.decode(desafio.getAudio(), Base64.DEFAULT);
                    int iRes = audioTrack.write(data, 0, data.length);
                    audioTrack.release();
                }
            });
        }
    }

    private Bitmap decodeImageFromBase64ToBitmap(String encodedImage){
        byte[] decodedImage = Base64.decode(encodedImage, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedImage, 0, decodedImage.length);
        return decodedByte;
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public void adicionarListaDesafios(ArrayList<Desafio> listaDesafio) {
        dataset.addAll(listaDesafio);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView fotoImageView;
        private TextView palavraTextView;
        private Button audioBtnView;



        public ViewHolder(View itemView){
            super(itemView);

            fotoImageView = (ImageView)itemView.findViewById(R.id.decodedimg64);
            palavraTextView = (TextView) itemView.findViewById(R.id.palavraTextView);
            audioBtnView = (Button)itemView.findViewById(R.id.audio_play);
        }


    }
}
