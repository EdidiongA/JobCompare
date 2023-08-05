package edu.gatech.seclass.jobcompare6300;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
public class JobUnitTest {
    private DisplayJobs displayJobs;
    @Before
    public void setUp(){

    }

    @Test
    public void testCalculateScoreIsNotNull(){
        DisplayJobs displayJobs = new DisplayJobs();
        double AYS   = 10212;
        double AYB   = 112;
        double RBP   = 112;
        double LT    = 11;
        double COL = 121;
        double CT    = 11;
        double CT_W  = 1;
        double LT_W  = 1;
        double RBP_W = 1;
        double AYS_W = 1;
        double AYB_W = 1;
        Double sum = displayJobs.calculateScore(COL,AYS,AYB,RBP,LT,CT,CT_W,LT_W,RBP_W,AYS_W,AYB_W);
        assertNotNull(sum);
    }
    @Test
    public void testCalculateScore(){
        DisplayJobs displayJobs = new DisplayJobs();
        double AYS   = 10212;
        double AYB   = 112;
        double RBP   = 112;
        double LT    = 11;
        double CT    = 11;
        double col = 122;

        double CT_W  = 1;
        double LT_W  = 1;
        double RBP_W = 1;
        double AYS_W = 1;
        double AYB_W = 1;
        double SUM_W = CT_W + AYS_W + AYB_W + RBP_W + LT_W;
        double sum;
        sum = displayJobs.calculateScore(col, AYS,AYB,RBP,LT,CT,CT_W,LT_W,RBP_W,AYS_W,AYB_W);
        assertEquals(1336.3911727616646,sum,0);
    }

    @Test
    public void testCalculateScore2(){
        DisplayJobs displayJobs = new DisplayJobs();
        double AYS   = 10212;
        double AYB   = 112;
        double RBP   = 112;
        double LT    = 11;
        double CT    = 11;

        double CT_W  = 2;
        double LT_W  = 2;
        double RBP_W = 4;
        double AYS_W = 2;
        double AYB_W = 5;
        double col = 122;
        double SUM_W = CT_W + AYS_W + AYB_W + RBP_W + LT_W;
        double sum;
        Double jobScore = ((AYS_W / SUM_W) * AYS) +
                ((AYB_W / SUM_W) * AYB) +
                ((RBP_W / SUM_W) * (RBP * AYS)) +
                ((LT_W  / SUM_W) * (LT * (AYS / 260))) -
                ((CT_W  / SUM_W) * (CT * (AYS / 8)));
        sum = displayJobs.calculateScore(col,AYS,AYB,RBP,LT,CT,CT_W,LT_W,RBP_W,AYS_W,AYB_W);
        assertEquals(2159.281546868432,sum,0);
    }
}
