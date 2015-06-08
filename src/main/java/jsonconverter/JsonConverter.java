package jsonconverter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import pojo.Categories;
import pojo.IngredientsList;
import pojo.Subcategories;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonConverter {
	private FileInputStream excelFile;
	private XSSFWorkbook workbook;
	private Gson gson;
	private Boolean successful;
	
	public JsonConverter(){
		gson =  new GsonBuilder().create();
		successful = false;
	}
	
	public String generateJson(File file){
		IngredientsList list = new IngredientsList();
		
		try {
			excelFile = new FileInputStream(file);
			workbook = new XSSFWorkbook (excelFile);
			
			list.setCategories(getCategories(workbook));
			successful = true;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return gson.toJson(list);
	}
	
	public Boolean wasSuccessful(){
		return successful;
	}
	
	private List<Categories> getCategories(XSSFWorkbook workbook){
		List<Categories> catList = new ArrayList<Categories>();
		
		for(XSSFSheet sheet : workbook){
			Categories cat = new Categories();
			cat.setName(sheet.getSheetName());
			
			cat.setSubcategories(getSubCategories(sheet));
			catList.add(cat);
		}
		
		return catList;
		
	}
	
	private List<Subcategories> getSubCategories(XSSFSheet sheet){

		List<Subcategories> subCatList = new ArrayList<Subcategories>();
		Iterator<Row> rowIterator = sheet.iterator();
		
		while(rowIterator.hasNext()){
			Row row = rowIterator.next();
			Iterator<Cell> cellIterator = row.cellIterator();

			while(cellIterator.hasNext()){
				if(row.getRowNum() == 0){
					Subcategories subCat = new Subcategories();
					Cell cell = cellIterator.next();
					subCat.setName(cell.getStringCellValue().trim());
					subCat.setIngredients(new ArrayList<String>());
					subCatList.add(subCat);
				}
				else{
					Cell cell  = cellIterator.next();
					Subcategories temp = subCatList.get(cell.getColumnIndex());
					temp.getIngredients().add(cell.getStringCellValue().trim());
				}
			}
		}
		
		return subCatList;
	}
	
}
