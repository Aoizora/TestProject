package com.example.joseph.myapplication;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by Aoryu on 19/12/2016.
 */

public class BiersAdapterEN {
    private JSONArray biers;



    public BiersAdapterEN(JSONArray jsonArray){
        this.biers = jsonArray;
    }

    @Override
    public BiersAdapterEN.BierHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_bier_element, null, false);
        return new BierHolder(v);
    }

    @Override
    public void onBindViewHolder(BiersAdapter.BierHolder holder, int position) {
        try {
            holder.name.setText(biers.getJSONObject(position).getString("nameEN"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        if(biers==null) {
            return 0;
        }else{
            return biers.length();
        }
    }

    class BierHolder extends RecyclerView.ViewHolder{

        public TextView name;

        public BierHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.rv_bier_element_name);
        }
    }

    public void setNewBiere(JSONArray jsonArray){
        biers = jsonArray;
        notifyDataSetChanged();
    }

}
