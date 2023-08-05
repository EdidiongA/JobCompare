package edu.gatech.seclass.jobcompare6300;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class CompareJobsDisplay extends AppCompatActivity {
    public static final String DATABASE_NAME = "jobApp.db";
    public static final int DATABASE_VERSION = 1;
    Button btn_exit, btnCompareAgain;
    int JobId1,JobId2;
    TextView job1,job2,company1,company2;
    TextView location1, location2, commute1,commute2;
    TextView salary1,salary2,bonus1,bonus2;
    TextView benefits1, benefits2,vacation1,vacation2;
    TextView current1,current2;
    DisplayJobs displayJobs;
    DataBaseHelper dataBaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare_jobs_display);
        btn_exit = findViewById(R.id.btn_exit);
        btnCompareAgain = findViewById(R.id.btnCompareAgain);
        job1 = findViewById(R.id.job1);
        job2 = findViewById(R.id.job2);

        company1 = findViewById(R.id.company1);
        company2 = findViewById(R.id.company2);

        location1 = findViewById(R.id.location1);
        location2 = findViewById(R.id.location2);

        commute1 = findViewById(R.id.commute1);
        commute2 = findViewById(R.id.commute2);

        salary1 = findViewById(R.id.salary1);
        salary2 = findViewById(R.id.salary2);

        bonus1 = findViewById(R.id.bonus1);
        bonus2 = findViewById(R.id.bonus2);

        benefits1 = findViewById(R.id.benefits1);
        benefits2 = findViewById(R.id.benefits2);

        vacation1 = findViewById(R.id.vacation1);
        vacation2 = findViewById(R.id.vacation2);

        current1 = findViewById(R.id.current1);
        current2 = findViewById(R.id.current2);

        dataBaseHelper = new DataBaseHelper(CompareJobsDisplay.this, DATABASE_NAME, null, DATABASE_VERSION);
        displayJobs = new DisplayJobs();
        Intent iin= getIntent();
        Bundle b = iin.getExtras();

        if(b!= null) {
            JobId1 = b.getInt("jobId1");
            JobId2 = b.getInt("jobId2");
            populateList1(JobId1,dataBaseHelper,displayJobs);
            populateList2(JobId2,dataBaseHelper,displayJobs);
        }

        //Redirect to main
        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CompareJobsDisplay.this, MainActivity.class);
                startActivityForResult(i, 0);
            }
        });

        //Return to CompareJobsSelect class
        btnCompareAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CompareJobsDisplay.this, DisplayJobs.class);
                startActivityForResult(i, 0);
            }
        });
    }

    private void populateList1(int JobId, DataBaseHelper dataBaseHelper, DisplayJobs displayJobs) {
        job1.setText(dataBaseHelper.getJobTitle(JobId,DataBaseHelper.COLUMN_JOB_TITLE));
        company1.setText(dataBaseHelper.getJobTitle(JobId,DataBaseHelper.COLUMN_JOB_COMPANY));
        location1.setText(dataBaseHelper.getJobTitle(JobId,DataBaseHelper.COLUMN_JOB_LOCATION));
        commute1.setText(dataBaseHelper.getJobTitle(JobId,DataBaseHelper.COLUMN_JOB_COMMUTE_TIME));
        salary1.setText(dataBaseHelper.getJobTitle(JobId,DataBaseHelper.COLUMN_JOB_YEARLY_SALARY));
        bonus1.setText(dataBaseHelper.getJobTitle(JobId,DataBaseHelper.COLUMN_JOB_YEARLY_BONUS));
        benefits1.setText(dataBaseHelper.getJobTitle(JobId,DataBaseHelper.COLUMN_JOB_RETIREMENT_BENEFITS));
        vacation1.setText(dataBaseHelper.getJobTitle(JobId,DataBaseHelper.COLUMN_JOB_LEAVE_TIME));
        current1.setText(dataBaseHelper.getJobTitle(JobId,DataBaseHelper.COLUMN_CURRENT_JOB));

    }

    private void populateList2(int JobId, DataBaseHelper dataBaseHelper, DisplayJobs displayJobs) {
        job2.setText(dataBaseHelper.getJobTitle(JobId,DataBaseHelper.COLUMN_JOB_TITLE));
        company2.setText(dataBaseHelper.getJobTitle(JobId,DataBaseHelper.COLUMN_JOB_COMPANY));
        location2.setText(dataBaseHelper.getJobTitle(JobId,DataBaseHelper.COLUMN_JOB_LOCATION));
        commute2.setText(dataBaseHelper.getJobTitle(JobId,DataBaseHelper.COLUMN_JOB_COMMUTE_TIME));
        salary2.setText(dataBaseHelper.getJobTitle(JobId,DataBaseHelper.COLUMN_JOB_YEARLY_SALARY));
        bonus2.setText(dataBaseHelper.getJobTitle(JobId,DataBaseHelper.COLUMN_JOB_YEARLY_BONUS));
        benefits2.setText(dataBaseHelper.getJobTitle(JobId,DataBaseHelper.COLUMN_JOB_RETIREMENT_BENEFITS));
        vacation2.setText(dataBaseHelper.getJobTitle(JobId,DataBaseHelper.COLUMN_JOB_LEAVE_TIME));
        current2.setText(dataBaseHelper.getJobTitle(JobId,DataBaseHelper.COLUMN_CURRENT_JOB));
    }
}