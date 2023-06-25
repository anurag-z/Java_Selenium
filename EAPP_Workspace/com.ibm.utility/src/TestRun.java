import java.util.List;

import com.ibm.automation.ui.AutomationConfig;
import com.ibm.automation.ui.DataSheetDto;
import com.ibm.utility.ConnectionManager;

public class TestRun {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		try
		{
			ConnectionManager conMgr=new ConnectionManager("jdbc:sqlserver://10.155.6.16:1433;databaseName=DART_PORTAL_DB","com.microsoft.sqlserver.jdbc.SQLServerDriver","ind10901","ind10901@16");
			AutomationConfig autoconfig=new AutomationConfig(conMgr.getConnection());
//			List<Object> list = (List<Object>)(List<?>) new ArrayList<LocatorDTO>();
//			List<LocatorDTO> list = new ArrayList<LocatorDTO>();
			List<DataSheetDto> list=autoconfig.GetTestCases("EAPP","MajorSame","APC");
//			autoconfig.GetTestCases(sql);
			System.out.println(list.size());
			
			
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

	}

}
