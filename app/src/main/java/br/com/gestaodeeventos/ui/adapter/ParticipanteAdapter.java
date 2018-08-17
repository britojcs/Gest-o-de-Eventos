package br.com.gestaodeeventos.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.gestaodeeventos.R;
import br.com.gestaodeeventos.api.model.Participante;
import br.com.gestaodeeventos.util.DateUtil;
import br.com.gestaodeeventos.util.ItemClickListener;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ParticipanteAdapter extends RecyclerView.Adapter<ParticipanteAdapter.ImageViewHolder> implements Filterable {

    private Context context;
    private ItemClickListener itemClickListener;
    private List<Participante> participantes = new ArrayList<>();
    private List<Participante> participantesFiltered = new ArrayList<>();

    public ParticipanteAdapter(Context context, ItemClickListener itemClickListener) {
        this.context = context;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_participante, parent, false);

        final ImageViewHolder imageViewHolder = new ImageViewHolder(itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onItemClick(v, imageViewHolder.getAdapterPosition());
            }
        });

        return imageViewHolder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        Participante p = participantesFiltered.get(position);

        holder.txtNome.setText(p.getNome());
        holder.txtCodigo.setText(String.valueOf(p.getId()));

        if (p.getCheckin() == null || p.getCheckin().equals(""))
            holder.txtCheckIn.setVisibility(View.INVISIBLE);
        else {
            holder.txtCheckIn.setVisibility(View.VISIBLE);
            holder.txtCheckIn.setText(context.getString(R.string.checkin) + " " + DateUtil.formatDateLong(p.getCheckin()));
        }
    }

    @Override
    public int getItemCount() {
        return participantesFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty())
                    participantesFiltered = participantes;
                else {
                    List<Participante> filteredList = new ArrayList<>();
                    for (Participante participante : participantes) {
                        if (participante.getNome().toLowerCase().contains(charString.toLowerCase()))
                            filteredList.add(participante);
                    }

                    participantesFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = participantesFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                participantesFiltered = (ArrayList<Participante>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public void addParticipante(Participante participante) {
        participantes.add(participante);
        notifyItemChanged(getItemCount() - 1);
    }

    public void addParticipantes(List<Participante> participantes) {
        this.participantes.addAll(participantes);
        this.participantesFiltered.addAll(participantes);
        notifyDataSetChanged();
    }

    public void replaceAllParticipantes(List<Participante> participantes) {
        this.participantes.clear();
        this.participantes.addAll(participantes);
        notifyDataSetChanged();
    }

    public List<Participante> getParticipantes() {
        return participantesFiltered;
    }

    class ImageViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txt_nome)
        TextView txtNome;
        @BindView(R.id.txt_codigo)
        TextView txtCodigo;
        @BindView(R.id.txt_checkin)
        TextView txtCheckIn;

        ImageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
