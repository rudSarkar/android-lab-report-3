package xyz.rudra0x01.labreport3;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    EditText userName, collegeName;
    Button insertData, displayData, exitApp;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userName = findViewById(R.id.userName);
        collegeName = findViewById(R.id.collegeName);
        insertData = findViewById(R.id.insertData);
        displayData = findViewById(R.id.displayData);
        exitApp = findViewById(R.id.exitApp);


        db = openOrCreateDatabase("database_01", MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS student(student_id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, college VARCHAR);");

        insertData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = userName.getText().toString();
                String college = collegeName.getText().toString();

                if(!username.isEmpty() && !college.isEmpty()) {
                    String insertDataQuery = String.format("INSERT INTO student VALUES (null, '%s', '%s');", username, college).toString();

                    try {
                        db.execSQL(insertDataQuery);
                        Toast.makeText(getApplicationContext(), "Data inserted success.", Toast.LENGTH_LONG).show();

                        userName.setText("");
                        collegeName.setText("");

                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "Error occurred!", Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "Fill up the inputs.", Toast.LENGTH_LONG).show();
                }
            }
        });

        displayData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: create activity and pass the value and show the data into that activity
                Intent showDataIntent = new Intent(getApplicationContext(), ShowData.class);
                startActivity(showDataIntent);
                finish();
            }
        });

        exitApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Exit the application
                System.exit(0);
            }
        });

    }
}