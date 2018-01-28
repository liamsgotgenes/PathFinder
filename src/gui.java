import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Enumeration;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

class gui extends JFrame
{
    public static boolean pressed;
    public boolean cleared=true;
    private JPanel gridPanel,optionPanel;
    public static JPanel topPanel, btmPanel;
    public static JLabel nodeLabel;
    public static ButtonGroup btnGroupOption, btnGroupSearch;
    public static node startNode,stopNode;
    public static node[][] nodes=new node[100][100];
    public static int pathLength=0;

    public gui()
    {
        setTitle("Path Finder");
        setLayout(new BorderLayout());
        topPanel=new JPanel();
        optionPanel=new JPanel();
        optionPanel.setPreferredSize(new Dimension(100,100));
        createOptionPanel();
        gridPanel=new JPanel();
        gridPanel.setLayout(new GridLayout(100,100));
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
                if (startNode==null||stopNode==null)
                {
                    nodeLabel.setText("A graph need both a start node and a stop node.");
                }
                else
                {
                    reset();
                    String tmp=getSearchSelected().getText();
                    if (tmp.equals("BFS")) Search.bfs();
                    else if (tmp.equals("Dijkstra")) Search.dijkstra();
                    else Search.aStar();
                }
            }
        });
        JButton reset=new JButton("Reset");
        reset.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                reset();
            }
        });
        JRadioButton start,stop,wallBut,erase;
        start=new JRadioButton("Start");
        start.setSelected(true);
        stop=new JRadioButton("Stop");
        wallBut=new JRadioButton("Walls");
        erase=new JRadioButton("Erase");
        btnGroupOption=new ButtonGroup();
        btnGroupOption.add(start);
        btnGroupOption.add(stop);
        btnGroupOption.add(wallBut);
        btnGroupOption.add(erase);
        topPanel.add(start);
        topPanel.add(stop);
        topPanel.add(wallBut);
        topPanel.add(erase);
        topPanel.add(go);

        JButton clear=new JButton("Clear");
        clear.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                clear();
            }
        });
        JRadioButton bfsBtn,dijBtn,aStarBtn;
        nodeLabel=new JLabel();
        bfsBtn=new JRadioButton("BFS");
        dijBtn=new JRadioButton("Dijkstra");
        aStarBtn=new JRadioButton("A*");
        bfsBtn.setSelected(true);
        btnGroupSearch=new ButtonGroup();
        btnGroupSearch.add(bfsBtn);
        btnGroupSearch.add(dijBtn);
        btnGroupSearch.add(aStarBtn);
        btmPanel=new JPanel();
        btmPanel.add(bfsBtn);
        btmPanel.add(dijBtn);
        btmPanel.add(aStarBtn);
        btmPanel.add(reset);
        btmPanel.add(clear);
        optionPanel.setLayout(new GridLayout(3,0));
        optionPanel.add(topPanel);
        optionPanel.add(btmPanel);
        optionPanel.add(nodeLabel);
    }

    public static JRadioButton getOptionSelected()
    {
         Enumeration<AbstractButton> bm=btnGroupOption.getElements();
         while (bm.hasMoreElements())
         {
             AbstractButton button=bm.nextElement();
             if (button.isSelected()) return (JRadioButton)button;
         }
         return null;
    }

    public static JRadioButton getSearchSelected()
    {
         Enumeration<AbstractButton> bm=btnGroupSearch.getElements();
         while (bm.hasMoreElements())
         {
             AbstractButton button=bm.nextElement();
             if (button.isSelected()) return (JRadioButton)button;
         }
         return null;
    }

    public static void printPath(node e)
    {
        if (e.path!=null&&e.path!=startNode)
        {
            pathLength++;
            e.path.makePath();
            printPath(e.path);
        }
    }

    public static ArrayList<node> getAdjacent(int x,int y)
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

    private void reset()
    {
        for (int i=0;i<nodes.length;i++)
        {
            for (int j=0;j<nodes[i].length;j++)
            {
                node x=nodes[i][j];
                if (x!=startNode&&x!=stopNode&&!x.isWall())
                {
                    x.resetNode();
                }
            }
        }
        pathLength=0;
        startNode.path=null;
        startNode.setDist(-1);
        stopNode.path=null;
        stopNode.setDist(-1);
    }

    private void clear()
    {
        for (int i=0;i<nodes.length;i++)
        {
            for (int j=0;j<nodes[i].length;j++)
            {
                nodes[i][j].resetNode();
            }
        }
        pathLength=0;
        startNode=null;
        stopNode=null;
    }

    public static double heuristic(node e)
    {
        float a2=(Math.abs(e.getx()-stopNode.getx()));
        float b2=(Math.abs(e.gety()-stopNode.gety()));
        a2*=a2;
        b2*=b2;
        double c2=a2+b2;
        c2=Math.sqrt(c2);
        return c2;
    }
}
