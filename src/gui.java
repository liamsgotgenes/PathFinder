import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.Queue;

import java.awt.*;
import javax.swing.*;



class gui extends JFrame
{
    public static boolean pressed;
    private JPanel gridPanel,optionPanel;
    public static JPanel topPanel, btmPanel;
    public static JLabel nodeLabel;
    private JRadioButton start,stop,wallBut,erase;
    public static ButtonGroup btnGroup;
    public static node startNode,stopNode;
    public node[][] nodes=new node[100][100];

    public gui()
    {
        setLayout(new BorderLayout());
        topPanel=new JPanel();
        optionPanel=new JPanel();
        nodeLabel=new JLabel();
        optionPanel.setPreferredSize(new Dimension(100,100));
        createOptionPanel();
        gridPanel=new JPanel();
        gridPanel.setLayout(new GridLayout(100,100));
        gridPanel.setBackground(Color.BLUE);
        fillGrid();
        add(gridPanel,BorderLayout.CENTER);
        add(optionPanel,BorderLayout.NORTH);
        setSize(1000,1000);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE); 
    }

    private void createOptionPanel()
    {
        JButton go=new JButton("Go");
        go.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                bfs();
            }
        });
        start=new JRadioButton("Start");
        start.setSelected(true);
        stop=new JRadioButton("Stop");
        wallBut=new JRadioButton("Walls");
        erase=new JRadioButton("Erase");
        btnGroup=new ButtonGroup();
        btnGroup.add(start);
        btnGroup.add(stop);
        btnGroup.add(wallBut);
        btnGroup.add(erase);
        topPanel.add(start);
        topPanel.add(stop);
        topPanel.add(wallBut);
        topPanel.add(erase);
        topPanel.add(go);
        optionPanel.setLayout(new GridLayout(2,0));
        optionPanel.add(topPanel);
        optionPanel.add(nodeLabel);
    }

    public static JRadioButton getSelected()
    {
         Enumeration<AbstractButton> bm=btnGroup.getElements();
         while (bm.hasMoreElements())
         {
             AbstractButton button=bm.nextElement();
             if (button.isSelected()) return (JRadioButton)button;
         }
         return null;
    }

    private void bfs()
    {
        Queue<node> q=new LinkedList<node>();
        startNode.setDist(0);
        q.add(startNode);

        while (!q.isEmpty())
        {
            node e=q.remove();
            if (e==stopNode)
            {
                printPath(e);
                break;
            }
            ArrayList<node> adjacentNodes=getAdjacent(e.getx(),e.gety());
            for (int i=0;i<adjacentNodes.size();i++)
            {
                node n=adjacentNodes.get(i);
                if (n.getDist()==-1)
                {
                    n.setDist(e.getDist()+1);
                    n.path=e;
                    q.add(n);
                }
            }
        }
    }

    private void printPath(node e)
    {
        if (e.path!=null&&e.path!=startNode)
        {
            e.path.makePath();
            printPath(e.path);
        }
    }

    private ArrayList<node> getAdjacent(int x,int y)
    {
        ArrayList<node> adjacentNodes=new ArrayList<node>();
        try
        {
            node tmp=nodes[x+1][y];
            if (!tmp.isWall()) adjacentNodes.add(tmp);
        }
        catch (Exception e)
        {

        }
        try
        {
            node tmp=nodes[x-1][y];
            if (!tmp.isWall()) adjacentNodes.add(tmp);
        }
        catch (Exception e)
        {

        }
        try
        {
            node tmp=nodes[x][y-1];
            if (!tmp.isWall()) adjacentNodes.add(tmp);
        }
        catch (Exception e)
        {

        }
        try
        {
            node tmp=nodes[x][y+1];
            if (!tmp.isWall()) adjacentNodes.add(tmp);
        }
        catch (Exception e)
        {

        }
       return adjacentNodes;
    }

    private void fillGrid()
    {
        for (int i=0;i<nodes.length;i++)
        {
            for (int j=0;j<nodes[i].length;j++)
            {
                node x=new node(i,j);
                nodes[i][j]=x;
                gridPanel.add(x);
            }
        }
    }
}
