package com.ibm.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.remote.ErrorCodes;

import com.ibm.automation.ui.CustomException;

public class ReadExcelByMapping{
	
	public FileOutputStream fileout=null;
	public static String path;
	public static FileInputStream fis;
	public static XSSFWorkbook workbook;
	public static XSSFSheet sheet;
	public static XSSFRow columnheader;
	public static XSSFCell cell;
	
	public ReadExcelByMapping(){
	
	}

//	public static void writeExcel(int sceid,String clmName,String clmValue){
//		String path = System.getProperty("user.dir")+"\\src\\test\\resources\\Datasheet\\Datasheet.xlsm";
//		writeExcel(path,"",sceid,clmName,clmValue);
//		
//	}
//		
	
	
	
	public static void writeExcel(String path,String sheetName,int sceid,String clmName,String clmValue) throws Exception
	{
		
		System.out.println("ReadExcelByMapping.writeExcel():: "+sceid);
		
		workbook=new XSSFWorkbook(new FileInputStream(new File(path)));
		sheet=workbook.getSheet(sheetName);
 
		 int cloumnno=findColumnHeaderLocation(sheet,clmName);
		 
 		
		 Row rw=sheet.getRow(sceid);
		 
		 System.out.println("column number :"+cloumnno);
		 System.out.println("row number :"+rw);
		 
		 Cell cell=rw.createCell(cloumnno-1);
		 cell.setCellValue(clmValue);
		 try{
		 FileOutputStream fout=new FileOutputStream(path);
		 workbook.write(fout);
		 fout.close();
		 }catch(IOException e){
			 
			 e.printStackTrace();
		 }
		
	}
	
	public static void writeExcel1(String path, String sheetName, String sceid, String clmName, String clmValue) throws CustomException {

		// String path =
		// System.getProperty("user.dir")+"\\src\\test\\resources\\Datasheet\\Datasheet.xlsx";
		File f = new File(path);
		System.out.println(path);
		try {
			fis = new FileInputStream(f);
			workbook = new XSSFWorkbook(fis);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 
		System.out.println(clmValue);

		sheet = workbook.getSheet(sheetName);

		Row Rowheader = sheet.getRow(0);
		int TC_ID = 0;
		
		for(int i=0;i<sheet.getLastRowNum();i++){
			Cell descr_clm=sheet.getRow(i).getCell(0);
			String field_rowvalue=descr_clm.getStringCellValue();
			if(field_rowvalue.equalsIgnoreCase(sceid)){
			System.out.println("Row Noumber: "+i);
			 TC_ID=descr_clm.getRow().getRowNum();
			System.out.println(TC_ID);
			break;
			}
			
			}

		Iterator<Cell> cellitr = Rowheader.cellIterator();

		int cloumnno = 0;

		while (cellitr.hasNext()) {

			Cell cell = cellitr.next();
			++cloumnno;
		

			if (clmName.equalsIgnoreCase(cell.getStringCellValue())) {

				break;
			}
		}

		Row rw = sheet.getRow(TC_ID);
		 Cell cell=rw.createCell(cloumnno-1);
		//Cell cell = rw.getCell(cloumnno - 1);
		// cell.setCellValue("");
		cell.setCellValue(clmValue);
		try {
			FileOutputStream fout = new FileOutputStream(path);
			// System.out.println(fout.toString()+"test------------");
			workbook.write(fout);
			fout.close();
		} catch (IOException e) {

			e.printStackTrace();
			throw new CustomException("Unable to write"+clmName ,e,ErrorCodes.ELEMENT_NOT_VISIBLE);
		}

	}
	
	
	
	
	private static int findRowByValue(XSSFSheet sheet, String cellContent) 
	{
		
	    for (Row row : sheet) 
	    {
	        for (Cell cell : row) 
	        {
	        	//cell
                if (cell.getRichStringCellValue().getString().trim().equals(cellContent)) 
                {
	                    return row.getRowNum();  
                }
	        }
	    }               
	    return 0;
	}
	
	
	private static int findColumnHeaderLocation(XSSFSheet sheet, String colName) 
	{
		 
		 XSSFRow rowHeader=sheet.getRow(0);
		 
 		 for(Cell cell:rowHeader)	 
		 {
			 if(colName.equalsIgnoreCase(cell.getStringCellValue()))
			 {
				 return cell.getColumnIndex();
			 }
		 }
 		 
		 return 0;
	}	
	
	
	public static HashMap<String, String> getColumnDataIndex(String path, String sheetName, int colNum)
			throws FileNotFoundException, IOException {

		workbook = new XSSFWorkbook(new FileInputStream(new File(path)));
		sheet = workbook.getSheet(sheetName);
		HashMap<String, String> colIndexMap = new HashMap<>();
		for (Row row : sheet) {
			//System.out.println("colNum: " + colNum);
			Cell cell = row.getCell(colNum);
			if (null!=cell) {

				//System.out.println("cell: " + cell);
				String stringCellValue = (null == cell.getStringCellValue() ? "" : cell.getStringCellValue().trim());
				if (!stringCellValue.equalsIgnoreCase("")) {

					colIndexMap.put(cell.getStringCellValue(), String.valueOf(row.getRowNum()));
				}
			}

		}
		return colIndexMap;
	}
	
}
