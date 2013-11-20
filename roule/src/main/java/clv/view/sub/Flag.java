/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clv.view.sub;

/**
 *
 * @author CLV
 */
public class Flag {

    private boolean field;

    public Flag(boolean b) {
        field = b;
    }

    public boolean isTrue() {
        return field;
    }

    public void set(boolean b) {
        field = b;
    }
}
