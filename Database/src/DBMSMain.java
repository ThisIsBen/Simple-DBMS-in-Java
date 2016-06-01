import java.awt.Checkbox;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.ObjectInputStream.GetField;
import java.security.KeyStore.Entry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Vector;



public class DBMSMain {
	
	//Modifiy the read in file path here 
	public static String returnFileLoc(String fileName)
	{
		
		//Modifiy the read in file path here 
		return "C:/Users/USER/Documents/ABC/"+fileName;
	}
	
	
	
	public static void outputToAFile(DuplicateMap<Long,Node>HT,String outputFileName) 
	{
		/*
		for (Map.Entry entry : HT.returnMap().entrySet()) 
		{
  		    System.out.println(entry.getKey() + ", " + entry.getValue());
  		    entry.getValue()
		
		
		
		}
		*/
		
		
		
		
		 try {
		     
			 PrintWriter   out = new PrintWriter  (returnFileLoc(outputFileName));

		      //
		    //iterating over keys only
			    int bucketNo=0;
				for (Long key : HT.returnMap().keySet()) {
					out.println("Hash function output key = " + key+" | stored in Bucket "+bucketNo);
					bucketNo++;
				}

				//iterating over values only
			    bucketNo=0;
				for (ArrayList<Node> value : HT.returnMap().values()) {
					out.print("Bucket " + bucketNo+"  "  );
					for (Node s : value) 
				    {
						
						  
						//attributeValue
						out.print("attributeValue:"+s.getTag()+" | ");
						//attributeposition
						out.print(" attributeRowPosition:"+s.getColumn());
				        out.print("  ,  ");
				    		
				    }
					out.println();
					bucketNo++;
				}
				//if(bucketNo<=9)
					for(int i=bucketNo;i<=9;i++)
					out.println("Bucket " + i +"  ");

				
		      //
		      out.close();

		    } catch (FileNotFoundException e) {
		      e.printStackTrace();
		    }
	}

	public static void initNode( String[][] Table,Map<String,Integer> ATH,Node[] node,String attribute)
	{
		for(int i = 0; i < Table.length; i++) 
  		{
			node[i] = new Node();
			node[i].setTag(Table[i][ATH.get(attribute)]);
			node[i].addColumn(i);	
  		}
		
	}
	
	public static void buildHT(String[][] Table,Map<String,Integer>  ATH,DuplicateMap<Long,Node>HT ,Node[] Node,String attribute)
	{
		for(int i = 0; i < Table.length; i++) 
  		{
  			HT.put(hash33(Table[i][ATH.get(attribute)]), Node[i]);
  		}
	}
	
	
	
