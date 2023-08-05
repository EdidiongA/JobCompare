package edu.gatech.seclass.jobcompare6300;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class SaveJobOffer extends Job {
    public static final String DATABASE_NAME = "jobApp.db";
    public static final int DATABASE_VERSION = 1;
    DataBaseHelper dataBaseHelper;
    Button btn_exit, btn_enterAnother, btn_compareOfferWithCurrent;
    public int currentJobId;
    public int jobOfferId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //tells which view the class corresponds too
        setContentView(R.layout.save_job_offer);

        btn_exit = findViewById(R.id.btn_exit);
        btn_enterAnother = findViewById(R.id.btn_enterAnother);
        btn_compareOfferWithCurrent = findViewById(R.id.btn_compareOfferWithCurrent);

        //Redirect to main
        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SaveJobOffer.this, MainActivity.class);
                startActivityForResult(i, 0);
            }
        });

        //Redirect to Job class
        btn_enterAnother.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SaveJobOffer.this, Job.class);
                i.putExtra("isCurrentJob", false);
                startActivityForResult(i, 0);
            }
        });

        //Redirect to compare job display
        btn_compareOfferWithCurrent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataBaseHelper = new DataBaseHelper(SaveJobOffer.this,DATABASE_NAME,null,DATABASE_VERSION);
                currentJobId = dataBaseHelper.getCurrentJobId();
                jobOfferId = (int)getIntent().getLongExtra("jobOfferId", -1);

                Toast.makeText(SaveJobOffer.this, "Success = " + (((currentJobId == -1) || (jobOfferId == -1)) ? false : true), Toast.LENGTH_SHORT).show();

                if ((currentJobId != -1) && (jobOfferId != -1)) {
                    Intent i = new Intent(SaveJobOffer.this, CompareJobsDisplay.class);
                    i.putExtra("jobId1", currentJobId);
                    i.putExtra("jobId2", jobOfferId);
                    startActivityForResult(i, 0);
                }
            }
        });
    }
}
