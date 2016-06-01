import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ReadInData {

	
	private static int numOfLine=0;
	
	 public String preprocess(String content)
	 {
		 
		 content=content.substring(content.indexOf("*/")+2);
		 content=content.trim();
		 return content;
	 }
	
	  public void bulidTable(String[][]table,String fileContent)
	  {
		  String[] parts = fileContent.split("\n");
		  
		  
	
		  for(int k=0;k<parts.length;k++)
		  {
			  
				  //
			  //System.out.println( parts[k]);
				 parts[k]=parts[k].replace("|", ",");
				  table[k]=parts[k].split(",");
				  
			
		  }
		  
		  
			  
		  
		  
	  }
	
	
	
	//read file class
		 public static String readFile(String fileName) {
		        StringBuilder data = new StringBuilder();
		        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
		            while (reader.ready())
		                {
			            	data.append(reader.readLine()).append("\n");
			            	numOfLine++;
		                }
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
		        return data.toString();
		    }
	
	  public int getNumOfLine()
	  {
		  return numOfLine-1;
	  }
	  
	  public void resetNumOfLine()
	  {
		   numOfLine=0;
	  }
	  
	  public String[] getAttributeName(String FileContent)
	  {
		  int start=FileContent.indexOf("/*");
		  start+=2;
		  int end=FileContent.indexOf("*/");
		 
		  String subResult=FileContent.substring(start,end);
		  subResult=subResult.trim();
		  subResult=subResult.replace("|", ",");
		  String [] attribute=subResult.split(",");
		  //for(int i=0;i<attribute.length;i++)
		  //		System.out.println(attribute[i]);
		  return attribute;
		  
	  }
}
