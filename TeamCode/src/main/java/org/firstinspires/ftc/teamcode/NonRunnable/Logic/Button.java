/*
 * Copyright (c)  3/20/2021. FTC Team 14214 NvyUs
 * This code is very epic
 */

package org.firstinspires.ftc.teamcode.NonRunnable.Logic;

public class Button {
    private boolean wasPressed = false;

    public Button() {
    }

    public boolean isPressed(boolean button) {
        boolean tempWasPressed = wasPressed;
        wasPressed = button;
        return button && !tempWasPressed;
    }
}
