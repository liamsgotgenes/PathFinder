import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

import java.awt.*;
import javax.swing.*;
import java.lang.Math.*;



class gui extends JFrame
{
    public static boolean pressed;
    private JPanel gridPanel,optionPanel;
    public static JPanel topPanel, btmPanel;
    public static JLabel nodeLabel;
    public static ButtonGroup btnGroupOption, btnGroupSearch;
    public static node startNode,stopNode;
    public node[][] nodes=new node[100][100];
    public static int pathLength=0;

    public gui()
    {
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
                String tmp=getSearchSelected().getText();
                if (tmp.equals("BFS")) bfs();
                else if (tmp.equals("Dijkstra")) dijkstra();
                else aStar();

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
        optionPanel.setLayout(new GridLayout(2,0));
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

    private void bfs()
    {
        Queue<node> q=new LinkedList<node>();
        startNode.setDist(0);
        q.add(startNode);
        int nodesSearched=0;

        while (!q.isEmpty())
        {
            node e=q.remove();
            if (e==stopNode)
            {
                //Color will turn orange without this
                e.setBackground(Color.RED);
                printPath(e);
                nodeLabel.setText("Nodes Searched:"+String.valueOf(nodesSearched)+" Path Length:"+String.valueOf(pathLength));
                break;
            }
            ArrayList<node> adjacentNodes=getAdjacent(e.getx(),e.gety());
            for (int i=0;i<adjacentNodes.size();i++)
            {
                node n=adjacentNodes.get(i);
                if (n.getDist()==-1)
                {
                    n.makeVisited();
                    n.setDist(e.getDist()+1);
                    n.path=e;
                    q.add(n);
                    nodesSearched++;
                }
            }
        }
    }

    private void dijkstra()
    {
        Comparator<Object> compare=new NodeCompare();
        PriorityQueue<node> pq=new PriorityQueue<node>(compare);
        startNode.setDist(0);
        pq.add(startNode);
        boolean found=false;
        int nodesSearched=0;
        while (!pq.isEmpty()&&!found)
        {
            node u=pq.remove();
            ArrayList<node> adjacentNodes=getAdjacent(u.getx(),u.gety());
            for (int i=0;i<adjacentNodes.size();i++)
            {
                node v=adjacentNodes.get(i);
                if (v.getDist()==-1)
                {
                    v.setDist(u.getDist()+1);
                    v.path=u;
                    v.makeVisited();
                    pq.add(v);
                    nodesSearched++;
                    if (v==stopNode) found=true;
                }
                else
                {
                    if (v.getDist()>u.getDist()+1)
                    {
                        v.setDist(u.getDist()+1);
                        v.path=u;
                        v.makeVisited();
                        pq.add(v);
                        nodesSearched++;
                        if (v==stopNode) found=true;
                    }
                }

            }
        }
        printPath(stopNode);
        stopNode.setBackground(Color.RED);
        nodeLabel.setText("Nodes Searched:"+String.valueOf(nodesSearched)+" Path Length:"+String.valueOf(pathLength));
    }

    private void aStar()
    {
        Comparator<Object> compare=new NodeCompare();
        PriorityQueue<node> pq=new PriorityQueue<node>(compare);
        startNode.setDist(0);
        pq.add(startNode);
        boolean found=false;
        int nodesSearched=0;
        while (!pq.isEmpty()&&!found)
        {
            node u=pq.remove();
            ArrayList<node> adjacentNodes=getAdjacent(u.getx(),u.gety());
            for (int i=0;i<adjacentNodes.size();i++)
            {
                node v=adjacentNodes.get(i);
                if (v.getDist()==-1||v.getDist()>u.getDist()+1)
                {
                    //v.setDist(u.getDist()+1);
                    double priority=heuristic(v);
                    v.setDist(priority);
                    v.makeVisited();
                    pq.add(v);
                    v.path=u;
                    nodesSearched++;
                    if (v==stopNode) found=true;
                }
            }
        }
        printPath(stopNode);        
        stopNode.setBackground(Color.RED);
        nodeLabel.setText("Nodes Searched:"+String.valueOf(nodesSearched)+" Path Length:"+String.valueOf(pathLength));

    }

    private void printPath(node e)
    {
        if (e.path!=null&&e.path!=startNode)
        {
            pathLength++;
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

    private void reset()
    {
        this.dispose();
        new gui();
        this.pathLength=0;
    }

    private double heuristic(node e)
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
