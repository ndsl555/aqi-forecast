package com.example.combine;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class GasAdapter extends RecyclerView.Adapter<GasAdapter.OilViewHolder> {
    private ArrayList<GasData> gasDataList;

    public GasAdapter(ArrayList<GasData> list) {
        this.gasDataList = list;
    }

    @NonNull
    @Override
    public OilViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gasoline, parent, false);
        return new OilViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OilViewHolder holder, int position) {
        GasData gasData = gasDataList.get(position);
        holder.textViewName.setText(gasData.getName());
        holder.textViewPrice.setText(gasData.getPrice()+gasData.getUnit());
        String state= String.valueOf(gasData.getValue().split(" ")[0]);
        String value= String.valueOf(gasData.getValue().split(" ")[1]);
        holder.textViewValue.setText(value);
        if(state.equals("south")){
            holder.stateimageView.setImageResource(R.drawable.baseline_arrow_downward_24);
            holder.textViewValue.setTextColor(Color.GREEN);
        }
        else if(state.equals("north")){
            holder.stateimageView.setImageResource(R.drawable.baseline_arrow_upward_24);
            holder.textViewValue.setTextColor(Color.RED);
        }
        else {

        }
    }

    @Override
    public int getItemCount() {
        return gasDataList.size();
    }

    public class OilViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName;
        TextView textViewPrice;
        TextView textViewValue;

        ImageView stateimageView;

        public OilViewHolder(View itemView) {
            super(itemView);
            stateimageView=itemView.findViewById(R.id.state_img);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewPrice = itemView.findViewById(R.id.textViewPrice);
            textViewValue = itemView.findViewById(R.id.textViewValue);
        }
    }
}
