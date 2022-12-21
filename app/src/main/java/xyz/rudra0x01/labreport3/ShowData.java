package xyz.rudra0x01.labreport3;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class ShowData extends AppCompatActivity {
    TextView getName, getCollege;
    Button prevData, goHome, NextData, updateData, deleteData;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_data);


        getName = findViewById(R.id.getName);
        getCollege = findViewById(R.id.getCollege);
        prevData = findViewById(R.id.prevData);
        goHome = findViewById(R.id.goHome);
        NextData = findViewById(R.id.NextData);
        updateData = findViewById(R.id.updateData);
        deleteData = findViewById(R.id.deleteData);

        // TODO: Open or create database
        db = openOrCreateDatabase("database_01", MODE_PRIVATE, null);
        final Cursor cursor = db.rawQuery("SELECT * FROM student", null);

        cursor.moveToFirst();

        int getIdIndex = cursor.getColumnIndex("student_id");
        int getNameIndex = cursor.getColumnIndex("name");
        int getCollegeIndex = cursor.getColumnIndex("college");

        try {
            getName.setText(cursor.getString(getNameIndex));
            getCollege.setText(cursor.getString(getCollegeIndex));
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), (CharSequence) e, Toast.LENGTH_LONG).show();
        }

        prevData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    cursor.moveToPrevious();
                    getName.setText(cursor.getString(getNameIndex));
                    getCollege.setText(cursor.getString(getCollegeIndex));
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "First record", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });

        NextData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    cursor.moveToNext();
                    getName.setText(cursor.getString(getNameIndex));
                    getCollege.setText(cursor.getString(getCollegeIndex));
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Last record", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });

        goHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goHomeIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(goHomeIntent);
                finish();
            }
        });

        updateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: pass cursor data to another activity
                Intent updateDataIntent = new Intent(getApplicationContext(), UpdateData.class);
                updateDataIntent.putExtra("student_id", cursor.getString(getIdIndex));
                updateDataIntent.putExtra("getName", cursor.getString(getNameIndex));
                updateDataIntent.putExtra("getCollege", cursor.getString(getCollegeIndex));
                startActivity(updateDataIntent);
            }
        });

        deleteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String deleteDataQuery = String.format("DELETE FROM student WHERE student_id='%s';", cursor.getString(getIdIndex)).toString();

                try {
                    db.execSQL(deleteDataQuery);
                    Toast.makeText(getApplicationContext(), "Data deleted.", Toast.LENGTH_LONG).show();
                    Intent refreshShowData = new Intent(getApplicationContext(), ShowData.class);
                    startActivity(refreshShowData);

                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Error occurred!", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });
    }
}