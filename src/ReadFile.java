import java.io.BufferedReader;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

import javax.swing.JOptionPane;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class ReadFile {
	
	
	public  String[][] userInput = new String[5][2];
	private int i;
	String csvFile="scores/highscores.csv";
	PrintWriter pw;
	 ReadFile() {
			String line = "";
		    String cvsSplitBy = ",";
		    i=0;
		    ClassLoader classLoader = getClass().getClassLoader();

			try (BufferedReader bh = new BufferedReader(new InputStreamReader(classLoader.getResourceAsStream(csvFile)))) {
				  while ((line = bh.readLine()) != null) {
					  userInput[i]= line.split(cvsSplitBy);
					  i+=1;					  
				  }
			  }catch (IOException f) {
		          f.printStackTrace();
			  }
			for(int i=0;i<=4;i++){
				System.out.println(userInput[i][0] +" "+userInput[i][1] );
			}
	   }

	  
	   
	   public String print(){
		   StringBuilder sb = new StringBuilder();
		   for(int j=0;j<=4;j++){
			  
		        sb.append(userInput[j][0]);
		        sb.append(": ");
		        sb.append(userInput[j][1]);
		        sb.append('\n');
		   }
		   return sb.toString();
		   
	   }
	   public void checkScore(int score){
		   ClassLoader classLoader = getClass().getClassLoader();
		   for(int i=0;i<=4;i++){
			   if(score>Integer.parseInt(userInput[i][1])){
				   String input = JOptionPane.showInputDialog("New Highscore! \nPlease input your name:");
				   if(input==null)break;
				   for(int j=4;j>=i+1;j--){	//olisthainoume ton pinaka
					   userInput[j][0]=userInput[j-1][0];
					   userInput[j][1]=userInput[j-1][1];
				   }
				   userInput[i][0]=input;
				   userInput[i][1]=Integer.toString(score);
				  
				   try{
					  pw = new PrintWriter(new File(classLoader.getResource(csvFile).getFile())) ;
				   }
				   catch (IOException e){
					   e.printStackTrace();
				   }
				   StringBuilder sb = new StringBuilder();
				   for(int j=0;j<=4;j++){
					  
				        sb.append(userInput[j][0]);
				        sb.append(',');
				        sb.append(userInput[j][1]);
				        sb.append(',');
				        sb.append('\n');
				   }
				   
			        pw.write(sb.toString());
			        pw.close();
			        System.out.println(sb);
			        System.out.println("done!");
			        break;
			   }
		   }
		   
	   }
	
		
}
