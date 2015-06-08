
package pojo;

import java.util.List;

public class Subcategories{
	private String name;
   	private List<String> ingredients;

 	public List<String> getIngredients(){
		return this.ingredients;
	}
	public void setIngredients(List<String> ingredients){
		this.ingredients = ingredients;
	}
 	public String getName(){
		return this.name;
	}
	public void setName(String name){
		this.name = name;
	}
}
