package com.si.inventory;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

public final class KeyboardNavigation {

    private KeyboardNavigation() {
    }

    public static void configureEnterAsTab(JComponent component) {
        String actionKey = "focusNextComponent";
        component.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke("ENTER"), actionKey);
        Action action = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent event) {
                component.transferFocus();
            }
        };
        component.getActionMap().put(actionKey, action);
    }
}