	public static void main(String[] args) {
		
		//read in books file content
        ReadInData booksReader=new ReadInData();
		String booksFileContent = booksReader.readFile(returnFileLoc("books.txt"));
		//System.out.println(booksFileContent);
		String [] booksAttribute=booksReader.getAttributeName(booksFileContent);
		
	  	
        booksFileContent=booksReader.preprocess(booksFileContent);
		
	   
	    //get value separated by "|" from books
	    int numOfLine=booksReader.getNumOfLine();
	    
	    String[][] booksTable = new String[numOfLine][5];
	    booksReader.bulidTable(booksTable, booksFileContent);
	   /*
	    for(int i=0;i<numOfLine;i++)
	    {
	    	for(int j=0;j<5;j++)
	    		System.out.println(booksTable[i][j]);
	    	
	    }
	   */
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    //get value separated by "|" from sellRecord
	  //read in  sellRecord file content
	  	ReadInData sellRecordReader=new ReadInData();
	  	sellRecordReader.resetNumOfLine();
	    String sellRecordFileContent = sellRecordReader.readFile(returnFileLoc("sellRecord.txt"));
	    String [] sellRecordAttribute=sellRecordReader.getAttributeName(sellRecordFileContent);
		
	  
	  	
	  	sellRecordFileContent=sellRecordReader.preprocess(sellRecordFileContent);
		//System.out.println(sellRecordFileContent);	
	
	    numOfLine=sellRecordReader.getNumOfLine();
	    
	    String[][] sellRecordTable = new String[numOfLine][3];
	    sellRecordReader.bulidTable(sellRecordTable, sellRecordFileContent);
	    
	    /*
	    for(int i=0;i<numOfLine;i++)
	    {
	    	for(int j=0;j<3;j++)
	    		System.out.println(sellRecordTable[i][j]);
	    	
	    }
	    
	    */
	    
	    
	    
	    
	    //setup booksAttribute hash table
	    // Create a Empty HashMap 
        Map<String,Integer> booksATH= new HashMap<String,Integer>();
        for(int i=0;i<booksAttribute.length;i++)
        	booksATH.put(booksAttribute[i],i);
        
       
       
      //setup sellRecordAttribute hash table
	    // Create a Empty HashMap 
        Map<String,Integer> sellRecordATH= new HashMap<String,Integer>();
        for(int i=0;i<sellRecordAttribute.length;i++)
        	sellRecordATH.put(sellRecordAttribute[i],i);
        
        /*test sellRecordATH
        System.out.println("hihi"+sellRecordATH.get("uid"));
	    */
        
        
        
        
        
        
      //BUILD HASH TABLE FOR books table    
	 
        //build up all isbn node to record the position and name of title
  		Node[]isbnNode= new Node[booksTable.length];
  		initNode(booksTable,booksATH,isbnNode,"isbn");
  		DuplicateMap<Long,Node> isbnHT=new DuplicateMap<>();
  		buildHT(booksTable,booksATH,isbnHT,isbnNode,"isbn");
  		//output the hash table to a file
  		outputToAFile(isbnHT,"books_isbn.txt");
  		

  		/*///////////////////test to get the position index from isbnHT
  		ArrayList <Node> isbnList =isbnHT.get(hash33("375810609"));
	    System.out.println("isbnht");
	    for (Node s : isbnList) 
	    {
	    	if(s.getTag().equals("375810609"))
	    		System.out.println(s.getColumn());
	    }
        *////////////////////////////////////////
	    
	    
	    
	    
	    
	    
	    
	    
        //build up all author node to record the position and name of title
  		Node[]authorNode= new Node[booksTable.length];
  		initNode(booksTable,booksATH,authorNode,"author");
  		DuplicateMap<Long,Node> authorHT=new DuplicateMap<>();
  		buildHT(booksTable,booksATH,authorHT,authorNode,"author");
  	    //output the hash table to a file
  		outputToAFile(authorHT,"books_author.txt");
	    
      		//System.out.println("JK"+hash33("J. K. Rowling"));
      		//System.out.println("MZ"+hash33("Marion Zimmer Bradley"));
    
  		/*////test to get position index from authorHT
      		ArrayList <Node> authorList =authorHT.get(hash33("James Marshall"));
		    System.out.println("ht");
		    for (Node s : authorList) 
		    {
		    	if(s.getTag().equals("James Marshall"))
		    		System.out.println(s.getColumn());
		    }
        */////////////////////////////////////////////
        
		    
		    
		    
		    
		    
		    
		  //build up all title node to record the position and name of title
	  		Node[]titleNode= new Node[booksTable.length];
	  		initNode(booksTable,booksATH,titleNode,"title");
	  		DuplicateMap<Long,Node> titleHT=new DuplicateMap<>();
	  		buildHT(booksTable,booksATH,titleHT,titleNode,"title");
	  	    //output the hash table to a file
	  		outputToAFile(titleHT,"books_title.txt");
		    
		    
	     
	  		/*////test to get position index from titleHT
	      		ArrayList <Node> titleList =titleHT.get(hash33("Lasher"));
			    System.out.println("titleht");
			    for (Node s : titleList) 
			    {
			    	if(s.getTag().equals("Lasher"))
			    		System.out.println(s.getColumn());
			    }
	      *//////////////////////////////////////////
			    
			    
			    
			    
			    
			    
			    
			    
			    //build up all price node to record the position and name of title
		  		Node[]priceNode= new Node[booksTable.length];
		  		initNode(booksTable,booksATH,priceNode,"price");
		  		DuplicateMap<Long,Node>priceHT=new DuplicateMap<>();
		  		buildHT(booksTable,booksATH,priceHT,priceNode,"price");
		  		 //output the hash table to a file
		  		outputToAFile(priceHT,"books_price.txt");
			    
			    
		     
		  		/*////test to get position index from titleHT
		      		ArrayList <Node> priceList =priceHT.get(hash33("6.99"));
				    System.out.println("priceList");
				    for (Node s : priceList) 
				    {
				    	if(s.getTag().equals("6.99"))
				    		System.out.println(s.getColumn());
				    }
		      *//////////////////////////////////////////
			    
			    
				    
				    
				    
				    
				    //build up all subject node to record the position and name of title
			  		Node[]subjectNode= new Node[booksTable.length];
			  		initNode(booksTable,booksATH,subjectNode,"subject");
			  		DuplicateMap<Long,Node>subjectHT=new DuplicateMap<>();
			  		buildHT(booksTable,booksATH,subjectHT,subjectNode,"subject");
			  		 //output the hash table to a file
			  		outputToAFile(subjectHT,"books_subject.txt");
				    
				    
			     
			  		/*////test to get position index from titleHT
			      		ArrayList <Node> subjectList =subjectHT.get(hash33("FICTION"));
					    System.out.println("subjectList");
					    for (Node s : subjectList) 
					    {
					    	if(s.getTag().equals("FICTION"))
					    		System.out.println(s.getColumn());
					    }
			      *//////////////////////////////////////////	    
					   
					    
					    
					    
					    
					    
					    
					    
					    
					    
					//BUILD HASH TABLE FOR sellRecord table    
					    
					    
					    //build up all uid node to record the position and name of title
				  		Node[]uidNode= new Node[sellRecordTable.length];
				  		initNode(sellRecordTable,sellRecordATH,uidNode,"uid");
				  		DuplicateMap<Long,Node>uidHT=new DuplicateMap<>();
				  		buildHT(sellRecordTable,sellRecordATH,uidHT,uidNode,"uid");
				  		 //output the hash table to a file
				  		outputToAFile(uidHT,"sellRecord_uid.txt");
					    
					    
				     
				  		/*////test to get position index from titleHT
				      		ArrayList <Node> uidList =uidHT.get(hash33("15"));
						    System.out.println("uidList");
						    for (Node s : uidList) 
						    {
						    	if(s.getTag().equals("15"))
						    		System.out.println(s.getColumn());
						    }
				      *//////////////////////////////////////////	 					    
				
						    
						    
						    
						    
						    
						    
						    
						    //build up all no node to record the position and name of title
					  		Node[]noNode= new Node[sellRecordTable.length];
					  		initNode(sellRecordTable,sellRecordATH,noNode,"no");
					  		DuplicateMap<Long,Node>noHT=new DuplicateMap<>();
					  		buildHT(sellRecordTable,sellRecordATH,noHT,noNode,"no");
					  		 //output the hash table to a file
					  		outputToAFile(noHT,"sellRecord_no.txt");
						    
						    
					     
					  		/*////test to get position index from titleHT
					      		ArrayList <Node> noList =noHT.get(hash33("2"));
							    System.out.println("noList");
							    for (Node s : noList) 
							    {
							    	if(s.getTag().equals("2"))
							    		System.out.println(s.getColumn());
							    }
					      *//////////////////////////////////////////	 					    
					
	
							    
							    
							    
							    
							    
							    
							  //build up all isbn_no node to record the position and name of title
						  		Node[]isbn_noNode= new Node[sellRecordTable.length];
						  		initNode(sellRecordTable,sellRecordATH,isbn_noNode,"isbn_no");
						  		DuplicateMap<Long,Node>isbn_noHT=new DuplicateMap<>();
						  		buildHT(sellRecordTable,sellRecordATH,isbn_noHT,isbn_noNode,"isbn_no");
						  		 //output the hash table to a file
						  		outputToAFile(isbn_noHT,"sellRecord_isbn_no.txt");
							    
							    
						     
						  		/*////test to get position index from titleHT
						      		ArrayList <Node> isbn_noList =isbn_noHT.get(hash33("33"));
								    System.out.println("isbn_noList");
								    for (Node s : isbn_noList) 
								    {
								    	if(s.getTag().equals("33"))
								    		System.out.println(s.getColumn());
								    }
						      *//////////////////////////////////////////								    
					    System.out.println("Write your query:");
					    Scanner scanner=new Scanner(System.in);
					    String query=scanner.nextLine();
					    query=query.trim();
					    
					    //get rid of ; in the query
					    query=query.substring(0,query.indexOf(";"));
					    //query=query.toLowerCase();
					    
					    
					    
					    
					    if(query.contains("SELECT") && query.contains("FROM"))
					    {
					    	int selectIndex=query.indexOf("SELECT");
					    	int fromIndex=query.indexOf("FROM");
					    	String target=query.substring(selectIndex+6, fromIndex);
					    	
					    	
					    	//if query contains DISTICNT 
					    	if(target.contains("DISTINCT"))
					    		target=target.substring(target.indexOf("DISTINCT")+8);
					    	
					    	target=target.trim();
					    	
					    	//select all 
					    	//get the target
			    			String [] attributeTarget=target.split(",");
			    			TargetAttr [] targetAttr=new TargetAttr[attributeTarget.length];
			    			
			    			if(target.equals("*")==false)
					    	{
						    	
				    			
				    			//find which table and where in the table the attr exists.
				    			findAttrInTable(attributeTarget,targetAttr,booksATH,sellRecordATH);
					    	}
					    	
					    	
			    			
			    			
			    			
			    			
			    			
			    			
					    	//5/10 if it contains where in query
					    	if(query.contains("WHERE"))
					    	{
					    		
					    		
					    		
					    		
					    		
					    		
					    		//to store the requirment after "where"
					    	
					    	    
					    		String allRequirement=getRequirement(query);
					    		
					    		String[] requirementList=allRequirement.split("AND");
					    		 
					    		//to contain the joined result after taking all the requirement into account
					    		ArrayList<Integer>joinRowListInTable=new ArrayList<>();
					    		
					    		for(int i=0;i<requirementList.length;i++)
					    			checkRequireType( booksTable,sellRecordTable,requirementList[i],joinRowListInTable,booksATH,sellRecordATH,authorHT,priceHT,isbnHT,titleHT, subjectHT, uidHT, noHT, isbn_noHT);
					    		
					    		
					    		/*test joinlist
					    		System.out.println("test join list");
					    		
					    		for (int s : joinRowListInTable) 
					    		 {
					    			 System.out.println(s);
					    		 }
								*////	//
					    			
					    		boolean  hasDistinct=false;
					    		//if QUERY contains DISTINCT 
					    		if(query.contains("DISTINCT"))
					    			hasDistinct=true;
					    
					    		
					    		//print joined result from table
					    		printJoinedResultFromTable(hasDistinct,joinRowListInTable,targetAttr,booksTable,sellRecordTable);
					    		
					    							    		
					    		
					    		
					    		
					    		
					    		
					    		
					    	}
					    		
					    		
					    		
					    		
					    		
					    		
					    		
					    	else //no where in query
					    	{
					    		
					    		String targetTable=query.substring(fromIndex+4);
					    		targetTable=targetTable.trim();
					    		
					    		//select all 
					    			if(target.equals("*"))
							    	{
							    		printWholeTable(targetTable,booksTable,sellRecordTable);
							    	}
					    		
					    		
					    		
					    			
					    			
					    			
					    			
					    		//select attribute
					    	else{
						    			
						    	
						    			//取值:print the found data in its belonging table
						    			printAttrTarget(targetAttr,booksTable,sellRecordTable);
					    			}
					    		
					    		
					    		
					    		
					    		
					    		
							}

					    	
					    	
					    }
					    
			    
			    
			    
			    
        
        
        
        
        
        
        
	    
		
	}
		
//////////////////////////////////////////////////////////////
	public static void checkRequireType(String[][]booksTable,String[][]sellRecordTable,String requirementElement,ArrayList<Integer>joinRowListInTable,Map<String, Integer>booksATH,Map<String, Integer>sellRecordATH,DuplicateMap<Long,Node>authorHT,DuplicateMap<Long,Node>priceHT,DuplicateMap<Long,Node>isbnHT,DuplicateMap<Long,Node>titleHT,DuplicateMap<Long,Node>subjectHT,DuplicateMap<Long,Node>uidHT,DuplicateMap<Long,Node>noHT,DuplicateMap<Long,Node>isbn_noHT)
	{
		
		String [] elementInARequirement=requirementElement.split("=");
		for(int i=0;i<elementInARequirement.length;i++)
		{
			elementInARequirement[i]=elementInARequirement[i].trim();
			//System.out.println(elementInARequirement[i]);
		}
		
		//if the attribute exists in booksATH
		//if(booksATH.get(elementInARequirement[0])!=null)
		//{
			//if the lefthand side is a value rather than an attribute.
			//if(booksATH.get(elementInARequirement[1])==null  &&  sellRecordATH.get(elementInARequirement[1])==null)
			//{
				//trim the ' mark in the value string
				if(elementInARequirement[1].contains("'"))
					elementInARequirement[1]=trimRequirementValue(elementInARequirement[1]);
				
				///////if the value is either an attribute of table books or an attribute of table sellRecord.
				if(booksATH.get(elementInARequirement[1])!=null || sellRecordATH.get(elementInARequirement[1])!=null )
				{
					//if the value is an attribute of table books
					if(booksATH.get(elementInARequirement[1])!=null)
					{
						int columnOfLeftValue=booksATH.get(elementInARequirement[1]);
						//to store the acquired common Value Row List from  the table which has right hand side attribute as its content.
						//ArrayList <Integer> commonValueRowList=new ArrayList<>();
						
						checkLeftAttrForCommonValue( columnOfLeftValue,joinRowListInTable,booksTable, elementInARequirement,authorHT,priceHT,isbnHT,titleHT,subjectHT,uidHT,noHT,isbn_noHT);
						/*test joinlist
			    		System.out.println("test when left is books");
			    		
			    		for (int s : joinRowListInTable) 
			    		 {
			    			 System.out.println(s);
			    		 }
						*////	//
						
						
					
					
					}
					
					//if the value is an attribute of table sellRecord
					else
					{
						int columnOfLeftValue=sellRecordATH.get(elementInARequirement[1]);
						//to store the acquired common Value Row List from  the table which has right hand side attribute as its content.
						//ArrayList <Integer> commonValueRowList=new ArrayList<>();
						checkLeftAttrForCommonValue( columnOfLeftValue,joinRowListInTable,sellRecordTable, elementInARequirement,authorHT,priceHT,isbnHT,titleHT,subjectHT,uidHT,noHT,isbn_noHT);
						/*test joinlist
			    		System.out.println("test when left is sellRecord");
			    		
			    		for (int s : joinRowListInTable) 
			    		 {
			    			 System.out.println(s);
			    		 }
						*////	//
						
					}
				}
				
				
				///if the value is neither an attribute of table books nor an attribute of table sellRecord.
				if(booksATH.get(elementInARequirement[1])==null && sellRecordATH.get(elementInARequirement[1])==null )
				{	//get the joined row after taking an attribute requirement into account
					getJoinRow(elementInARequirement,joinRowListInTable,authorHT,priceHT,isbnHT,titleHT,subjectHT,uidHT,noHT,isbn_noHT);
				}
				
				
				
				
			//}
			
		//}
		
		
		
		/*if the attribute exists in sellRecordATH
		if(sellRecordATH.get(elementInARequirement[0])!=null)
		{
			//if the lefthand side is a value rather than an attribute.
			if(booksATH.get(elementInARequirement[1])==null  &&  sellRecordATH.get(elementInARequirement[1])==null)
			{
				//trim the ' mark in the value string
				if(elementInARequirement[1].contains("'"))
					elementInARequirement[1]=trimRequirementValue(elementInARequirement[1]);
				
				//////////////
				
				
				/////////////
				//get the joined row after taking an attribute requirement into account
				getJoinRow(elementInARequirement,joinRowListInTable,authorHT,priceHT,isbnHT,titleHT,subjectHT,uidHT,noHT,isbn_noHT);
				
				
				
				
				
			}
		}
		*/
			
	}
	
