package edu.gatech.seclass.jobcompare6300;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import static java.lang.Character.isWhitespace;

public class Job extends MainActivity {
    private int id;
    private String jobTitle, jobCompany, location;
    private double COL;
    private double commuteTime;
    private double yearlySalary;
    private double yearlyBonus;
    private double retirementBenefitPercentage;
    private double leaveTime;
    private double jobScore;
    private boolean isCurrentJob;
    EditText et_jobTitle, et_companyName, et_Location, et_COL, et_commuteTime, et_yearlySalary, et_yearlyBonus, et_retirementBenefits, et_leaveTime;
    Button btn_save, btn_cancel;
    public static final String DATABASE_NAME = "jobApp.db";
    public static final int DATABASE_VERSION = 1;
    DataBaseHelper dataBaseHelper;
    ModifySettings modifySettings = new ModifySettings();

    //toString is necessary for printing contents of class object
    @Override
    public String toString() {
        return "Job{" +
                "id=" + id +
                ", jobTitle='" + jobTitle + '\'' +
                ", jobCompany='" + jobCompany + '\'' +
                ", location='" + location + '\'' +
                ", COL=" + COL +
                ", commuteTime=" + commuteTime +
                ", yearlySalary=" + yearlySalary +
                ", yearlyBonus=" + yearlyBonus +
                ", retirementBenefitPercentage=" + retirementBenefitPercentage +
                ", leaveTime=" + leaveTime +
                ", isCurrentJob=" + isCurrentJob +
                '}';
    }

