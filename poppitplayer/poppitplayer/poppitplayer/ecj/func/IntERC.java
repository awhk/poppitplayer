package poppitplayer.ecj.func;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import ec.Evaluator;
import ec.EvolutionState;
import ec.Problem;
import ec.app.lawnmower.Lawnmower;
import ec.gp.ADFStack;
import ec.gp.ERC;
import ec.gp.GPData;
import ec.gp.GPIndividual;
import ec.gp.GPNode;
import ec.util.Code;
import ec.util.DecodeReturn;
import ec.util.Parameter;
import simpoppit.gameboard.Coord;
import poppitplayer.ecj.PoppitData;

public class IntERC extends ERC {

    public int value;
    
    private int x;
    private int y;
    
    public void setup(final EvolutionState state, final Parameter base)
    {
    super.setup(state,base);
    // figure the coordinate base -- this will break if the underlying
    // base changes, oops
    Parameter newbase = 
        new Parameter(EvolutionState.P_EVALUATOR).push(Evaluator.P_PROBLEM);


    // load our map coordinates
    x = state.parameters.getInt(newbase.push("x"),null,1);
    y = state.parameters.getInt(newbase.push("y"),null,1);

    state.output.exitIfErrors();      
    }

    public void resetNode(final EvolutionState state, final int thread)
        {   //value = state.random[thread].nextInt(x*y) + 1;
            value = state.random[thread].nextInt(5);
        }

    public int nodeHashCode()
    {
    // a reasonable hash code
    //return this.getClass().hashCode() + Float.floatToIntBits(value.getX()) + Float.floatToIntBits(value.getY());
        return this.getClass().hashCode() + value;
    }
    
    public boolean nodeEquals(final GPNode node)
        {
        // check first to see if we're the same kind of ERC -- 
        // won't work for subclasses; in that case you'll need
        // to change this to isAssignableTo(...)
        if (this.getClass() != node.getClass()) return false;
        // now check to see if the ERCs hold the same value
        return ((IntERC)node).value == value;
        }

    public void readNode(final EvolutionState state, final DataInput dataInput) throws IOException
        {
        value = dataInput.readInt();
        }

    public void writeNode(final EvolutionState state, final DataOutput dataOutput) throws IOException
        {
        dataOutput.writeInt(value);
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

        if (dret.type != DecodeReturn.T_INT) // uh oh!
            {
            // restore the position and the string; it was an error
            dret.data = data;
            dret.pos = pos;
            return false;
            }

        // store the data
        value  = (int)(dret.l);
        return true;
        }

    public String name() { return "interc"; }

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
         pd.number = value;
        }
}
