package com.example.project.Frontcategories;

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
import com.example.project.CategoriesDetails.CategorieAdapter;
import com.example.project.CategoriesDetails.Categories;
import com.example.project.CategoriesDetails.CategoryModel;
import com.example.project.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class FrontCatAdapter extends RecyclerView.Adapter<FrontCatAdapter.MyViewHolder> {
    Context context;
    List<FrontCatModel> frontCatModelList;

    public FrontCatAdapter(Context context, List<FrontCatModel> frontCatModelList) {
        this.context = context;
        this.frontCatModelList = frontCatModelList;
    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.viewholder_cat,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull FrontCatAdapter.MyViewHolder holder, int position) {
        FrontCatModel frontCatModel = frontCatModelList.get(position);
        holder.Tittle.setText(frontCatModel.getTittle().toString().trim());
        Glide.with(holder.img.getContext()).load(frontCatModel.getImg()).into(holder.img);
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Categories.class);
//                intent.putExtra("ImageName", frontCatModel.getImg());
//                intent.putExtra("Tittle",frontCatModel.getTittle());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return frontCatModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView Tittle;
        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.categoryPic);
            Tittle = itemView.findViewById(R.id.categoryName);
        }
    }
}
