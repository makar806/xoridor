package org.xoridor.net;

public class DisconnectFromCommunityCommand extends AbstractCommunityCommand {
    public DisconnectFromCommunityCommand(ConnectToCommunityCommand ctc) {
        super();
        this.ctc = ctc;
    }
    
    protected String getRelativeUrl() {
        return DISCONNECT_URL;
    }
    
    protected void addParameters(StringBuffer parameters) {
        super.addParameters(parameters);
        addParameter(parameters, "id", new Integer(ctc.getId()).toString());
        addParameter(parameters, "user", ctc.getUser());
    }
    
    private ConnectToCommunityCommand ctc;
    static final String DISCONNECT_URL = "disconnect.php";
}