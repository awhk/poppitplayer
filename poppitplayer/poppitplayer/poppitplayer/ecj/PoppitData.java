package poppitplayer.ecj;

import simpoppit.gameboard.Coord;
import ec.gp.GPData;

public class PoppitData extends GPData {

    public GPData copyTo(final GPData gpd)
    {
        ((PoppitData) gpd).point = point;
        ((PoppitData) gpd).result = result;
        return gpd;
    }

    public Coord point;
    public boolean result;
}