/*
 * Created on 11-mrt-2006.
 *
 * This software is published under the "GNU General Public
 * license", see http://www.gnu.org/copyleft/gpl.html for 
 * additional information.
 *
 */
package org.xoridor.ui.net;

import org.xoridor.net.CommunityException;
import org.xoridor.net.ConnectToCommunityCommand;
import org.xoridor.net.DisconnectFromCommunityCommand;
import org.xoridor.ui.Controller;
import org.xoridor.ui.DefaultExceptionHandler;
import org.xoridor.util.cmd.DelayedCommand;
import org.xoridor.util.i18n.JFrameTitleI18nListener;
import org.xoridor.util.ui.JWizard;

public class CommunityWizard extends JWizard<CommunityWizard> {
    public CommunityWizard(Controller c, ConnectToCommunityCommand ctc) {
        this.c = c;
        c.getI18n().addI18nListener(new JFrameTitleI18nListener(this, "titleCommunity"));
        addPanel(new CommunityConnectPanel(c, ctc, this));
        pack();
        setLocationRelativeTo(c.getBoardFrame());
        setVisible(true);
    }
    
    @Override
    public void setPanel(int index) {
        // TODO Auto-generated method stub
        super.setPanel(index);
        setLocationRelativeTo(c.getBoardFrame());
    }
    
    public void close(Controller c, ConnectToCommunityCommand ctc) {
//        setVisible(false);
        dispose();
        new DelayedCommand<CommunityException>(new DisconnectFromCommunityCommand(ctc),
                new DefaultExceptionHandler<CommunityException>(c)).execute();
        c.signalInfo("infoCancel");
    }
    
    private Controller c;
}