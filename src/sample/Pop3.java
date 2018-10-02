package sample;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Base64;

public class Pop3 {
    private final static int POP3_PORT = 110;
    BufferedReader in;
    PrintWriter out;
    InetAddress mailHost;

    public Pop3(String host) throws UnknownHostException {
        mailHost = InetAddress.getByName(host);
        System.out.println("mailhost = " + mailHost);
        System.out.println("Pop3 constructor done\n");
    }


    public boolean receive(String from, String password)
            throws IOException {
        Socket smtpPipe;
        InputStream inn;
        OutputStream outt;
        smtpPipe = new Socket(mailHost, POP3_PORT);
        if (smtpPipe == null) {
            return false;
        }
        inn = smtpPipe.getInputStream();
        outt = smtpPipe.getOutputStream();
        in = new BufferedReader(new InputStreamReader(inn));
        out = new PrintWriter(new OutputStreamWriter(outt), true);
        if (inn == null || outt == null) {
            System.out.println("Failed to open streams to socket.");
            return false;
        }
        String initialID = in.readLine();
        System.out.println(initialID);
        System.out.println("user " + from);
        out.println("user " + from);
        String userOK = in.readLine();
        System.out.println(userOK);
        System.out.println("pass " + password);
        out.println("pass " + password);
        String passOK = in.readLine();
        System.out.println(passOK);
        System.out.println("QUIT");
        out.println("QUIT");
        return true;
    }






}
