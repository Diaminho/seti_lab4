package sample;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Base64;


public class SMTP {

    private final static int SMTP_PORT = 25;
    InetAddress mailHost;
    InetAddress localhost;
    BufferedReader in;
    PrintWriter out;

    public InetAddress getMailHost() {
        return mailHost;
    }

    public void setMailHost(InetAddress mailHost) {
        this.mailHost = mailHost;
    }

    public InetAddress getLocalhost() {
        return localhost;
    }

    public void setLocalhost(InetAddress localhost) {
        this.localhost = localhost;
    }

    public SMTP(String host) throws UnknownHostException {
            mailHost = InetAddress.getByName(host);
            localhost = InetAddress.getLocalHost();
            System.out.println("mailhost = " + mailHost);
            System.out.println("localhost= " + localhost);
            System.out.println("SMTP constructor done\n");
        }

    public boolean send(String data, String from, String to, String password)
            throws IOException {
        Socket smtpPipe;
        InputStream inn;
        OutputStream outt;
        smtpPipe = new Socket(mailHost, SMTP_PORT);
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
        System.out.println("HELO " + localhost.getHostName());
        out.println("HELO " + localhost.getHostName());
        String welcome = in.readLine();
        System.out.println(welcome);
        System.out.println("AUTH LOGIN");
        out.println("AUTH LOGIN");
        String authLoginOK = in.readLine();
        System.out.println(authLoginOK);
        String login = from.substring(0,from.indexOf('@'));
        String encodedLogin = Base64.getEncoder().encodeToString(login.getBytes());
        System.out.println(encodedLogin);
        out.println(encodedLogin);
        String loginOK = in.readLine();
        System.out.println(loginOK);
        String encodedPassword = Base64.getEncoder().encodeToString(password.getBytes());
        System.out.println(encodedPassword);
        out.println(encodedPassword);
        String passOK = in.readLine();
        System.out.println(passOK);
        //String auth=in.readLine();
        //out.println(in.readLine());
        System.out.println("MAIL From:<" + from + ">");
        out.println("MAIL From:<" + from + ">");
        String senderOK = in.readLine();
        System.out.println(senderOK);
        System.out.println("RCPT TO:<" + to + ">");
        out.println("RCPT TO:<" + to + ">");
        String recipientOK = in.readLine();
        System.out.println(recipientOK);
        System.out.println("DATA");
        out.println("DATA");
        System.out.println(data);
        out.println(data);
        System.out.println(".");
        out.println(".");
        String acceptedOK = in.readLine();
        System.out.println(acceptedOK);
        System.out.println("QUIT");
        out.println("QUIT");
        return true;
    }
}


