import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

public class node extends JPanel
{
    boolean wall;
    boolean visited;
    boolean start=false;
    boolean stop=false;
    int x;
    int y;

    public node()
    {
        super();
        wall=false;
        visited=false;
        this.setBackground(Color.WHITE);
        this.addMouseListener(new MyMouse());
    }

    public void makeWall()
    {
        wall=true;
        start=false;
        stop=false;
        this.setBackground(Color.BLACK);
    }

    public void makeStart()
    {
        start=true;
        wall=false;
        stop=false;
        this.setBackground(Color.GREEN);
    }

    public void makeStop()
    {
        stop=true;
        wall=false;
        stop=false;
        this.setBackground(Color.RED);
    }

    public void makeNothing()
    {
        wall=false;
        start=false;
        stop=false;
        this.setBackground(Color.WHITE);
    }

    class MyMouse extends MouseAdapter
    {
        public void mousePressed(MouseEvent e)
        {
            gui.pressed=true;
            JRadioButton button=gui.getSelected();
            node tmp=(node)e.getSource();
            if (button!=null)
            {
                if (button.getText().equals("Start"))
                {
                    if (gui.startNode!=null) gui.startNode.makeNothing();
                    tmp.makeStart();
                    gui.startNode=tmp;
                }
                else if (button.getText().equals("Stop"))
                {
                    if (gui.stopNode!=null) gui.stopNode.makeNothing();
                    tmp.makeStop();
                    gui.stopNode=tmp;
                }
                else if (button.getText().equals("Erase"))
                {
                    tmp.makeNothing();
                }
                else
                {
                    tmp.makeWall();
                }
            }
        }

        public void mouseReleased(MouseEvent e)
        {
            gui.pressed=false;
        }

        public void mouseEntered(MouseEvent e)
        {
            JRadioButton button=gui.getSelected();
            node tmp=(node)e.getSource();
            if (button!=null)
            {
                if (gui.pressed)
                {
                    if (button.getText().equals("Walls"))
                    {
                        tmp.makeWall();
                    }
                    else if (button.getText().equals("Erase"))
                    {
                        tmp.makeNothing();
                    }
                }
            }
        }


    }
}
