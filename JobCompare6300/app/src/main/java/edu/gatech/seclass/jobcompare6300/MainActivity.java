package edu.gatech.seclass.jobcompare6300;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    // references to menu controls on enter_offer (Member variables)
    TextView currentJobDetails, addJob, compareJob, modifySettings;
    Button btn_exitApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        currentJobDetails = findViewById(R.id.addCurrentJob);
        addJob = findViewById(R.id.addNewJob);
        compareJob = findViewById(R.id.compare_jobs);
        modifySettings = findViewById(R.id.modify_setting);
        btn_exitApp = findViewById(R.id.btn_exitApp);

        currentJobDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, CurrentJob.class);
                i.putExtra("isCurrentJob", true);
                startActivityForResult(i, 0);
            }
        });

        addJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, Job.class);
                i.putExtra("isCurrentJob", false);
                startActivityForResult(i, 0);
            }
        });

        compareJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, DisplayJobs.class);
                startActivityForResult(i, 0);
            };
        });

        modifySettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, ModifySettings.class);
                startActivityForResult(i, 0);
            }
        });

        //Exit application
        btn_exitApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                System.exit(0);
            }
        });
    }
}