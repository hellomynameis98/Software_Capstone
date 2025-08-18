package com.example.myapplication.UI;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;


import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.myapplication.R;
import com.example.myapplication.database.Repository;
import com.example.myapplication.entities.Excursions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ExcursionDetails extends AppCompatActivity {
    String name;
    Double price;
    int excursionID;
    int vacationID;
    EditText editName;
    EditText editPrice;
    EditText editNote;
    EditText editDate;
    Repository repository;
    DatePickerDialog.OnDateSetListener startDate;
    final Calendar myCalendarStart = Calendar.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_excursion_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        repository = new Repository(getApplication());

        name = getIntent().getStringExtra("Vacation name");
        editName = findViewById(R.id.excursionName);

        editName.setText(name);

        price = getIntent().getDoubleExtra("price", -1.0);
        editPrice = findViewById(R.id.partPrice);

        editPrice.setText(Double.toString(price));

        excursionID = getIntent().getIntExtra("id", -1);
        vacationID = getIntent().getIntExtra("prodID", -1);
        editNote = findViewById(R.id.note);
        editDate = findViewById(R.id.date);
        // String myFormat = "MM/dd/yy"; //In which you need put here
        // SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);


    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_excursion_details, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
//                Intent intent=new Intent(PartDetails.this,MainActivity.class);
//                startActivity(intent);
//                return true;
        }

        return true;

    }
}
