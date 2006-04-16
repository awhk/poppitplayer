package simpoppit.gui;
import java.util.Random;
import java.awt.event.*;
import javax.swing.*;
import simpoppit.gameboard.*;

/**
 * 
 */

/**
 * @author Andrew W. Henry
 *
 */

public class GameButton extends JButton implements GameListener, GameConsts, ActionListener{

    public GameButton(GameInterface aGame, String label){
        this.game = aGame;
        super.setText(label);
        super.addActionListener(this);
    }
    
    public void gameEventReceived(GameEvent event){

        if (event.getAction() == "Save"){
            this.game.saveGame("c:\\simpoppit.sav");
        }
        if (event.getAction() == "Load"){
            this.game.loadGame("c:\\simpoppit.sav");
        }
        repaint();
    }
    
    public void actionPerformed(ActionEvent e) {
        this.gameEventReceived(new GameEvent(this, null, super.getText()));
    }

    private GameInterface game;
    static final long serialVersionUID = new Random().nextInt(50000);
}
