package br.com.gestaodeeventos.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import br.com.gestaodeeventos.R;
import br.com.gestaodeeventos.api.model.Evento;
import br.com.gestaodeeventos.util.DateUtil;
import br.com.gestaodeeventos.util.ImageUtil;
import br.com.gestaodeeventos.util.ItemClickListener;
import butterknife.BindView;
import butterknife.ButterKnife;

public class EventoAdapter extends RecyclerView.Adapter<EventoAdapter.ImageViewHolder> implements Filterable {

    private Context context;
    private ItemClickListener itemClickListener;
    private List<Evento> eventos = new ArrayList<>();
    private List<Evento> eventosFiltered = new ArrayList<>();

    public EventoAdapter(Context context, ItemClickListener itemClickListener) {
        this.context = context;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_evento, parent, false);

        final ImageViewHolder imageViewHolder = new ImageViewHolder(itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onItemClick(v, imageViewHolder.getAdapterPosition());
            }
        });

        return imageViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        Evento e = eventosFiltered.get(position);

        holder.txtNome.setText(e.getNome());
        holder.txtLocal.setText(e.getLocal());
        holder.txtInicio.setText(DateUtil.formatDateShort(e.getInicio()));

        Glide.with(context)
                .applyDefaultRequestOptions(ImageUtil.imgOptions())
                .load(ImageUtil.base64ToBitmap(e.getImagem()))
                .into(holder.imgEvento);

        Glide.with(context)
                .applyDefaultRequestOptions(ImageUtil.imgOptions())
                .load(ImageUtil.base64ToBitmap(e.getClienteImagem()))
                .thumbnail(0.5f)
                .into(holder.imgCliente);
    }

    @Override
    public int getItemCount() {
        return eventosFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty())
                    eventosFiltered = eventos;
                else {
                    List<Evento> filteredList = new ArrayList<>();
                    for (Evento evento : eventos) {
                        if (evento.getNome().toLowerCase().contains(charString.toLowerCase())
                                || evento.getLocal().toLowerCase().contains(charString.toLowerCase()))
                            filteredList.add(evento);
                    }

                    eventosFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = eventosFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                eventosFiltered = (ArrayList<Evento>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public void addEvento(Evento evento) {
        eventos.add(evento);
        notifyItemChanged(getItemCount() - 1);
    }

    public void addEventos(List<Evento> eventos) {
        this.eventos.addAll(eventos);
        this.eventosFiltered.addAll(eventos);
        notifyDataSetChanged();
    }

    public void replaceAllEventos(List<Evento> eventos) {
        this.eventos.clear();
        this.eventos.addAll(eventos);
        notifyDataSetChanged();
    }

    public List<Evento> getEventos() {
        return eventosFiltered;
    }

    class ImageViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txt_nome)
        TextView txtNome;
        @BindView(R.id.txt_local)
        TextView txtLocal;
        @BindView(R.id.txt_inicio)
        TextView txtInicio;
        @BindView(R.id.img_evento)
        ImageView imgEvento;
        @BindView(R.id.img_cliente)
        ImageView imgCliente;

        ImageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
