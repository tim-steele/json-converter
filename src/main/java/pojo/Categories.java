
package pojo;

import java.util.List;

public class Categories{
   	private String name;
   	private List<Subcategories> subcategories;

 	public String getName(){
		return this.name;
	}
	public void setName(String name){
		this.name = name;
	}
 	public List<Subcategories> getSubcategories(){
		return this.subcategories;
	}
	public void setSubcategories(List<Subcategories> subcategories){
		this.subcategories = subcategories;
	}
}
