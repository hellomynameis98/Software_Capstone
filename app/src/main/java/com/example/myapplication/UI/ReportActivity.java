package com.example.myapplication.UI;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.database.Repository;
import com.example.myapplication.entities.Excursions;
import com.example.myapplication.entities.Vacations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportActivity extends AppCompatActivity {

    private Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_report);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.report_root), (v, insets) -> {
            Insets sys = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(sys.left, sys.top, sys.right, sys.bottom);
            return insets;
        });

        RecyclerView recycler = findViewById(R.id.reportRecycler);
        recycler.setLayoutManager(new LinearLayoutManager(this));

        repository = new Repository(getApplication());


        List<Vacations> vacations = repository.getAllVacations();
        List<Excursions> excursions = repository.getAllExcursions();


        Map<Integer, List<String>> exNamesByVac = new HashMap<>();
        for (Excursions e : excursions) {
            int vacId = e.getVacationID();
            exNamesByVac.computeIfAbsent(vacId, k -> new ArrayList<>())
                    .add(safe(e.getExcursionName()));
        }


        List<Row> rows = new ArrayList<>();
        for (Vacations v : vacations) {
            String tripName = safe(v.getVacationName());
            String hotel = safe(v.getHotelName());
            String dateRange = safe(v.getStartDate()) + " - " + safe(v.getEndDate());

            List<String> names = exNamesByVac.get(v.getVacationID());
            String excursionsJoined;
            if (names == null || names.isEmpty()) {
                excursionsJoined = "(no excursions)";
            } else {

                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < names.size(); i++) {
                    if (i > 0) sb.append(", ");
                    sb.append(names.get(i));
                }
                excursionsJoined = sb.toString();
            }

            rows.add(new Row(tripName, hotel, excursionsJoined, dateRange));
        }

        recycler.setAdapter(new ReportAdapter(rows));
    }

    private String safe(String s) { return s == null ? "" : s; }


    static class Row {
        final String tripName;
        final String hotelName;
        final String excursions;
        final String dateRange;

        Row(String tripName, String hotelName, String excursions, String dateRange) {
            this.tripName = tripName;
            this.hotelName = hotelName;
            this.excursions = excursions;
            this.dateRange = dateRange;
        }
    }


    static class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.VH> {
        private final List<Row> data;

        ReportAdapter(List<Row> data) {
            this.data = data != null ? data : new ArrayList<>();
        }

        static class VH extends RecyclerView.ViewHolder {
            TextView tvTrip, tvHotel, tvExcursions, tvDates;
            VH(@NonNull View itemView) {
                super(itemView);
                tvTrip = itemView.findViewById(R.id.tvTrip);
                tvHotel = itemView.findViewById(R.id.tvHotel);
                tvExcursions = itemView.findViewById(R.id.tvExcursions);
                tvDates = itemView.findViewById(R.id.tvDates);
            }
        }

        @NonNull
        @Override
        public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.report_list_item, parent, false);
            return new VH(v);
        }

        @Override
        public void onBindViewHolder(@NonNull VH h, int position) {
            Row r = data.get(position);
            h.tvTrip.setText(isEmpty(r.tripName) ? "(unnamed trip)" : r.tripName);
            h.tvHotel.setText(isEmpty(r.hotelName) ? "(no hotel)" : r.hotelName);
            h.tvExcursions.setText(isEmpty(r.excursions) ? "(no excursions)" : r.excursions);
            h.tvDates.setText(isEmpty(r.dateRange) ? "(no dates)" : r.dateRange);
        }

        @Override
        public int getItemCount() { return data.size(); }

        private boolean isEmpty(String s) { return s == null || s.trim().isEmpty(); }
    }
}

