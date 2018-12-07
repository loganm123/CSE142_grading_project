//Logan Marek
//2/27/18
//TA: Alexander Johnson
//Section: AS
//Assignment 7: DNA
//This will produce various facts and
//statistics about a given file with strips
//of DNA

import java.util.*;
import java.io.*;

public class DNA {

   //This constant says the number of codons a valid protein has
   public static final int CODONS = 5;
   
   //This constant sets the percent mass from C to G for a protein
   //to be valid
   public static final int PERCENTMASS = 30;
   
   //This constant sets the number of unique nucleotides
   public static final int NUCLEOS = 4;
      
   //This constant sets the number of nucleotides
   //per codon
   public static final int NUCPERCOD = 3;
   
   //This is an array of the mass of all the nucleotides
   //and junk
   public static final double MASSESNUCS[] = {135.128,111.103,151.128,125.107,100.000};

   public static void main(String[] args) throws FileNotFoundException {
      
      Scanner console = new Scanner(System.in);
      
      String nameFile = inputTaker(console);
      
      //This creates a scanner for 
      //the user specified file
      Scanner dnaReader = new Scanner(new File(nameFile));
      
      //This prompts the user to create the name of the
      //output file
      System.out.print("Output file name? ");
      String outputName = console.next();
      
      //This creates the output file, naming it
      //whatever the user types in
      PrintStream outputFile = new PrintStream(new File(outputName));
      
      //This goes through the file line by line
      //stopping when the input file has no 
      //more lines to go through
      while(dnaReader.hasNextLine()) {
      
         //This is a string value for the current line
         String line = dnaReader.nextLine();
                  
         boolean regionName = false;
         String [] codonCounter = {};
         int[] everythingCounter = {};
         int [] nucCountReal = {};
         double[] massPerAndTotal = {};
         double[] massPer = {};
         String protCheck = "";
         
         //This prints the line if it just 
         //contains the name of the protein
         if(line.contains(" ")) {
         
            regionName = true;
            
         } else {
         
            //This is a count of every nucleotide 
            //it's stored as an array as well as the junk
            //in the molecule
            everythingCounter = nucCounts(line);
   
            
            //This counts the codons in the molecule
            //it skips the junk spaces
            codonCounter = codonCounterXtreme(line,everythingCounter);
   
            
            //This is an array to hold the count 
            //of just nucleotides in a molecule
            nucCountReal = new int[NUCLEOS];
            
            //This converts the array that has the junk included
            //into an array that just has the count of 
            //nucleotides
            for (int i = 0; i < NUCLEOS; i++) {
               nucCountReal[i] = everythingCounter[i];
            }
   
            //This is an array that stores the mass percents
            //of each nucleotide and the final total
            //mass of the molecule
            massPerAndTotal = massCalc(everythingCounter);
   
            //This is an array to keep track of 
            //the mass percents of the nucleotides
            //in the given molcule
            massPer = new double [NUCLEOS];
            
            //This selects only the percent masses of the nucleotides
            for (int i = 0; i < massPerAndTotal.length - 1; i ++) {
                  
               massPer[i] = massPerAndTotal[i];
            }
            
            //This is a string that says either "YES"
            //the molecule is a protein or "NO"
            //the molecule is not a protein
            //Factors for that determination 
            //are given in the method description
            protCheck = proteinChecker(massPer,codonCounter);
            


         }
         
         //This method prints the results to the 
         //given output file
         printer(dnaReader, console, line, nucCountReal, 
                 massPer, massPerAndTotal, protCheck, codonCounter,
                 outputFile, regionName);
      }
   
   }
   
   //This method takes in the file that
   //the program will read in and produce
   //output from
   //    [console] is a scanner that accepts user input
   //It returns a string representing the files name
   public static String inputTaker(Scanner console) throws FileNotFoundException {
   
      System.out.println("This program reports information about DNA");
      System.out.println("nucleotide sequences that may encode proteins.");
      System.out.print("Input file name? ");
      String inputFile = console.next();
      return(inputFile);
   }
   
   //This method will do some calculations on nucleotide
   //counts for a specific protein
   //    [line] is the line the reader is on
   //           it must contain the DNA for the protein
   
   public static int[] nucCounts(String line) {
   
      int[] nucCounter = new int[NUCLEOS + 1];
      
      //This goes over the protein's DNA
      //and counts nucleotides
      for (int i = 0; i < line.length(); i++) {
      
         char currentChar = line.charAt(i);
         
         if(currentChar == 'A' || currentChar == 'a') {
            nucCounter[0]++;
            
         } else if(currentChar == 'C' || currentChar == 'c') {
            nucCounter[1]++;
            
         } else if (currentChar == 'G' || currentChar == 'g') {
            nucCounter[2]++;
            
         } else if (currentChar == 'T' || currentChar == 't') {
            nucCounter[3]++;
            
         } else {
            nucCounter[4]++;
         }
      }
      return(nucCounter);
   }
   
