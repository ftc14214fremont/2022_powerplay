package org.firstinspires.ftc.teamcode.NonRunnable.Logic;

public class Button
{
    private boolean wasPressed        = false;
    private boolean finishedExecuting = true;
    
    public Button()
    {
    
    }
    
    public boolean isPressed(boolean button)
    {
        boolean tempWasPressed = wasPressed;
        wasPressed = button;
        if (button && !tempWasPressed)
        {
            finishedExecuting = false;
        }
        return button && !tempWasPressed;
    }
    
    public void isFinished()
    {
        finishedExecuting = true;
    }
}