	//
	public static void checkLeftAttrForCommonValue(int columnOfLeftValue,ArrayList<Integer>joinRowListInTable,String[][]Table,String [] elementInARequirement,DuplicateMap<Long,Node>authorHT,DuplicateMap<Long,Node>priceHT,DuplicateMap<Long,Node>isbnHT,DuplicateMap<Long,Node>titleHT,DuplicateMap<Long,Node>subjectHT,DuplicateMap<Long,Node>uidHT,DuplicateMap<Long,Node>noHT,DuplicateMap<Long,Node>isbn_noHT)
	{
		
		//if the left attribute is in table sellRecord
		if(elementInARequirement[0].equals("isbn_no"))
		{
			//get the postiton that the same values are located at in the table ,which holds that value.
			getCommonValueRow(isbn_noHT,Table,columnOfLeftValue,joinRowListInTable);
			
		}
		
		
		
		if(elementInARequirement[0].equals("no"))
		{
			//get the postiton that the same values are located at in the table ,which holds that value.
			getCommonValueRow(noHT,Table,columnOfLeftValue,joinRowListInTable);
			
		}
		
		if(elementInARequirement[0].equals("uid"))
		{
			//get the postiton that the same values are located at in the table ,which holds that value.
			getCommonValueRow(uidHT,Table,columnOfLeftValue,joinRowListInTable);
			
		}
		
		
		
		/////////////////////
		//if the left attribute is in table books
		if(elementInARequirement[0].equals("isbn"))
		{
			//get the postiton that the same values are located at in the table ,which holds that value.
			getCommonValueRow(isbnHT,Table,columnOfLeftValue,joinRowListInTable);
			
		}
		
		if(elementInARequirement[0].equals("author"))
		{
			//get the postiton that the same values are located at in the table ,which holds that value.
			getCommonValueRow(authorHT,Table,columnOfLeftValue,joinRowListInTable);
			
		}
		
		if(elementInARequirement[0].equals("title"))
		{
			//get the postiton that the same values are located at in the table ,which holds that value.
			getCommonValueRow(titleHT,Table,columnOfLeftValue,joinRowListInTable);
			
		}
		
		if(elementInARequirement[0].equals("price"))
		{
			//get the postiton that the same values are located at in the table ,which holds that value.
			getCommonValueRow(priceHT,Table,columnOfLeftValue,joinRowListInTable);
			
		}
		
		if(elementInARequirement[0].equals("subject"))
		{
			//get the postiton that the same values are located at in the table ,which holds that value.
			getCommonValueRow(subjectHT,Table,columnOfLeftValue,joinRowListInTable);
			
		}
		
		
		
		
		
	}
	
	
	
	
	
	
	
	
	//get the row that contains common value
	public static void getCommonValueRow(DuplicateMap<Long, Node>HT,String[][]Table,int columnOfLeftValue,ArrayList<Integer>joinRowListInTable)
	{

		//to store the acquired arraylist from attribute hash table
		ArrayList <Node> acquiredList=new ArrayList<>();
		for(int k=0;k<Table.length;k++)
		{
			acquiredList=HT.get(hash33(Table[k][columnOfLeftValue]));
		
			//if the common value is found .
			if(acquiredList!=null)
			{
				//traverse throught the acquiredList to find the common value and reord its column position in the table.
				 for (Node acquiredRow : acquiredList) 
				    {
					 //find the common value in the acquiredList , and record its column position in the table in an arraylist commonValueRowList.
				    	if(acquiredRow.getTag().equals(Table[k][columnOfLeftValue]))
				    		joinRowListInTable.add(acquiredRow.getColumn());
				    }
			}
		
		
		
		
		}
	}
	
	
	
	
	
	
	//print joined result from table
	public static void printJoinedResultFromTable(Boolean hasDistinct,ArrayList<Integer>joinRowListInTable,TargetAttr[] targetAttr,String [][]booksTable,String [][]sellRecordTable)
	{
		
		//to store the result that has showed up earlier.
		ArrayList <String> alreadyShownList=new ArrayList<>();
		
		
		
		for(int i=0;i<targetAttr.length;i++)
		{
			//if the attr belongs to table books
			if(targetAttr[i].getTableName().equals("books"))
			{
				 for (int joinedRow : joinRowListInTable) 
				{
					 //if the query contains DISTINCT
				   if(hasDistinct)
				   {
						if(!alreadyShownList.contains(booksTable[joinedRow][targetAttr[i].getAttrIndex()]))
						{
							alreadyShownList.add(booksTable[joinedRow][targetAttr[i].getAttrIndex()]);
							 System.out.println(booksTable[joinedRow][targetAttr[i].getAttrIndex()]);
						}
				    }
				   //if the query doesn't contain DISTINCT
				    else
				    {
				    	System.out.println(booksTable[joinedRow][targetAttr[i].getAttrIndex()]);
				    }
				}
			}
			
			//if the attr belongs to table books
			else if (targetAttr[i].getTableName().equals("sellRecord"))
			{
				 for (int joinedRow : joinRowListInTable) 
					{
					 //if the query contains DISTINCT
					    if(hasDistinct)
					    {
						 if(!alreadyShownList.contains(sellRecordTable[joinedRow][targetAttr[i].getAttrIndex()]))
							{
							     alreadyShownList.add(sellRecordTable[joinedRow][targetAttr[i].getAttrIndex()]);
								 System.out.println(sellRecordTable[joinedRow][targetAttr[i].getAttrIndex()]);
							}
					    }
					    //if the query doesn't contain DISTINCT
					    else
					    {
					    	System.out.println(sellRecordTable[joinedRow][targetAttr[i].getAttrIndex()]);
					    }
					}
			}
				
		}
	}
	
