package com.example.project.chatf.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.example.project.R;
import com.example.project.chatf.model.dataCommunication;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommunicationRecyclerAdapter extends RecyclerView.Adapter<CommunicationRecyclerAdapter.communicationViewHolder> {
    Context context;
    List<dataCommunication> listCommunication = new ArrayList<>();
    List<String> listData;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference("Users");

//EMPTY CONSTRUCTOR
    public CommunicationRecyclerAdapter(Context context, ArrayList<dataCommunication> listCommunication, ArrayList<String> listData) {
    }
//CONSTRUCTOR WITH PARAMETORS
    public CommunicationRecyclerAdapter(Context context, ArrayList<dataCommunication> listCommunication, List<String> listData) {
        this.context = context;
        this.listCommunication = listCommunication;
        this.listData = listData;
    }



    @NonNull
    @Override
    public communicationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_communication, parent, false);

        return new communicationViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull communicationViewHolder holder, int position) {
//        int plus = position + 1;
//        dataCommunication itemPlus = listCommunication.get(plus);
        String itemData = listData.get(position);
//SETTING THE DATE IN THE CHAT
        dataCommunication itemNormal = listCommunication.get(position);
        Date date = new Date();
//        Calendar calendar = Calendar.getInstance();
        Locale locale = new Locale("in", "ID");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MMM/yyyy", locale);
        long yesterday = date.getTime() - (1000 * 60 * 60 * 24);


            database.child(itemNormal.getKey()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    try {
                        Glide.with(context).load(dataSnapshot.child("profile").getValue().toString()).placeholder(R.drawable.profile).into(holder.imageView);
                    }catch (Exception e){

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        if (itemNormal.getDate().equals(itemData)){
            holder.linearDate.setVisibility(View.GONE);

        }
        if (itemNormal.getDate().equals(simpleDateFormat.format(yesterday))){
            holder.textDate.setText("Yesterday");
        }else if (itemNormal.getDate().equals(simpleDateFormat.format(date.getTime()))){
            holder.textDate.setText("Today");
        }else{
            holder.textDate.setText(itemNormal.getDate());
        }
            binView(listCommunication.get(position),holder);
    }

    @Override
    public int getItemCount() {
        return listCommunication.size();
    }

    public class communicationViewHolder extends RecyclerView.ViewHolder {
        TextView de, message, time, textDate;
        CircleImageView imageView;
        LinearLayout linear, linear2, linearDate;
        CardView cardView, cardDate;

        public communicationViewHolder(@NonNull View itemView) {
            super(itemView);
            de= itemView.findViewById(R.id.de);
            message = itemView.findViewById(R.id.message);
            time= itemView.findViewById(R.id.time);
            imageView = itemView.findViewById(R.id.imageView);
            linear = itemView.findViewById(R.id.linear);
            linearDate = itemView.findViewById(R.id.linearDate);
            cardDate = itemView.findViewById(R.id.cardDate);
            cardView = itemView.findViewById(R.id.cardView);
            textDate = itemView.findViewById(R.id.textDate);
            linear2 = itemView.findViewById(R.id.linear2);
        }


    }
    public void  binView(dataCommunication dataCommunication, communicationViewHolder holder) {

        Locale locale = new Locale("in", "ID");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm aa", locale);

        holder.de.setText(dataCommunication.getDe());
        holder.message.setText(dataCommunication.getMessage());
        holder.time.setText(simpleDateFormat.format(dataCommunication.getTime()));

//THE USER SHOULD BE IN THIS COLLECTION IN FIREBASE FIRESTORE DATABASE
//            DocumentReference reference = FirebaseFirestore.getInstance().collection("Users")
//                    .document(FirebaseAuth.getInstance().getCurrentUser().getUid());
//            reference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
//                @Override
//                public void onEvent( DocumentSnapshot value,FirebaseFirestoreException error) {
//                   try {
//                       String srcImage = value.get("profile").toString();
//                       Glide.with(context).load(srcImage).placeholder(R.drawable.profile).into(imageView);
//                   }catch (Exception e){
//                       Toast.makeText(context, "User Profile is null", Toast.LENGTH_SHORT).show();
//                   }
//                }
//            });

        if (dataCommunication.getKey().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
            holder.de.setVisibility(View.GONE);
            holder.imageView.setVisibility(View.GONE);
            holder.linear.setGravity(Gravity.CENTER|Gravity.END);
            holder.linear2.setGravity(Gravity.CENTER|Gravity.END);
            holder.message.setTextColor(context.getResources().getColor(android.R.color.white));
            holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.white));
        }

    }
}
