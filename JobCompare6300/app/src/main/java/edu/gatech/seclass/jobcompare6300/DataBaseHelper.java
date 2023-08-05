package edu.gatech.seclass.jobcompare6300;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "jobApp.db";
    public static final String JOB_TABLE = "JOB_TABLE";
    public static final String COLUMN_JOB_TITLE = "JOB_TITLE";
    public static final String COLUMN_JOB_COMPANY = "JOB_COMPANY";
    public static final String COLUMN_JOB_LOCATION = "JOB_LOCATION";
    public static final String COLUMN_JOB_COL = "JOB_COL";
    public static final String COLUMN_JOB_COMMUTE_TIME = "JOB_COMMUTE_TIME";
    public static final String COLUMN_JOB_YEARLY_SALARY = "JOB_YEARLY_SALARY";
    public static final String COLUMN_JOB_YEARLY_BONUS = "JOB_YEARLY_BONUS";
    public static final String COLUMN_JOB_RETIREMENT_BENEFITS = "JOB_RETIREMENT_BENEFITS";
    public static final String COLUMN_JOB_LEAVE_TIME = "JOB_LEAVE_TIME";
    public static final String COLUMN_CURRENT_JOB = "JOB_CURRENT_JOB";
    public static final String COLUMN_JOB_SCORE = "JOB_RANK";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_CT_WEIGHT = "JOB_CT_WEIGHT";
    public static final String COLUMN_YS_WEIGHT = "JOB_YS_WEIGHT";
    public static final String COLUMN_YB_WEIGHT = "JOB_YB_WEIGHT";
    public static final String COLUMN_RB_WEIGHT = "JOB_RB_WEIGHT";
    public static final String COLUMN_LT_WEIGHT = "JOB_LT_WEIGHT";

    public DataBaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Called first time you access db object. It creates a new database.
    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_ENTRIES = "CREATE TABLE "
                + JOB_TABLE
                + " ("
                + COLUMN_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_JOB_TITLE
                + " TEXT NOT NULL, "
                + COLUMN_JOB_COMPANY
                + " TEXT NOT NULL, "
                + COLUMN_JOB_LOCATION
                + " TEXT NOT NULL, "
                + COLUMN_JOB_COL
                + " REAL NOT NULL, "
                + COLUMN_JOB_COMMUTE_TIME
                + " REAL NOT NULL, "
                + COLUMN_JOB_YEARLY_SALARY
                + " REAL NOT NULL, "
                + COLUMN_JOB_YEARLY_BONUS
                + " REAL NOT NULL, "
                + COLUMN_JOB_RETIREMENT_BENEFITS
                + " REAL NOT NULL, "
                + COLUMN_JOB_LEAVE_TIME
                + " REAL NOT NULL, "
                + COLUMN_CURRENT_JOB
                + " BOOL NOT NULL, "
                + COLUMN_JOB_SCORE
                + " REAL NOT NULL DEFAULT 0, "
                + COLUMN_CT_WEIGHT
                + " INTEGER NOT NULL DEFAULT 1, "
                + COLUMN_YS_WEIGHT
                + " INTEGER NOT NULL DEFAULT 1, "
                + COLUMN_YB_WEIGHT
                + " INTEGER NOT NULL DEFAULT 1, "
                + COLUMN_RB_WEIGHT
                + " INTEGER NOT NULL DEFAULT 1, "
                + COLUMN_LT_WEIGHT
                + " INTEGER NOT NULL DEFAULT 1 "
                + ")";

        db.execSQL(SQL_CREATE_ENTRIES);

    }

    //Called whenever version # is upgraded. Prevents previous user apps from breaking when db design changes.
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }


    public long addOneJobOffer(Job job) {
        SQLiteDatabase db = this.getWritableDatabase();
        //think of cv as a hashmap
        ContentValues cv = new ContentValues();
        //ADDS COLUMNS
        cv.put(COLUMN_JOB_TITLE, job.getJobTitle());
        cv.put(COLUMN_JOB_COMPANY, job.getJobCompany());
        cv.put(COLUMN_JOB_LOCATION, job.getLocation());
        cv.put(COLUMN_JOB_COL, job.getCOL());
        cv.put(COLUMN_JOB_COMMUTE_TIME, job.getCommuteTime());
        cv.put(COLUMN_JOB_YEARLY_SALARY, job.getYearlySalary());
        cv.put(COLUMN_JOB_YEARLY_BONUS, job.getYearlyBonus());
        cv.put(COLUMN_JOB_RETIREMENT_BENEFITS, job.getRetirementBenefitPercentage());
        cv.put(COLUMN_JOB_LEAVE_TIME, job.getLeaveTime());
        cv.put(COLUMN_CURRENT_JOB, job.isCurrentJob());
        cv.put(COLUMN_JOB_SCORE, job.getJobScore());
        //Command to actually insert into the db
        long insert = db.insert(JOB_TABLE, null, cv);
        return insert;
    }

    //Delete Job Offer
    public boolean deleteOne(Job job) {
        //find Job in db. If found, delete it and return true
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM " + JOB_TABLE + " WHERE " + COLUMN_ID + " = " + job.getId();
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            return true;
        } else {
            return false;
        }
    }

    //Delete all current jobs
    public boolean deleteCurrentJobs() {
        //find Job in db. If found, delete it and return true
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM " + JOB_TABLE + " WHERE " + COLUMN_CURRENT_JOB + " = 1";
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            return true;
        } else {
            return false;
        }
    }

    public int getCountOfCurrentJobs(){
        //SELECT COUNT (ID) FROM JOB_TABLE WHERE JOB_CURRENT_JOB=0;
        SQLiteDatabase db = this.getReadableDatabase();
        String queryString = "SELECT COUNT (" + COLUMN_ID + ")" + " FROM " + JOB_TABLE + " WHERE " + COLUMN_CURRENT_JOB + " =1 ";
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
             int count = cursor.getInt(0);
            return count;
        } else {
            int errorCount = -1;
            return errorCount;
        }
    }

    //Update Job Score
    public void updateScores( int jobId, double score) {
        SQLiteDatabase db = this.getWritableDatabase();
//        String queryString = "UPDATE" + JOB_TABLE + " SET " + COLUMN_JOB_SCORE + " = " + score + " WHERE " + COLUMN_ID + " = "
//                + jobId;
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_JOB_SCORE,score);
        //Command to actually insert into the db
       db.update(JOB_TABLE, cv,COLUMN_ID + " = " + jobId,null);
    }

    //item is the weight column that is to be adjusted
    public void updateWeights( int ct_weight, int lt_weight, int rb_weight,
                                  int ys_weight, int yb_weight ) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_LT_WEIGHT,lt_weight);
        cv.put(COLUMN_CT_WEIGHT,ct_weight);
        cv.put(COLUMN_RB_WEIGHT,rb_weight);
        cv.put(COLUMN_YB_WEIGHT,yb_weight);
        cv.put(COLUMN_YS_WEIGHT,ys_weight);
        //Updates every row in db with new weights
        db.update(JOB_TABLE,cv, null, null);
    }


    public List getAllJobs() {
        List returnList = new ArrayList<>();
        //get data from db
        String queryString = "SELECT * FROM " + JOB_TABLE + " ORDER BY JOB_RANK DESC ";
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cv = new ContentValues();

        //cursor return type
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {

            //loop through cursor(result set) and create new custome objects
            //do pattern as long as there are new lines
            do {
                int jobId = cursor.getInt(0);
                String jobTitle = cursor.getString(1);
                String jobCompany = cursor.getString(2);
                String location = cursor.getString(3);
                double COL = cursor.getDouble(4);
                double commuteTime = cursor.getDouble(5);
                double yearlySalary = cursor.getDouble(6);
                double yearlyBonus = cursor.getDouble(7);
                double retirementBenefitPercentage = cursor.getDouble(8);
                double leaveTime = cursor.getDouble(9);
                //No booleans in sql only 0 or 1(true) so we use ternary

                boolean isCurrentJob = cursor.getInt(10) == 1 ? true : false;
                double jobScore = cursor.getDouble(11);
                //Change currentJob true/False to a better identifier

                String currentJob;
                if(isCurrentJob == true){
                    currentJob = "Current Job";
                } else {
                    currentJob = "";
                }

                //Add to list ONLY what you want to show up on the list
                Job newJob = new Job(jobId, jobTitle, jobCompany, location, COL, commuteTime,
                        yearlySalary, yearlyBonus, retirementBenefitPercentage, leaveTime, isCurrentJob, jobScore);
                returnList.add(
                        newJob.getId() + " " +
                        newJob.getJobTitle() + " " +
                        newJob.getJobCompany() + " " +
                        currentJob);

                //moveToNext = go through db one at a time
            } while (cursor.moveToNext());
        } else {
            //failure, do not add to list
        }
        //close cursor and db
        cursor.close();
        db.close();
        return returnList;
    }

    public String getJobTitle(int jobId, String item) {
        String query = "SELECT " +  item + " FROM " + JOB_TABLE + " WHERE ID= " + jobId;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        cursor.moveToFirst();
        String result = cursor.getString(0);
        cursor.close();
        db.close();
        return  result;

    }

    public Double getJobScoreInfo(int jobId, String item) {
        String query = "SELECT " +  item + " FROM " + JOB_TABLE + " WHERE ID= " + jobId;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        cursor.moveToFirst();
        Double result = cursor.getDouble(0);
        cursor.close();
        db.close();
        return result;

    }
    public Integer getJobScoreWeights(int jobId, String item) {
        String query = "SELECT " +  item + " FROM " + JOB_TABLE + " WHERE ID= " + jobId;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        cursor.moveToFirst();
        int result = cursor.getInt(0);
        cursor.close();
        db.close();
        return result;

    }
    public int getCurrentJobId() {
        String queryString = "SELECT ID FROM " + JOB_TABLE + " WHERE " + COLUMN_CURRENT_JOB + "=1";
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cv = new ContentValues();
        int jobId = -1;
        //cursor return type
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        cursor.moveToFirst();
        jobId = cursor.getInt(0);
        //close cursor and db
        cursor.close();
        db.close();
        return jobId;
    }

    public List getAllJobIds() {
        List returnList = new ArrayList<>();
        //get data from db
        String queryString = "SELECT * FROM " + JOB_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cv = new ContentValues();
        //cursor return type
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            //loop through cursor(result set) and create new job objects
            //do pattern as long as there are new lines
            do {
                int jobId = cursor.getInt(0);
                returnList.add(jobId);
                //moveToNext = go through db one at a time
            } while (cursor.moveToNext());
        } else {
            //failure, do not add to list
            cursor.close();
            db.close();
        }
        //close cursor and db
        cursor.close();
        db.close();
        return returnList;
    }

    public long updateCurrentJob(int currentJobId, String title, String company, String location,
                                 double col, double ct, double ys, double yb, double rbp, double lt, boolean currentJob, double jobScore
                                 ) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_JOB_TITLE, title);
        cv.put(COLUMN_JOB_COMPANY, company);
        cv.put(COLUMN_JOB_LOCATION, location);
        cv.put(COLUMN_JOB_COL, col);
        cv.put(COLUMN_JOB_COMMUTE_TIME, ct);
        cv.put(COLUMN_JOB_YEARLY_SALARY, ys);
        cv.put(COLUMN_JOB_YEARLY_BONUS, yb);
        cv.put(COLUMN_JOB_RETIREMENT_BENEFITS, rbp);
        cv.put(COLUMN_JOB_LEAVE_TIME, lt);
        cv.put(COLUMN_CURRENT_JOB, currentJob);
        cv.put(COLUMN_JOB_SCORE, jobScore);
        long update = db.update(JOB_TABLE, cv,COLUMN_ID + " = " + currentJobId,null);
        return update;
        //Command to actually insert into the db

    }
}