	//get the joined row after taking an attribute requirement into account
	public static void getJoinRow(String [] elementInARequirement,ArrayList<Integer>joinRowListInTable,DuplicateMap<Long,Node>authorHT,DuplicateMap<Long,Node>priceHT,DuplicateMap<Long,Node>isbnHT,DuplicateMap<Long,Node>titleHT,DuplicateMap<Long,Node>subjectHT,DuplicateMap<Long,Node>uidHT,DuplicateMap<Long,Node>noHT,DuplicateMap<Long,Node>isbn_noHT)
	{
		////////
		
		/////////
		
		
		
		//to store the acquired arraylist from attribute hash table
		ArrayList <Node> acquiredList=new ArrayList<>();
		
		//if the requirement is related to "isbn"
		if(elementInARequirement[0].equals("isbn"))
		{
		     //////////////to get the position index from isbnHT
		    acquiredList =isbnHT.get(hash33(elementInARequirement[1]));
				
		}
		//if the requirement is related to "title"
		if(elementInARequirement[0].equals("title"))
		{
				     //////////////to get the position index from titleHT
				 acquiredList =titleHT.get(hash33(elementInARequirement[1]));
						
		}
		
		//if the requirement is related to "author"
		if(elementInARequirement[0].equals("author"))
		{
        //////////////to get the position index from priceHT
			 acquiredList =authorHT.get(hash33(elementInARequirement[1]));
		}
		//if the requirement is related to "price"
		if(elementInARequirement[0].equals("price"))
		{
        //////////////to get the position index from priceHT
			acquiredList =priceHT.get(hash33(elementInARequirement[1]));
		
		}
		//if the requirement is related to "subject"
		if(elementInARequirement[0].equals("subject"))
		{
		        //////////////to get the position index from subjectHT
			acquiredList =subjectHT.get(hash33(elementInARequirement[1]));
				
		}		
		//if the requirement is related to "uid"
		if(elementInARequirement[0].equals("uid"))
		{
				        //////////////to get the position index from uidHT
			acquiredList =uidHT.get(hash33(elementInARequirement[1]));
						
		}		
		//if the requirement is related to "no"
     	if(elementInARequirement[0].equals("no"))
		{
						        //////////////to get the position index from uidHT
			acquiredList =noHT.get(hash33(elementInARequirement[1]));
								
		}			
     	//if the requirement is related to "isbn_no"
     	if(elementInARequirement[0].equals("isbn_no"))
		{
						        //////////////to get the position index from uidHT
			acquiredList =isbn_noHT.get(hash33(elementInARequirement[1]));
								
		}		
		    Boolean removeFromJoinList=true;
		  //if the join list is empty,put all the acquired rows into it.
    		 if( joinRowListInTable.isEmpty() )
    		 {
    			 for (Node s : acquiredList) 
				    {
				    	if(s.getTag().equals(elementInARequirement[1]))
				    	{
				    		//System.out.println(s.getColumn());
				    		joinRowListInTable.add( s.getColumn() );
				    			 

				    			 
				    	}
				    }
    			 
    		 }
    		 
    		//if the join list is not empty,put the row ,which also already exists in the join list, into the join list.
    		 else
    		 {
    			 Iterator<Integer> rowInList = joinRowListInTable.iterator();

    			 while (rowInList.hasNext()) 
	    		 {
    				    removeFromJoinList=true;
    				    int CurrentRow=rowInList.next();
    				 
    				    for (Node s : acquiredList) 
					    {
					    	if(s.getTag().equals(elementInARequirement[1]))
					    	{
					    		//System.out.println(s.getColumn());
					    		if(CurrentRow==s.getColumn())
				    				 removeFromJoinList=false;
					    			 

					    			 
					    	}
					    	
					    }
    				    //remove the row from join list ,if it doesn't even show up in the acquired row list from other requirement result.  
    				    if(removeFromJoinList==true)
    				    {
    				    	rowInList.remove();
    				    	
    				    }
    				    	
    				    	
    				    
    				   
	    		 }
    			 
    		 }

		
		 
		
	}
	
	
	
	
	
