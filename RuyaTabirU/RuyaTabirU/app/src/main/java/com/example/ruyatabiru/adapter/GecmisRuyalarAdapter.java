package com.example.ruyatabiru.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;  // ImageButton yerine ImageView olacak
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ruyatabiru.R;
import com.example.ruyatabiru.database.FavoriDBHelper;
import com.example.ruyatabiru.model.RuyaYorum;

import java.util.List;

public class GecmisRuyalarAdapter extends RecyclerView.Adapter<GecmisRuyalarAdapter.ViewHolder> {

    private final Context context;
    private final List<RuyaYorum> ruyaYorumListesi;
    private final FavoriDBHelper dbHelper;

    public GecmisRuyalarAdapter(Context context, List<RuyaYorum> ruyaYorumListesi) {
        this.context = context;
        this.ruyaYorumListesi = ruyaYorumListesi;
        this.dbHelper = new FavoriDBHelper(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_gecmis_ruya, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RuyaYorum item = ruyaYorumListesi.get(position);
        holder.txtRuya.setText(item.getRuya());
        holder.txtYorum.setText(item.getYorum());

        // Paylaş butonu
        holder.btnPaylas.setOnClickListener(v -> {
            String paylasMetni = "Rüya: " + item.getRuya() + "\n\nYorum: " + item.getYorum();
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, paylasMetni);
            context.startActivity(Intent.createChooser(intent, "Uygulama ile paylaş"));
        });

        // Favori durumuna göre kalp ikonunu ayarla
        boolean favorideMi = dbHelper.favorideMi(item.getRuya(), item.getYorum());
        if (favorideMi) {
            holder.btnFavori.setImageResource(R.drawable.ic_kalp_dolu);  // dolu kalp
        } else {
            holder.btnFavori.setImageResource(R.drawable.ic_kalp_bos);   // boş kalp
        }

        // Favori butonuna tıklama ile favori ekle/kaldır işlemi
        holder.btnFavori.setOnClickListener(v -> {
            boolean simdiFavori = dbHelper.favorideMi(item.getRuya(), item.getYorum());
            if (simdiFavori) {
                dbHelper.favoridenCikar(item.getRuya(), item.getYorum());
                holder.btnFavori.setImageResource(R.drawable.ic_kalp_bos);
                Toast.makeText(context, "Favoriden çıkarıldı", Toast.LENGTH_SHORT).show();
            } else {
                dbHelper.favoriEkle(item.getRuya(), item.getYorum());
                holder.btnFavori.setImageResource(R.drawable.ic_kalp_dolu);
                Toast.makeText(context, "Favorilere eklendi", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return ruyaYorumListesi != null ? ruyaYorumListesi.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtRuya, txtYorum;
        ImageView btnPaylas, btnFavori;  // ImageButton'dan ImageView'e çevrildi

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtRuya = itemView.findViewById(R.id.txtRuya);
            txtYorum = itemView.findViewById(R.id.txtYorum);
            btnPaylas = itemView.findViewById(R.id.btnPaylas);
            btnFavori = itemView.findViewById(R.id.btnFavori);
        }
    }
}
