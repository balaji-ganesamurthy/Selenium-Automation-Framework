package utils;

public class TestDataUtils {
	
	public static Object[][] getLoginData() {

	    int rowCount = ExcelUtils.getRowCount("LoginData");
	    int executableRows = 0;

	    for (int i = 1; i <= rowCount; i++) {

	        String runMode = ExcelUtils.getCellData("LoginData", i, 7);

	        if ("Y".equals(runMode)) {
	            executableRows++;
	        }
	    }

	    Object[][] data = new Object[executableRows][6];

	    int dataIndex = 0;

	    for (int i = 1; i <= rowCount; i++) {

	        String runMode = ExcelUtils.getCellData("LoginData", i, 7);

	        if ("Y".equals(runMode)) {

	            String username = ExcelUtils.getCellData("LoginData", i, 1);
	            String password = ExcelUtils.getCellData("LoginData", i, 2);
	            String productName = ExcelUtils.getCellData("LoginData", i, 3);
	            String firstName = ExcelUtils.getCellData("LoginData", i, 4);
	            String lastName = ExcelUtils.getCellData("LoginData", i, 5);
	            String zipCode = ExcelUtils.getCellData("LoginData", i, 6);

	            data[dataIndex][0] = username;
	            data[dataIndex][1] = password;
	            data[dataIndex][2] = productName;
	            data[dataIndex][3] = firstName;
	            data[dataIndex][4] = lastName;
	            data[dataIndex][5] = zipCode;

	            dataIndex++;
	        }
	    }

	    return data;
	}

}
