package sample;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Base64;


public class SMTP {

    private int SMTP_PORT = 25;
    InetAddress mailHost;
    InetAddress localhost;
    BufferedReader in;
    PrintWriter out;
    String log;
    String logFile;

    public int getSMTP_PORT() {
        return SMTP_PORT;
    }

    public void setSMTP_PORT(int SMTP_PORT) {
        this.SMTP_PORT = SMTP_PORT;
    }

    public String getLogFile() {
        return logFile;
    }

    public void setLogFile(String logFile) {
        this.logFile = logFile;
    }

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
            log="mailhost = " + mailHost+"\nlocalhost= " + localhost;
            System.out.println("mailhost = " + mailHost);
            System.out.println("localhost= " + localhost);
            System.out.println("SMTP constructor done\n");
    }

    public void saveToFile(String path){
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {
            bw.write(log);
        }
        catch (IOException e){
            System.out.println(e);
        }
    }

    public boolean send(String[] data, String from, String to, String password)
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
        log+=initialID+'\n';
        System.out.println("HELO " + localhost.getHostName());
        out.println("HELO " + localhost.getHostName());
        log+="HELO " + localhost.getHostName()+'\n';
        String welcome = in.readLine();
        System.out.println(welcome);
        log+=welcome+'\n';
        System.out.println("AUTH LOGIN");
        out.println("AUTH LOGIN");
        log+="AUTH LOGIN\n";
        String authLoginOK = in.readLine();
        System.out.println(authLoginOK);
        log+=authLoginOK+"\n";
        //String login = from.substring(0,from.indexOf('@'));
        String encodedLogin = Base64.getEncoder().encodeToString(from.getBytes());
        System.out.println(encodedLogin);
        out.println(encodedLogin);
        log+=encodedLogin+"\n";
        String loginOK = in.readLine();
        System.out.println(loginOK);
        log+=loginOK+"\n";
        String encodedPassword = Base64.getEncoder().encodeToString(password.getBytes());
        System.out.println(encodedPassword);
        log+=encodedPassword+"\n";
        out.println(encodedPassword);
        String passOK = in.readLine();
        System.out.println(passOK);
        log+=passOK+"\n";
        //String auth=in.readLine();
        //out.println(in.readLine());
        System.out.println("MAIL From:<" + from + ">");
        out.println("MAIL From:<" + from + ">");
        log+="MAIL From:<" + from + ">"+"\n";
        String senderOK = in.readLine();
        System.out.println(senderOK);
        log+=senderOK+"\n";
        System.out.println("RCPT TO:<" + to + ">");
        out.println("RCPT TO:<" + to + ">");
        log+="RCPT TO:<" + to + ">\n";
        String recipientOK = in.readLine();
        System.out.println(recipientOK);
        log+=recipientOK+"\n";
        System.out.println("DATA");
        out.println("DATA");
        log+="DATA\n";
        for (int i=0;i<data.length;i++){
            System.out.println(data[i]);
            out.println(data[i]);
            log+=data[i]+"\n";
        }
        System.out.println(".");
        out.println(".");
        log+=".\n";
        String acceptedOK = in.readLine();
        System.out.println(acceptedOK);
        log+=acceptedOK+ "\n";
        System.out.println("QUIT");
        out.println("QUIT");
        log+="QUIT\n";
        String quitOK = in.readLine();
        System.out.println(quitOK);
        log+=quitOK+"\n";
        saveToFile(logFile);
        return true;
    }
}


