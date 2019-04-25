package sushigame.view;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.ScrollPaneLayout;
import javax.swing.SwingConstants;

import comp401sushi.AvocadoPortion;
import comp401sushi.CrabPortion;
import comp401sushi.EelPortion;
import comp401sushi.IngredientImpl;
import comp401sushi.IngredientPortion;
import comp401sushi.IngredientPortionImpl;
import comp401sushi.Nigiri;
import comp401sushi.Plate;
import comp401sushi.Plate.Color;
import comp401sushi.PlateImpl;
import comp401sushi.RedPlate;
import comp401sushi.Roll;
import comp401sushi.Sashimi;
import comp401sushi.SeaweedPortion;
import comp401sushi.ShrimpPortion;
import comp401sushi.Sushi;
import comp401sushi.TunaPortion;
import comp401sushi.YellowtailPortion;
import comp401sushi.Nigiri.NigiriType;

public class PlayerChefView extends JPanel implements ActionListener {

	private List<ChefViewListener> listeners;
	private int belt_size;
	private JSlider sushiSlider;
	private JSlider plateSlider;
	private JSlider nonRollSlider;
	private IngredientAmountSliderImpl[] ingAmount;
	private JTextField rollName;
	private JSlider goldPrice;
	
	public PlayerChefView(int belt_size) {
		this.belt_size = belt_size;
		listeners = new ArrayList<ChefViewListener>();
		
		setLayout(new GridLayout(0,2));
		 
		// slider for picking type of sushi
		sushiSlider = new JSlider(SwingConstants.HORIZONTAL, 0, 2, 0);
		sushiSlider.setPaintTicks(true);
		sushiSlider.setPaintLabels(true);
		sushiSlider.setSnapToTicks(true);
		
		Hashtable<Integer, JLabel> sushiLabel = new Hashtable<Integer, JLabel>();
		sushiLabel.put(0, new JLabel("Nigiri"));
		sushiLabel.put(1, new JLabel("Sashimi"));
		sushiLabel.put(2, new JLabel("Roll"));
		sushiSlider.setLabelTable(sushiLabel);
		
		sushiSlider.setSize(10, 1);
		add(new JLabel("Pick type of Sushi"));
		add(sushiSlider);
		
		// slider for picking plate color
		plateSlider = new JSlider(SwingConstants.HORIZONTAL, 0, 3, 0);
		plateSlider.setPaintTicks(true);
		plateSlider.setPaintLabels(true);
		plateSlider.setSnapToTicks(true);
		plateSlider.setMajorTickSpacing(1);
		
		Hashtable<Integer, JLabel> plateMap = new Hashtable<Integer, JLabel>();
		plateMap.put(0, new JLabel("Red"));
		plateMap.put(1, new JLabel("Blue"));
		plateMap.put(2, new JLabel("Green"));
		plateMap.put(3, new JLabel("Gold"));
		plateSlider.setLabelTable(plateMap);
		add(new JLabel("Choose Plate Color"));
		add(plateSlider);
		add(new JLabel("If you choose a gold plate. Input a price between $5-$10"));
		
		goldPrice = new JSlider(SwingConstants.HORIZONTAL, 0, 5, 0);
		goldPrice.setPaintTicks(true);
		goldPrice.setSnapToTicks(true);
		goldPrice.setPaintLabels(true);
		goldPrice.setMajorTickSpacing(1);
		
		Hashtable<Integer, JLabel> priceLabel = new Hashtable<Integer, JLabel>();
		priceLabel.put(0, new JLabel("$5"));
		priceLabel.put(1, new JLabel("$6"));
		priceLabel.put(2, new JLabel("$7"));
		priceLabel.put(3, new JLabel("$8"));
		priceLabel.put(4, new JLabel("$9"));
		priceLabel.put(5, new JLabel("$10"));
		goldPrice.setLabelTable(priceLabel);
		
		add(goldPrice);
		
		// slider for picking ingredients
		nonRollSlider = new JSlider(SwingConstants.HORIZONTAL, 0, 4, 0);
		nonRollSlider.setPaintTicks(true);
		nonRollSlider.setPaintLabels(true);
		nonRollSlider.setSnapToTicks(true);
		nonRollSlider.setMajorTickSpacing(1);
		
		Hashtable<Integer, JLabel> ingredientNames = new Hashtable<Integer, JLabel>(); 
		ingredientNames.put(0,new JLabel("Tuna"));
		ingredientNames.put(1, new JLabel("YellowTail"));
		ingredientNames.put(2, new JLabel("Eel"));
		ingredientNames.put(3, new JLabel("Crab"));
		ingredientNames.put(4, new JLabel("Shrimp"));
		nonRollSlider.setLabelTable(ingredientNames);
		
		add(new JLabel("Pick type of Nigiri/Sashimi: "));
		add(nonRollSlider);
		
		add(new JLabel("Name your roll!"));
		rollName = new JTextField(10);
		add(rollName);
		add(new JLabel("Choose ingredients for your roll: "));
		add(new JLabel(""));
		
		// sliders to pick roll amount
		ingAmount = new IngredientAmountSliderImpl[] {
		 new IngredientAmountSliderImpl(),
		 new IngredientAmountSliderImpl(),
		 new IngredientAmountSliderImpl(),
		 new IngredientAmountSliderImpl(),
		 new IngredientAmountSliderImpl(), 
		 new IngredientAmountSliderImpl(),
		 new IngredientAmountSliderImpl(),
		 new IngredientAmountSliderImpl()
		 };
		
		add(new JLabel("Choose Tuna amount for roll"));
		add(ingAmount[0]);
		add(new JLabel("Choose Yellowtail amount for roll"));
		add(ingAmount[1]);
		add(new JLabel("Choose Eel amount for roll"));
		add(ingAmount[2]);
		add(new JLabel("Choose Crab amount for roll"));
		add(ingAmount[3]);
		add(new JLabel("Choose Shrimp amount for roll"));
		add(ingAmount[4]);
		add(new JLabel("Chosse Tuna amount for roll"));
		add(ingAmount[5]);
		add(new JLabel("Choose Rice amount for roll"));
		add(ingAmount[6]);
		add(new JLabel("Choose Seaweed amount for roll"));
		add(ingAmount[7]);
		
		 	JButton createPlate = new JButton("Create Plate");
			createPlate.addActionListener(this);
			add(createPlate);
	}

