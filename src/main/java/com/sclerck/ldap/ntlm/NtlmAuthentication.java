package com.sclerck.ldap.ntlm;

import jcifs.UniAddress;
import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbException;
import jcifs.smb.SmbSession;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.ldap.InitialLdapContext;
import java.net.UnknownHostException;
import java.util.Properties;

public class NtlmAuthentication {

    public DirContext login(String serverName, int port, String domain, String authUser, String password)
            throws NamingException, UnknownHostException, SmbException {

        NtlmPasswordAuthentication ntlmAuth = new NtlmPasswordAuthentication(domain, authUser, password);
        UniAddress dc = UniAddress.getByName(serverName, true);
        SmbSession.logon(dc, ntlmAuth);

        Properties properties = new Properties();
        properties.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        properties.put(Context.PROVIDER_URL,
                    new StringBuilder()
                            .append("ldap://")
                            .append(serverName)
                            .append(":")
                            .append(port));
        properties.put(Context.SECURITY_PRINCIPAL, authUser);
        properties.put(Context.SECURITY_CREDENTIALS, password);
        properties.put(Context.REFERRAL, "follow");
        properties.put("java.naming.ldap.attributes.binary", "objectSID");
        return new InitialLdapContext(properties, null);
    }
}