	//trim the ' mark of the value
	public static String trimRequirementValue(String value)
	{
		value=value.substring(value.indexOf("'")+1,value.lastIndexOf("'"));
		value=value.trim();
		return value;
	}
	
	//get requirement after where
	public static String  getRequirement(String query)
	{
		int start=query.indexOf("WHERE");
		String allRequirement=query.substring(start+5);
	    return allRequirement;
		
	  
		
	}
	
	
	
	
	
	
	
	
	
	
	//find which table and where in the table the attr exists.
	public static void findAttrInTable(String[] attributeTarget,TargetAttr[] targetAttr, Map<String,Integer>booksATH, Map<String,Integer>sellRecordATH)
	{
		for(int i=0;i<attributeTarget.length;i++)
		{
			attributeTarget[i]=attributeTarget[i].trim();
			
			targetAttr[i] = new TargetAttr();
			
			
			//seatch this attribute in books table
			if(booksATH.get(attributeTarget[i])!=null)
				{
					targetAttr[i].setAttrIndex(booksATH.get(attributeTarget[i]));
					targetAttr[i].setTableName("books");
				}
		
			//seatch this attribute in sellReord table
			else if(sellRecordATH.get(attributeTarget[i])!=null)
				{
					targetAttr[i].setAttrIndex(sellRecordATH.get(attributeTarget[i]));
					targetAttr[i].setTableName("sellRecord");
				}
			
				
		}
	}
	
	
	
	
	
