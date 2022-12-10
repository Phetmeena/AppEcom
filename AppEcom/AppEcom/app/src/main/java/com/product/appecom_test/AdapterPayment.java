package com.product.appecom_test;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class AdapterPayment extends RecyclerView.Adapter<AdapterPayment.Viewholider> {
    Context context;
    List<Payment> list;

    public AdapterPayment(Context context, List<Payment> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public Viewholider onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View heroView = inflater.inflate(R.layout.item_payment, parent, false);
        Viewholider viewHolder = new Viewholider(heroView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholider holder, int position) {
        Payment product = list.get(position);
        holder.txtTen.setText(product.getName());
        holder.txtGia.setText("Thành tiền: " + product.getThanhtien() + " VND");
        holder.txtInfo.setText("Số lượng: " + product.getSoluong());
        holder.txtDate.setText(product.getDate());
        Glide.with(context)
                .load(list.get(position).getImgView())
                .error(R.mipmap.ecom_logo)
                .into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class Viewholider extends RecyclerView.ViewHolder {
        TextView txtTen, txtGia, txtInfo, txtDate;
        ImageView imageView;

        public Viewholider(@NonNull View itemView) {
            super(itemView);
            txtTen = itemView.findViewById(R.id.txtProductName1);
            txtGia = itemView.findViewById(R.id.txtProductPrice1);
            txtInfo = itemView.findViewById(R.id.txtNumber);
            txtDate = itemView.findViewById(R.id.txtCreated);
            imageView = itemView.findViewById(R.id.imgProduct1);
        }
    }
}
