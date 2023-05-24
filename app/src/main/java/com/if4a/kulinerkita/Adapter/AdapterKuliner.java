package com.if4a.kulinerkita.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.if4a.kulinerkita.API.APIRequestData;
import com.if4a.kulinerkita.API.RetroServer;
import com.if4a.kulinerkita.Activity.MainActivity;
import com.if4a.kulinerkita.Activity.UbahActivity;
import com.if4a.kulinerkita.Model.ModelKuliner;
import com.if4a.kulinerkita.Model.ModelResponse;
import com.if4a.kulinerkita.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterKuliner extends RecyclerView.Adapter<AdapterKuliner.VHKuliner> {
    private Context ctx;
    private List<ModelKuliner> listKuliner;

    public AdapterKuliner(Context ctx, List<ModelKuliner> listKuliner) {
        this.ctx = ctx;
        this.listKuliner = listKuliner;
    }

    @NonNull
    @Override
    public AdapterKuliner.VHKuliner onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View varView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_kuliner, parent, false);
        return new VHKuliner(varView);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterKuliner.VHKuliner holder, int position) {
        ModelKuliner MK = listKuliner.get(position);

        holder.tvId.setText(MK.getId());
        holder.tvNama.setText(MK.getNama());
        holder.tvAsal.setText(MK.getAsal());
        holder.tvDeskripsiSingkat.setText(MK.getDeskripsi_singkat());
    }

    @Override
    public int getItemCount() {
        return listKuliner.size();
    }

    public class VHKuliner extends RecyclerView.ViewHolder {
        TextView tvId, tvNama, tvAsal, tvDeskripsiSingkat;
        public VHKuliner(@NonNull View itemView) {
            super(itemView);

            tvId = itemView.findViewById(R.id.tv_idKuliner);
            tvNama = itemView.findViewById(R.id.tv_namaKuliner);
            tvAsal = itemView.findViewById(R.id.tv_asalKuliner);
            tvDeskripsiSingkat = itemView.findViewById(R.id.tv_deskripsiSingkat);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AlertDialog.Builder pesan = new AlertDialog.Builder(ctx);
                    pesan.setTitle("Perhatian");
                    pesan.setMessage("Operasi apa yang akan dilakukan?");
                    pesan.setCancelable(true);

                    pesan.setNegativeButton("Hapus", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            hapusKuliner(tvId.getText().toString());
                            dialog.dismiss();
                        }
                    });

                    pesan.setPositiveButton("Ubah", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(ctx, UbahActivity.class);
                            intent.putExtra("xId", tvId.getText().toString());
                            intent.putExtra("xNama", tvNama.getText().toString());
                            intent.putExtra("xAsal", tvAsal.getText().toString());
                            intent.putExtra("xDeskripsiSingkat", tvDeskripsiSingkat.getText().toString());
                            ctx.startActivity(intent);
                            dialog.dismiss();
                        }
                    });

                    pesan.show();
                    return false;
                }
            });
        }

        private void hapusKuliner(String idKuliner){
            APIRequestData ARD = RetroServer.konekRetrofit().create(APIRequestData.class);
            Call<ModelResponse> proses = ARD.ardDelete(idKuliner);

            proses.enqueue(new Callback<ModelResponse>() {
                @Override
                public void onResponse(Call<ModelResponse> call, Response<ModelResponse> response) {
                    String kode = response.body().getKode();
                    String pesan = response.body().getPesan();

                    Toast.makeText(ctx, "Kode : " + kode + "\nPesan : " + pesan, Toast.LENGTH_SHORT).show();
                    ((MainActivity) ctx).retriveKuliner();
                }

                @Override
                public void onFailure(Call<ModelResponse> call, Throwable t) {
                    Toast.makeText(ctx, "Gagal Menghubungi Server", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
