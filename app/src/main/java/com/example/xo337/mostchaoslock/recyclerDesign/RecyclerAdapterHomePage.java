package com.example.xo337.mostchaoslock.recyclerDesign;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.xo337.mostchaoslock.R;
import com.example.xo337.mostchaoslock.firebase.JavaBeanMyDevice;

import java.util.ArrayList;

public class RecyclerAdapterHomePage extends RecyclerView.Adapter<RecyclerAdapterHomePage.RecyclerViewHolder>{
    Context context;
    ArrayList<JavaBeanMyDevice> arrayList;
    String firebaseUid;

    public RecyclerAdapterHomePage(Context context, ArrayList arrayList, String firebaseUid) {
        this.context = context;
        this.arrayList = arrayList;
        this.firebaseUid = firebaseUid;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_list_view,viewGroup,false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder recyclerViewHolder, int i) {
        recyclerViewHolder.recyclerButton.setText(arrayList.get(i).getDeviceName());
        recyclerViewHolder.recyclerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder{
        Button recyclerButton;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            recyclerButton = itemView.findViewById(R.id.recyclerButton);
        }
    }

}
