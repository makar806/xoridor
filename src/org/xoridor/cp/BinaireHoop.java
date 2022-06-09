package org.xoridor.cp;

public final class BinaireHoop {
        
    private PathFinder.Path [] deHoop;
    private int huidigeGrootte;
    private final PathFinder.Position goal;

    public BinaireHoop(int defaultCapacity, PathFinder.Path minOneindig, PathFinder.Position goal) {
        huidigeGrootte = 0;
        deHoop = new PathFinder.Path [defaultCapacity+1];
        deHoop[0] = minOneindig;
        this.goal = goal;
    }

    private int distance(PathFinder.Position start) {
        return Math.abs(start.row - goal.row) + Math.abs(start.col - goal.col);
    }
    
    private int distance(int hoopNummer) {
           if (hoopNummer == 0)
               return -1;
           else 
               return distance(deHoop[hoopNummer].head());
    }

    public final void maakLeeg() {
        huidigeGrootte = 0;
    }
    
    public final boolean isEmpty() {
        return huidigeGrootte == 0;
    }

    public final void add(PathFinder.Path x) {
        if (huidigeGrootte + 1 == deHoop.length)
            verdubbelHoop();
        int vrijePlaats = ++huidigeGrootte;
        for ( ; distance(x.head()) < distance(vrijePlaats/2); vrijePlaats /= 2)
            deHoop[vrijePlaats] = deHoop[vrijePlaats/2];
        deHoop[vrijePlaats] = x;
    }

    public final PathFinder.Path remove()  {
        PathFinder.Path kleinste = zoekKleinste();
        if (kleinste == null)
            return null;
        deHoop[1] = deHoop[huidigeGrootte--];
        percoleerNaarBeneden(1);
        return kleinste;
    }

    public final PathFinder.Path zoekKleinste() {
        if (isEmpty())
            return null;
        return deHoop[1];
    }
    
    private void verdubbelHoop() {
        PathFinder.Path [] origineel = deHoop;
        deHoop = new PathFinder.Path [origineel.length * 2];
        for (int i = 0; i < origineel.length; i++)
            deHoop[i] = origineel[i];
    }
    
    private void percoleerNaarBeneden(int plaats) {
        int kind;
        PathFinder.Path tmp = deHoop[plaats];
        int dist;
        if (plaats == 0)
            dist = -1;
        else
           dist = distance(tmp.head());
        for ( ; plaats * 2 <= huidigeGrootte; plaats = kind) {
            kind = plaats * 2;
            if (kind != huidigeGrootte &&
            distance(kind+1) < distance(kind))
                kind++;
            if (distance(kind) < dist)
                deHoop[plaats] = deHoop[kind];
            else
                break;
        }
        deHoop[plaats] = tmp;
    }
    
}
