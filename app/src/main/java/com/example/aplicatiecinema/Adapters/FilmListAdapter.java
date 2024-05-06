//package com.example.aplicatiecinema.Adapters;
//
//import android.content.Context;
//import android.content.Intent;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.bumptech.glide.Glide;
//import com.bumptech.glide.load.resource.bitmap.CenterCrop;
//import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
//import com.bumptech.glide.request.RequestOptions;
//import com.example.aplicatiecinema.Activities.DetailActivity;
//import com.example.aplicatiecinema.Domain.ListFilm;
//import com.example.aplicatiecinema.R;
//
//public class FilmListAdapter extends RecyclerView.Adapter<FilmListAdapter.ViewHolder> {
//    ListFilm items;
//    Context context;
//
//    public FilmListAdapter(ListFilm item) {
//        this.items = item;
//    }
//
//    @NonNull
//    @Override
//    public FilmListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        context=parent.getContext();
//        View inflate= LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_film,parent,false);
//        return new ViewHolder(inflate);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull FilmListAdapter.ViewHolder holder, int position) {
//        holder.titleTxt.setText(items.getData().get(position).getTitle());
//        RequestOptions requestOptions=new RequestOptions();
//        requestOptions=requestOptions.transform(new CenterCrop(), new RoundedCorners(30));
//
//        Glide.with(context)
//                .load(items.getData().get(position).getPoster())
//                .apply(requestOptions)
//                .into(holder.pic);
//
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent= new Intent(holder.itemView.getContext(), DetailActivity.class);
//                intent.putExtra("id",items.getData().get(position).getId());
//                context.startActivity(intent);
//            }
//        });
//    }
//
//    @Override
//    public int getItemCount() {
//        return items.getData().size();
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder{
//        TextView titleTxt;
//        ImageView pic;
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//            titleTxt=itemView.findViewById(R.id.titleTxt);
//            pic=itemView.findViewById(R.id.pic);
//        }
//    }
//}
package com.example.aplicatiecinema.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.aplicatiecinema.Activities.DetailActivity;
import com.example.aplicatiecinema.Domain.ListFilm1; // Change import statement
import com.example.aplicatiecinema.Domain.Result; // Change import statement
import com.example.aplicatiecinema.R;

import java.util.List;

public class FilmListAdapter extends RecyclerView.Adapter<FilmListAdapter.ViewHolder> {
    List<Result> items; // Change List type to Result
    Context context;

    public FilmListAdapter(List<Result> item) { // Change constructor parameter type to List<Result>
        this.items = item;
    }

    @NonNull
    @Override
    public FilmListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_film, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull FilmListAdapter.ViewHolder holder, int position) {
        Result result = items.get(position); // Get the Result object at the given position
        holder.titleTxt.setText(result.getTitle());
        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.transform(new CenterCrop(), new RoundedCorners(30));

        Glide.with(context)
                .load(result.getPosterPath()) // Use getPosterPath() instead of getPoster()
                .apply(requestOptions)
                .into(holder.pic);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.itemView.getContext(), DetailActivity.class);
                intent.putExtra("id", result.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTxt;
        ImageView pic;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTxt = itemView.findViewById(R.id.titleTxt);
            pic = itemView.findViewById(R.id.pic);
        }
    }
}

