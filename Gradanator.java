//Logan Marek
//1/30/18
//TA: Alexander Johnson
//Section: AS
//Assignment 4: Gradantor
//This will produce a list of grade related items, 
//it will calcualate the users grade in the class 

//This imports the scanner stuff
import java.util.*;

public class Gradanator {
   
   //This sets what all exam are out of, e.g. 100
   public static final int DIV = 100;
   
   //This sets how many points can be earned for sections
   public static final int SECSWORTH = 5;
   
   //This sets how many sections there are a year
   public static final int SECSNUMBER = 6;
   
   public static void main(String[] args) { 
   
      //This creates the scanner object for later use
      Scanner console = new Scanner(System.in);
      
      
      
      double weightedScoreMid = midtermWeighted(console);
      double weightedScoreFinal = finalWeighted(console);
      double weightedScoreHomework = homeworkWeighted(console);
     
      grade(weightedScoreMid, weightedScoreFinal,
            weightedScoreHomework);
      
   }
   /*This returns the weighted score of the midterm as well as 
   giving the user instructions to the program 
   It accepts the parameter of the scanner to accept inputs*/
   public static double midtermWeighted(Scanner console) {
      
      System.out.println("This program reads exam/homework scores");
      System.out.println("and reports your overall course grade.");
      System.out.print("Weight (0-100)? ");
      
      //This takes in weight
      int weightMid = console.nextInt(); 
   
      System.out.print("Score earned? ");
      int score = console.nextInt(); 
      
      //This takes in score earned
      System.out.print("Were score shifted? (1=yes, 2=no)? ");  
      
      //This asks if scores were shifted 
      int scoreShift = console.nextInt(); 
      int scoreShiftAmt = 0;
      
      //This establishes what to do if scores were shifted
      if (scoreShift == 1) { 
         
         System.out.print("Shift amount? ");
         scoreShiftAmt = console.nextInt();
      } 
      int totalPointsMid = score + scoreShiftAmt;
      
      //This sets totalPoints from being greater than 100
      if (totalPointsMid > DIV) {
         totalPointsMid = DIV;
      }
      
      /*This converts the total exams are out of 
      to a double for percent calcuations */
      double div = DIV;
      double percentMid = totalPointsMid / div; 
      double weightedScoreMid = percentMid * weightMid;
      
      System.out.println("Total points =" + totalPointsMid + " / " + DIV);
      System.out.printf("Weighted score = %.1f / ", weightedScoreMid);
      System.out.print(weightMid + "\n\n");
      
      //This returns the weighted midterm score for use later
      return(weightedScoreMid);
      
   }
   
   //This returns the weighted score for the final
   //It takes in the scanner to accept user score inputs
   public static double finalWeighted(Scanner console){
   
      System.out.print("Final:\nWeight(0-100)? ");
      int finalWeight = console.nextInt();
      System.out.print("Score Earned? " );
      int finalScore = console.nextInt();
      System.out.print("Were scores shifted? (1=yes, 2=no) ");
      int scoreShift = console.nextInt();
      
      //This sets the score shift to be zero unless changed or "shifted"
      int scoreShiftAmt = 0;
      
      //This dictates what happens if scoreShift = TRUE
      if (scoreShift == 1) { 
         
         System.out.print("Shift amount? ");
         scoreShiftAmt = console.nextInt();
      } 
      
      int totalPointsFinal = finalScore + scoreShiftAmt;
      
      //This keeps totalPointsFinal <= 100 
      if (totalPointsFinal > DIV) {
         totalPointsFinal = DIV;
      }
     
      /*This converts the total exams are out of to a double 
      for percentage calculations */
      double div = DIV;
      double percentFinal = totalPointsFinal / div;
      double weightedScoreFinal = percentFinal * finalWeight;
     
      System.out.println("Total points = " + totalPointsFinal + " / " + DIV);
      System.out.printf("Weighted score = %.1f / ", weightedScoreFinal);
      System.out.print(finalWeight + "\n\n");
     
      //This returns the weighted score of the final for use later
      return(weightedScoreFinal);
      
   }
   
   //This returns the weighted homework score
   public static double homeworkWeighted(Scanner console) {
   
      System.out.print("Homework: \nWeight (0-100)? ");
      int homeworkWeight = console.nextInt();
      System.out.print("Number of Assignments? ");
      int homeworkNumber = console.nextInt();
      
      //These set score and max totals
      //for homework to zero to start with
      int assiScoreTotal = 0;
      int assiMaxTotal = 0;
      
      /*This automatically counts and asks about 
      total number of assignments and their scores*/
      for (int i = 1; i <= homeworkNumber; i++){
         System.out.print("Assignment " + i + " Score and max? ");
         int assiScore = console.nextInt();
         int assiMax = console.nextInt();
         
         /*This tallys total assignment score 
         as user enters scores*/
         assiScoreTotal = assiScoreTotal + assiScore;
         
         /* This tallys maximum total score 
         as user enters maximum scores */
         assiMaxTotal = assiMaxTotal + assiMax;
      }
      
      
      System.out.print("How many sections did you attend? ");
      int secsDone = console.nextInt();
      
      //This calculates section points
      int secsPoints = secsDone * SECSWORTH;
      
      //This calculates total section points that could have been earned
      int totalSecsPoints = SECSWORTH * SECSNUMBER;
      
      //This caps the max amount of section points
      //you can earnt to the max amount of 
      //section points possible
      if (secsPoints > totalSecsPoints) {
      
         secsPoints = totalSecsPoints;
      
      }
       
      System.out.println("Section Points = " + secsPoints + " / " + totalSecsPoints);
      
      //This computes all points earned
      int points = secsPoints + assiScoreTotal;
      
      //This computes total points earned
      int totalPoints = assiMaxTotal + totalSecsPoints;
      
      //This capds points from
      //being greater than total points
      //as specified in the assignment description
      if (points > totalPoints) {
      
         points = totalPoints;
      
      }
      
      System.out.println("Total Points = " + points + " / " + totalPoints);
      
      /*This sets the total points as a double
      for computing percents */
      double totalPointscalc = totalPoints;
      
      double weightedScoreHomework = (points / totalPointscalc) * homeworkWeight;
      System.out.printf("Weighted Score = %.1f / ", weightedScoreHomework);
      System.out.print(homeworkWeight + "\n\n");
      
      //This returns the weighted score of the homework for use later
      return(weightedScoreHomework);
      
   }
   
   //This method takes in weighted scores for
   //the midterm, final exam, and homework
   //and prints the final score for the class
   //as well as a message for the user
   public static void grade(double weightedScoreMid, 
                            double weightedScoreFinal, double weightedScoreHomework) {
      
      //This calculates the final percentage
      double grade = weightedScoreMid + weightedScoreFinal + weightedScoreHomework;
      
      
      //This sets initial values for
      //message given to user at the end
      //as well as GPA score for course
      double gradeDot = 0.0;
      String message = "";
      
      //This calculates what the minimum GPA  
      //gotten will be dependent on
      //the grade percentage
      if (grade >= 85.0) {
      
         gradeDot = 3.0;
         message = "Good job!";
         
      } else if ( grade >=75.0) {
      
         gradeDot = 2.0;
         message = "I'm proud of you.";
      
      } else if (grade >= 60.0) {
      
         gradeDot = 0.7;
         message = "You tried your hardest.";
         
      } else {
      
         message = "Better luck next time!";
      
      }
      
      System.out.printf("Overall percentage = %.1f \n", grade);
      System.out.println("Your grade will be at least: " + gradeDot);
      System.out.println(message);
      
      
      
      
   
   }
} 