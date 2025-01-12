package org.dang0x5f;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class Gfx
{
    private JFrame frame;

    public Gfx()
    {
        createFrame();
    }

    private void createFrame()
    {
        frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(512,380);
    }

    public void display()
    {
        this.frame.setVisible(true);
    }
}
