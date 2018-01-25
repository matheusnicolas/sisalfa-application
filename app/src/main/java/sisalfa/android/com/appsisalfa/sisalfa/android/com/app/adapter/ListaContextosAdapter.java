package sisalfa.android.com.appsisalfa.sisalfa.android.com.app.adapter;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Base64;
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
import sisalfa.android.com.appsisalfa.sisalfa.android.com.app.model.Contexto;


public class ListaContextosAdapter extends RecyclerView.Adapter<ListaContextosAdapter.ViewHolder>{

    private String directory;
    private List<Contexto> dataset;
    private Context context;

    public ListaContextosAdapter(Context context){
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
        final Contexto contexto = dataset.get(position);
        holder.contextoTextView.setText(contexto.getPalavra_contexto());
        byte[] decodedImage = Base64.decode(contexto.getImagem(), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedImage, 0, decodedImage.length);
        holder.fotoImageView.setImageBitmap(decodedByte);
        directory = Environment.getExternalStorageDirectory().getAbsolutePath();
        directory += "/" + contexto.getPalavra_contexto() + ".3gp";
        byte[] data = Base64.decode(contexto.getAudio(), Base64.DEFAULT);
        try{
            File file = new File(directory);
            FileOutputStream os = new FileOutputStream(file, true);
            os.write(data);
            os.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        holder.audioBtnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String caminho =  directory = Environment.getExternalStorageDirectory().getAbsolutePath();
                MediaPlayer mediaPlayer = new MediaPlayer();
                try{
                    mediaPlayer.setDataSource(caminho + "/" + contexto.getPalavra_contexto() + ".3gp");
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                }catch(Exception e){
                    e.printStackTrace();
                }

            }
        });
    }

    public void adicionarListaContextos(List<Contexto> listaContexto) {
        dataset.addAll(listaContexto);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView fotoImageView;
        private TextView contextoTextView;
        private Button audioBtnView;

        public ViewHolder(View itemView){
            super(itemView);

            fotoImageView = (ImageView)itemView.findViewById(R.id.imageView);
            contextoTextView = (TextView) itemView.findViewById(R.id.textView);
            audioBtnView = (Button)itemView.findViewById(R.id.button);

        }


    }
}
