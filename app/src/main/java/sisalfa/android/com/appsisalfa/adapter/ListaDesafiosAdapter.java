package sisalfa.android.com.appsisalfa.adapter;

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

import sisalfa.android.com.appsisalfa.R;
import sisalfa.android.com.appsisalfa.model.Desafio;

public class ListaDesafiosAdapter extends RecyclerView.Adapter<ListaDesafiosAdapter.ViewHolder>{

    private String directory;
    private List<Desafio> dataset;
    private Context context;

    public ListaDesafiosAdapter(Context context){
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
        final Desafio desafio = dataset.get(position);
        holder.palavraTextView.setText(desafio.getPalavra_desafio());
        //Decodificar a imagem! (O método decodeImageFromBase64ToBitmap converte a imagem de Base64 pra Bitmap)
        Log.i("IMAGEM64", "Código da imagem: " + desafio.getImagem());
        byte[] decodedImage = Base64.decode(desafio.getImagem(), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedImage, 0, decodedImage.length);
        holder.fotoImageView.setImageBitmap(decodedByte);
        directory = Environment.getExternalStorageDirectory().getAbsolutePath();
        directory += "/" + desafio.getPalavra_desafio() + ".3gp";
        byte[] data = Base64.decode(desafio.getAudio(), Base64.DEFAULT);
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
                    mediaPlayer.setDataSource(caminho + "/" + desafio.getPalavra_desafio() + ".3gp");
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

    public void adicionarListaDesafios(List<Desafio> listaDesafio) {
        dataset.addAll(listaDesafio);
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
