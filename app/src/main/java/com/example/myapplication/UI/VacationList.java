package com.example.myapplication.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.database.Repository;
import com.example.myapplication.entities.Excursions;
import com.example.myapplication.entities.Vacations;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class VacationList extends AppCompatActivity {
    private Repository repository;
    private VacationAdapter vacationAdapter;   // keep ONE adapter instance
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_vacation_list);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(VacationList.this, VacationDetails.class);
            startActivity(intent);
        });

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        repository = new Repository(getApplication());
        vacationAdapter = new VacationAdapter(this);
        recyclerView.setAdapter(vacationAdapter);


        List<Vacations> allVacations = repository.getAllVacations();
        vacationAdapter.setVacations(allVacations);
    }

    @Override
    protected void onResume() {
        super.onResume();

        List<Vacations> allVacations = repository.getAllVacations();
        vacationAdapter.setVacations(allVacations);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_product_list, menu);


        MenuItem searchItem = menu.findItem(R.id.action_search);
        if (searchItem != null) {
            SearchView searchView = (SearchView) searchItem.getActionView();
            if (searchView != null) {
                searchView.setQueryHint("Search vacations...");
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override public boolean onQueryTextSubmit(String query) {
                        vacationAdapter.filter(query);
                        return true;
                    }
                    @Override public boolean onQueryTextChange(String newText) {
                        vacationAdapter.filter(newText);
                        return true;
                    }
                });
                searchItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
                    @Override public boolean onMenuItemActionExpand(MenuItem item) { return true; }
                    @Override public boolean onMenuItemActionCollapse(MenuItem item) {
                        vacationAdapter.filter(""); // clear filter
                        return true;
                    }
                });
            }
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.mysample) {
            Vacations vacation = new Vacations(0, "Honolulu", "Great Hotel","8/21/25" ,"8/30/25");
            repository.insert(vacation);
            vacation = new Vacations(0, "SLC", "Less Great Hotel","8/10/25","8/20/25");
            repository.insert(vacation);
            Excursions excursion = new Excursions(0,"skiing",1,"8/22/25");
            repository.insert(excursion);
            excursion = new Excursions(0,"hiking",2,"8/15/25");
            repository.insert(excursion);

            vacationAdapter.setVacations(repository.getAllVacations());
            return true;
        } else if (id == R.id.action_report) {

            startActivity(new Intent(VacationList.this, ReportActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
