package edu.gatech.seclass.jobcompare6300;

import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class DisplayJobs extends AppCompatActivity {
    public static final String DATABASE_NAME = "jobApp.db";
    public static final int DATABASE_VERSION = 1;
    int jobId1;
    int jobId2;
    ArrayAdapter jobArrayAdapter;
    Button btn_cancel;
    List<Integer> jobArrayList;

    public DisplayJobs(){ }

    public void setJobId1(int jobId1) {
        this.jobId1 = jobId1;
    }

    public void setJobId2(int jobId2) {
        this.jobId2 = jobId2;
    }

    Button btn_compare;
    DataBaseHelper dataBaseHelper;
    ListView lv_allJobList;

    /**
     * Called when the activity is first created.
     */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.compare_all_jobs);
        btn_cancel = findViewById(R.id.btn_cancel);
        btn_compare = findViewById(R.id.btn_compare);
        lv_allJobList = findViewById(R.id.lv_allJobList);
        dataBaseHelper = new DataBaseHelper(DisplayJobs.this, DATABASE_NAME, null, DATABASE_VERSION);
       //Calculate score
        updateAllScoresInDb(dataBaseHelper);
        //Show scores in desc rank
        ShowAllJobsOnListView(dataBaseHelper);
        //Redirect to main
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DisplayJobs.this, MainActivity.class);
                startActivityForResult(i, 0);
            }
        });

        //Currently showing same screen. need to show  job comparison
        btn_compare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Integer> selectJobId = new ArrayList<Integer>();
                List<String> selected = new ArrayList<String>();
                int cntChoice = lv_allJobList.getCount();
                SparseBooleanArray sparseBooleanArray = lv_allJobList.getCheckedItemPositions();

                for(int i = 0; i < cntChoice; i++){
                    if(sparseBooleanArray.get(i)) {
                        selected.add(lv_allJobList.getItemAtPosition(i).toString().replaceAll("(\\d+).+", "$1"));// + "\n";
                    }
                }

                for(String s : selected) selectJobId.add(Integer.valueOf(s));

                if (selected.size() != 2) {
                    Toast.makeText(DisplayJobs.this, "Success = " + false, Toast.LENGTH_SHORT).show();
                }

                else {
                    Toast.makeText(DisplayJobs.this, "Success = " + true, Toast.LENGTH_SHORT).show();

                    setJobId1(selectJobId.get(0));
                    setJobId2(selectJobId.get(1));
                    Intent i = new Intent(DisplayJobs.this, CompareJobsDisplay.class);
                    i.putExtra("jobId1", jobId1);
                    i.putExtra("jobId2", jobId2);
                    startActivityForResult(i, 0);
                }
            }});
    }

    //Gets all jobs from DB
    private void ShowAllJobsOnListView(DataBaseHelper dataBaseHelper2) {
        jobArrayAdapter = new ArrayAdapter<Job>(DisplayJobs.this, android.R.layout.simple_list_item_multiple_choice, dataBaseHelper2.getAllJobs());
        lv_allJobList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        lv_allJobList.setAdapter(jobArrayAdapter);
    }

    private void updateAllScoresInDb(DataBaseHelper dataBaseHelper) {
        //grab all jobs from the database
        jobArrayList = dataBaseHelper.getAllJobIds();
        for(int i=0; i<jobArrayList.size(); i++){
            int id = jobArrayList.get(i);
            double COL   = dataBaseHelper.getJobScoreInfo(id,DataBaseHelper.COLUMN_JOB_COL);
            double AYS   = dataBaseHelper.getJobScoreInfo(id,DataBaseHelper.COLUMN_JOB_YEARLY_SALARY);
            double AYB   = dataBaseHelper.getJobScoreInfo(id, DataBaseHelper.COLUMN_JOB_YEARLY_BONUS);
            double RBP   = dataBaseHelper.getJobScoreInfo(id, DataBaseHelper.COLUMN_JOB_RETIREMENT_BENEFITS);
            double LT    = dataBaseHelper.getJobScoreInfo(id, DataBaseHelper.COLUMN_JOB_LEAVE_TIME);
            double CT    = dataBaseHelper.getJobScoreInfo(id, DataBaseHelper.COLUMN_JOB_COMMUTE_TIME);
            double CT_W  = dataBaseHelper.getJobScoreWeights(id, DataBaseHelper.COLUMN_CT_WEIGHT);
            double LT_W  = dataBaseHelper.getJobScoreWeights(id, DataBaseHelper.COLUMN_LT_WEIGHT);
            double RBP_W = dataBaseHelper.getJobScoreWeights(id, DataBaseHelper.COLUMN_RB_WEIGHT);
            double AYS_W = dataBaseHelper.getJobScoreWeights(id, DataBaseHelper.COLUMN_YS_WEIGHT);
            double AYB_W = dataBaseHelper.getJobScoreWeights(id, DataBaseHelper.COLUMN_YB_WEIGHT);
            Double score = calculateScore(COL,AYS,AYB,RBP,LT,CT,CT_W,LT_W,RBP_W,AYS_W,AYB_W);
            dataBaseHelper.updateScores(id, score);
            Toast.makeText(DisplayJobs.this, "Updated all scores", Toast.LENGTH_SHORT).show();
        }
    }


    public double calculateScore(double COL, double AYS, double AYB, double RBP, double LT, double CT, double CT_W,
                                 double LT_W, double RBP_W, double AYS_W, double AYB_W ) {

        // Per Professor Orso's answer here: https://piazza.com/class/kdrfwoqul5fx0?cid=421_f10
        AYS = (100.0 / COL) * AYS;
        AYB = (100.0 / COL) * AYB;

        // RBP is a double that needs to turn into a decimal percentage per piazza (6.00 => 0.06)
        RBP = RBP / 100.0;

        // Sum the weights
        double SUM_W = CT_W + AYS_W + AYB_W + RBP_W + LT_W;

        // Calculate the job score
        double jobScore = ((AYS_W / SUM_W) * AYS) +
                          ((AYB_W / SUM_W) * AYB) +
                          ((RBP_W / SUM_W) * (RBP * AYS)) +
                          ((LT_W  / SUM_W) * (LT * (AYS / 260))) -
                          ((CT_W  / SUM_W) * (CT * (AYS / 8)));

        return jobScore;
    }
}