package com.example.combine;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class forecastAdapter extends RecyclerView.Adapter<forecastAdapter.ViewHolder> {

    List<forecastData>list;
    Context context;

    public forecastAdapter(List<forecastData> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.forecast,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textViewDate.setText(list.get(position).getDate());
        holder.textViewtem.setText(list.get(position).getTem());
        holder.textViewrain.setText(list.get(position).getRain());
        holder.textViewfeel.setText(list.get(position).getFeel());
        holder.textViewhumid.setText(list.get(position).getHumid());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView textViewDate;
        TextView textViewtem;
        TextView textViewrain;
        TextView textViewfeel;
        TextView textViewhumid;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewDate = itemView.findViewById(R.id.textdate);
            textViewtem = itemView.findViewById(R.id.texttem);
            textViewrain = itemView.findViewById(R.id.textrain);
            textViewfeel = itemView.findViewById(R.id.textfeel);
            textViewhumid=itemView.findViewById(R.id.texthumid);
        }
    }

}