package com.example.project.Admin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.project.Activity.SupportActivity;
import com.example.project.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.viewHolder>  {

    Context context;
    List<String> listStrings;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference("Users");
    public MessagesAdapter(Context context, ArrayList<String> listStrings) {
        this.context = context;
        this.listStrings = listStrings;
    }

    @NonNull
    @NotNull
    @Override
    public MessagesAdapter.viewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_tile, parent, false);

        return new viewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MessagesAdapter.viewHolder holder, int position) {
        String id=listStrings.get(position);
        database.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                holder.emailHeader.setText(snapshot.child("email").getValue().toString());
                holder.nameHeader.setText(snapshot.child("full").getValue().toString());
                try {
                    Glide.with(context)
                            .load(snapshot.child("profile").getValue().toString())
                            .centerCrop()
                            .placeholder(R.drawable.ic_launcher_foreground)
                            .into(holder.profile);

                } catch (Exception e) {
//                    Toast.makeText(MainActivity.this, "" + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
                holder.nameHeader.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(context, SupportActivity.class);
                        intent.putExtra("userId",id);
                        context.startActivity(intent);
                    }
                });
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }
    @Override
    public int getItemCount() {
        return listStrings.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        CircleImageView profile;
        TextView nameHeader ,emailHeader;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            profile= itemView.findViewById(R.id.profile);
            nameHeader= itemView.findViewById(R.id.nameHeader);
            emailHeader= itemView.findViewById(R.id.emailHeader);
        }
    }
}
