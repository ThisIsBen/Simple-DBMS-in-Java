import java.awt.List;
import java.util.ArrayList;

public class Node {

	private String tag;
	//private ArrayList<Integer> column = new ArrayList<Integer>();
	private int column;
	
	public void setTag(String tagName)
	{
		this.tag=tagName;
	}
	
	public void addColumn(int columnValue)
	{
		this.column=columnValue;
	}
	
	public String getTag()
	{
		return tag;
	}
	
	public int getColumn()
	{
		return column;
	}
	
}
