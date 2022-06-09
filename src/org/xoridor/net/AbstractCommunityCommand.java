package org.xoridor.net;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;

import org.xoridor.util.VersionProvider;
import org.xoridor.util.VersionProvisionException;
import org.xoridor.util.cmd.HttpCommand;

public abstract class AbstractCommunityCommand extends HttpCommand<CommunityException> {
    public AbstractCommunityCommand() {
        try {
            proxyIp = InetAddress.getLocalHost().getHostAddress();
        }
        catch (UnknownHostException exc) {
        }
    }
    
    protected String getUrl() {
        return COMMUNITY_URL + getRelativeUrl();
    }
    
    protected CommunityException createException(Exception exc) {
        return new CommunityException(exc);
    }

    protected CommunityException createNokException(String id, String message) {
        return new CommunityException(id, message);
    }

    protected String getParameters() {
        StringBuffer result = new StringBuffer();
        addParameters(result);
        return result.toString();
    }

    public InputStream getSessionInputStream() throws MalformedURLException, IOException {
        return getHttpResult(SESSIONS_URL, getParameters());
    }

    protected abstract String getRelativeUrl();

    protected void addParameters(StringBuffer parameters) {
        try {
            addParameter(parameters, "version", new VersionProvider().getVersion());
        }
        catch (VersionProvisionException exc1) {
            exc1.printStackTrace();
        }
        try {
            addParameter(parameters, "ip", proxyIp);
            addParameter(parameters, "orig-ip", InetAddress.getLocalHost().getHostAddress());
            addParameter(parameters, "port", "1099");
            addParameter(parameters, "global", (InetAddress.getLocalHost().isSiteLocalAddress()) ? "0" : "1");
        }
        catch (UnknownHostException exc) {
            exc.printStackTrace();
        }
    }

    public void setProxyIp(String proxyIp) {
        this.proxyIp = proxyIp; 
    }

    private String proxyIp;
    protected static final String COMMUNITY_URL = "http://xoridor.sourceforge.net/community/";
    private static final String SESSIONS_URL = COMMUNITY_URL + "sessions.php";
}