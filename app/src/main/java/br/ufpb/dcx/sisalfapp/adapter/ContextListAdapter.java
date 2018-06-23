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

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import br.ufpb.dcx.sisalfapp.EncoderDecoderClass;
import br.ufpb.dcx.sisalfapp.R;
import br.ufpb.dcx.sisalfapp.model.ContextM;


public class ContextListAdapter extends RecyclerView.Adapter<ContextListAdapter.ViewHolder>{

    private String directory;
    private List<ContextM> dataset;
    private Context context;
    private EncoderDecoderClass edc = new EncoderDecoderClass();

    public ContextListAdapter(Context context){
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
        final ContextM contextM = dataset.get(position);
        holder.contextoTextView.setText(contextM.getName());
        Picasso.get().load(contextM.getImage()).into(holder.image);

        /*
        Bitmap decoded = edc.getBitmapFromURL(contextM.getImage());
        Log.i("DEC", decoded.toString());
        holder.fotoImageView.setImageBitmap(decoded);
        */
        directory = Environment.getExternalStorageDirectory().getAbsolutePath();
        directory += "/" + contextM.getName() + ".3gp";
        byte[] data = Base64.decode(contextM.getSound(), Base64.DEFAULT);
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
                    mediaPlayer.setDataSource(caminho + "/" + contextM.getName() + ".3gp");
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                }catch(Exception e){
                    e.printStackTrace();
                }

            }
        });
    }

    public void adicionarListaContextos(List<ContextM> listaContextM) {
        dataset.addAll(listaContextM);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView image;
        private TextView contextoTextView;
        private Button audioBtnView;

        public ViewHolder(View itemView){
            super(itemView);

            image = itemView.findViewById(R.id.imageView);
            contextoTextView = itemView.findViewById(R.id.textView);
            audioBtnView = itemView.findViewById(R.id.button);

        }


    }
}
