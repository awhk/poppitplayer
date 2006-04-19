package poppitplayer.ecj;

import ec.gp.GPData;

public class BooleanData extends GPData {

    public GPData copyTo(final GPData gpd)
    {
        ((BooleanData) gpd).result = result;
        return gpd;
    }

    public boolean result;
}

