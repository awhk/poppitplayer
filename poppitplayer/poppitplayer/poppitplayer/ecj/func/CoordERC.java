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

public class CoordERC extends ERC {

    public Coord value;
    
    private int x;
    private int y;
    
    public void setup(final EvolutionState state, final Parameter base)
    {
    super.setup(state,base);
    // figure the coordinate base -- this will break if the underlying
    // base changes, oops
    Parameter newbase = 
        new Parameter(EvolutionState.P_EVALUATOR).push(Evaluator.P_PROBLEM);

    // obviously not using the default base for any of this stuff

    // load our map coordinates
    x = state.parameters.getInt(newbase.push("x"),null,1);
    y = state.parameters.getInt(newbase.push("y"),null,1);

    state.output.exitIfErrors();      
    }

    // for now, this will produce coordinates in the range
    // (0,0)-(6,6)...hopefully I can find a way to make the range
    // match the board size...
    public void resetNode(final EvolutionState state, final int thread)
        { value = new Coord(state.random[thread].nextInt(x), state.random[thread].nextInt(y));}

    public int nodeHashCode()
    {
    // a reasonable hash code
    //return this.getClass().hashCode() + Float.floatToIntBits(value.getX()) + Float.floatToIntBits(value.getY());
        return this.getClass().hashCode() + x*y + y;
    }
    
    public boolean nodeEquals(final GPNode node)
        {
        // check first to see if we're the same kind of ERC -- 
        // won't work for subclasses; in that case you'll need
        // to change this to isAssignableTo(...)
        if (this.getClass() != node.getClass()) return false;
        // now check to see if the ERCs hold the same value
        return (((CoordERC)node).value.equals(value));
        }

    public void readNode(final EvolutionState state, final DataInput dataInput) throws IOException
        {
        byte input[] = new byte[2];
        dataInput.readFully(input);
        value = new Coord(input[0], input[1]);
        }

    public void writeNode(final EvolutionState state, final DataOutput dataOutput) throws IOException
        {
        byte output[] = {(byte)value.getX(), (byte)value.getY()};
        dataOutput.write(output);
        }

    public String encode()
        { return Code.encode(value.toString()); }

    public boolean decode(DecodeReturn dret)
        {
        // store the position and the string in case they
        // get modified by Code.java
        int pos = dret.pos;
        String data = dret.data;

        // decode
        Code.decode(dret);

        if (dret.type != DecodeReturn.T_STRING) // uh oh!
            {
            // restore the position and the string; it was an error
            dret.data = data;
            dret.pos = pos;
            return false;
            }

        // store the data
        value  = new Coord(Integer.valueOf(dret.s.charAt(1)), Integer.valueOf(dret.s.charAt(3)));
        return true;
        }
    public Object clone() {
        CoordERC cloneResult = (CoordERC)super.clone();
        if (this.value == null){
            cloneResult.value = null;
        }else{
            cloneResult.value = (Coord)this.value.clone();
        }
//        cloneResult.x = this.x;
//        cloneResult.y = this.y;
        return cloneResult;
    }

    public String name() { return "coorderc"; }

    public String toStringForHumans()
        { return value.toString(); }

    public void eval(final EvolutionState state,
                     final int thread,
                     final GPData input,
                     final ADFStack stack,
                     final GPIndividual individual,
                     final Problem problem)
        {
        PoppitData pd = ((PoppitData)(input));
         pd.point = value;
         pd.result = false;
        }
}
