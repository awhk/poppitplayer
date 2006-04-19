package poppitplayer.ecj;

import ec.gp.*;
import simpoppit.gameboard.Coord;

public class CoordData extends GPData {

    public GPData copyTo(final GPData gpd)
    {
        ((CoordData) gpd).point = point;
        return gpd;
    }

    public Coord point;
}
