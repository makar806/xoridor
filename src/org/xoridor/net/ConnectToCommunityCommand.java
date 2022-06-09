package org.xoridor.net;

import org.xoridor.util.xml.ValueNotFoundException;

public class ConnectToCommunityCommand extends AbstractCommunityCommand {
    public ConnectToCommunityCommand() {
    }

    public ConnectToCommunityCommand(String user) throws InvalidNameException {
        setUser(user);
    }
    
    public void setUser(String user) throws InvalidNameException {
        if (isValidName(user))
            this.user = user;
        else
            throw new InvalidNameException();
    }
    
    private boolean isValidName(String name) {
        return name != null &&
            name.toString().length() > 0;
    }

    protected void addParameters(StringBuffer parameters) {
        super.addParameters(parameters);
        addParameter(parameters, "user", user);
    }

    public void execute() throws CommunityException {
        super.execute();
        try {
            id = Integer.parseInt(getXmlProperties().getValue("id"));
        }
        catch (ValueNotFoundException exc) {
            throw new CommunityException(exc);
        }
    }

    protected String getRelativeUrl() {
        return CONNECT_URL;
    }
    
    int getId() {
        return id;
    }
    
    public String getUser() {
        return user;
    }

    public void setUseProxy(boolean useProxy) {
        this.useProxy = useProxy;
    }

    private String user;
    private int id;
    private boolean useProxy;
    static final String CONNECT_URL = "connect.php";
}