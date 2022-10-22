package org.firstinspires.ftc.teamcode.PowerPlay.Helpers;

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

