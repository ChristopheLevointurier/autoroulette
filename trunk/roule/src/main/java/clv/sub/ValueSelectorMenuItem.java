/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clv.sub;

import javax.swing.JOptionPane;
import javax.swing.JToggleButton;

/**
 *
 * @author CLV
 */
public class ValueSelectorMenuItem {

    private String title = "";
    private String input = "";

    public ValueSelectorMenuItem(String _input, String _title) {
        input = _input;
        title = _title;
    }

    public String getValue() {
        return JOptionPane.showInputDialog(null, title + ":", input);
    }
    
    public int getIntValue() {
        return Integer.parseInt(JOptionPane.showInputDialog(null, title + ":", input).trim());
    } 
    
    public float getFloatValue() {
        return Float.parseFloat(JOptionPane.showInputDialog(null, title + ":", input).trim());
    }
    public double getDoubleValue() {
        return Double.parseDouble(JOptionPane.showInputDialog(null, title + ":", input).trim());
    }
}
