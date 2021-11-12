package com.example.project.Admin;

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
import com.example.project.CategoriesDetails.CategoryModel;
import com.example.project.CategoriesDetails.ProductDetails;
import com.example.project.CategoriesDetails.UploadImageInfo;
import com.example.project.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class SpecialsAdapter extends RecyclerView.Adapter<SpecialsAdapter.MyViewHolder> {

     Context context;
     List<UploadImageInfo> categoryModelList;

    public SpecialsAdapter(Context context, ArrayList<UploadImageInfo> categoryModelList) {
        this.context = context;
        this.categoryModelList = categoryModelList;
    }

    @NonNull
    @NotNull
    @Override
    public SpecialsAdapter.MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_iteme_retrieved,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull SpecialsAdapter.MyViewHolder holder, int position) {

       UploadImageInfo categoryModel = categoryModelList.get(position);
        holder.type.setText(categoryModel.getImageName().toString().trim());
        holder.description.setText(categoryModel.getDescription().toString().trim());
        Glide.with(holder.img.getContext()).load(categoryModel.getImageURL()).into(holder.img);
//        holder.addBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(holder.itemView.getContext(), ProductDetails.class);
//                intent.putExtra("Type", categoryModel.getType());
//                intent.putExtra("Price", categoryModel.getPrice());
//                intent.putExtra("Image", categoryModel.getImage());
//                intent.putExtra("object", (Parcelable) categoryModelList.get(position));
//
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                holder.itemView.getContext().startActivity(intent);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return categoryModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView img;
        TextView type,orderedBy,description;
        TextView phone;



        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            description = itemView.findViewById(R.id.d);
            img = itemView.findViewById(R.id.i);
            type = itemView.findViewById(R.id.n);
            phone = itemView.findViewById(R.id.number);
            orderedBy = itemView.findViewById(R.id.name);

        }
    }

}
