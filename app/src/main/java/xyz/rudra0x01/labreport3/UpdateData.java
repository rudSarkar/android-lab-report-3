package xyz.rudra0x01.labreport3;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class UpdateData extends AppCompatActivity {

    EditText getName, getCollege;
    Button updateData;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_data);

        getName = findViewById(R.id.getName);
        getCollege = findViewById(R.id.getCollege);
        updateData = findViewById(R.id.updateData);

        // TODO: get intent data and set the data to the preferable input
        Intent intentData = getIntent();
        String getIdIntent = intentData.getStringExtra("student_id");
        String getNameIntent = intentData.getStringExtra("getName");
        String getCollegeIntent = intentData.getStringExtra("getCollege");

        getName.setText(getNameIntent);
        getCollege.setText(getCollegeIntent);

        db = openOrCreateDatabase("database_01", MODE_PRIVATE, null);

        Log.i("student_id", getIdIntent.toString());

        // TODO: Write update query
        updateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = getName.getText().toString();
                String college = getCollege.getText().toString();

                Log.i("db_id", getIdIntent.toString());

                if(!username.isEmpty() && !college.isEmpty()) {
                    String updateDataQuery = String.format("UPDATE student SET name='%s', college='%s' WHERE student_id='%s';", username, college, Integer.parseInt(getIdIntent.toString())).toString();

                    Log.i("update_query", updateDataQuery);

                    try {
                        db.execSQL(updateDataQuery);
                        Toast.makeText(getApplicationContext(), "Data Update success.", Toast.LENGTH_LONG).show();

                        Intent goDisplay = new Intent(getApplicationContext(), ShowData.class);
                        startActivity(goDisplay);

                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "Error occurred!", Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Fill up the inputs.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}