package com.example.myapplication.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.entities.Excursions;
import com.example.myapplication.entities.Vacations;

import java.util.List;

public class ExcursionAdapter extends RecyclerView.Adapter<ExcursionAdapter.ExcursionViewHolder> {

    private List<Excursions> mExcursions;
    private final Context context;
    private final LayoutInflater mInflater;



    public class ExcursionViewHolder extends RecyclerView.ViewHolder {
        private final TextView excursionItemView;


        private ExcursionViewHolder(View itemView) {
            super(itemView);
            excursionItemView = itemView.findViewById(R.id.textView2);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAbsoluteAdapterPosition();
                    final Excursions current = mExcursions.get(position);
                    Intent intent = new Intent(context, ExcursionDetails.class);
                    intent.putExtra("id", current.getExcursionID());
                    intent.putExtra("Vacation name", current.getExcursionName());
                    intent.putExtra("price", current.getPrice());
                    context.startActivity(intent);
                }
            });
        }
    }

    public ExcursionAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public ExcursionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.excursion_list_item, parent, false);
        return new ExcursionViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(@NonNull ExcursionViewHolder holder, int position) {
        if (mExcursions != null) {
            Excursions current = mExcursions.get(position);
            String title = current.getExcursionName();
            int vacationID = current.getProductID();
            holder.excursionItemView.setText(title);
        } else {
            holder.excursionItemView.setText("No excursion title");
        }
    }
    public void setExcursions(List<Excursions> excursions) {
        mExcursions = excursions;
        notifyDataSetChanged();

    }
    public int getItemCount() {
        if (mExcursions != null) return mExcursions.size();
        else return 0;
    }




}
