package com.example.ruyatabiru.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ruyatabiru.R;
import com.example.ruyatabiru.activity.TabirDetayActivity;
import com.example.ruyatabiru.database.FavoriDBHelper;
import com.example.ruyatabiru.model.AltKategori;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AltKategoriAdapter extends RecyclerView.Adapter<AltKategoriAdapter.ViewHolder> implements Filterable {

    private final Context context;
    private List<AltKategori> altKategoriList;
    private final List<AltKategori> altKategoriListFull;
    private final FavoriDBHelper dbHelper;

    public AltKategoriAdapter(Context context, List<AltKategori> altKategoriList) {
        this.context = context;
        this.altKategoriList = new ArrayList<>(altKategoriList);
        this.altKategoriListFull = new ArrayList<>(altKategoriList);
        this.dbHelper = new FavoriDBHelper(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_alt_kategori, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AltKategori altKategori = altKategoriList.get(position);
        holder.txtBaslik.setText(altKategori.getBaslik());

        // Favori durumu: başlık + açıklama ile kontrol
        boolean favoriMi = dbHelper.favorideMi(altKategori.getBaslik(), altKategori.getAciklama());
        altKategori.setFavori(favoriMi);
        holder.kalpImage.setImageResource(favoriMi ? R.drawable.ic_kalp_dolu : R.drawable.ic_kalp_bos);

        // Kalp tıklama
        holder.kalpImage.setOnClickListener(v -> {
            boolean mevcut = altKategori.isFavori();
            altKategori.setFavori(!mevcut);

            if (altKategori.isFavori()) {
                holder.kalpImage.setImageResource(R.drawable.ic_kalp_dolu);
                dbHelper.favoriEkle(altKategori.getBaslik(), altKategori.getAciklama());
                Toast.makeText(context, "Favoriye eklendi", Toast.LENGTH_SHORT).show();
            } else {
                holder.kalpImage.setImageResource(R.drawable.ic_kalp_bos);
                dbHelper.favoridenCikar(altKategori.getBaslik(), altKategori.getAciklama());
                Toast.makeText(context, "Favoriden çıkarıldı", Toast.LENGTH_SHORT).show();
            }
        });

        // Paylaş tıklama
        holder.paylasButton.setOnClickListener(v -> {
            String paylasMetni = altKategori.getBaslik() + "\n\n" + altKategori.getAciklama();
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_SUBJECT, "Rüya Yorumu");
            intent.putExtra(Intent.EXTRA_TEXT, paylasMetni);
            context.startActivity(Intent.createChooser(intent, "Paylaş"));
        });

        // Detay sayfasına geçiş
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, TabirDetayActivity.class);
            intent.putExtra("baslik", altKategori.getBaslik());
            intent.putExtra("aciklama", altKategori.getAciklama());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return altKategoriList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtBaslik;
        ImageView kalpImage;
        ImageButton paylasButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtBaslik = itemView.findViewById(R.id.altKategoriBaslik);
            kalpImage = itemView.findViewById(R.id.favoriKalp);
            paylasButton = itemView.findViewById(R.id.btnPaylas);
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<AltKategori> filtrelenmisListe = new ArrayList<>();
                if (constraint == null || constraint.length() == 0) {
                    filtrelenmisListe.addAll(altKategoriListFull);
                } else {
                    String filtre = constraint.toString().toLowerCase(Locale.getDefault()).trim();
                    for (AltKategori item : altKategoriListFull) {
                        if (item.getBaslik().toLowerCase(Locale.getDefault()).contains(filtre)) {
                            filtrelenmisListe.add(item);
                        }
                    }
                }

                FilterResults results = new FilterResults();
                results.values = filtrelenmisListe;
                return results;
            }

            @Override
            @SuppressWarnings("unchecked")
            protected void publishResults(CharSequence constraint, FilterResults results) {
                altKategoriList.clear();
                if (results.values != null) {
                    altKategoriList.addAll((List<AltKategori>) results.values);
                }
                notifyDataSetChanged();
            }
        };
    }
}
