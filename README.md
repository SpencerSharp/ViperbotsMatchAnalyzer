#ViperbotsMatchAnalyzer
Constantly updated FTC Match Analyzation Software originally created by Spencer Sharp and Ethan McCosky. 
The project is currently maintained by Spencer Sharp and Ethan McCosky from FTC team 4546 Snakebyte.

This software allows teams to analyze the level of teams at the competition based solely on final match results. The beauty of this software is that it works quite accurately with just final scores and doesnt require intensive stand scouting. It is a great resource for rookie teams and those just starting data analysis.

After the numbers are crunched, the rankings show the following data points:

Rank, Team Name and Number, MMR (More on this in a bit), Average Score (with Alliance Partner, uses standard deviation), Predicted score range (again, relies on a variant of standard deviation), Consistency (average difference between each score and the average score), Average MMR of partners (Pretty self-explanatory), BVM Ratio (A complicated combination of MMR, Avg Score, Consistency, and QP), QP/RP (In order to compare to the competition rankings)

###How Data is Shown

Our software uses the Apache POI API to interface with Microsoft Excel documents. Each competition has its own file with 3 sheets in it. First there is the matches sheet, which holds the match data in a nice color coded format, then there is the rankings sheet which is generated by the software, and finally there is the teams sheet which sould be filled out with the team numbers and names respectively. The first cell in the 1st and 3rd sheets is the number of matches/teams and MUST be accurate or the code won't work. (It is used by the Java Scanner package) The run.bat file is used to run the jar file (For Windows only) which has a built in command line interface. The data files wished to be analyzed must be somewhere in the dist folder (the location of the jar file) or it will not work. Other that, it is pretty simple if you take a look at the provided sample files.

###About MMR

Our MMR System is based off of what many popular video games use for ranked play. It takes into account both scores in the match as well as the quality of your alliance partner and your opponents. Each team starts at 1500 points and from there, depending on how their matches go, points are either subtracted or added.

For example, if you are with a roughly equal alliance partner, you would get each get equal points added (A scaling number based on the quality of opponents: Beating harder teams = more points) However, if your alliance partner was better than you, you would get less points and could even get points subtracted depending on the difference in skill. Additionally, if your alliance partner was worse, you would get more points than the first case.

The full algorithm is in the code (More specifically, in MatchRanker.java as part of the calcMMR() method) I encourage you to take a look at the full code for more details regarding how it works.