   //This method calculates the various percent
   //of the mass each nucleotide occupies
   //as well as calculating the total mass of the protein
   //it returns it as a string because that's easiest to print
   //    [line] is the line the reader is on
   //           it must contain the DNA for the molecule we're on
   public static double[] massCalc(int[] everythingCounter) {
      
      double totalMass = 0;
      
      //This creates an array that is big enough to hold
      //all the mass percents of the various 
      //nucleotides and the final mass total
      //(not a percent)
      double massPercent[] = new double[NUCLEOS+1];
      
      //This tallies up total mass in the DNA
      for(int i = 0; i < NUCLEOS + 1; i++) {
            totalMass += everythingCounter[i] * MASSESNUCS[i];
      } 
      
      massPercent[4] = Math.round(totalMass*10.0)/10.0;
      //This fills the massPercent array
      //as well as rounding all the masses off to one decimale place
      for (int i = 0; i < NUCLEOS; i++) {
            massPercent[i] = Math.round((everythingCounter[i] * MASSESNUCS[i] /
                                         totalMass * 100)*10.0)/10.0;
      }
            
      return(massPercent);
   }
   
   //This method tallies the codons in the DNA string
   //    [line] is the specific line the reader is on
   //    [everythingCounter] is an array that contains
   //                        a count of all the codons in the current molecule
   public static String[] codonCounterXtreme(String line, int[] everythingCounter){
      
      int totalNucs = 0;
      
      //This tallies the number of nucleotides in DNA
      for(int i = 0; i < everythingCounter.length - 1; i++) {
      
         totalNucs += everythingCounter[i];
      
      }
      
      //This creates the array for codons in a molecule
      //by dividing the total number of nucleotides
      //by the number of nucleotides that should be
      //in a codon
      String codonBois[] = new String[totalNucs/NUCPERCOD];
      
      String codon = "";
      int codCounter = 0;
      
      //This counts through the molecule nucleotide
      //by nucleotide and if it isn't junk
      //then it adds it to the codon
      //it adds the codon to the array once
      //the nucPerCod is reached
      //as given by a fixed variable nucPerCod
      for(int i = 0; i < line.length(); i++) {
         String letter = line.substring(i,i + 1);
         
         if(letter.equalsIgnoreCase("A") || letter.equalsIgnoreCase("G") ||
            letter.equalsIgnoreCase("T") || letter.equalsIgnoreCase("C")) {
            
            codon += letter.toUpperCase();
            
            if(codon.length() == NUCPERCOD) {
            
               codonBois[codCounter] = codon;
               codon = "";
               codCounter++;
               
            }
         }
      }
      
      return(codonBois);  
   }
   
   //This method will determine whether the molecule
   //is a protein given by start codon, stop codon, 
   //containing at least 5 codons, and C + G 
   //is at least 30% of mass
   //    [massNucs] gives an array of the various percent masses each
   //               nucleotide takes up
   //    [codonCounter] is an array of strings of each codon in order
   //                   of appearance in the molecule
   
   public static String proteinChecker(double[] massPer, String[] codonCounter) {
   
      String check = "YES";
      
      //This is an array containing all valid end codons
      String[] validEndCodons = {"TAA","TAG","TGA"};
      
      //This is the ending codon of the molecule
      String endCodon = codonCounter[codonCounter.length - 1];
      
      //This is the start codon of the molecule
      String startCodon = codonCounter[0];
      
      //This is an array containing all the valid start codons
      String[] validStartCodon = {"ATG"};
      
      if(massPer[1] + massPer[2] < PERCENTMASS || codonCounter.length < CODONS ||
         Arrays.binarySearch(validStartCodon,startCodon) < 0 || Arrays.binarySearch(validEndCodons,endCodon) < 0){

         check = "NO";
      }

      return(check);
   }   
   
   //This method will print the information in
   //the file specified by the user
   //    [dnaReader] is a scanner that reads over the input file
   //    [console] is a scanner that allows user input
   //    [line] is the current line the input file scanner is on
   //    [nucCountReal] is an array that holds all the count of all
   //                   the nucleotides in the given molecule
   //    [massPer] is an array that holds the values 
   //              for the mass percentage of each nucleotide
   //              in the molecule
   //    [massPerAndTotal] is an array that holds the values 
   //                      for the mass percentage of each nucleotide
   //                      in the molecule plus the total mass of the molecule
   //    [protCheck] is a string that is either "YES" or "NO" for answering
   //                whether or not the molecule is a protein
   //    [codonCounter] is an array of strings that holds the 
   //                   values of all the codons in the molecule
   //                   in order, skipping junk portions
   //    [outputFile] is a Printstream to the output file the user specified
   //    [regionName] is a boolean variable assigned to whether or not the 
   //                 line is a nucleotide reading of the molecule or it's 
   //                 region name
   // whew
   public static void printer(Scanner dnaReader, Scanner console, String line, int[] nucCountReal,
                              double[] massPer, double[] massPerAndTotal, String protCheck,
                              String[] codonCounter, PrintStream outputFile, boolean regionName) throws FileNotFoundException {
         
         //This prints the line if it just 
         //contains the name of the protein,
         //i.e. region name
         if(regionName) {
         
            outputFile.println("Region Name: " + line);
            
         } else {
            outputFile.println("Nucleotides: " + line.toUpperCase());
            outputFile.println("Nuc. Counts: " + Arrays.toString(nucCountReal));
            outputFile.println("Total Mass%: " + Arrays.toString(massPer) + " of " + massPerAndTotal[4]);
            outputFile.println("Codons List: " + Arrays.toString(codonCounter));
            outputFile.println("Is Protein?: " + protCheck);
            outputFile.println();
         }                
   }
}