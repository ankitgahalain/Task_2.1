package com.example.task21;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


public class MainActivity extends AppCompatActivity {

    private Spinner categorySpinner, sourceSpinner, destinationSpinner;
    private EditText inputValue;
    private TextView resultText;

    private final String[] categories = {"Length", "Weight", "Temperature"};
    private final String[][] unitOptions = {
            {"Inch", "Foot", "Yard", "Mile", "Centimeter", "Kilometer"},
            {"Pound", "Ounce", "Ton", "Gram", "Kilogram"},
            {"Celsius", "Fahrenheit", "Kelvin"}
    };

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

        categorySpinner = findViewById(R.id.categorySpinner);
        sourceSpinner = findViewById(R.id.sourceSpinner);
        destinationSpinner = findViewById(R.id.destinationSpinner);
        inputValue = findViewById(R.id.inputValue);
        resultText = findViewById(R.id.resultText);
        Button convertButton = findViewById(R.id.convertButton);

        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categories);
        categorySpinner.setAdapter(categoryAdapter);

        // Handle category selection to update source and destination spinners
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateUnitSpinners(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        // Convert button click listener
        convertButton.setOnClickListener(v -> performConversion());
    }

    private void updateUnitSpinners(int categoryIndex) {
        // Update source and destination spinners with the corresponding unit list
        ArrayAdapter<String> unitAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, unitOptions[categoryIndex]);
        sourceSpinner.setAdapter(unitAdapter);
        destinationSpinner.setAdapter(unitAdapter);
    }

    private void performConversion() {
        int categoryIndex = categorySpinner.getSelectedItemPosition();
        String sourceUnit = sourceSpinner.getSelectedItem().toString();
        String destinationUnit = destinationSpinner.getSelectedItem().toString();

        // Validate input
        String inputStr = inputValue.getText().toString();
        if (inputStr.isEmpty()) {
            resultText.setText("Enter a valid number!");
            return;
        }
        if (sourceUnit.equals(destinationUnit)) {
            resultText.setText("Please select different units.");
            return;
        }

        double input = Double.parseDouble(inputStr);
        double result = 0.0;

        // Perform conversion based on category using switch statements
        switch (categoryIndex) {
            case 0:
                result = convertLength(input, sourceUnit, destinationUnit);
                break;
            case 1:
                result = convertWeight(input, sourceUnit, destinationUnit);
                break;
            case 2:
                result = convertTemperature(input, sourceUnit, destinationUnit);
                break;
        }

        resultText.setText(String.format("Converted Value: %.2f %s", result, destinationUnit));
    }

    private double convertLength(double value, String sourceUnit, String destinationUnit) {
        double inCm;
        switch (sourceUnit) {
            case "Inch":
                inCm = value * 2.54;
                break;
            case "Foot":
                inCm = value * 30.48;
                break;
            case "Yard":
                inCm = value * 91.44;
                break;
            case "Mile":
                inCm = value * 160934;
                break;
            case "Kilometer":
                inCm = value * 100000;
                break;
            default:
                inCm = value;
                break;
        }

        switch (destinationUnit) {
            case "Inch":
                return inCm / 2.54;
            case "Foot":
                return inCm / 30.48;
            case "Yard":
                return inCm / 91.44;
            case "Mile":
                return inCm / 160934;
            case "Kilometer":
                return inCm / 100000;
            default:
                return inCm;
        }
    }

    private double convertWeight(double value, String sourceUnit, String destinationUnit) {
        double inKg;
        switch (sourceUnit) {
            case "Pound":
                inKg = value * 0.453592;
                break;
            case "Ounce":
                inKg = value * 0.0283495;
                break;
            case "Ton":
                inKg = value * 907.185;
                break;
            case "Gram":
                inKg = value * 0.001;
                break;
            default:
                inKg = value;
                break;
        }

        switch (destinationUnit) {
            case "Pound":
                return inKg / 0.453592;
            case "Ounce":
                return inKg / 0.0283495;
            case "Ton":
                return inKg / 907.185;
            case "Gram":
                return inKg / 0.001;
            default:
                return inKg;
        }
    }

    private double convertTemperature(double value, String sourceUnit, String destinationUnit) {
        switch (sourceUnit + " to " + destinationUnit) {
            case "Celsius to Fahrenheit":
                return (value * 1.8) + 32;
            case "Fahrenheit to Celsius":
                return (value - 32) / 1.8;
            case "Celsius to Kelvin":
                return value + 273.15;
            case "Kelvin to Celsius":
                return value - 273.15;
            case "Fahrenheit to Kelvin":
                return ((value - 32) / 1.8) + 273.15;
            case "Kelvin to Fahrenheit":
                return ((value - 273.15) * 1.8) + 32;
            default:
                return value;
        }
    }

}