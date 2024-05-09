package com.example.aplicatiecinema.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aplicatiecinema.Domain.Detail;
import com.example.aplicatiecinema.R;

import java.util.List;

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.ViewHolder> {
    private List<Detail> items;
    private Context context;

    public DetailAdapter(List<Detail> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View itemView = LayoutInflater.from(context).inflate(R.layout.activity_detail, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Detail detail = items.get(position);

        holder.bind(detail);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTxt;
        TextView overviewTxt;
        // Add references to other views as needed

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTxt = itemView.findViewById(R.id.movieNameTxt);
            overviewTxt = itemView.findViewById(R.id.movieSummary);
            // Initialize other views here
        }

        public void bind(Detail detail) {
            titleTxt.setText(detail.getTitle());
            overviewTxt.setText(detail.getOverview());
            // Bind other movie details as needed
        }
    }
}