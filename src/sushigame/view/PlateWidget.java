package sushigame.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;

import comp401sushi.Plate;
import comp401sushi.Sashimi;
import sushigame.model.Belt;
import sushigame.model.BeltEvent;
import sushigame.model.BeltObserver;

public class PlateWidget extends JButton {
	
	private int age;
	private Plate plate;
	
	public PlateWidget(Plate p, int age) {
		if (p == null) {
			setText("");
			setBackground(Color.GRAY);
			this.age = age;
		} else {
		
		setText(p.getContents().getName());
		
		switch(p.getColor()) {
		case RED: 
			setBackground(Color.RED); break;
		case GREEN:
			setBackground(Color.GREEN); break;
		case BLUE: 
			setBackground(Color.BLUE); break;
		case GOLD:
			setBackground(Color.YELLOW); break;
		}
	}
	}
	
	public Plate getPlate() {
		return plate;
	}
	
	public int getAge() {
		return age;
	}
	
	public void setPlate(Plate p) {
		this.plate = p;
		setText(p.getContents().getName());
		
		switch(p.getColor()) {
		case RED: 
			setBackground(Color.RED); break;
		case GREEN:
			setBackground(Color.GREEN); break;
		case BLUE: 
			setBackground(Color.BLUE); break;
		case GOLD:
			setBackground(Color.YELLOW); break;
	}
	}
	
	public void setAge (int age) {
		this.age = age;
	}
	
}


	

