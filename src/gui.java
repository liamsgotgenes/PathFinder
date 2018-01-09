import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Enumeration;

import java.awt.*;
import javax.swing.*;



class gui extends JFrame
{
    public static boolean pressed;
    public static JPanel gridPanel;
    public static JPanel sidePanel;
    public static JRadioButton start,stop,wallBut,erase;
    public static ButtonGroup btnGroup;
    public static node startNode,stopNode;
    public gui()
    {
        setLayout(new BorderLayout());
        sidePanel=new JPanel();
        sidePanel.setPreferredSize(new Dimension(100,100));
        createSidePanel();
        gridPanel=new JPanel();
        gridPanel.setLayout(new GridLayout(100,100));
        fillGrid();
        add(sidePanel,BorderLayout.NORTH);
        add(gridPanel,BorderLayout.CENTER);
        setSize(1000,1000);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE); 
    }

    private void createSidePanel()
    {
        JButton go=new JButton("Go");
        go.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                //perforn search
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
        sidePanel.add(start);
        sidePanel.add(stop);
        sidePanel.add(wallBut);
        sidePanel.add(erase);
        sidePanel.add(go);
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

    private void fillGrid()
    {
        node[][] nodes=new node[100][100];
        for (int i=0;i<nodes.length;i++)
        {
            for (int j=0;j<nodes[i].length;j++)
            {
                node x=new node();
                gridPanel.add(x);
            }
        }
    }
}
