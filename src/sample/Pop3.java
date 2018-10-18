package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Base64;

public class Pop3 {
    private final static int POP3_PORT = 110;
    BufferedReader in;
    PrintWriter out;
    InetAddress mailHost;
    String[] mailArray;

    public Pop3(String host) throws UnknownHostException {
        mailHost = InetAddress.getByName(host);
        System.out.println("mailhost = " + mailHost);
        System.out.println("Pop3 constructor done\n");
    }


    public boolean auth(String from, String password)
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
        //System.out.println("QUIT");
        //out.println("QUIT");

        return true;
    }

    public ArrayList receiveMessages() throws IOException {
        //out.println("stat");
        //System.out.println("stat");
        out.println("list");
        System.out.println("list");
        String listOK=in.readLine();
        System.out.println(listOK);
        int countMess=getMessagesCount(listOK);
        String tmpStr="";
        while ((tmpStr=in.readLine())!=null){
            if (tmpStr.length()>0){
                if (tmpStr.charAt(0)=='.') {
                    break;
                }
            }
            System.out.println(tmpStr);
        }

        String prev="";
        ArrayList messages=new ArrayList();
        for (int i=0;i<countMess;i++){
            out.println("top "+(i+1)+" "+20);
            System.out.println("top "+(i+1)+" "+20);
            String topOK ="";
            while ((topOK=in.readLine())!=null){
                if (topOK.length()>0){
                    if (topOK.charAt(0)=='.') {
                        break;
                    }
                }
                System.out.println(topOK);
                prev=String.copyValueOf(topOK.toCharArray());
            }
            //System.out.println(topOK);
            messages.add(prev);
        }

        //FXCollections.observableArrayList
        return messages;
    }

    public void quit() throws IOException {
        out.println("quit");
        System.out.println("quit");
        String quitOK = in.readLine();
        System.out.println(quitOK);
    }

    public int getMessagesCount(String listStr){
        String resStr=listStr.substring(listStr.indexOf(' ')+1,listStr.indexOf(' ',listStr.indexOf(' ')+1));
        //System.out.println(resStr);
        return Integer.parseInt(resStr);
    }

    public void deleteMessage(int index) throws IOException{
        out.println("dele "+index);
        System.out.println("dele "+index);
        String deleOK = in.readLine();
        System.out.println(deleOK);
    }

    public ArrayList getFullMessage(int index) throws IOException{
        out.println("retr "+index);
        System.out.println("retr "+index);
        String retrOK=in.readLine();
        System.out.println(retrOK);
        ArrayList fullMessage=new ArrayList();
        String tmpStr="";
        while ((tmpStr=in.readLine())!=null){
            if (tmpStr.length()>0){
                if (tmpStr.charAt(0)=='.') {
                    break;
                }
            }
            System.out.println(tmpStr);
            fullMessage.add(tmpStr);
        }
        return fullMessage;
    }

}
