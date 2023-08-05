package edu.gatech.seclass.jobcompare6300;

import android.view.View;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.not;

// The system test cases here are described in the Team 121 Test Plan

@LargeTest
@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SystemTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);
    private View decorView; // Needed for checking toast messages

    @Before
    public void setUp() {
        // Needed for checking toast messages
        activityRule.getScenario().onActivity(activity -> {
            decorView = activity.getWindow().getDecorView();
        });
    }

    // Set job title to invalid value (empty)
    @Test
    public void TC_01() {
        onView(withId(R.id.addNewJob)).perform(click());
        onView(withId(R.id.et_companyName)).perform(clearText(), replaceText("Google"));
        onView(withId(R.id.et_location)).perform(clearText(), replaceText("Mountain View, CA"));
        onView(withId(R.id.et_COL)).perform(clearText(), replaceText("100"));
        onView(withId(R.id.et_commuteTime)).perform(clearText(), replaceText("0.5"));
        onView(withId(R.id.et_yearlySalary)).perform(clearText(), replaceText("150000"));
        onView(withId(R.id.et_yearlyBonus)).perform(clearText(), replaceText("10000"));
        onView(withId(R.id.et_retirementBenefits)).perform(clearText(), replaceText("10"));
        onView(withId(R.id.et_leaveTime)).perform(clearText(), replaceText("30"));
        onView(withId(R.id.btn_save)).perform(click());
        onView(withId(R.id.et_jobTitle)).check(matches(hasErrorText("Invalid Title")));
    }

    // Set job company to invalid value (empty)
    @Test
    public void TC_02() {
        onView(withId(R.id.addNewJob)).perform(click());
        onView(withId(R.id.et_jobTitle)).perform(clearText(), replaceText("Software Engineer"));
        onView(withId(R.id.et_location)).perform(clearText(), replaceText("Mountain View, CA"));
        onView(withId(R.id.et_COL)).perform(clearText(), replaceText("100"));
        onView(withId(R.id.et_commuteTime)).perform(clearText(), replaceText("0.5"));
        onView(withId(R.id.et_yearlySalary)).perform(clearText(), replaceText("150000"));
        onView(withId(R.id.et_yearlyBonus)).perform(clearText(), replaceText("10000"));
        onView(withId(R.id.et_retirementBenefits)).perform(clearText(), replaceText("10"));
        onView(withId(R.id.et_leaveTime)).perform(clearText(), replaceText("30"));
        onView(withId(R.id.btn_save)).perform(click());
        onView(withId(R.id.et_companyName)).check(matches(hasErrorText("Invalid Company")));
    }

    // Set job location to invalid value (empty)
    @Test
    public void TC_03() {
        onView(withId(R.id.addNewJob)).perform(click());
        onView(withId(R.id.et_jobTitle)).perform(clearText(), replaceText("Software Engineer"));
        onView(withId(R.id.et_companyName)).perform(clearText(), replaceText("Google"));
        onView(withId(R.id.et_COL)).perform(clearText(), replaceText("100"));
        onView(withId(R.id.et_commuteTime)).perform(clearText(), replaceText("0.5"));
        onView(withId(R.id.et_yearlySalary)).perform(clearText(), replaceText("150000"));
        onView(withId(R.id.et_yearlyBonus)).perform(clearText(), replaceText("10000"));
        onView(withId(R.id.et_retirementBenefits)).perform(clearText(), replaceText("10"));
        onView(withId(R.id.et_leaveTime)).perform(clearText(), replaceText("30"));
        onView(withId(R.id.btn_save)).perform(click());
        onView(withId(R.id.et_location)).check(matches(hasErrorText("Invalid Location")));
    }

    // Set job cost of living to invalid value (empty)
    @Test
    public void TC_04() {
        onView(withId(R.id.addNewJob)).perform(click());
        onView(withId(R.id.et_jobTitle)).perform(clearText(), replaceText("Software Engineer"));
        onView(withId(R.id.et_companyName)).perform(clearText(), replaceText("Google"));
        onView(withId(R.id.et_location)).perform(clearText(), replaceText("Mountain View, CA"));
        onView(withId(R.id.et_commuteTime)).perform(clearText(), replaceText("0.5"));
        onView(withId(R.id.et_yearlySalary)).perform(clearText(), replaceText("150000"));
        onView(withId(R.id.et_yearlyBonus)).perform(clearText(), replaceText("10000"));
        onView(withId(R.id.et_retirementBenefits)).perform(clearText(), replaceText("10"));
        onView(withId(R.id.et_leaveTime)).perform(clearText(), replaceText("30"));
        onView(withId(R.id.btn_save)).perform(click());
        onView(withId(R.id.et_COL)).check(matches(hasErrorText("Invalid Cost of Living")));
    }

    // Set job commute time to invalid value (empty)
    @Test
    public void TC_05() {
        onView(withId(R.id.addNewJob)).perform(click());
        onView(withId(R.id.et_jobTitle)).perform(clearText(), replaceText("Software Engineer"));
        onView(withId(R.id.et_companyName)).perform(clearText(), replaceText("Google"));
        onView(withId(R.id.et_location)).perform(clearText(), replaceText("Mountain View, CA"));
        onView(withId(R.id.et_COL)).perform(clearText(), replaceText("100"));
        onView(withId(R.id.et_yearlySalary)).perform(clearText(), replaceText("150000"));
        onView(withId(R.id.et_yearlyBonus)).perform(clearText(), replaceText("10000"));
        onView(withId(R.id.et_retirementBenefits)).perform(clearText(), replaceText("10"));
        onView(withId(R.id.et_leaveTime)).perform(clearText(), replaceText("30"));
        onView(withId(R.id.btn_save)).perform(click());
        onView(withId(R.id.et_commuteTime)).check(matches(hasErrorText("Invalid Commute Time")));
    }

    // Set job yearly salary to invalid value (empty)
    @Test
    public void TC_06() {
        onView(withId(R.id.addNewJob)).perform(click());
        onView(withId(R.id.et_jobTitle)).perform(clearText(), replaceText("Software Engineer"));
        onView(withId(R.id.et_companyName)).perform(clearText(), replaceText("Google"));
        onView(withId(R.id.et_location)).perform(clearText(), replaceText("Mountain View, CA"));
        onView(withId(R.id.et_COL)).perform(clearText(), replaceText("100"));
        onView(withId(R.id.et_commuteTime)).perform(clearText(), replaceText("0.5"));
        onView(withId(R.id.et_yearlyBonus)).perform(clearText(), replaceText("10000"));
        onView(withId(R.id.et_retirementBenefits)).perform(clearText(), replaceText("10"));
        onView(withId(R.id.et_leaveTime)).perform(clearText(), replaceText("30"));
        onView(withId(R.id.btn_save)).perform(click());
        onView(withId(R.id.et_yearlySalary)).check(matches(hasErrorText("Invalid Yearly Salary")));
    }

    // Set job yearly bonus to invalid value (empty)
    @Test
    public void TC_07() {
        onView(withId(R.id.addNewJob)).perform(click());
        onView(withId(R.id.et_jobTitle)).perform(clearText(), replaceText("Software Engineer"));
        onView(withId(R.id.et_companyName)).perform(clearText(), replaceText("Google"));
        onView(withId(R.id.et_location)).perform(clearText(), replaceText("Mountain View, CA"));
        onView(withId(R.id.et_COL)).perform(clearText(), replaceText("100"));
        onView(withId(R.id.et_commuteTime)).perform(clearText(), replaceText("0.5"));
        onView(withId(R.id.et_yearlySalary)).perform(clearText(), replaceText("150000"));
        onView(withId(R.id.et_retirementBenefits)).perform(clearText(), replaceText("10"));
        onView(withId(R.id.et_leaveTime)).perform(clearText(), replaceText("30"));
        onView(withId(R.id.btn_save)).perform(click());
        onView(withId(R.id.et_yearlyBonus)).check(matches(hasErrorText("Invalid Yearly Bonus")));
    }

    // Set job retirement benefits to invalid value (empty)
    @Test
    public void TC_08() {
        onView(withId(R.id.addNewJob)).perform(click());
        onView(withId(R.id.et_jobTitle)).perform(clearText(), replaceText("Software Engineer"));
        onView(withId(R.id.et_companyName)).perform(clearText(), replaceText("Google"));
        onView(withId(R.id.et_location)).perform(clearText(), replaceText("Mountain View, CA"));
        onView(withId(R.id.et_COL)).perform(clearText(), replaceText("100"));
        onView(withId(R.id.et_commuteTime)).perform(clearText(), replaceText("0.5"));
        onView(withId(R.id.et_yearlySalary)).perform(clearText(), replaceText("150000"));
        onView(withId(R.id.et_yearlyBonus)).perform(clearText(), replaceText("10000"));
        onView(withId(R.id.et_leaveTime)).perform(clearText(), replaceText("30"));
        onView(withId(R.id.btn_save)).perform(click());
        onView(withId(R.id.et_retirementBenefits)).check(matches(hasErrorText("Invalid Retirement Benefits")));
    }

    // Set job leave time to invalid value (empty)
    @Test
    public void TC_09() {
        onView(withId(R.id.addNewJob)).perform(click());
        onView(withId(R.id.et_jobTitle)).perform(clearText(), replaceText("Software Engineer"));
        onView(withId(R.id.et_companyName)).perform(clearText(), replaceText("Google"));
        onView(withId(R.id.et_location)).perform(clearText(), replaceText("Mountain View, CA"));
        onView(withId(R.id.et_COL)).perform(clearText(), replaceText("100"));
        onView(withId(R.id.et_commuteTime)).perform(clearText(), replaceText("0.5"));
        onView(withId(R.id.et_yearlySalary)).perform(clearText(), replaceText("150000"));
        onView(withId(R.id.et_yearlyBonus)).perform(clearText(), replaceText("10000"));
        onView(withId(R.id.et_retirementBenefits)).perform(clearText(), replaceText("10"));
        onView(withId(R.id.btn_save)).perform(click());
        onView(withId(R.id.et_leaveTime)).check(matches(hasErrorText("Invalid Leave Time")));
    }

    // Set commute time integer weight to invalid value (empty)
    @Test
    public void TC_10() {
        onView(withId(R.id.modify_setting)).perform(click());
        onView(withId(R.id.et_csYearlySalary)).perform(clearText(), replaceText("1"));
        onView(withId(R.id.et_csYearlyBonus)).perform(clearText(), replaceText("1"));
        onView(withId(R.id.et_csRetirementBenefits)).perform(clearText(), replaceText("1"));
        onView(withId(R.id.et_csLeaveTime)).perform(clearText(), replaceText("1"));
        onView(withId(R.id.btn_adjust)).perform(click());
        onView(withId(R.id.et_csCommuteTime)).check(matches(hasErrorText("Invalid Commute Time Integer Weight")));
    }

    // Set yearly salary integer weight to invalid value (empty)
    @Test
    public void TC_11() {
        onView(withId(R.id.modify_setting)).perform(click());
        onView(withId(R.id.et_csCommuteTime)).perform(clearText(), replaceText("1"));
        onView(withId(R.id.et_csYearlyBonus)).perform(clearText(), replaceText("1"));
        onView(withId(R.id.et_csRetirementBenefits)).perform(clearText(), replaceText("1"));
        onView(withId(R.id.et_csLeaveTime)).perform(clearText(), replaceText("1"));
        onView(withId(R.id.btn_adjust)).perform(click());
        onView(withId(R.id.et_csYearlySalary)).check(matches(hasErrorText("Invalid Yearly Salary Integer Weight")));
    }

    // Set yearly bonus integer weight to invalid value (empty)
    @Test
    public void TC_12() {
        onView(withId(R.id.modify_setting)).perform(click());
        onView(withId(R.id.et_csCommuteTime)).perform(clearText(), replaceText("1"));
        onView(withId(R.id.et_csYearlySalary)).perform(clearText(), replaceText("1"));
        onView(withId(R.id.et_csRetirementBenefits)).perform(clearText(), replaceText("1"));
        onView(withId(R.id.et_csLeaveTime)).perform(clearText(), replaceText("1"));
        onView(withId(R.id.btn_adjust)).perform(click());
        onView(withId(R.id.et_csYearlyBonus)).check(matches(hasErrorText("Invalid Yearly Bonus Integer Weight")));
    }

    // Set retirement benefits integer weight to invalid value (empty)
    @Test
    public void TC_13() {
        onView(withId(R.id.modify_setting)).perform(click());
        onView(withId(R.id.et_csCommuteTime)).perform(clearText(), replaceText("1"));
        onView(withId(R.id.et_csYearlySalary)).perform(clearText(), replaceText("1"));
        onView(withId(R.id.et_csYearlyBonus)).perform(clearText(), replaceText("1"));
        onView(withId(R.id.et_csLeaveTime)).perform(clearText(), replaceText("1"));
        onView(withId(R.id.btn_adjust)).perform(click());
        onView(withId(R.id.et_csRetirementBenefits)).check(matches(hasErrorText("Invalid Retirement Benefits Integer Weight")));
    }

    // Set leave time integer weight to invalid value (empty)
    @Test
    public void TC_14() {
        onView(withId(R.id.modify_setting)).perform(click());
        onView(withId(R.id.et_csCommuteTime)).perform(clearText(), replaceText("1"));
        onView(withId(R.id.et_csYearlySalary)).perform(clearText(), replaceText("1"));
        onView(withId(R.id.et_csYearlyBonus)).perform(clearText(), replaceText("1"));
        onView(withId(R.id.et_csRetirementBenefits)).perform(clearText(), replaceText("1"));
        onView(withId(R.id.btn_adjust)).perform(click());
        onView(withId(R.id.et_csLeaveTime)).check(matches(hasErrorText("Invalid Leave Time Integer Weight")));
    }

    // Set number of jobs selected to compare to invalid value (0)
    @Test
    public void TC_15() {
        onView(withId(R.id.compare_jobs)).perform(click());
        onView(withId(R.id.btn_compare)).perform(click());
        onView(withText("Success = false")).inRoot(withDecorView(not(decorView))).check(matches(isDisplayed()));
    }

    // Set current job details to valid values (Amazon)
    @Test
    public void TC_16() {
        onView(withId(R.id.addCurrentJob)).perform(click());
        onView(withId(R.id.et_jobTitle)).perform(clearText(), replaceText("Test Engineer"));
        onView(withId(R.id.et_companyName)).perform(clearText(), replaceText("Amazon"));
        onView(withId(R.id.et_location)).perform(clearText(), replaceText("Seattle, WA"));
        onView(withId(R.id.et_COL)).perform(clearText(), replaceText("120"));
        onView(withId(R.id.et_commuteTime)).perform(clearText(), replaceText("1"));
        onView(withId(R.id.et_yearlySalary)).perform(clearText(), replaceText("175000"));
        onView(withId(R.id.et_yearlyBonus)).perform(clearText(), replaceText("7500"));
        onView(withId(R.id.et_retirementBenefits)).perform(clearText(), replaceText("7.5"));
        onView(withId(R.id.et_leaveTime)).perform(clearText(), replaceText("20"));
        onView(withId(R.id.btn_save)).perform(click());
        onView(withText("Success = true")).inRoot(withDecorView(not(decorView))).check(matches(isDisplayed()));
    }

    // Set job offer details to valid values (Google)
    @Test
    public void TC_17() {
        onView(withId(R.id.addNewJob)).perform(click());
        onView(withId(R.id.et_jobTitle)).perform(clearText(), replaceText("Software Engineer"));
        onView(withId(R.id.et_companyName)).perform(clearText(), replaceText("Google"));
        onView(withId(R.id.et_location)).perform(clearText(), replaceText("Mountain View, CA"));
        onView(withId(R.id.et_COL)).perform(clearText(), replaceText("100"));
        onView(withId(R.id.et_commuteTime)).perform(clearText(), replaceText("0.5"));
        onView(withId(R.id.et_yearlySalary)).perform(clearText(), replaceText("150000"));
        onView(withId(R.id.et_yearlyBonus)).perform(clearText(), replaceText("10000"));
        onView(withId(R.id.et_retirementBenefits)).perform(clearText(), replaceText("10"));
        onView(withId(R.id.et_leaveTime)).perform(clearText(), replaceText("30"));
        onView(withId(R.id.btn_save)).perform(click());
        onView(withText("Success = true")).inRoot(withDecorView(not(decorView))).check(matches(isDisplayed()));
    }

    // Set all integer weights to valid values (1)
    @Test
    public void TC_18() {
        onView(withId(R.id.modify_setting)).perform(click());
        onView(withId(R.id.et_csCommuteTime)).perform(clearText(), replaceText("1"));
        onView(withId(R.id.et_csYearlySalary)).perform(clearText(), replaceText("1"));
        onView(withId(R.id.et_csYearlyBonus)).perform(clearText(), replaceText("1"));
        onView(withId(R.id.et_csRetirementBenefits)).perform(clearText(), replaceText("1"));
        onView(withId(R.id.et_csLeaveTime)).perform(clearText(), replaceText("1"));
        onView(withId(R.id.btn_adjust)).perform(click());
        onView(withText("Success = true")).inRoot(withDecorView(not(decorView))).check(matches(isDisplayed()));
    }

    // Set number of jobs selected to compare to valid value (2)
    @Test
    public void TC_19() {
        // Might want to add two valid jobs here
        onView(withId(R.id.compare_jobs)).perform(click());
        onData(anything()).inAdapterView(withId(R.id.lv_allJobList)).atPosition(0).perform(click());
        onData(anything()).inAdapterView(withId(R.id.lv_allJobList)).atPosition(1).perform(click());
        onView(withId(R.id.btn_compare)).perform(click());
        onView(withText("Success = true")).inRoot(withDecorView(not(decorView))).check(matches(isDisplayed()));
    }
}