package com.example.myapplication.UI;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.R;

public class MainActivity extends AppCompatActivity {

    private static final String EXPECTED_PASSCODE = "1234";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button button = findViewById(R.id.button);
        button.setOnClickListener(v -> showPasscodeDialog());
    }

    private void showPasscodeDialog() {

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        input.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)}); // optional max length
        input.setHint("Enter passcode");


        int pad = (int) (16 * getResources().getDisplayMetrics().density);
        LinearLayout container = new LinearLayout(this);
        container.setPadding(pad, pad, pad, 0);
        container.addView(input);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Passcode Required")
                .setView(container)
                .setCancelable(true)
                .setPositiveButton("Unlock", null)
                .setNegativeButton("Cancel", (d, which) -> d.dismiss())
                .create();

        dialog.setOnShowListener(d -> {

            Button ok = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            ok.setOnClickListener(v -> {
                String code = input.getText().toString().trim();
                if (EXPECTED_PASSCODE.equals(code)) {
                    dialog.dismiss();
                    goToNextPage();
                } else {
                    input.setText("");
                    input.requestFocus();
                    Toast.makeText(MainActivity.this, "Incorrect passcode. Try again.", Toast.LENGTH_SHORT).show();
                }
            });
        });

        dialog.show();
    }

    private void goToNextPage() {
        Intent intent = new Intent(MainActivity.this, VacationList.class);
        startActivity(intent);
    }
}