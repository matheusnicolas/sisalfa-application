package br.ufpb.dcx.sisalfapp.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import br.ufpb.dcx.sisalfapp.R;
import br.ufpb.dcx.sisalfapp.model.Challenge;

public class ChallengeListAdapter extends RecyclerView.Adapter<ChallengeListAdapter.ViewHolder>{

    private String directory;
    private List<Challenge> dataset;
    private Context context;

    public ChallengeListAdapter(Context context){
        this.context = context;
        this.dataset = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_desafio, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Challenge challenge = dataset.get(position);
        holder.palavraTextView.setText(challenge.getPalavra_desafio());
        //Decodificar a imagem! (O método decodeImageFromBase64ToBitmap converte a imagem de Base64 pra Bitmap)
        byte[] decodedImage = Base64.decode(challenge.getImagem(), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedImage, 0, decodedImage.length);
        holder.fotoImageView.setImageBitmap(decodedByte);
        directory = Environment.getExternalStorageDirectory().getAbsolutePath();
        directory += "/" + challenge.getPalavra_desafio() + ".3gp";
        byte[] data = Base64.decode(challenge.getAudio(), Base64.DEFAULT);
        try{
            File file = new File(directory);
            FileOutputStream os = new FileOutputStream(file, true);
            os.write(data);
            os.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        //Reproduzir o áudio que vem do serviço, dentro está o código para realizar a conversão e reproduzir o áudio!
        holder.audioBtnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String caminho =  directory = Environment.getExternalStorageDirectory().getAbsolutePath();
                MediaPlayer mediaPlayer = new MediaPlayer();
                try{
                    mediaPlayer.setDataSource(caminho + "/" + challenge.getPalavra_desafio() + ".3gp");
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                }catch(Exception e){
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public void adicionarListaDesafios(List<Challenge> listChallenge) {
        dataset.addAll(listChallenge);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView fotoImageView;
        private TextView palavraTextView;
        private Button audioBtnView;

        public ViewHolder(View itemView){
            super(itemView);

            fotoImageView = (ImageView)itemView.findViewById(R.id.imageView);
            palavraTextView = (TextView) itemView.findViewById(R.id.textView);
            audioBtnView = (Button)itemView.findViewById(R.id.button);
        }


    }
}
