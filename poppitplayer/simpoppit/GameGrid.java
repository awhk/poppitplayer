/**
 * <p>Title: GameGrid</p>
 *
 * <p>Description: Game grid class</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * @author Andrew W. Henry
 * @version 1.0
 */
public class GameGrid {

    public GameGrid(){
        this(15);
    }
    public GameGrid(int aNumberOfColumns){
        this(aNumberOfColumns, 10);
    }
    
    public GameGrid(int aNumberOfColumns, int aNumberOfRows){
        if ((aNumberOfColumns % 2) == 0){
            aNumberOfColumns++;
            System.out.println("Number of columns must be odd.  Incremented.");
        }
        this.grid = new BalloonColumn[aNumberOfColumns];
        for (int i=0; i<this.Columns(); i++){
            this.grid[i] = new BalloonColumn(aNumberOfRows);
        }
        this.xMax = aNumberOfColumns-1;
        this.yMax = aNumberOfRows-1;
        this.bottomRight = new Coord(aNumberOfColumns-1, aNumberOfRows-1);
    }
    
    public int Columns(){
        return grid.length;
    }
    
    public Coord GridSize(){
        return this.bottomRight;
    }
    
    public Balloon.Color Color(int aX, int aY){
        return this.Color(new Coord(aX, aY));
    }
    
    public Balloon.Color Color(Coord aBalloon){
        if (aBalloon.GetX() > this.xMax || aBalloon.GetY() > this.yMax){
            return null;
        }
        return this.grid[aBalloon.GetX()].Color(aBalloon.GetY());
    }
    
    public String toString(){
        String result = "";
        for (int i=0; i<this.xMax+1; i++){
            for (int j=0; j<this.yMax+1; j++){
                result += this.grid[j].Color(i);
                result += "\t";
            }
            result += "\n";
        }
        return result;
    }
    
    private int CenterColumn(){
        int length = this.Columns();
        if (length == 1) return 0;
        return ((length-1)/2);
    }
    
    private BalloonColumn[] grid;
    private int xMax;
    private int yMax;
    private Coord bottomRight;
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        GameGrid test = new GameGrid(5,5);
        System.out.println(test);
        System.out.print("Center column is ");
        System.out.println(test.CenterColumn());
        System.out.print("Ballon at 2,2 is ");
        System.out.println(test.Color(2,2));
        System.out.print("Ballon at bottom right is ");
        System.out.println(test.Color(test.GridSize()));
    }

}
