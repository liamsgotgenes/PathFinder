import java.awt.Color;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

public class Search
{
    public static void bfs()
    {
        long startTime=System.nanoTime();
        Queue<node> q=new LinkedList<node>();
        gui.startNode.setDist(0);
        q.add(gui.startNode);
        boolean found=false;
        int nodesSearched=0;
        while (!q.isEmpty()&&!found)
        {
            node e=q.remove();
            ArrayList<node> adjacentNodes=gui.getAdjacent(e.getx(),e.gety());
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
                    if (n==gui.stopNode) found=true;
                }
            }
        }
        double stopTime=System.nanoTime()-startTime;
        stopTime=stopTime*0.0000000001;
        print(nodesSearched,stopTime);
    }

    public static void dijkstra()
    {
        long startTime=System.nanoTime();
        Comparator<Object> compare=new NodeCompare();
        PriorityQueue<node> pq=new PriorityQueue<node>(compare);
        gui.startNode.setDist(0);
        pq.add(gui.startNode);
        boolean found=false;
        int nodesSearched=0;
        while (!pq.isEmpty()&&!found)
        {
            node u=pq.remove();
            ArrayList<node> adjacentNodes=gui.getAdjacent(u.getx(),u.gety());
            for (int i=0;i<adjacentNodes.size();i++)
            {
                node v=adjacentNodes.get(i);
                if (v.getDist()==-1||v.getDist()>u.getDist()+1)
                {
                    v.setDist(u.getDist()+1);
                    v.path=u;
                    v.makeVisited();
                    pq.add(v);
                    nodesSearched++;
                    if (v==gui.stopNode) found=true;
                }
            }
        }
        double stopTime=System.nanoTime()-startTime;
        stopTime=stopTime*0.0000000001;
        print(nodesSearched,stopTime);
   }

    public static void aStar()
    {
        long startTime=System.nanoTime();
        Comparator<Object> compare=new NodeCompare();
        PriorityQueue<node> pq=new PriorityQueue<node>(compare);
        gui.startNode.setDist(0);
        pq.add(gui.startNode);
        boolean found=false;
        int nodesSearched=0;
        while (!pq.isEmpty()&&!found)
        {
            node u=pq.remove();
            ArrayList<node> adjacentNodes=gui.getAdjacent(u.getx(),u.gety());
            for (int i=0;i<adjacentNodes.size();i++)
            {
                node v=adjacentNodes.get(i);
                if (v.getDist()==-1||v.getDist()>u.getDist()+1)
                {
                    double priority=gui.heuristic(v);
                    v.setDist(priority);
                    v.makeVisited();
                    pq.add(v);
                    v.path=u;
                    nodesSearched++;
                    if (v==gui.stopNode) found=true;
                }
            }
        }
        double stopTime=System.nanoTime()-startTime;
        stopTime=stopTime*0.0000000001;
        print(nodesSearched,stopTime);
   }

    private static void print(int nodesSearched,double time)
    {
        gui.printPath(gui.stopNode);
        gui.stopNode.setBackground(Color.RED);
        gui.nodeLabel.setText("Nodes Searched:("+String.valueOf(nodesSearched)+") Path Length:("+String.valueOf(gui.pathLength)+") Time:("+time+")");
    }
}
