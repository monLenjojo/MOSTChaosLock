package com.example.xo337.mostchaoslock.recyclerDesign;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.xo337.mostchaoslock.R;
import com.example.xo337.mostchaoslock.firebase.JavaBeanPassRecord;

import java.util.ArrayList;

public class RecyclerAdapterControlPage extends RecyclerView.Adapter<RecyclerAdapterControlPage.RecyclerViewHolder>{
    Context context;
    ArrayList<JavaBeanPassRecord> arrayList;
    String firebaseId;
    ImageView imageView;

    public RecyclerAdapterControlPage(Context context, ArrayList<JavaBeanPassRecord> arrayList, String firebaseId, ImageView imageView) {
        this.context = context;
        this.arrayList = arrayList;
        this.firebaseId = firebaseId;
        this.imageView = imageView;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_pass_record_list_view,viewGroup,false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder recyclerViewHolder, int i) {
        recyclerViewHolder.name.setText(arrayList.get(i).getName());
        recyclerViewHolder.time.setText(arrayList.get(i).getTime());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder{
        TextView name , time;
        public RecyclerViewHolder(@NonNull final View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textView_passRecordName);
            time = itemView.findViewById(R.id.textView_passRecordTime);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Glide.with(context)
//                            .load(arrayList.get(getAdapterPosition()).getUrl())
//                            .error(R.drawable.common_google_signin_btn_icon_dark)
//                            .into(imageView);

                    byte[] decode = Base64.decode(arrayList.get(getAdapterPosition()).getUrl(),
                                                Base64.DEFAULT);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(decode,0,decode.length);
                    imageView.setImageBitmap(bitmap);
                }
            });
        }
    }
}
