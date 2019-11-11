
import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Itzik
 */
public class QuntityTableCellRendere extends DefaultTableCellRenderer {

   private static final long serialVersionUID = 1L;

   public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int col) {

      Component c = super.getTableCellRendererComponent(table, value,
               isSelected, hasFocus, row, col);
      Object valueAt = table.getModel().getValueAt(row, 5); // 5 = quntity at storage
      if (valueAt == null || valueAt.toString().equals("0")) {
         c.setBackground(Color.RED);
      }
      else if(row % 2 == 0){
          c.setBackground(Color.WHITE);
      }
      else{
          c.setBackground(Color.LIGHT_GRAY);
      }
      return c;
   }
}
