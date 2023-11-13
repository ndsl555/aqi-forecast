package com.example.combine.Adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.combine.DataClass.OilData;
import com.example.combine.R;

import java.util.ArrayList;

public class OilAdapter extends RecyclerView.Adapter<OilAdapter.OilViewHolder> {
    private ArrayList<OilData> oilDataList;

    public OilAdapter(ArrayList<OilData> list) {
        this.oilDataList = list;
    }

    @NonNull
    @Override
    public OilViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_oil, parent, false);
        return new OilViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OilViewHolder holder, int position) {
        OilData oilData = oilDataList.get(position);
        holder.textViewName.setText(oilData.getName());
        holder.textViewPrice.setText(oilData.getPrice()+ oilData.getUnit());
        String state= String.valueOf(oilData.getState());
        String value= String.valueOf(oilData.getValue());
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
            holder.stateimageView.setImageResource(R.drawable.baseline_horizontal_rule_24);
        }
    }

    @Override
    public int getItemCount() {
        return oilDataList.size();
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
