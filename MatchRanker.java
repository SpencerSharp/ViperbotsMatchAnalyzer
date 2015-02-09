//===============Match Analyzer v2.3====================
//
//@Authors: Spencer Sharp and Ethan McCosky
//@Release Date: 1/24/15
//@Contributing Teams: 4545 Ouroboros and 4546 SnakeByte
//@File: MatchRanker.java (Provides methods to analyze score data)
//
//======================================================

package matchanalyzerv2;

import java.util.ArrayList;

public class MatchRanker {
	
        private ArrayList<Integer> scores;

        public MatchRanker()
        {
        }

        public MatchRanker(ArrayList<Integer> s)
        {
            scores = s;
        }

        public int getAvg()
        {
            int sum = 0;
            for (Integer item : scores)
            {
                sum += item;
            }
            int length = scores.size();
            int avg = 0;
            if (length > 0)
                avg = sum / scores.size();
            else
                avg = sum;
            return avg;
        }

    	private Double std()
    	{
            int avg = getAvg();
            ArrayList<Integer> vars = new ArrayList<Integer>();

            //get differences between each score and avg
            for(Integer score : scores)
            {
                    vars.add(Math.abs(score - avg));
            }

            Double var = 0.0;
            for (Integer item : vars)
            {
                    var += Math.pow(item, 2);
            }
            Double std = Math.sqrt(var/scores.size());
            return std;
    	}
    	
    	public int getConsistency()
    	{
            int a = (int) Math.round(std());
            return a;
    	}
    	
    	public int getSTDA()
    	{
            Double STDA = 0.0;
            Double sum = 0.0;
            Double count = 0.0;
            int avg = getAvg();
            Double STD = std();
            Double stdLow = (avg - STD);
            Double stdHigh = (avg + STD);
            for (int i = 0; i < scores.size(); i++)
            {
                    if(scores.get(i) > stdLow && scores.get(i) < stdHigh)
                    {
                            sum += scores.get(i);
                            count++;
                    }
            }
            STDA = sum / count;
            int a = (int) Math.round(STDA);
            return a;
    	}
        
        public int calcMMR(int curMMR, int allyMMR, int score)
        {
            double newMMR = (double) curMMR;
            double dAllyMMR = (double) allyMMR;
            double dScore = (double) score;
            double subtract = dAllyMMR / 5;
            double add = score * 40;
            add = add * add;
            add = Math.pow(add, 1.0/3);
            newMMR += add;
            newMMR -= subtract;
            int finalMMR = (int) Math.round(newMMR);
            return finalMMR;
        }
}