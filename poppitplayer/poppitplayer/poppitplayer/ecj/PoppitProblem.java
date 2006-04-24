package poppitplayer.ecj;

import ec.EvolutionState;
import ec.Individual;
import ec.gp.GPIndividual;
import ec.gp.GPProblem;
import ec.gp.koza.KozaFitness;
import ec.simple.SimpleProblemForm;
import ec.util.Parameter;
import simpoppit.gameboard.GameInterface;
import simpoppit.gameboard.Coord;

public class PoppitProblem extends GPProblem implements SimpleProblemForm {

    public GameInterface game;

    public PoppitData gamedata;

    public int perfect;

    // public int penalty;
    
    private static int x;
    private static int y;
    
    public static Coord convertIntCoord(int index, final EvolutionState state){
        if (index <= 0){
            //state.output.warning("Index can't be zero!  Setting to 1.");
            index = 1;
        }
        if (index > (x*y)){
            //state.output.warning("Index can't exceed board size!  Setting to maximum.");
            index = (x*y);
        }
        int myIndex = index;
        int myY = 0;
        int myX = 0;
        while (myIndex > x){
            myIndex -= x;
            myY++;
        }
        myX = myIndex - 1;
        //System.out.println("For " + index + " computed coord (" + myX + "," + myY + ")");
        return new Coord(myX, myY);
    }

    public Object clone() {
        PoppitProblem myobj = (PoppitProblem) (super.clone());
        myobj.game = (GameInterface) (game.clone());
        myobj.gamedata = (PoppitData) this.gamedata.clone();
        return myobj;
    }

    public void setup(final EvolutionState state, final Parameter base) {
        // very important, remember this
        super.setup(state, base);

        x = state.parameters.getInt(base.push("x"), null,
                1);
        y = state.parameters.getInt(base.push("y"), null, 1);
        game = new GameInterface(x, y);
        game.loadGame("c:\\simpoppit.sav");

        gamedata = (PoppitData) state.parameters.getInstanceForParameterEq(base
                .push(P_DATA), null, PoppitData.class);
        gamedata.setup(state, base.push(P_DATA));
    }

    public void evaluate(final EvolutionState state, final Individual ind,
            final int threadnum) {
        if (!ind.evaluated) // don't bother reevaluating
        {
            perfect = 0;
            int loopCount = 0;
            //GameInterface temp = (GameInterface) game.clone();
            //game.restartGame();

            while ((!(game.isGameOver())) && loopCount < (4 * game.getMaxScore())) {
                ((GPIndividual) ind).trees[0].child.eval(state, threadnum,
                        gamedata, stack, ((GPIndividual) ind), this);
                loopCount++;
            }

            int max = game.getMaxScore();
            int popped = game.getScore();

            // the fitness better be KozaFitness!
            KozaFitness f = ((KozaFitness) ind.fitness);
            f.setStandardizedFitness(state, (max - popped));
            f.hits = perfect;
            ind.evaluated = true;
            // if ((max - popped) == 0) {
            // System.out.println(temp.toString());
            // System.out.println("Fitness now " + (max - popped) + ":");
            // System.out.println(game.toString());
            // game.replayGameGUI();
            // }
            //game.restartGame();
            //stack.reset();
        }
    }

    public void describe(final Individual ind, final EvolutionState state,
            final int threadnum, final int log, final int verbosity)

    {
        state.output.println("\n\nBest Individual:\n", verbosity, log);

        state.output.println(game.toString(), verbosity, log);

        int loopCount = 0;
        // evaluate the individual
        while ((!(game.isGameOver())) && loopCount < (4 * game.getMaxScore())) {
            ((GPIndividual) ind).trees[0].child.eval(state, threadnum,
                    gamedata, stack, ((GPIndividual) ind), this);
            loopCount++;
        }

        // print out the game
        state.output.println(game.toString(), verbosity, log);

        game.replayGameGUI();

        // SimPoppitGui gamegui = new SimPoppitGui(game, false);
        // gamegui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // game.restartGame();
        // gamegui.setVisible(true);
        // game.replayGame();

        try {
            Thread.sleep(10000);
        } catch (Exception e) {
            System.out.println("Failed to sleep - " + e);
        }

    }

}
