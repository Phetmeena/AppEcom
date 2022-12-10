package com.product.appecom_test;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterComment extends RecyclerView.Adapter<AdapterComment.Viewholider> {
    Context context;
    List<Comment> list;

    public AdapterComment(Context context, List<Comment> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public Viewholider onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View heroView = inflater.inflate(R.layout.item_comment, parent, false);
        Viewholider viewHolder = new Viewholider(heroView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholider holder, int position) {
        Comment comment = list.get(position);

        holder.txtUsername.setText(comment.getName());
        holder.txtComment.setText(comment.getComment());
        holder.txtDate.setText(comment.getDate());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class Viewholider extends RecyclerView.ViewHolder {
        TextView txtUsername, txtComment, txtDate;

        public Viewholider(@NonNull View itemView) {
            super(itemView);
            txtUsername = itemView.findViewById(R.id.txtUserName);
            txtComment = itemView.findViewById(R.id.txtCommentN);
            txtDate = itemView.findViewById(R.id.txtDatetime);
        }
    }
}
