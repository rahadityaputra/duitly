package duitly.util;

import javax.swing.JOptionPane;

public class ErrorDialogSwing {
    public static void showError(String title, String message) {
        // null berarti dialog muncul di tengah layar
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
    }
}