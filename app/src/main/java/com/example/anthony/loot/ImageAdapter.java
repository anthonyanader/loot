package com.example.anthony.loot;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {
    private Context mContext;
    private List<Upload> mUploads;
    private OnItemClickListener mListener;

    public ImageAdapter(Context context, List<Upload> uploads) {
        mContext = context;
        mUploads = uploads;
    }


    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.loot_item, parent, false);

        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        Upload uploadCurrent = mUploads.get(position);
        holder.textViewCardName.setText(uploadCurrent.getmName());
        holder.textViewCardTimer.setText(uploadCurrent.getmTimer() + " days left");
        Picasso.get()
                .load(uploadCurrent.getmImageUrl())
                .fit()
                .centerCrop()
                .into(holder.imageViewCard);
    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView textViewCardName;
        public TextView textViewCardTimer;
        public ImageView imageViewCard;

        public ImageViewHolder(View itemView) {
            super(itemView);

            textViewCardName = itemView.findViewById(R.id.text_view_card_name);
            textViewCardTimer = itemView.findViewById(R.id.text_view_card_item_timer);
            imageViewCard = itemView.findViewById(R.id.image_view_card_upload);

            itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {
            if(mListener != null){
                int position = getAdapterPosition();
                if(position != RecyclerView.NO_POSITION){
                    mListener.onItemClick(v,position);
                }
            }
        }
    }

    public void filterList(ArrayList<Upload> filteredList){
        mUploads = filteredList;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

}
