package com.example.project.CategoriesDetails;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.project.Activity.ShowDetailActivity;
import com.example.project.R;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class CategorieAdapter extends RecyclerView.Adapter<CategorieAdapter.MyViewHolder> {

     Context context;
     List<CategoryModel> categoryModelList;

    public CategorieAdapter(Context context, ArrayList<CategoryModel> categoryModelList) {
        this.context = context;
        this.categoryModelList = categoryModelList;
    }

    @NonNull
    @NotNull
    @Override
    public CategorieAdapter.MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.categoryitems,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CategorieAdapter.MyViewHolder holder, int position) {

       CategoryModel categoryModel = categoryModelList.get(position);
        holder.type.setText(categoryModel.getType().toString().trim());
        holder.price.setText(categoryModel.getPrice().toString().trim());
        Glide.with(holder.img.getContext()).load(categoryModel.getImage()).into(holder.img);
        holder.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), ProductDetails.class);
                intent.putExtra("Type", categoryModel.getType());
                intent.putExtra("Price", categoryModel.getPrice());
                intent.putExtra("Image", categoryModel.getImage());
                intent.putExtra("object", (Parcelable) categoryModelList.get(position));

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView img;
        TextView type,price;
        TextView addBtn;



        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            addBtn = itemView.findViewById(R.id.addBtn);
            img = itemView.findViewById(R.id.image);
            type = itemView.findViewById(R.id.pizza);
            price = itemView.findViewById(R.id.fee);
        }
    }

}
