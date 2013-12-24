//@author Mayank Mahajan, mm4399, 12.2.2013

import java.util.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SpamTester
{
   public static HashMap<String, Integer> ben = new HashMap<String, Integer>();
   public static HashMap<String, Integer> mal = new HashMap<String, Integer>();
   public static final int N = 4;
   //static Scanner keyboard;

   
   public static void main (String[] args) throws IOException, ClassNotFoundException
   {
      JFrame frame = new JFrame("Spam Filter");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      Container contentPane = frame.getContentPane();
      contentPane.setLayout(new FlowLayout());
      JPanel panel = new JPanel();
      frame.add(panel);
      
      //BUTTONS
   
      JButton info = new JButton("Information");
      info.addActionListener(
            new ActionListener(){
               public void actionPerformed (ActionEvent e)
               { displayInfo();
               
               }
            }
         );
      frame.add(info);
      
      JButton loadfile = new JButton("Load file to train the filter.");
      loadfile.addActionListener(
            new ActionListener(){
               public void actionPerformed (ActionEvent e)
               { 
                  try{loadSingle();}
                  catch(IOException k){}
               
                  System.out.println("Filter has been trained with the given file.");
               }
            }
         );
      frame.add(loadfile);
         
      JButton loaddir = new JButton("Load directory to train the filter.");
      loaddir.addActionListener(
            new ActionListener(){
               public void actionPerformed (ActionEvent e)
               { 
                  try{loadDirectory();}
                  catch(IOException k){}
               
                  System.out.println("Filter has been trained with the given directory.");
               }
            }
         );
      frame.add(loaddir);
         
      JButton checkfile = new JButton("Test file to determine the probability it is spam");
      checkfile.addActionListener(
            new ActionListener(){
               public void actionPerformed (ActionEvent e)
               { 
                  try{checkFile();}
                  catch(IOException k){}
               
               }
            }
         );
      frame.add(checkfile);
         
      JButton savedict = new JButton("Save dictionary.");
      savedict.addActionListener(
            new ActionListener(){
               public void actionPerformed (ActionEvent e) 
               { 
                  try{saveDictionary();}
                  catch(IOException k){}
                  System.out.println("Dictionary has been saved to file.");
               }
            }
         );
      frame.add(savedict);
         
      JButton loaddict = new JButton("Load dictionary.");
      loaddict.addActionListener(
            new ActionListener(){
               public void actionPerformed (ActionEvent e)
               { 
                  try{loadDictionary();}
                  catch(IOException | ClassNotFoundException k){}
                  System.out.println("Dictionary has been loaded.");
               }
            }
         );
      frame.add(loaddict);
         
      JButton clear = new JButton("Clear dictionary.");
      clear.addActionListener(
            new ActionListener(){
               public void actionPerformed (ActionEvent e)
               { clearDictionary();
               
                  System.out.println("Dictionary has been cleared.");
               }
            }
         );
      frame.add(clear);
         
      JButton exit = new JButton("Quit program.");
      exit.addActionListener(
            new ActionListener(){
               public void actionPerformed (ActionEvent e)
               { System.exit(0);
               }
            }
         );
      frame.add(exit);
      
      JButton display = new JButton("Display dictionary");
      display.addActionListener(
            new ActionListener(){
               public void actionPerformed (ActionEvent e)
               {
                  for (String k: ben.keySet())
                     System.out.println(k + " " + ben.get(k));
                 System.out.println("MAL");
                  for (String w: mal.keySet())
                     System.out.println(w + " " + mal.get(w));
               }
            }
         );
      frame.add(display);
        
         
      //Making the frame visible
      frame.pack();
      frame.setVisible(true);
   
   
   //don't feel like deleting this, just in case you want to switch back to a console-only user interface.
   
      //keyboard = new Scanner(System.in);
      //ask user for input
      // System.out.println("0: Information");
      // System.out.println("1: Load a file to train the filter.");
      // System.out.println("2: Load a directory of files to train the filter.");
      // System.out.println("3: Check a given file to determine the probability it is spam.");
      // System.out.println("4: Save the dictionary to file.");
      // System.out.println("5: Load the dictionary from file.");
      // System.out.println("6: Clear dictionary.");
      // System.out.println("-1: Quit the program.");
   
      // while(true)
      // {
      // 
         // System.out.println("Select an option.");
         // int input = Integer.parseInt(keyboard.next());
      // 
         // switch (input)
         // {
            // case 0: displayInfo();
               // break;
            // case 1: loadSingle();
               // break;
            // case 2: loadDirectory();
               // break;
            // case 3: checkFile();
               // break;
            // case 4: saveDictionary();
               // break;
            // case 5: loadDictionary();
               // break;
            // case 6:   
               // clearDictionary();
               // break;
            // case -1: System.exit(0);
               // break;
         // }
      // }
             
      
   }
   
   //displays basic info about me and the program
   public static void displayInfo()
   {
      System.out.println("Title: Spam Filter");
      System.out.println("Author: Mayank Mahajan");
      System.out.println("Version: 1.0");
      System.out.println("Date Created: 12/02/2013");
      System.out.println("Course: COMS W3137, Honors Data Structures and Algorithms");
   }
   
   //helper method for training the dictionary with a single file
   public static void loadSingle() throws IOException
   {
   //ask user for filename
      Scanner i = new Scanner(System.in);
      System.out.println("Enter v for virus or b for benign");
      String type = i.next();      
      System.out.println("Enter filename");
      String filename = i.next();
      loadFile(type, new File(filename));
   }
   
   //helper method for training the dictionary with an entire directory
   public static void loadDirectory() throws IOException
   {
      Scanner q = new Scanner(System.in);
      System.out.println("Enter v for virus directory or b for benign directory");
      String type = q.next();
      System.out.print("Enter the name of the file directory: ");
      File dir = new File(q.next());
      for(File file : dir.listFiles()){
         if(file.isFile()){
            loadFile(type, file);
         }
      } 
   
   }
   
   //the method that actually loads a file into the dictionary
   public static void loadFile(String type, File filename) throws IOException
   {
      String total = getFileString(filename);   
         
      if (type.compareTo("v")==0)
      {
         for (int k = 0; k < total.length()-2*N-1;k++)
         {
            String piece = total.substring(k,k+2*N);
            if (mal.containsKey(piece))
               mal.put(piece,mal.get(piece)+1);
            else mal.put(piece,1);
         }
      }
      else if (type.compareTo("b")==0)
      {
         for (int k = 0; k < total.length()-2*N-1;k++)
         {
            String piece = total.substring(k,k+2*N);
            if (ben.containsKey(piece))
               ben.put(piece,ben.get(piece)+1);
            else ben.put(piece,1);
         }
      }
      else System.out.println("Invalid file type");
   }
   
   //Given a file, reads it into a string and removes all spaces. This will be used to add it to the
   //dictionary or test it against the existing dictionary
   public static String getFileString(File filename) throws IOException
   {
      Scanner s = new Scanner(filename);
      
      StringBuffer total = new StringBuffer();
      while (s.hasNext())
      {
         String line = s.nextLine();
         line = line.replaceAll(" ","");
         total.append(line);
         //System.out.println(line);
      }
      return total.toString();
   }
  
  //saves the dictionary into two files, one for benign, and one for malicious
   public static void saveDictionary() throws IOException
   {
      Scanner s = new Scanner (System.in);
      System.out.println("Enter filename to save BENIGN hashtable to.");
      String benfilename = s.next();
      System.out.println("Enter filename to save MALICIOUS hashtable to.");
      String malfilename = s.next();
      saveBenDictionary(benfilename);
      saveMalDictionary(malfilename); 
   }
   
   //saves the benign dictionary
   public static void saveBenDictionary(String filename) throws IOException
   {
      File f = new File(filename);
      if (!f.exists())
      {
         f.createNewFile();
      }
      FileOutputStream outfile = new FileOutputStream(f);
      ObjectOutputStream out = new ObjectOutputStream(outfile);
      out.writeObject(ben);
      out.close();
      outfile.close();
   }
   
   //saves the malicious dictionary
   public static void saveMalDictionary(String filename) throws IOException
   {
      File f = new File(filename);
      if (!f.exists())
      {
         f.createNewFile();
      }
      FileOutputStream outfile = new FileOutputStream(f);
      ObjectOutputStream out = new ObjectOutputStream(outfile);
      out.writeObject(mal);
      out.close();
      outfile.close();
   }

   //loads the dictionaries from file
   public static void loadDictionary() throws IOException, ClassNotFoundException
   {
      Scanner n = new Scanner (System.in);
      System.out.println("Enter filename to load BENIGN hashtable from.");
      String benfilename = n.next();
      System.out.println("Enter filename to load MALICIOUS hashtable from.");
      String malfilename = n.next();
      loadBenDictionary(benfilename);
      loadMalDictionary(malfilename);
   }
   
   //loads the benign dictionary
   public static void loadBenDictionary(String filename) throws IOException, ClassNotFoundException
   {
      FileInputStream infile = new FileInputStream(filename);
      ObjectInputStream in = new ObjectInputStream(infile);
      ben = (HashMap<String, Integer>)in.readObject();
      in.close();
      infile.close();
   }
  
  //loads the malicious dictionary 
   public static void loadMalDictionary(String filename) throws IOException, ClassNotFoundException
   {
      FileInputStream infile = new FileInputStream(filename);
      ObjectInputStream in = new ObjectInputStream(infile);
      mal = (HashMap<String, Integer>)in.readObject();
      in.close();
      infile.close();
   }
   
   //clears both dictionaries
   public static void clearDictionary()
   {
      ben.clear();
      mal.clear();
   }
   
   //helper method for checking the probability that an unknown file is a virus
   public static void checkFile() throws IOException
   {
      Scanner a = new Scanner(System.in);
      System.out.println("Enter name of tester file.");
      String filename = a.next();
      getProb(filename);
   }
   
   //given a filename, points to the file and evaluates its sequences to calculate the probability it's
   //a virus
   public static void getProb(String filename) throws IOException
   {
      File f = new File(filename);
      String total = getFileString(f);
      final double DEFAULT_PROB = 0.01; //default
      double malProb = DEFAULT_PROB;
      double benProb = DEFAULT_PROB;
      //System.out.println("ben keys " + ben.keySet()); 
      double sum = 0;
      
      
      for (int k = 0; k < total.length() - 2*N - 1; k++)
      {
         String sequence = total.substring(k,k+2*N);
         System.out.println(sequence + " " + k + "mal: " + mal.get(sequence) + "ben: " + ben.get(sequence));  
         if(mal.containsKey(sequence)){
            double benFreq = (ben.containsKey(sequence))?ben.get(sequence):DEFAULT_PROB;
            malProb = mal.get(sequence)/(mal.get(sequence)+benFreq);
         }
         else
            malProb = DEFAULT_PROB;
      
         if(ben.containsKey(sequence)){
            double malFreq = (mal.containsKey(sequence))? mal.get(sequence): DEFAULT_PROB;
            benProb = ben.get(sequence) / (ben.get(sequence) + malFreq);
         }
         else
            benProb = DEFAULT_PROB;
      
         double genProb = malProb / (malProb + benProb);
         sum += Math.log(1 - genProb) - Math.log(genProb);
         
         
      } 
      
      double overallProb = 1 / (1 + Math.exp(sum));
           
      
      System.out.println("The probability that this file is a virus is: " + overallProb);
   }
}
