//===============Match Analyzer v2.3====================
//
//@Authors: Spencer Sharp and Ethan McCosky
//@Release Date: 1/24/15
//@Contributing Teams: 4545 Ouroboros and 4546 SnakeByte
//@File: MatchAnalyzer.java (Main Class)
//
//======================================================

package matchanalyzerv6;

import java.util.*;
import java.io.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import java.awt.Color;
import org.apache.poi.xssf.usermodel.XSSFColor;

public class MatchAnalyzerv6
{
    public static void main (String [] args) throws IOException 
    {
        System.out.println("===========================================\n\nFTC Match Analyzer\nBy: Ethan McCosky and Spencer Sharp\n\nUse this software to analyze the scoring\ncapabilities of teams in an FTC Competition\n\nPlease follow file conventions specified\n\n===========================================\n\n");
        Scanner sc = new Scanner(System.in);
        System.out.print("Please enter the name of the file you wish to analyze (Without file extension) :: ");
        String fName = sc.nextLine();
        ArrayList<Team> teams;
        ArrayList<Match> matches;
        MatchRanker a = new MatchRanker();
        teams = new ArrayList<Team>();
        matches = new ArrayList<Match>();
        File myFile = new File(fName + ".xlsx");//CHANGE NAME TO CURRENT COMPETITION
        InputStream inp = new FileInputStream(myFile);
        XSSFWorkbook wb = new XSSFWorkbook(inp);

        //Access the match result sheet
        Sheet matchList = wb.getSheetAt(0);
        Row matchNumRow = matchList.getRow(0);
        Cell amount = matchNumRow.getCell(0);

        //get the number of matches
        Double matchNum = amount.getNumericCellValue();

        //import each match and add to the matches list
        System.out.println("Importing Matches...");
        for(int i = 2; i < matchNum + 2; i++)
        {
            //get the teams and score data
            Row row = matchList.getRow(i);
            Cell red1 = row.getCell(1);
            String red1Name = String.valueOf((int)red1.getNumericCellValue());
            Cell red2 = row.getCell(2);
            String red2Name = String.valueOf((int)red2.getNumericCellValue());
            Cell blue1 = row.getCell(3);
            String blue1Name = String.valueOf((int)blue1.getNumericCellValue());
            Cell blue2 = row.getCell(4);
            String blue2Name = String.valueOf((int)blue2.getNumericCellValue());
            Cell redScore = row.getCell(5);
            Cell blueScore = row.getCell(6);
            Match match = new Match(red1Name,red2Name,blue1Name,blue2Name,redScore.getNumericCellValue(),blueScore.getNumericCellValue());
            matches.add(match);
        }
        inp.close();

        //Loop through the matches and calculate MMR for each team
        System.out.println("Looping through matches...");
        for(int i = 0; i < matches.size(); i++)
        {
            //Set default variables and pull in match data
            int indexOfr1 = -1;
            int indexOfr2 = -1;
            int indexOfb1 = -1;
            int indexOfb2 = -1;
            int redScore = matches.get(i).getRedScore();
            int blueScore = matches.get(i).getBlueScore();
            
            //Create teams if they don't exist, get the index if they do
            //also, assign the appropriate score to each team
            
            //Red 1
            String t1 = matches.get(i).red1();
            for(int j = 0; j < teams.size();j++)
                if(t1.equals(teams.get(j).getTeamString()))
                    indexOfr1 = j;
            if(indexOfr1 < 0)
            {
                indexOfr1 = teams.size();
                Team newTeam1 = new Team(t1, redScore, blueScore);
                teams.add(newTeam1);
            }
            else
            {
                teams.get(indexOfr1).addMatch(redScore,blueScore);
            }
            
            //Red 2
            String t2 = matches.get(i).red2();
            for(int j = 0; j < teams.size();j++)
                if(t2.equals(teams.get(j).getTeamString()))
                    indexOfr2 = j;
            if(indexOfr2 < 0)
            {
                indexOfr2 = teams.size();
                Team newTeam2 = new Team(t2, redScore, blueScore);
                teams.add(newTeam2);
            }
            else
            {
                teams.get(indexOfr2).addMatch(redScore,blueScore);
            }
            
            //Update MMR value for both red teams
            int oldMMRr1 = teams.get(indexOfr1).getMMR();
            int oldMMRr2 = teams.get(indexOfr2).getMMR();
            teams.get(indexOfr1).setMMR(a.calcMMR(oldMMRr1,oldMMRr2,redScore));
            System.out.println("Match # " + (i + 1));
            System.out.println(teams.get(indexOfr1).getTeamString() + " now has an MMR of " + teams.get(indexOfr1).getMMR());
            teams.get(indexOfr2).setMMR(a.calcMMR(oldMMRr2,oldMMRr1,redScore));
            System.out.println(teams.get(indexOfr2).getTeamString() + " now has an MMR of " + teams.get(indexOfr2).getMMR());
            
            //Create teams if they don't exist, get the index if they do
            //also, assign the appropriate score to each team
            
            //Blue 1
            String t3 = matches.get(i).blue1();
            for(int j = 0; j < teams.size();j++)
                if(t3.equals(teams.get(j).getTeamString()))
                    indexOfb1 = j;
            if(indexOfb1 < 0)
            {
                indexOfb1 = teams.size();
                Team newTeam1 = new Team(t3, blueScore, redScore);
                teams.add(newTeam1);
            }
            else
            {
                teams.get(indexOfb1).addMatch(blueScore,redScore);
            }
            
            //Blue 2
            String t4 = matches.get(i).blue2();
            for(int j = 0; j < teams.size();j++)
                if(t4.equals(teams.get(j).getTeamString()))
                    indexOfb2 = j;
            if(indexOfb2 < 0)
            {
                indexOfb2 = teams.size();
                Team newTeam2 = new Team(t4, blueScore, redScore);
                teams.add(newTeam2);
            }
            else
            {
                teams.get(indexOfb2).addMatch(blueScore,redScore);
            }
            
            //Update MMR value for both blue teams
            int oldMMRb1 = teams.get(indexOfb1).getMMR();
            int oldMMRb2 = teams.get(indexOfb2).getMMR();
            teams.get(indexOfb1).setMMR(a.calcMMR(oldMMRb1,oldMMRb2,blueScore));
            System.out.println(teams.get(indexOfb1).getTeamString() + " now has an MMR of " + teams.get(indexOfb1).getMMR());
            teams.get(indexOfb2).setMMR(a.calcMMR(oldMMRb2,oldMMRb1,blueScore));
            System.out.println(teams.get(indexOfb2).getTeamString() + " now has an MMR of " + teams.get(indexOfb2).getMMR());
        }
        
        //Set the each teams name from the provided team list (Sheet 3)
        
        //Open input stream
        InputStream inp3 = new FileInputStream(myFile);
        XSSFWorkbook wb2 = new XSSFWorkbook(inp3);
        Sheet teamList = wb2.getSheetAt(2);
        
        //get the total numbert of teams
        Row totTeamRow = teamList.getRow(0);
        Cell totTeams = totTeamRow.getCell(0);
        Double teamCount = totTeams.getNumericCellValue();
        
        //get each team name and add it to the appropriate team
        for (int i = 1; i <= teamCount; i++)
        {
            Row teamRow = teamList.getRow(i);
            
            //Get team number
            Cell teamNumber = teamRow.getCell(0);
            Double currentNum = teamNumber.getNumericCellValue();
            
            //find team in teams list
            int teamIndex = -1;
            for (int x = 0; x < teams.size(); x++)
            {
                if(currentNum == teams.get(x).getTeamNum() / 1.0)
                    teamIndex = x;
            }
            
            //get the team name
            Cell teamNameCell = teamRow.getCell(1);
            String teamName = teamNameCell.getRichStringCellValue().toString();
            System.out.println("Importing " + teamName);
            
            //make sure the team actually showed up (If they played a match and are in teams list)
            if(teamIndex >= 0)
            {
                teams.get(teamIndex).setName(teamName);
            }
        }

        //Rank teams based on MMR

        //Create temp arrayLists
        ArrayList<String> tempNames;
        tempNames = new ArrayList<String>();
        ArrayList<Integer> tempMMR;
        tempMMR = new ArrayList<Integer>();
        ArrayList<Team> tempTeams = new ArrayList<Team>();
        ArrayList<Double> tempRatios;
        tempRatios = new ArrayList<Double>();

        //Update team object variable and copy arrayLists to temp ArrayLists
        for(Team team : teams)
        {
            team.getAvgPartners(matches, teams);
            tempNames.add(team.getTeamString());
            tempMMR.add(team.getMMR());
            tempTeams.add(team);
            tempRatios.add(team.getRatio());
        }

        //Copy the tempArrayLists to the ranked lists in sorted order
        ArrayList<String> rankingsNames = new ArrayList<String>();
        ArrayList<Integer> rankingsMMR = new ArrayList<Integer>();
        ArrayList<Data> rankingsData = new ArrayList<Data>();
        ArrayList<Double> rankingsRatio = new ArrayList<Double>();
        for(int i = 0; i < teams.size();i++)
        {
            int spot = 0;
            int maxMMR = 0;
            Double maxRatio = 0.0;
            for(int j = 0; j < tempNames.size();j++)
            {
                if(tempRatios.get(j) > maxRatio)
                {
                    spot = j;
                    maxRatio = tempRatios.get(j);
                }
            }
            rankingsNames.add(tempNames.get(spot));
            rankingsMMR.add(tempMMR.get(spot));
            rankingsData.add(new Data(tempTeams.get(spot)));
            rankingsRatio.add(tempRatios.get(spot));
            tempMMR.remove(spot);
            tempNames.remove(spot);
            tempTeams.remove(spot);
            tempRatios.remove(spot);
        }


        //Instantiate the temp local ranking sheet
        InputStream inp2 = new FileInputStream(myFile);
        Workbook rankingsWB = new XSSFWorkbook(inp2);
        FileOutputStream os = new FileOutputStream(myFile);
        Sheet rankings;
        try
        {
            rankings = rankingsWB.getSheetAt(1);
        }
        catch(IllegalArgumentException e)
        {
            System.out.println("Rankings sheet not found... Creating one");
            rankings = rankingsWB.createSheet("Rankings");
        }

        //Create temp local ranking spreadsheet headers
        Row row1 = rankings.createRow(0);
        Cell rankTitle = row1.createCell(0);
        rankTitle.setCellValue("Rank");
        Cell teamNum = row1.createCell(1);
        teamNum.setCellValue("Team Number");
        Cell teamName = row1.createCell(2);
        teamName.setCellValue("Team Name");
        Cell mmrTitle = row1.createCell(3);
        mmrTitle.setCellValue("MMR");
        Cell avgTitle = row1.createCell(4);
        avgTitle.setCellValue("Average Score");
        Cell rangeTitle = row1.createCell(5);
        rangeTitle.setCellValue("Predicted Score Range");
        Cell consTitle = row1.createCell(6);
        consTitle.setCellValue("Consistency");
        Cell MMRavgTitle = row1.createCell(7);
        MMRavgTitle.setCellValue("Average MMR of Partners");
        Cell BVMRatioTitle = row1.createCell(8);
        BVMRatioTitle.setCellValue("BVM Ratio");
        Cell QPTitle = row1.createCell(9);
        QPTitle.setCellValue("QP points");
        Cell RPTitle = row1.createCell(10);
        RPTitle.setCellValue("RP points");

        //Section for custom team style colors

        //7161 style colors
        CellStyle bff = rankingsWB.createCellStyle();
        bff.setFillForegroundColor(IndexedColors.AQUA.getIndex());
        bff.setFillPattern(CellStyle.SOLID_FOREGROUND);
        //4545 style colors
        CellStyle ouroboros = rankingsWB.createCellStyle();
        ouroboros.setFillForegroundColor(IndexedColors.SEA_GREEN.getIndex());
        ouroboros.setFillPattern(CellStyle.SOLID_FOREGROUND);
        //6210 style colors
        XSSFCellStyle stryke = (XSSFCellStyle) rankingsWB.createCellStyle();
        XSSFColor strykeColor = new XSSFColor(Color.decode("#203764"));
        stryke.setFillForegroundColor(strykeColor);
        stryke.setFillPattern(CellStyle.SOLID_FOREGROUND);
        //6209 style colors
        XSSFCellStyle venom = (XSSFCellStyle) rankingsWB.createCellStyle();
        XSSFColor venomColor = new XSSFColor(Color.decode("#FF0000"));
        venom.setFillForegroundColor(venomColor);
        venom.setFillPattern(CellStyle.SOLID_FOREGROUND);
        //4546 style colors
        XSSFCellStyle snakebyte = (XSSFCellStyle) rankingsWB.createCellStyle();
        XSSFColor snakeByteColor = new XSSFColor(Color.decode("#8E1ACC"));
        snakebyte.setFillForegroundColor(snakeByteColor);
        snakebyte.setFillPattern(CellStyle.SOLID_FOREGROUND);
        //6209 style colors
        XSSFCellStyle quadx = (XSSFCellStyle) rankingsWB.createCellStyle();
        XSSFColor quadxColor = new XSSFColor(Color.decode("#D3B354"));
        quadx.setFillForegroundColor(quadxColor);
        quadx.setFillPattern(CellStyle.SOLID_FOREGROUND);

        //add the teams (in ranked order) to the temp local spreadsheet one at a time
        for(int i = 0; i < rankingsNames.size();i++)
        {
            Row row = rankings.createRow(i + 1);
            Cell rankCell = row.createCell(0);
            rankCell.setCellValue(i + 1);
            Cell teamCell = row.createCell(1);
            teamCell.setCellValue(Integer.parseInt(rankingsNames.get(i)));
            System.out.println("Adding " + rankingsNames.get(i) + " to the ranking list");

            //Check if custom style colors are needed
            if(Integer.parseInt(rankingsNames.get(i)) == 7161)
            {
                System.out.println("7161 colored aqua!");
                teamCell.setCellStyle(bff);
            }
            else if(Integer.parseInt(rankingsNames.get(i)) == 4545)
            {
                System.out.println("4545 colored sea green!");
                teamCell.setCellStyle(ouroboros);
            }
            else if(Integer.parseInt(rankingsNames.get(i)) == 6210)
            {
                System.out.println("6210 colored blue grey!");
                teamCell.setCellStyle(stryke);
            }
            else if(Integer.parseInt(rankingsNames.get(i)) == 6209)
            {
                System.out.println("6209 colored red!");
                teamCell.setCellStyle(venom);
            }
            else if(Integer.parseInt(rankingsNames.get(i)) == 4546)
            {
                System.out.println("4546 colored purple!");
                teamCell.setCellStyle(snakebyte);
            }
            else if(Integer.parseInt(rankingsNames.get(i)) == 6299)
            {
                System.out.println("6299 colored gold!");
                teamCell.setCellStyle(quadx);
            }

            //Assign appropriate data to the temp local spreadsheet
            Cell nameCell = row.createCell(2);
            nameCell.setCellValue(rankingsData.get(i).teamName);
            Cell MMRCell = row.createCell(3);
            MMRCell.setCellValue(rankingsMMR.get(i));
            Cell avg = row.createCell(4);
            avg.setCellValue(rankingsData.get(i).STDA);
            Cell rangeCell = row.createCell(5);
            rangeCell.setCellValue(rankingsData.get(i).range);
            Cell consCell = row.createCell(6);
            consCell.setCellValue(rankingsData.get(i).Consistency);
            Cell MMRavg = row.createCell(7);
            MMRavg.setCellValue(rankingsData.get(i).partnerMMR);
            Cell BVM = row.createCell(8);
            BVM.setCellValue(rankingsRatio.get(i));
            Cell QPval = row.createCell(9);
            QPval.setCellValue(rankingsData.get(i).QP);
            Cell RPval = row.createCell(10);
            RPval.setCellValue(rankingsData.get(i).RP);
        }


        //write the temp local spreadsheet to the master spreadsheet
        rankingsWB.write(os);

        //close the output stream
        os.close();
    }
}