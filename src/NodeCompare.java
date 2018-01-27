import java.util.Comparator;

public class NodeCompare implements Comparator
{
    @Override
    public int compare(Object a, Object b)
    {
        double x=((node)a).getDist();
        double y=((node)b).getDist();
        
        if (x==-1&&y==-1) return 0;
        if (x==-1) return 1;
        if (y==-1) return -1;

        if (x<y) return -1;
        if (y<x) return 1;
        return 0;
    }
}
