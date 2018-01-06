package sisalfa.android.com.appsisalfa.sisalfa.android.com.app.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import sisalfa.android.com.appsisalfa.R;
import sisalfa.android.com.appsisalfa.sisalfa.android.com.app.model.Desafio;

public class ListaDesafiosAdapter extends RecyclerView.Adapter<ListaDesafiosAdapter.ViewHolder>{

    private ArrayList<Desafio> dataset;

    public ListaDesafiosAdapter(){
        dataset = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_desafio, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Desafio d = dataset.get(position);
        holder.palavraTextView.setText(d.getPalavra_desafio());
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public void adicionarListaDesafios(ArrayList<Desafio> listaDesafio) {
        dataset.addAll(listaDesafio);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView fotoImageView;
        private TextView palavraTextView;

        public ViewHolder(View itemView){
            super(itemView);

            fotoImageView = (ImageView)itemView.findViewById(R.id.fotoImageView);
            palavraTextView = (TextView) itemView.findViewById(R.id.palavraTextView);
        }


    }
}