	//取值:print the found data in its belonging table
	public static void printAttrTarget(TargetAttr[] targetAttr,String [][]booksTable,String [][]sellRecordTable)
	{
		for(int i=0;i<targetAttr.length;i++)
		{
			//if the attr belongs to table books
			if(targetAttr[i].getTableName().equals("books"))
			{
				for(int k=0;k<booksTable.length;k++)
				{
					System.out.println(booksTable[k][targetAttr[i].getAttrIndex()]);
				}
			}
			
			//if the attr belongs to table books
			else if (targetAttr[i].getTableName().equals("sellRecord"))
			{
				for(int k=0;k<sellRecordTable.length;k++)
				{
					System.out.println(sellRecordTable[k][targetAttr[i].getAttrIndex()]);
				}
			}
				
		}
	}
	

	//hash function
	public static long hash33(String key){
		int i, k;
		long hv = 0;
		k=key.length();
		for(i=0;i<k;i++){
			hv=(hv<<5)+hv+key.charAt(i);
			
		}
		hv=hv%10;
		return hv;
	} 
	
	//print out the whole table
	public static void printWholeTable(String targetTable,String [][]booksTable,String [][]sellRecordTable)
	{
		if(targetTable.equals("books"))
			printStr2DArray(booksTable);
		if(targetTable.equals("sellRecord"))
			printStr2DArray(sellRecordTable);
		if( targetTable.contains("books") &&  targetTable.contains("sellRecord") )
			{
			   printStr2DArray(booksTable);
			   printStr2DArray(sellRecordTable);
			}
		
			
	}
 
	//printing tool for String 2D array
	public static void printStr2DArray(String [][]Str2DArray)
	{
		for(int i=0;i<Str2DArray.length;i++)
		{
			for(int j = 0; j < Str2DArray[i].length; j++)
		    {
				System.out.print(Str2DArray[i][j]+" ");

		    }
			System.out.println();
		}
			
	}
	
}
