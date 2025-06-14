package com.example.ruyatabiru.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ruyatabiru.R;
import com.example.ruyatabiru.activity.AltKategoriActivity;
import com.example.ruyatabiru.model.Kategori;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class KategoriAdapter extends RecyclerView.Adapter<KategoriAdapter.KategoriViewHolder> implements Filterable {

    private final Context context;
    private List<Kategori> kategoriList; // Güncel liste (filtreli olabilir)
    private final List<Kategori> kategoriListFull; // Orijinal tüm liste

    public KategoriAdapter(Context context, List<Kategori> kategoriList) {
        this.context = context;
        this.kategoriList = new ArrayList<>(kategoriList);
        this.kategoriListFull = new ArrayList<>(kategoriList);
    }

    @NonNull
    @Override
    public KategoriViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_kategori, parent, false);
        return new KategoriViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KategoriViewHolder holder, int position) {
        Kategori kategori = kategoriList.get(position);
        holder.kategoriAd.setText(kategori.getAd());
        holder.kategoriIkon.setImageResource(kategori.getIkon());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, AltKategoriActivity.class);
            intent.putExtra("kategoriAdi", kategori.getAd());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return kategoriList.size();
    }

    public static class KategoriViewHolder extends RecyclerView.ViewHolder {
        ImageView kategoriIkon;
        TextView kategoriAd;

        public KategoriViewHolder(@NonNull View itemView) {
            super(itemView);
            kategoriIkon = itemView.findViewById(R.id.kategoriIkon);
            kategoriAd = itemView.findViewById(R.id.kategoriAd);
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<Kategori> filteredList = new ArrayList<>();

                if (constraint == null || constraint.length() == 0) {
                    filteredList.addAll(kategoriListFull);
                } else {
                    String filterPattern = constraint.toString().toLowerCase(new Locale("tr", "TR")).trim();

                    for (Kategori item : kategoriListFull) {
                        if (item.getAd().toLowerCase(new Locale("tr", "TR")).contains(filterPattern)) {
                            filteredList.add(item);
                        }
                    }
                }

                FilterResults results = new FilterResults();
                results.values = filteredList;
                return results;
            }

            @Override
            @SuppressWarnings("unchecked")
            protected void publishResults(CharSequence constraint, FilterResults results) {
                kategoriList.clear();
                if (results.values != null) {
                    kategoriList.addAll((List<Kategori>) results.values);
                }
                notifyDataSetChanged();
            }
        };
    }
}
