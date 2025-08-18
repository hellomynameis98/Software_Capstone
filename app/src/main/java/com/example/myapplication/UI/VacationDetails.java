package com.example.myapplication.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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
    int vacationID;
    Vacations currentVacation;
    int numExcursions;


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
        this.repository = new Repository(getApplication());
        final ExcursionAdapter excursionAdapter = new ExcursionAdapter(this);
        recyclerView.setAdapter(excursionAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Excursions> filteredExcursions = new ArrayList<>();
        for (Excursions excursion : repository.getAllExcursions()) {
            if (excursion.getExcursionID() == vacationID) filteredExcursions.add(excursion);
        }
        //excursionAdapter.setExcursions(filteredExcursions);
        excursionAdapter.setExcursions(repository.getAllExcursions());


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
                vacation = new Vacations(productID, editName.getText().toString(), editHotel.getText().toString(), , );
                repository.insert(vacation);
                this.finish();
            }
            else{
                vacation = new Vacations(productID, editName.getText().toString(), editHotel.getText().toString(), , );
                repository.update(vacation);
                this.finish();
                return true;

            }

        }

        if (item.getItemId() == R.id.vacationdelete) {
            Repository repository = new Repository(getApplication());
            for (Vacations v : repository.getAllVacations()) {
                if (v.getVacationID() == productID) currentVacation = v;
            }
            numExcursions = 0;
            for (Excursions excursion : repository.getAllExcursions()) {
                if (excursion.getProductID() == vacationID) ++numExcursions;
            }
            //if the vacation has any associated excursions, prevent deletion of the vacation, otherwise delete it
            if (numExcursions == 0) {
                repository.delete(currentVacation);
                Toast.makeText(VacationDetails.this, currentVacation.getVacationName() + " was deleted", Toast.LENGTH_LONG).show();
                VacationDetails.this.finish();
            } else {
                Toast.makeText(VacationDetails.this, "Can't delete a vacation with excursions", Toast.LENGTH_LONG).show();
            }
        }
        return super.onOptionsItemSelected(item);

    }


}



          /*  if (item.getItemId() == R.id.vacationsave) {
            Vacations vacation;
            if (vacationID == -1) {
                if (repository.getAllVacations().size() == 0) vacationID = 1;
                else
                    vacationID = repository.getAllVacations().get(repository.getAllVacations().size() - 1).getVacationID() + 1;
                vacation = new Vacations(vacationID, editName.getText().toString(), editHotel.getText().toString());
                repository.insert(vacation);
            } else {
                vacation = new Vacations(vacationID, editName.getText().toString(), (editHotel.getText().toString()));
                repository.update(vacation);
            }
            return true;
        }
            if (item.getItemId() == R.id.vacationdelete) {
            for (Vacations v : repository.getAllVacations()) {
                if (v.getVacationID() == vacationID) currentVacation = v;
            }

            numParts = 0;
            for (Excursions e : repository.getAllExcursions()) {
                if (e.getExcursionID() == vacationID) ++numParts; //Look at this if it's not working
            }

            if (numParts == 0) {
                repository.delete(currentVacation);
                Toast.makeText(VacationDetails.this, currentVacation.getVacationName() + " was deleted", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(VacationDetails.this, "Can't delete a product with parts", Toast.LENGTH_LONG).show();
            }
            return true;
        }
            if (item.getItemId() == R.id.addSampleExcursions) {
                if (vacationID == -1)
                    Toast.makeText(VacationDetails.this, "Please save product before adding parts", Toast.LENGTH_LONG).show();

                else {
                    int excursionID;

                    if (repository.getAllExcursions().size() == 0) excursionID = 1;
                    else
                        excursionID = repository.getAllExcursions().get(repository.getAllExcursions().size() - 1).getExcursionID() + 1;
                    Excursions excursion = new Excursions(excursionID, "Skydive", 10, vacationID); //look here as well
                    repository.insert(excursion);
                    excursion = new Excursions(++excursionID, "Snorkeling", 10, vacationID);
                    repository.insert(excursion);
                    RecyclerView recyclerView = findViewById(R.id.excursionrecyclerView);
                    final ExcursionAdapter excursionAdapter = new ExcursionAdapter(this);
                    recyclerView.setAdapter(excursionAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(this));
                    List<Excursions> filteredExcursions = new ArrayList<>();
                    for (Excursions e : repository.getAllExcursions()) {
                        if (e.getProductID() == vacationID) filteredExcursions.add(e);
                    }
                    excursionAdapter.setExcursions(filteredExcursions);
                    return true;

                }
        }
        return super.onOptionsItemSelected(item);
            }
    }

*/
