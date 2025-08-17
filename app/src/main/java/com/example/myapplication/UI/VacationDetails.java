package com.example.myapplication.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class VacationDetails extends AppCompatActivity {
    String name;
    String hotel;
    int productID;

    EditText editName;
    EditText editHotel;

    Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_vacation_details);
        FloatingActionButton fab = findViewById(R.id.floatingActionButton2);

        editName = findViewById(R.id.titletext);
        editHotel = findViewById(R.id.hoteltext);
        productID = getIntent().getIntExtra("id", -1);
        name = getIntent().getStringExtra("Vacation name");
        hotel = getIntent().getStringExtra("Hotel name");
        editName.setText(name);
        editHotel.setText(hotel);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VacationDetails.this, ExcursionDetails.class);
                startActivity(intent);
            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        RecyclerView recyclerView = findViewById(R.id.excursionrecyclerView);
        Repository repository = new Repository(getApplication());
        final ExcursionAdapter excursionAdapter = new ExcursionAdapter(this);
        recyclerView.setAdapter(excursionAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Excursions> filteredExcursions = new ArrayList<>();
        for (Excursions excursion : repository.getAllExcursions()) {
            if (excursion.getExcursionID() == productID) filteredExcursions.add(excursion);
        }
        excursionAdapter.setExcursions(filteredExcursions);
        //excursionAdapter.setExcursions(repository.getAllExcursions());


    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_vacation_details, menu);
        return true;

    }
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.vacationsave) {
            Vacations vacation;
            Repository repository = new Repository(getApplication());
            if (productID == -1) {
                if(repository.getAllVacations().size()==0) productID=1;
                else productID=repository.getAllVacations().get(repository.getAllVacations().size()-1).getVacationID()+1;
                vacation = new Vacations(productID, editName.getText().toString(), editHotel.getText().toString());
                repository.insert(vacation);
                this.finish();
            }
            else{
                vacation = new Vacations(productID, editName.getText().toString(), editHotel.getText().toString());
                repository.update(vacation);
                this.finish();

            }
        }
        return true;

    }
}