    //getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public String getJobCompany() {
        return jobCompany;
    }

    public String getLocation() {
        return location;
    }

    public double getCOL() {
        return COL;
    }

    public double getCommuteTime() {
        return commuteTime;
    }

    public double getYearlySalary() {
        return yearlySalary;
    }

    public double getYearlyBonus() {
        return yearlyBonus;
    }

    public double getRetirementBenefitPercentage() {
        return retirementBenefitPercentage;
    }

    public double getJobScore() {
        return jobScore;
    }

    public double getLeaveTime() {
        return leaveTime;
    }

    //constructors
    //Called when a new job is added
    public Job(int id, String jobTitle, String jobCompany, String location, double COL, double commuteTime, double yearlySalary, double yearlyBonus,
               double retirementBenefitPercentage, double leaveTime, boolean isCurrentJob, double jobScore) {
        this.id = id;
        this.jobTitle = jobTitle;
        this.jobCompany = jobCompany;
        this.location = location;
        this.COL = COL;
        this.commuteTime = commuteTime;
        this.yearlySalary = yearlySalary;
        this.yearlyBonus = yearlyBonus;
        this.retirementBenefitPercentage = retirementBenefitPercentage;
        this.leaveTime = leaveTime;
        this.isCurrentJob = isCurrentJob;
        this.jobScore = jobScore;

    }
    public boolean isCurrentJob() {
        return isCurrentJob;
    }

    public void setCurrentJob(boolean currentJob) {
        isCurrentJob = currentJob;
    }

    public Job() {
    }

    /**
     * Called when the activity is first created.
     */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_job_details);

        et_jobTitle = findViewById(R.id.et_jobTitle);
        et_companyName = findViewById(R.id.et_companyName);
        et_Location = findViewById(R.id.et_location);
        et_COL = findViewById(R.id.et_COL);
        et_commuteTime = findViewById(R.id.et_commuteTime);
        et_yearlySalary = findViewById(R.id.et_yearlySalary);
        et_yearlyBonus = findViewById(R.id.et_yearlyBonus);
        et_retirementBenefits = findViewById(R.id.et_retirementBenefits);
        et_leaveTime = findViewById(R.id.et_leaveTime);

        btn_cancel = findViewById(R.id.btn_cancel);
        btn_save = findViewById(R.id.btn_save);
        dataBaseHelper = new DataBaseHelper(Job.this,DATABASE_NAME,null,DATABASE_VERSION);

        //Button Listeners for saving redirects to SaveJobOffer class
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Validate the text input fields before doing anything else
                if (!fieldError())
                {
                    Job jobOffer;
                    isCurrentJob = getIntent().getBooleanExtra("isCurrentJob", false);

                    double COL = Double.parseDouble(et_COL.getText().toString());
                    double AYS = Double.parseDouble(et_yearlySalary.getText().toString());
                    double AYB = Double.parseDouble(et_yearlyBonus.getText().toString());
                    double RBP = Double.parseDouble(et_retirementBenefits.getText().toString());
                    double LT  = Double.parseDouble(et_leaveTime.getText().toString());
                    double CT  = Double.parseDouble(et_commuteTime.getText().toString());

                    // Per Professor Orso's answer here: https://piazza.com/class/kdrfwoqul5fx0?cid=421_f10
                    AYS = (100.0 / COL) * AYS;
                    AYB = (100.0 / COL) * AYB;

                    // RBP is a double that needs to turn into a decimal percentage per piazza (6.00 => 0.06)
                    RBP = RBP / 100.0;

                    double AYS_W = modifySettings.getYearlySalaryWeight();
                    double AYB_W = modifySettings.getYearlyBonusWeight();
                    double RBP_W = modifySettings.getRetirementBenefitsWeight();
                    double LT_W  = modifySettings.getLeaveTimeWeight();
                    double CT_W  = modifySettings.getCommuteWeight();

                    // Sum the weights
                    double SUM_W = CT_W + AYS_W + AYB_W + RBP_W + LT_W;

                    // Calculate the job score
                    double jobScore = ((AYS_W / SUM_W) * AYS) +
                                      ((AYB_W / SUM_W) * AYB) +
                                      ((RBP_W / SUM_W) * (RBP * AYS)) +
                                      ((LT_W  / SUM_W) * (LT * (AYS / 260))) -
                                      ((CT_W  / SUM_W) * (CT * (AYS / 8)));

                    try {
                        //create new job reference
                        jobOffer = new Job(-1,
                                et_jobTitle.getText().toString(),
                                et_companyName.getText().toString(),
                                et_Location.getText().toString(),
                                Double.parseDouble(et_COL.getText().toString()),
                                Double.parseDouble(et_commuteTime.getText().toString()),
                                Double.parseDouble(et_yearlySalary.getText().toString()),
                                Double.parseDouble(et_yearlyBonus.getText().toString()),
                                Double.parseDouble(et_retirementBenefits.getText().toString()),
                                Double.parseDouble(et_leaveTime.getText().toString()),
                                isCurrentJob,
                                jobScore
                        );

                    } catch (Exception e) {
                        Toast.makeText(Job.this, "Error creating Customer ", Toast.LENGTH_SHORT).show();
                        //if it fails insert a error
                        jobOffer = new Job(-1,
                                "error",
                                "error",
                                "error",
                                0,
                                0,
                                0,
                                0,
                                0,
                                0,
                                false,
                                0
                        );
                    }

                    DataBaseHelper dataBaseHelper = new DataBaseHelper(Job.this, DATABASE_NAME, null, DATABASE_VERSION);
                    long jobOfferId = dataBaseHelper.addOneJobOffer(jobOffer);
                    Toast.makeText(Job.this, "Success = " + (jobOfferId == -1 ? false : true), Toast.LENGTH_SHORT).show();

                    Intent i = new Intent(Job.this, SaveJobOffer.class);
                    i.putExtra("jobOfferId", jobOfferId);
                    startActivityForResult(i, 0);
                }
            }
        });

        //If they cancel redirect to main screen
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent(Job.this, MainActivity.class);
                startActivityForResult(i, 0);
            }
        });
    }

    // Set field error messages and return true if there are any errors
    private boolean fieldError() {
        boolean fieldErrorExists = false;
        String title = et_jobTitle.getText().toString();
        String company = et_companyName.getText().toString();
        String location = et_Location.getText().toString();
        String col = et_COL.getText().toString();
        String commute = et_commuteTime.getText().toString();
        String salary =  et_yearlySalary.getText().toString();
        String bonus = et_yearlyBonus.getText().toString();
        String benefits = et_retirementBenefits.getText().toString();
        String leave = et_leaveTime.getText().toString();

        if (title.isEmpty() || whitespace(title) ) {
            et_jobTitle.setError("Invalid Title");
            fieldErrorExists = true;
        }

        if (company.isEmpty() || whitespace(company) ) {
            et_companyName.setError("Invalid Company");
            fieldErrorExists = true;
        }

        if (location.isEmpty() || whitespace(location) ) {
            et_Location.setError("Invalid Location");
            fieldErrorExists = true;
        }

        if (col.isEmpty() || col.equals(".") ) {
            et_COL.setError("Invalid Cost of Living");
            fieldErrorExists = true;
        }

        if (commute.isEmpty() || commute.equals(".") ) {
            et_commuteTime.setError("Invalid Commute Time");
            fieldErrorExists = true;
        }

        if (salary.isEmpty() || salary.equals(".")) {
            et_yearlySalary.setError("Invalid Yearly Salary");
            fieldErrorExists = true;
        }

        if (bonus.isEmpty() || bonus.equals(".")) {
            et_yearlyBonus.setError("Invalid Yearly Bonus");
            fieldErrorExists = true;
        }

        if (benefits.isEmpty() || benefits.equals(".")) {
            et_retirementBenefits.setError("Invalid Retirement Benefits");
            fieldErrorExists = true;
        }

        if (leave.isEmpty() || leave.equals(".")) {
            et_leaveTime.setError("Invalid Leave Time");
            fieldErrorExists = true;
        }

        return fieldErrorExists;
    }

    // Count number of non-whitespace characters in string and return true if none found
    private boolean whitespace(String msg) {
        int count = 0;
        for (int i = 0; i < msg.length(); ++i) if (!isWhitespace(msg.charAt(i))) ++count;
        return count == 0 ? true : false;
    }
}