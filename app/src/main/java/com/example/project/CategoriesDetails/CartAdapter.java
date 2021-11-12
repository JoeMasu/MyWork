package com.example.project.CategoriesDetails;

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
import com.example.project.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {

    Context context;
    List<CategoryModel> categoryModelList;

    public CartAdapter(Context context, ArrayList<CategoryModel> categoryModelList) {
        this.context = context;
        this.categoryModelList = categoryModelList;
    }

//    public CategorieAdapter(List<CategoryModel> list) {
//    }

    @NonNull
    @NotNull
    @Override
    public CartAdapter.MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_card,parent,false);
        return new CartAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CartAdapter.MyViewHolder holder, int position) {

        CategoryModel categoryModel = categoryModelList.get(position);

        holder.type.setText(categoryModel.getType().toString().trim());
        holder.price.setText(categoryModel.getPrice().toString().trim());
        holder.price1.setText(categoryModel.getPrice().toString().trim());
        Glide.with(holder.img.getContext()).load(categoryModel.getImage()).into(holder.img);
    }

    @Override
    public int getItemCount() {
        return categoryModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView img;
        TextView type,price, price1;
    public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.picCard);
            type = itemView.findViewById(R.id.title2Txt);
            price = itemView.findViewById(R.id.feeEachItem);
            price1 = itemView.findViewById(R.id.totalEachItem);

        }
    }

}
