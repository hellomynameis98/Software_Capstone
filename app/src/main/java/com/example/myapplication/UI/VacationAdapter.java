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
import com.example.myapplication.entities.Vacations;

import java.util.ArrayList;
import java.util.List;



public class VacationAdapter extends RecyclerView.Adapter<VacationAdapter.VacationViewHolder> {


    private List<Vacations> mVacations;

    private List<Vacations> sVacations;

    private final Context context;
    private final LayoutInflater mInflater;

    public VacationAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    public class VacationViewHolder extends RecyclerView.ViewHolder {
        private final TextView vacationItemView;

        public VacationViewHolder(@NonNull View itemView) {
            super(itemView);
            vacationItemView = itemView.findViewById(R.id.textView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAbsoluteAdapterPosition();
                    if (position == RecyclerView.NO_POSITION || mVacations == null || position >= mVacations.size()) return;

                    final Vacations current = mVacations.get(position);
                    Intent intent = new Intent(context, VacationDetails.class);
                    intent.putExtra("id", current.getVacationID());
                    intent.putExtra("Vacation name", current.getVacationName());
                    intent.putExtra("Hotel name", current.getHotelName());
                    intent.putExtra("start date", current.getStartDate());
                    intent.putExtra("end date", current.getEndDate());
                    context.startActivity(intent);
                }
            });
        }
    }

    @NonNull
    @Override
    public VacationAdapter.VacationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.vacation_list_item, parent, false);
        return new VacationViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull VacationAdapter.VacationViewHolder holder, int position) {
        if (mVacations != null && position < mVacations.size()) {
            Vacations current = mVacations.get(position);
            String title = current.getVacationName();
            holder.vacationItemView.setText(title != null ? title : "No vacation title");
        } else {
            holder.vacationItemView.setText("No vacation title");
        }
    }

    @Override
    public int getItemCount() {
        return (mVacations != null) ? mVacations.size() : 0;
    }

    /** Set/refresh the backing data. Also resets the filter to show all items. */
    public void setVacations(List<Vacations> vacations) {
        // Keep a full copy as the source list
        sVacations = (vacations == null) ? new ArrayList<>() : new ArrayList<>(vacations);
        // Start visible list as a copy of source list
        mVacations = new ArrayList<>(sVacations);
        notifyDataSetChanged();
    }

    /**
     * Filter the visible list by the provided query.
     * Matches against VacationName, HotelName, StartDate, EndDate (adjust as needed).
     *
     * @param q The search text (case-insensitive). Empty/null shows all items.
     */
    public void filter(String q) {
        if (sVacations == null) {

            mVacations = new ArrayList<>();
            notifyDataSetChanged();
            return;
        }

        String query = (q == null) ? "" : q.trim().toLowerCase();
        if (query.isEmpty()) {
            // Show all
            mVacations = new ArrayList<>(sVacations);
            notifyDataSetChanged();
            return;
        }

        List<Vacations> filtered = new ArrayList<>();
        for (Vacations v : sVacations) {

            boolean match =
                    contains(v.getVacationName(), query) ||
                            contains(v.getHotelName(), query) ||
                            contains(v.getStartDate(), query) ||
                            contains(v.getEndDate(), query);

            if (match) filtered.add(v);
        }

        mVacations = filtered;
        notifyDataSetChanged();
    }


    private static boolean contains(String value, String query) {
        return value != null && value.toLowerCase().contains(query);
    }
}
