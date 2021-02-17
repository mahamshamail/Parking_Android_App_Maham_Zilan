package com.maham.parking_android_app_maham_zilan.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.maham.parking_android_app_maham_zilan.OnParkingListClickListener;
import com.maham.parking_android_app_maham_zilan.OnParkingListLongClickListener;
import com.maham.parking_android_app_maham_zilan.R;
import com.maham.parking_android_app_maham_zilan.model.ParkingList;

import java.util.ArrayList;

public class ParkingListAdapter extends RecyclerView.Adapter<ParkingListAdapter.ParkingListViewHolder> {
    private Context context;
    private ArrayList<ParkingList> parkingLists;
    private OnParkingListClickListener clickListener;
    private OnParkingListLongClickListener longClickListener;

    public ParkingListAdapter(Context context, ArrayList<ParkingList> parkingLists,
                              OnParkingListClickListener clickListener, OnParkingListLongClickListener longClickListener){
        this.context = context;
        this.parkingLists = parkingLists;
        Log.e("parkingLists",parkingLists.toString());
        this.clickListener = clickListener;
        this.longClickListener = longClickListener;
    }

    @NonNull
    @Override
    public ParkingListAdapter.ParkingListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.parkinglist_item, null, false);
        return new ParkingListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ParkingListViewHolder holder, int position) {
        holder.bind(parkingLists.get(position), clickListener, longClickListener);
    }

    @Override
    public int getItemCount() {
        return parkingLists.size();
    }

    public static class ParkingListViewHolder extends RecyclerView.ViewHolder{

        TextView parkingAddressTV;
        TextView parkingDateTv;
        TextView parkingHoursTv;

        public ParkingListViewHolder(@NonNull View itemView) {
            super(itemView);
            parkingAddressTV = itemView.findViewById(R.id.tvParkingAddress);
            parkingDateTv = itemView.findViewById(R.id.tvParkingDate);
            parkingHoursTv = itemView.findViewById(R.id.tvParkingDuration);
        }

        public void bind(ParkingList parkingList, OnParkingListClickListener clickListener, OnParkingListLongClickListener longClickListener){
            parkingAddressTV.setText(parkingList.getAddress());
            parkingDateTv.setText(parkingList.getDate().toString());
            parkingHoursTv.setText(parkingList.getHours());

            Log.e("binding list address: ", parkingList.getAddress());

//            parkingAddressTV.setOnLongClickListener(new View.OnLongClickListener() {
//                @Override
//                public boolean onLongClick(View view) {
//                    longClickListener.onParkingListLongClickListener(parkingList);
//                    return false;
//                }
//            });

            parkingAddressTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.onParkingListClickListener(parkingList );

                }
            });

            parkingDateTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.onParkingListClickListener(parkingList );

                }
            });

            parkingHoursTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.onParkingListClickListener(parkingList );

                }
            });
        }
    }
}
