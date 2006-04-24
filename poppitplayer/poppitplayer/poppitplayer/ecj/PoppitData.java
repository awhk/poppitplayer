package poppitplayer.ecj;

import simpoppit.gameboard.Coord;
import ec.gp.GPData;

public class PoppitData extends GPData {

    public GPData copyTo(final GPData gpd)
    {
        ((PoppitData) gpd).point = (Coord)point.clone();
        ((PoppitData) gpd).result = result;
        ((PoppitData) gpd).number = number;
        return gpd;
    }
    
    public Object clone() {
        PoppitData cloneResult = new PoppitData();
        cloneResult.result = this.result;
        cloneResult.number = this.number;
        if (this.point == null){
            cloneResult.point = null;
        }else{
            cloneResult.point = (Coord)this.point.clone();
        }
        return cloneResult;
    }

    public Coord point = new Coord(0,0);
    public boolean result = false;
    public int number = 0;
}