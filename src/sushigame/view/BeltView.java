package sushigame.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import comp401sushi.Plate;
import sushigame.controller.RollMakerChefController;
import sushigame.model.Belt;
import sushigame.model.BeltEvent;
import sushigame.model.BeltObserver;

public class BeltView extends JLabel implements BeltObserver, ActionListener {

	private Belt belt;
	private PlateWidget[] plates;

	


	public BeltView(Belt b) {
		this.belt = b;
		belt.registerBeltObserver(this);
		setLayout(new GridLayout(belt.getSize(), 1));
		this.plates = new PlateWidget[belt.getSize()];
		for (int i = 0; i < belt.getSize(); i++) {
			PlateWidget widget = new PlateWidget(null, 0);
			widget.setMinimumSize(new Dimension(100, 20));
			widget.setPreferredSize(new Dimension(100, 20));
			widget.setOpaque(true);
			widget.setBackground(Color.GRAY);
			add(widget);
			widget.setActionCommand(Integer.toString(i));
			plates[i] = widget;
		}
		refresh();
	}

	@Override
	public void handleBeltEvent(BeltEvent e) {	
		refresh();
	}

	
	// checks for any new plates
	private void refresh() {
		for (int i=0; i<belt.getSize(); i++) {
			Plate p = belt.getPlateAtPosition(i);
			if(p != null) {
			plates[i].setPlate(p);
			plates[i].setAge(belt.getAgeOfPlateAtPosition(i));
			plates[i].addActionListener(this);
			} else {
				plates[i].setBackground(Color.DARK_GRAY);
				plates[i].setText("");
			}
		}
	}
	


	
	
	@Override
	// retrieves action command -> parses to an int
	// collect information for plate
	// put plate info in a JOptionPane
	public void actionPerformed(ActionEvent e) {
		 
		// for loop for checking action command equal to plate
		JFrame plateFrame = new JFrame();
			JPanel platePanel = new JPanel();
			plateFrame.setContentPane(platePanel);
			platePanel.setLayout(new GridLayout(3,1));
			
			
			int index = Integer.parseInt(e.getActionCommand());
			Plate _plate = plates[index].getPlate();
			
			JLabel ingredients = new JLabel("This sushi item is not a roll. It is a " + _plate.getContents().getName());
			
			if (_plate.getContents().getName().contains("Roll")) {
			String rollIngredients = "The ingredients of " + _plate.getContents().getName() + " are: ";
			
			for(int j = 0; j < _plate.getContents().getIngredients().length; j++) { 
				if(!_plate.getChef().getName().equals("player")) {
					 rollIngredients +=  ((int) ((_plate.getContents().getIngredients()[j].getAmount() * 100.0) + 0.5)) / 100.0 + " ounces of " +
								_plate.getContents().getIngredients()[j].getName() + " " ;
				} else {
				if(_plate.getContents().getIngredients()[j].getAmount() != 0)  {
				 rollIngredients +=    _plate.getContents().getIngredients()[j].getAmount() + " ounces of " +
										_plate.getContents().getIngredients()[j].getName() + " ";
			}
			
			}
			ingredients.setText(rollIngredients);
		}
			}
			JLabel chef = new JLabel("The chef is: " + _plate.getChef().getName());
			JLabel age = new JLabel("The age of this plate is: " + belt.getAgeOfPlateAtPosition(index));
		
			platePanel.add(ingredients);
			plateFrame.add(chef);
			plateFrame.add(age);
			
			plateFrame.setSize(5000, 200);
			
			plateFrame.setVisible(true);
			plateFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
			
		
		
		}
	}