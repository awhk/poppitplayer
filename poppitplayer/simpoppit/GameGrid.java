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
        this.grid = new BalloonColumn[15];
        for (int i=0; i<this.Columns(); i++){
            this.grid[i] = new BalloonColumn();
        }
    }
    
    public GameGrid(int aNumberOfColumns){
        if ((aNumberOfColumns % 2) == 0){
            aNumberOfColumns++;
            System.out.println("Number of columns must be odd.  Incremented.");
        }
        this.grid = new BalloonColumn[aNumberOfColumns];
        for (int i=0; i<this.Columns(); i++){
            this.grid[i] = new BalloonColumn();
        }
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
    }
    
    public int Columns(){
        return grid.length;
    }
    
    public void GridSize(){
        
    }
    
    public String toString(){
        String result = "";
        int index = 1;
        for (BalloonColumn t : this.grid){
            result += "Column ";
            result += index;
            result += "\n";
            result += "---------\n";
            result += t;
            result += "\n\n";
            index++;
        }
        return result;
    }
    
    private int CenterColumn(){
        int length = this.Columns();
        if (length == 1) return 0;
        return ((length-1)/2);
    }
    
    private BalloonColumn[] grid;
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        GameGrid test = new GameGrid(5,5);
        System.out.println(test);
        System.out.print("Center column is ");
        System.out.println(test.CenterColumn());
    }

}
