package poppitplayer.ecj;

import ec.gp.*;
import simpoppit.gameboard.*;

public class GameData extends GPData {

    public GPData copyTo(final GPData gpd)
    {
        ((GameData) gpd).game = game;
        ((GameData) gpd).point = point;
        return gpd;
    }

    public GameInterface game;
    public Coord point;
}
