package com.example.mojnovcanik;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class recyclerAdapter extends RecyclerView.Adapter<recyclerAdapter.ViewHolder> {

    Activity activity;
    Context context;
    ArrayList<String> iznos = new ArrayList<>();
    ArrayList<String> opis = new ArrayList<>();
    ArrayList<String> datum = new ArrayList<>();

    public recyclerAdapter(Activity activity, Context context, ArrayList<String> iznos, ArrayList<String> opis, ArrayList<String> datum) {
        this.activity = activity;
        this.context = context;
        this.iznos = iznos;
        this.opis = opis;
        this.datum = datum;
    }

    public recyclerAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public recyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       /* LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recycler_design,null,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;*/

        LayoutInflater inflater =LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recycler_design,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull recyclerAdapter.ViewHolder holder, int position) {
        holder.tvIznos.setText(iznos.get(position));
        holder.tvOpis.setText(opis.get(position));
        holder.tvDatum.setText(datum.get(position));
        String value = holder.tvIznos.getText().toString().trim();
        if(value.charAt(0) == '-'){
            holder.tvIznos.setTextColor(Color.RED);
            holder.ikonica.setImageResource(R.drawable.ic_rashod);
        }else{
            holder.tvIznos.setTextColor(Color.GREEN);
            holder.ikonica.setImageResource(R.drawable.ic_prihod);
        }


    }

    @Override
    public int getItemCount() {
        return iznos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvIznos,tvOpis,tvDatum;
        ImageView ikonica;
        //CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvIznos = itemView.findViewById(R.id.tvIznos);
            tvOpis = itemView.findViewById(R.id.tvOpis);
            tvDatum = itemView.findViewById(R.id.tvDatum);
            ikonica = itemView.findViewById(R.id.ikonica);
           // cardView = itemView.findViewById(R.id.cardView);

        }
    }
}
