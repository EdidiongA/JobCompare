package edu.gatech.seclass.jobcompare6300;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Character.isWhitespace;

public class ModifySettings extends AppCompatActivity {
    Button btn_cancel, btn_adjust;
    public static final String DATABASE_NAME = "jobApp.db";
    public static final int DATABASE_VERSION = 1;
    EditText et_csCommuteTime,et_csYearlyBonus, et_csYearlySalary, et_csRetirementBenefits, et_csLeaveTime;
    private int commuteWeight = 1;
    private int yearlyBonusWeight = 1;
    private int yearlySalaryWeight= 1;
    private int retirementBenefitsWeight = 1;
    private int leaveTimeWeight = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //tells which view the class corresponds too
        setContentView(R.layout.modify_settings);
        btn_adjust = findViewById(R.id.btn_adjust);
        btn_cancel = findViewById(R.id.btn_cancel);
        et_csCommuteTime = findViewById(R.id.et_csCommuteTime);
        et_csYearlyBonus = findViewById(R.id.et_csYearlyBonus);
        et_csYearlySalary = findViewById(R.id.et_csYearlySalary);
        et_csRetirementBenefits = findViewById(R.id.et_csRetirementBenefits);
        et_csLeaveTime = findViewById(R.id.et_csLeaveTime);

        btn_adjust = findViewById(R.id.btn_adjust);
        btn_cancel = findViewById(R.id.btn_cancel);
        DataBaseHelper dataBaseHelper = new DataBaseHelper(ModifySettings.this, DATABASE_NAME, null, DATABASE_VERSION);

        btn_adjust.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!fieldError()) {
                    //set values to whats been adjusted
                    setCommuteWeight(Integer.parseInt(et_csCommuteTime.getText().toString()));
                    setYearlyBonusWeight(Integer.parseInt(et_csYearlyBonus.getText().toString()));
                    setYearlySalaryWeight(Integer.parseInt(et_csYearlySalary.getText().toString()));
                    setRetirementBenefitsWeight(Integer.parseInt(et_csRetirementBenefits.getText().toString()));
                    setLeaveTimeWeight(Integer.parseInt(et_csLeaveTime.getText().toString()));

                    UpdateAllWeightsInDB(dataBaseHelper);
                    Toast.makeText(ModifySettings.this, "Success = " + true, Toast.LENGTH_SHORT).show();

                    Intent i = new Intent(ModifySettings.this, MainActivity.class);
                    startActivityForResult(i, 0);
                }
            }
        });

        //Redirect to main
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ModifySettings.this, MainActivity.class);
                startActivityForResult(i, 0);
            }
        });
    }

    private void UpdateAllWeightsInDB(DataBaseHelper dataBaseHelper) {
            int ct_weight = Integer.parseInt(et_csCommuteTime.getText().toString());
            int lt_weight = Integer.parseInt(et_csLeaveTime.getText().toString());
            int rb_weight = Integer.parseInt(et_csRetirementBenefits.getText().toString());
            int ys_weight = Integer.parseInt(et_csYearlySalary.getText().toString());
            int yb_weight = Integer.parseInt(et_csYearlyBonus.getText().toString());
            dataBaseHelper.updateWeights(ct_weight,lt_weight,rb_weight,yb_weight,ys_weight);
        }


    public int getCommuteWeight() {
        return commuteWeight;
    }

    public void setCommuteWeight(int commuteWeight) {
        this.commuteWeight = commuteWeight;
    }

    public int getYearlyBonusWeight() {
        return yearlyBonusWeight;
    }

    public void setYearlyBonusWeight(int yearlyBonusWeight) {
        this.yearlyBonusWeight = yearlyBonusWeight;
    }

    public int getYearlySalaryWeight() {
        return yearlySalaryWeight;
    }

    public void setYearlySalaryWeight(int yearlySalaryWeight) {
        this.yearlySalaryWeight = yearlySalaryWeight;
    }

    public int getRetirementBenefitsWeight() {
        return retirementBenefitsWeight;
    }

    public void setRetirementBenefitsWeight(int retirementBenefitsWeight) {
        this.retirementBenefitsWeight = retirementBenefitsWeight;
    }

    public int getLeaveTimeWeight() {
        return leaveTimeWeight;
    }

    public void setLeaveTimeWeight(int leaveTimeWeight) {
        this.leaveTimeWeight = leaveTimeWeight;
    }

    // Set field error messages and return true if there are any errors
    private boolean fieldError() {
        boolean fieldErrorExists = false;
        String commute = et_csCommuteTime.getText().toString();
        String salary =  et_csYearlySalary.getText().toString();
        String bonus = et_csYearlyBonus.getText().toString();
        String benefits = et_csRetirementBenefits.getText().toString();
        String leave = et_csLeaveTime.getText().toString();

        if (commute.isEmpty() || Integer.parseInt(commute) == 0) {
            et_csCommuteTime.setError("Invalid Commute Time Integer Weight");
            fieldErrorExists = true;
        }

        if (salary.isEmpty() || Integer.parseInt(salary) == 0) {
            et_csYearlySalary.setError("Invalid Yearly Salary Integer Weight");
            fieldErrorExists = true;
        }

        if (bonus.isEmpty() || Integer.parseInt(bonus) == 0) {
            et_csYearlyBonus.setError("Invalid Yearly Bonus Integer Weight");
            fieldErrorExists = true;
        }

        if (benefits.isEmpty() || Integer.parseInt(benefits) == 0) {
            et_csRetirementBenefits.setError("Invalid Retirement Benefits Integer Weight");
            fieldErrorExists = true;
        }

        if (leave.isEmpty() || Integer.parseInt(leave) == 0) {
            et_csLeaveTime.setError("Invalid Leave Time Integer Weight");
            fieldErrorExists = true;
        }

        return fieldErrorExists;
    }
}
