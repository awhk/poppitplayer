package poppitplayer.ecj.func;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.ERC;
import ec.gp.GPData;
import ec.gp.GPIndividual;
import ec.gp.GPNode;
import ec.util.Code;
import ec.util.DecodeReturn;
import simpoppit.gameboard.Coord;
import poppitplayer.ecj.PoppitData;

public class BoolERC extends ERC {

    public boolean value;
    private float test;

    // for now, this will produce coordinates in the range
    // (0,0)-(8,8)...hopefully I can find a way to make the range
    // match the board size...
    public void resetNode(final EvolutionState state, final int thread)
        { test = state.random[thread].nextFloat();
            if (test >= 0.5){
                value = true;
            }else{
                value = false;
            }
        }

    public int nodeHashCode()
    {
    // a reasonable hash code
    return this.getClass().hashCode() + Float.floatToIntBits(test);
    }
    
    public boolean nodeEquals(final GPNode node)
        {
        // check first to see if we're the same kind of ERC -- 
        // won't work for subclasses; in that case you'll need
        // to change this to isAssignableTo(...)
        if (this.getClass() != node.getClass()) return false;
        // now check to see if the ERCs hold the same value
        return (((BoolERC)node).value = value);
        }

    public void readNode(final EvolutionState state, final DataInput dataInput) throws IOException
        {
        value = dataInput.readBoolean();
        test = dataInput.readFloat();
        }

    public void writeNode(final EvolutionState state, final DataOutput dataOutput) throws IOException
        {
        dataOutput.writeBoolean(value);
        dataOutput.writeFloat(test);
        }

    public String encode()
        { return Code.encode(value); }

    public boolean decode(DecodeReturn dret)
        {
        // store the position and the string in case they
        // get modified by Code.java
        int pos = dret.pos;
        String data = dret.data;

        // decode
        Code.decode(dret);

        if (dret.type != DecodeReturn.T_BOOLEAN) // uh oh!
            {
            // restore the position and the string; it was an error
            dret.data = data;
            dret.pos = pos;
            return false;
            }

        // store the data
        if (dret.l == 0){
            value = false;
        }else{
            value = true;
        }
        
        Code.decode(dret);
        
        if (dret.type != DecodeReturn.T_FLOAT) // uh oh!
        {
        // restore the position and the string; it was an error
        dret.data = data;
        dret.pos = pos;
        return false;
        }

    // store the data
    test = (float)dret.d;
        
        return true;
    }

    public String name() { return "boolerc"; } // I'm the only ERC class, this is fine

    public String toStringForHumans()
        { return "" + value; }

    public void eval(final EvolutionState state,
                     final int thread,
                     final GPData input,
                     final ADFStack stack,
                     final GPIndividual individual,
                     final Problem problem)
        {
        PoppitData pd = ((PoppitData)(input));
         pd.result = value;
         pd.point = new Coord(0,0);
        }
}
