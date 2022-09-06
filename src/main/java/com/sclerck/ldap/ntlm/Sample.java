package com.sclerck.ldap.ntlm;

import jcifs.smb.SmbException;

import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import java.net.UnknownHostException;

public class Sample {

    public static void main(String[] args) {
        String server = "ldap.com";
        int port = 389;
        String domain = "DOMAIN";
        String authUser = "authUser";
        String password = "****";

        NtlmAuthentication ntlmAuthentication = new NtlmAuthentication();
        try {
            DirContext dirContext = ntlmAuthentication.login(server, port, domain, authUser, password);

            // Do something with the dirContext
        } catch (NamingException e) {
            System.err.println("General ldap error. " + e.getMessage());
        } catch (UnknownHostException e) {
            System.err.println("No such server address. " + e.getMessage());
        } catch (SmbException e) {
            System.err.println("Invalid credentials. " + e.getMessage());
        }
    }
}
