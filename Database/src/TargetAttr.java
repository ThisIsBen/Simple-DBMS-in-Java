
public class TargetAttr {

	private int attrIndex;
	private String belongTable;
	
	public void setAttrIndex(int indexInTable)
	{
		attrIndex=indexInTable;
	}
	
	public int getAttrIndex()
	{
		return attrIndex;
	}
	
	
	public void setTableName(String Table)
	{
		belongTable=Table;
	}
	
	public String getTableName()
	{
		return belongTable;
	}
	
}
