package com.product.appecom_test;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class AdapterProduct extends RecyclerView.Adapter<AdapterProduct.Viewholider> {
    Context context;
    List<Product> list;
    String id;
    String name;
    String type;

    public AdapterProduct(Context context, List<Product> list, String id, String name, String type) {
        this.context = context;
        this.list = list;
        this.id = id;
        this.type = type;
        this.name = name;
    }

    @NonNull
    @Override
    public Viewholider onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View heroView = inflater.inflate(R.layout.item_sanpham, parent, false);
        Viewholider viewHolder = new Viewholider(heroView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholider holder, int position) {
        Product product = list.get(position);
        holder.txtTen.setText(product.getName());
        holder.txtGia.setText(product.getPrice() + " VND");
        holder.txtInfo.setText(product.getInfoShoes());
        holder.txtDate.setText(product.getDate());
        Glide.with(context)
                .load(list.get(position).getSmallImageUrl())
                .error(R.mipmap.ecom_logo)
                .into(holder.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                if (type.equals("CUSTOMER")) {
                    intent = new Intent(context, DetailProductActivity.class);
                    if (!product.getId_cart().equals("")) {
                        intent.putExtra("id_cart", product.getId_cart());
                    }
                    intent.putExtra("object", product);
                    intent.putExtra("id", id);
                    intent.putExtra("name", name);
                }
                if (type.equals("SALESMAN")) {
                    intent = new Intent(context, UpdateActivity.class);
                    intent.putExtra("object", product);
                }
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
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
            txtTen = itemView.findViewById(R.id.txtProductName);
            txtGia = itemView.findViewById(R.id.txtProductPrice);
            txtInfo = itemView.findViewById(R.id.txtInformation);
            txtDate = itemView.findViewById(R.id.txtCreatedDate);
            imageView = itemView.findViewById(R.id.imgProduct);
        }
    }
}
