package com.example.sqlitedatabaseexample;

import androidx.appcompat.app.AppCompatActivity;

import android.database.SQLException;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class ReadData extends AppCompatActivity {

    TextView mDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_data);

        mDisplay = findViewById(R.id.tvDisplayData);

        try {
            ContactsDB db = new ContactsDB(this);
            db.open();
            String result = db.readData();
            db.close();
            mDisplay.setText(result);
        }
        catch (SQLException e) {
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }
}