	public void registerChefListener(ChefViewListener cl) {
		listeners.add(cl);
	}

	private void makeRedPlateRequest(Sushi plate_sushi, int plate_position) {
		for (ChefViewListener l : listeners) {
			l.handleRedPlateRequest(plate_sushi, plate_position);
		}
	}

	private void makeGreenPlateRequest(Sushi plate_sushi, int plate_position) {
		for (ChefViewListener l : listeners) {
			l.handleGreenPlateRequest(plate_sushi, plate_position);
		}
	}

	private void makeBluePlateRequest(Sushi plate_sushi, int plate_position) {
		for (ChefViewListener l : listeners) {
			l.handleBluePlateRequest(plate_sushi, plate_position);
		}
	}
	
	private void makeGoldPlateRequest(Sushi plate_sushi, int plate_position, double price) {
		for (ChefViewListener l : listeners) {
			l.handleGoldPlateRequest(plate_sushi, plate_position, price);
		}
	}

	@Override
	// rollIngredients index line up with checkBox array
	public void actionPerformed(ActionEvent e) {
	
		double goldPlatePrice = 0.00;
		Color color = comp401sushi.Plate.Color.RED;
			switch(plateSlider.getValue()) {
			case 0: break;
			case 1: color = comp401sushi.Plate.Color.BLUE; break;
			case 2: color = comp401sushi.Plate.Color.GREEN; break;
			case 3: color = comp401sushi.Plate.Color.GOLD; 
				switch(goldPrice.getValue()) {
				case 0: 
					goldPlatePrice = 5.00; break;
				case 1:
					goldPlatePrice = 6.00; break;
				case 2: 
					goldPlatePrice = 7.00; break;
				case 3: 
					goldPlatePrice = 8.00; break;
				case 4:
					goldPlatePrice = 9.00; break;
				case 5: 
					goldPlatePrice = 10.00; break;
				}
				
					break;
			}
		String sushiType = "";
		String ingredient = "";
		String _rollName = "";
		
		_rollName = rollName.getText();
		if (!_rollName.contains("Roll")) {
		_rollName += " Roll";
		}
		
		ArrayList<IngredientPortionImpl> _rollIngredients = new ArrayList<IngredientPortionImpl>();
		double[] amount = new double[8];
		
		// gets type of sushi
		if(sushiSlider.getValue() == 0) {
			sushiType = "Nigiri";
		} else if (sushiSlider.getValue() == 1) {
			sushiType = "Sashimi";
		} else {
			sushiType = "Roll";
		}
		// gets ingredient for nigiri or sashimi
		if (sushiType.equals("Nigiri") || sushiType.contentEquals("Sashimi")) {
			switch (nonRollSlider.getValue()) {
			case 0: 
				ingredient = "Tuna"; break;
			case 1: 
				ingredient = "YellowTail"; break;
			case 2: 
				ingredient = "Eel"; break;
			case 3:
				ingredient = "Crab"; break;
			case 4:
				ingredient = "Shrimp"; break;
			}
		} else {
			// checks if box is checked
			// adds name of ingredient to a string array
			// amount is added to a corresponding double array
			for(int i = 0; i < ingAmount.length; i++) {
				if(ingAmount[i].getValue() != 0) {
					switch(ingAmount[i].getValue()) {
					case 0: 
						amount[i] = 0.00; break;
					case 25: 
						amount[i] = 0.25; break;
					case 50: 
						amount[i] = 0.50; break;
					case 75: 
						amount[i] = .75; break;
					case 100: 
						amount[i] = 1.00; break;
					case 125: 
						amount[i] = 1.25; break;
					case 150:
						amount[i] = 1.50; break;
					}
				
				switch(i) {
				case 1:
					if(amount[i] > 0) {
					_rollIngredients.add(new TunaPortion(amount[i]));
					}
					break;
				case 2:
					if(amount[i] > 0) {
					_rollIngredients.add(new YellowtailPortion(amount[i]));
					}
					break;
				case 3:
					if(amount[i] > 0) {
					_rollIngredients.add(new EelPortion(amount[i]));
					}
					break;
				case 4:
					if (amount[i] > 0) {
					_rollIngredients.add(new CrabPortion(amount[i]));
					}
					break;
				case 5:
					if(amount[i] > 0)
					_rollIngredients.add(new ShrimpPortion(amount[i]));
					break;
				}
			}
			}
		}
			
	
		switch(color) {
		case RED: 
			
			if (sushiType.equals("Nigiri")) {
				if(ingredient.equals("Tuna")) {
					makeRedPlateRequest(new Nigiri(Nigiri.NigiriType.TUNA), 0);
				} else if (ingredient.equals("YellowTail")) {
					makeRedPlateRequest(new Nigiri(Nigiri.NigiriType.YELLOWTAIL), 0);
				} else if (ingredient.equals("Eel")) {
					makeRedPlateRequest(new Nigiri(Nigiri.NigiriType.EEL), 0);
				} else if (ingredient.equals("Crab")) {
					makeRedPlateRequest(new Nigiri(Nigiri.NigiriType.CRAB), 0);
				} else {
					makeRedPlateRequest(new Nigiri(Nigiri.NigiriType.SHRIMP), 0);
				}
			} else if (sushiType.contentEquals("Sashimi")) {
				if(ingredient.equals("Tuna")) {
					makeRedPlateRequest(new Sashimi(Sashimi.SashimiType.TUNA), 0);
				} else if (ingredient.equals("YellowTail")) {
					makeRedPlateRequest(new Sashimi(Sashimi.SashimiType.YELLOWTAIL), 0);
				} else if (ingredient.equals("Eel")) {
					makeRedPlateRequest(new Sashimi(Sashimi.SashimiType.EEL), 0);
				} else if (ingredient.equals("Crab")) {
					makeRedPlateRequest(new Sashimi(Sashimi.SashimiType.CRAB), 0);
				} else {
					makeRedPlateRequest(new Sashimi(Sashimi.SashimiType.SHRIMP), 0);
				}
			} else {
				IngredientPortionImpl[] redRoll = new IngredientPortionImpl[_rollIngredients.size()] ;
				redRoll = _rollIngredients.toArray(redRoll);
				makeRedPlateRequest(new Roll(_rollName, redRoll), 0);
			}
			break;
			
		case BLUE :
			if (sushiType.equals("Nigiri")) {
				if(ingredient.equals("Tuna")) {
					makeBluePlateRequest(new Nigiri(Nigiri.NigiriType.TUNA), 0);
				} else if (ingredient.equals("YellowTail")) {
					makeBluePlateRequest(new Nigiri(Nigiri.NigiriType.YELLOWTAIL), 0);
				} else if (ingredient.equals("Eel")) {
					makeBluePlateRequest(new Nigiri(Nigiri.NigiriType.EEL), 0);
				} else if (ingredient.equals("Crab")) {
					makeBluePlateRequest(new Nigiri(Nigiri.NigiriType.CRAB), 0);
				} else {
					makeBluePlateRequest(new Nigiri(Nigiri.NigiriType.SHRIMP), 0);
				}
			} else if (sushiType.contentEquals("Sashimi")) {
				if(ingredient.equals("Tuna")) {
					makeBluePlateRequest(new Sashimi(Sashimi.SashimiType.TUNA), 0);
				} else if (ingredient.equals("YellowTail")) {
					makeBluePlateRequest(new Sashimi(Sashimi.SashimiType.YELLOWTAIL), 0);
				} else if (ingredient.equals("Eel")) {
					makeBluePlateRequest(new Sashimi(Sashimi.SashimiType.EEL), 0);
				} else if (ingredient.equals("Crab")) {
					makeBluePlateRequest(new Sashimi(Sashimi.SashimiType.CRAB), 0);
				} else {
					makeBluePlateRequest(new Sashimi(Sashimi.SashimiType.SHRIMP), 0);
				}
			} else {
				IngredientPortionImpl[] blueRoll = new IngredientPortionImpl[_rollIngredients.size()];
				blueRoll = _rollIngredients.toArray(blueRoll);
				makeBluePlateRequest(new Roll(_rollName, blueRoll), 0);
			}
			break;
			
		case GREEN:
			
			if (sushiType.equals("Nigiri")) {
				if(ingredient.equals("Tuna")) {
					makeGreenPlateRequest(new Nigiri(Nigiri.NigiriType.TUNA), 0);
				} else if (ingredient.equals("YellowTail")) {
					makeGreenPlateRequest(new Nigiri(Nigiri.NigiriType.YELLOWTAIL), 0);
				} else if (ingredient.equals("Eel")) {
					makeGreenPlateRequest(new Nigiri(Nigiri.NigiriType.EEL), 0);
				} else if (ingredient.equals("Crab")) {
					makeGreenPlateRequest(new Nigiri(Nigiri.NigiriType.CRAB), 0);
				} else {
					makeGreenPlateRequest(new Nigiri(Nigiri.NigiriType.SHRIMP), 0);
				}
			} else if (sushiType.contentEquals("Sashimi")) {
				if(ingredient.equals("Tuna")) {
					makeGreenPlateRequest(new Sashimi(Sashimi.SashimiType.TUNA), 0);
				} else if (ingredient.equals("YellowTail")) {
					makeGreenPlateRequest(new Sashimi(Sashimi.SashimiType.YELLOWTAIL), 0);
				} else if (ingredient.equals("Eel")) {
					makeGreenPlateRequest(new Sashimi(Sashimi.SashimiType.EEL), 0);
				} else if (ingredient.equals("Crab")) {
					makeGreenPlateRequest(new Sashimi(Sashimi.SashimiType.CRAB), 0);
				} else {
					makeGreenPlateRequest(new Sashimi(Sashimi.SashimiType.SHRIMP), 0);
				}
			} else {
				IngredientPortionImpl[] greenRoll = new IngredientPortionImpl[_rollIngredients.size()];
				greenRoll = _rollIngredients.toArray(greenRoll);
				makeGreenPlateRequest(new Roll(_rollName, greenRoll), 0);
			}
			break;
		
		case GOLD:
			System.out.println(goldPlatePrice);
			if (sushiType.equals("Nigiri")) {
				if(ingredient.equals("Tuna")) {
					makeGoldPlateRequest(new Nigiri(Nigiri.NigiriType.TUNA), 0, goldPlatePrice);
				} else if (ingredient.equals("YellowTail")) {
					makeGoldPlateRequest(new Nigiri(Nigiri.NigiriType.YELLOWTAIL), 0, goldPlatePrice);
				} else if (ingredient.equals("Eel")) {
					makeGoldPlateRequest(new Nigiri(Nigiri.NigiriType.EEL), 0, goldPlatePrice);
				} else if (ingredient.equals("Crab")) {
					makeGoldPlateRequest(new Nigiri(Nigiri.NigiriType.CRAB), 0, goldPlatePrice);
				} else {
					makeGoldPlateRequest(new Nigiri(Nigiri.NigiriType.SHRIMP), 0, goldPlatePrice);
				}
			} else if (sushiType.contentEquals("Sashimi")) {
				if(ingredient.equals("Tuna")) {
					makeGoldPlateRequest(new Sashimi(Sashimi.SashimiType.TUNA), 0, goldPlatePrice);
				} else if (ingredient.equals("YellowTail")) {
					makeGoldPlateRequest(new Sashimi(Sashimi.SashimiType.YELLOWTAIL), 0, goldPlatePrice);
				} else if (ingredient.equals("Eel")) {
					makeGoldPlateRequest(new Sashimi(Sashimi.SashimiType.EEL), 0, goldPlatePrice);
				} else if (ingredient.equals("Crab")) {
					makeGoldPlateRequest(new Sashimi(Sashimi.SashimiType.CRAB), 0, goldPlatePrice);
				} else {
					makeGoldPlateRequest(new Sashimi(Sashimi.SashimiType.SHRIMP), 0, goldPlatePrice);
				}
			} else {
				IngredientPortionImpl[] goldRoll = new IngredientPortionImpl[_rollIngredients.size()];
				goldRoll = _rollIngredients.toArray(goldRoll);
				makeGoldPlateRequest(new Roll(_rollName, goldRoll), 0, goldPlatePrice);
			}
			break;
		}
	sushiSlider.setValue(0);
	nonRollSlider.setValue(0);
	plateSlider.setValue(0);
	for(int i = 0; i < ingAmount.length; i++) {
		ingAmount[i].setValue(0);
		}
	}
}
