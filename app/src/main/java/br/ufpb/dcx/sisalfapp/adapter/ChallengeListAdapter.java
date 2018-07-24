package br.ufpb.dcx.sisalfapp.adapter;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import br.ufpb.dcx.sisalfapp.EncoderDecoderClass;
import br.ufpb.dcx.sisalfapp.R;
import br.ufpb.dcx.sisalfapp.model.Challenge;

public class ChallengeListAdapter extends RecyclerView.Adapter<ChallengeListAdapter.ViewHolder>{


    private String directory;
    private List<Challenge> dataset;
    private Context context;
    private EncoderDecoderClass edc = new EncoderDecoderClass();

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
        holder.palavraTextView.setText("Palavra: " + challenge.getWord());

        //holder.context.setText("Contexto: " + challenge.getContext().getName());
        Picasso.get().load(challenge.getImage()).into(holder.ïmage);
        /*
        Bitmap decoded = edc.getBitmapFromURL("https://app.sisalfa.dcx.ufpb.br/v1/" + challenge.getImage());
        //Log.i("");
        holder.ïmage.setImageBitmap(decoded);

        */
        directory = Environment.getExternalStorageDirectory().getAbsolutePath();
        directory += "/" + challenge.getWord() + ".3gp";
        byte[] data = Base64.decode(challenge.getSound(), Base64.DEFAULT);
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
                    mediaPlayer.setDataSource(caminho + "/" + challenge.getWord() + ".3gp");
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

        private ImageView ïmage;
        private TextView palavraTextView;
        private ImageButton audioBtnView;

        public ViewHolder(View itemView){
            super(itemView);

            ïmage = itemView.findViewById(R.id.imageView);
            palavraTextView =  itemView.findViewById(R.id.text_view_description);
            audioBtnView = itemView.findViewById(R.id.play_button);
        }



    }
 }