package org.xoridor.ui.cmd;

import javax.swing.SwingUtilities;

import org.xoridor.net.ConnectToCommunityCommand;
import org.xoridor.net.NetworkSetupState;
import org.xoridor.ui.Controller;
import org.xoridor.ui.net.CommunityWizard;

public class NewCommunityGameCommand extends UICommand {
    public NewCommunityGameCommand(Controller c) {
        super(c);
    }

    public void execute() {
        c.getOptions().setTwoPlayers();
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                ConnectToCommunityCommand ctc = new ConnectToCommunityCommand();
                new CommunityWizard(c, ctc);
                newGame();
                c.getGame().setState(new NetworkSetupState(c.getGame()));        
            }
        });
    }
}