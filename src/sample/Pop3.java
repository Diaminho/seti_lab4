package sample;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class Pop3 {
    private final static int POP3_PORT = 110;
    BufferedReader in;
    PrintWriter out;
    InetAddress mailHost;
    String[] mailArray;
    String log;

    public void saveToFile(String path){
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {
           bw.write(log);
        }
        catch (IOException e){
            System.out.println(e);
        }
    }

    public Pop3(String host) throws UnknownHostException {
        mailHost = InetAddress.getByName(host);
        System.out.println("mailhost = " + mailHost);
        System.out.println("Pop3 constructor done\n");
        log="mailhost = " + mailHost+'\n';
    }


    public boolean auth(String from, String password)
            throws IOException {
        Socket popPipe;
        InputStream inn;
        OutputStream outt;
        popPipe = new Socket(mailHost, POP3_PORT);
        System.out.println("CLIENT PORT: "+popPipe.getLocalPort());
        if (popPipe == null) {
            return false;
        }
        inn = popPipe.getInputStream();
        outt = popPipe.getOutputStream();
        in = new BufferedReader(new InputStreamReader(inn));
        out = new PrintWriter(new OutputStreamWriter(outt), true);
        if (inn == null || outt == null) {
            System.out.println("Failed to open streams to socket.");
            log+="Failed to open streams to socket.\n";
            return false;
        }
        String initialID = in.readLine();
        System.out.println(initialID);
        log+=initialID+'\n';
        System.out.println("user " + from);
        log+="user " + from+'\n';
        out.println("user " + from);
        String userOK = in.readLine();
        System.out.println(userOK);
        log+=userOK+'\n';
        System.out.println("pass " + password);
        out.println("pass " + password);
        log+="pass " + password+'\n';
        String passOK = in.readLine();
        System.out.println(passOK);
        log+=passOK+'\n';
        //System.out.println("QUIT");
        //out.println("QUIT");

        return true;
    }

    public ArrayList receiveMessages() throws IOException {
        //out.println("stat");
        //System.out.println("stat");
        out.println("list");
        log+="list\n";
        System.out.println("list");
        String listOK=in.readLine();
        System.out.println(listOK);
        log+=listOK+'\n';
        int countMess=getMessagesCount(listOK);
        String tmpStr="";
        while ((tmpStr=in.readLine())!=null){
            if (tmpStr.length()>0){
                if (tmpStr.charAt(0)=='.') {
                    break;
                }
            }
            System.out.println(tmpStr);
            log+=tmpStr+'\n';
        }

        String prev="";
        ArrayList messages=new ArrayList();
        for (int i=0;i<countMess;i++){
            out.println("top "+(i+1)+" "+1);
            log+="top "+(i+1)+" "+1+'\n';
            System.out.println("top "+(i+1)+" "+1);
            String topOK ="";
            while ((topOK=in.readLine())!=null){
                if (topOK.length()>0){
                    if (topOK.charAt(0)=='.') {
                        break;
                    }
                }
                System.out.println(topOK);
                log+=topOK+'\n';
                prev=String.copyValueOf(topOK.toCharArray());
            }
            //System.out.println(topOK);
            messages.add(prev);
        }

        return messages;
    }

    public void quit() throws IOException {
        out.println("quit");
        log+="quit\n";
        System.out.println("quit");
        String quitOK = in.readLine();
        System.out.println(quitOK);
        log+=quitOK+'\n';
        //System.out.println("LOG: \n"+log);
        saveToFile("log_pop3.log");
    }

    public int getMessagesCount(String listStr){
        String resStr=listStr.substring(listStr.indexOf(' ')+1,listStr.indexOf(' ',listStr.indexOf(' ')+1));
        //System.out.println(resStr);
        return Integer.parseInt(resStr);
    }

    public void deleteMessage(int index) throws IOException{
        out.println("dele "+index);
        log+="dele "+index+'\n';
        System.out.println("dele "+index);
        String deleOK = in.readLine();
        System.out.println(deleOK);
        log+=deleOK+'\n';

    }

    public ArrayList getFullMessage(int index) throws IOException{
        out.println("retr "+index);
        System.out.println("retr "+index);
        log+="retr "+index+'\n';
        String retrOK=in.readLine();
        System.out.println(retrOK);
        log+=retrOK+'\n';

        ArrayList fullMessage=new ArrayList();
        String tmpStr="";
        while ((tmpStr=in.readLine())!=null){
            if (tmpStr.length()>0){
                if (tmpStr.charAt(0)=='.') {
                    break;
                }
            }
            System.out.println(tmpStr);
            log+=tmpStr+'\n';
            fullMessage.add(tmpStr);
        }
        return fullMessage;
    }

}
