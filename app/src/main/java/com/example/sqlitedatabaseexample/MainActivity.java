package com.example.sqlitedatabaseexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText mName, mNumber;
    private Button mAdd, mRead, mDelete, mUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mName = findViewById(R.id.etName);
        mNumber = findViewById(R.id.etNumber);
        mAdd = findViewById(R.id.btnSubmit);
        mRead = findViewById(R.id.btnShowData);
        mDelete = findViewById(R.id.btnDeleteData);
        mUpdate = findViewById(R.id.btnUpdateData);
    }

    public void addData(View view) {
        String name = mName.getText().toString().trim();
        String number = mNumber.getText().toString().trim();

        try {
            ContactsDB db = new ContactsDB(this);
            db.open();
            db.createEntry(name, number);
            db.close();
            Toast.makeText(this,"Successfully saved!!!",Toast.LENGTH_SHORT).show();
            mName.setText("");
            mNumber.setText("");
        }
        catch (SQLException e) {
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
        }

    }

    public void readData(View view) {
        startActivity(new Intent(this, ReadData.class));

    }

    public void deleteData(View view) {
        try {
            ContactsDB db = new ContactsDB(this);
            db.open();
            db.deleteEntry("1");
            db.close();
            Toast.makeText(this,"Successfully deleted!!!",Toast.LENGTH_SHORT).show();
        }
        catch (SQLException e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
        }

    }

    public void updateData(View view) {
        try {
            ContactsDB db = new ContactsDB(this);
            db.open();
            db.updateEntry("1","XXXXX","00000000");
            db.close();
            Toast.makeText(this,"Successfully updated!!!",Toast.LENGTH_SHORT).show();
        }
        catch (SQLException e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }
}


