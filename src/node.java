import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

public class node extends JPanel
{
    private boolean wall;
    private boolean visited;
    private double dist;
    private boolean start=false;
    private boolean stop=false;
    private int x;
    private int y;
    public node path=null;

    public node(int x,int y)
    {
        super();
        wall=false;
        visited=false;
        dist=-1;
        this.setBackground(Color.WHITE);
        this.addMouseListener(new MyMouse());
        this.x=x;
        this.y=y;
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
        start=false;
        this.setBackground(Color.RED);
    }

    public void makeNothing()
    {
        wall=false;
        start=false;
        stop=false;
        this.setBackground(Color.WHITE);
    }

    public void makePath()
    {
        this.setBackground(Color.BLUE);
    }

    public void makeVisited()
    {
        visited=true;
        this.setBackground(Color.ORANGE);
    }

    public int getx()
    {
        return x;
    }

    public int gety()
    {
        return y;
    }

    public double getDist()
    {
        return dist;
    }

    public void setDist(double dist)
    {
        this.dist=dist;
    }

    public boolean isWall()
    {
        return wall;
    }

    public boolean isVisited()
    {
        return visited;
    }

    public String toString()
    {
        return "Stop: "+this.stop+" Start: "+start+" Wall: "+wall+" Visited: "+visited+" ("+x+","+y+")";
    }

    class MyMouse extends MouseAdapter
    {
        public void mousePressed(MouseEvent e)
        {
            gui.pressed=true;
            JRadioButton button=gui.getOptionSelected();
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
            JRadioButton button=gui.getOptionSelected();
            node tmp=(node)e.getSource();
            gui.nodeLabel.setText(tmp.toString());
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
