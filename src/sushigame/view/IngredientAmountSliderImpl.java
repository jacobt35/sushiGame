package sushigame.view;

import java.util.Hashtable;

import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;

import comp401sushi.Ingredient;

@SuppressWarnings("serial")
public class IngredientAmountSliderImpl extends JSlider implements IngredientAmountSlider{

	
	public IngredientAmountSliderImpl() {
		super(SwingConstants.HORIZONTAL, 0, 150, 0);
		setMajorTickSpacing(25);
		setPaintLabels(true);
		setSnapToTicks(true);
		setPaintTicks(true);
		
		Hashtable<Integer, JLabel> ingTable = new Hashtable<Integer, JLabel>();
		ingTable.put(0, new JLabel("0.0"));
		ingTable.put(25, new JLabel("0.25"));
		ingTable.put(50, new JLabel("0.50"));
		ingTable.put(75, new JLabel("0.75"));
		ingTable.put(100, new JLabel("1.00"));
		ingTable.put(125, new JLabel("1.25"));
		ingTable.put(150, new JLabel("1.50"));
		setLabelTable(ingTable);
		
	}

}
