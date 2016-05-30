package de.clausthal.tu.ielf.resus.views;

public class CheckListItem {
	   private String  label;
	   private boolean isSelected = false;

	   public CheckListItem(String label){
	      this.label = label;
	   }
	   public boolean isSelected(){
	      return isSelected;
	   }
	   public void setSelected(boolean isSelected){
	      this.isSelected = isSelected;
	   }
	   public String toString(){
	      return label;
	   }
	}
