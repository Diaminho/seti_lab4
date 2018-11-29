package sample;

import sun.misc.IOUtils;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.lang.Character.isDigit;

public class FTP {
    private int FTP_PORT = 110;
    private int datePort;
    private Socket socket = null;
    BufferedReader in;
    PrintWriter out;
    InetAddress server;
    String log;
    String logFile;
    String answer;
    List<Integer> flags=new ArrayList<>();

    public List getFlags() {
        return flags;
    }

    public int getFTP_PORT() {
        return FTP_PORT;
    }

    public void setFTP_PORT(int FTP_PORT) {
        this.FTP_PORT = FTP_PORT;
    }

    public String getLogFile() {
        return logFile;
    }

    public void setLogFile(String logFile) {
        this.logFile = logFile;
    }

    public void saveToFile(String path){
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {
           bw.write(log);
        }
        catch (IOException e){
            System.out.println(e);
        }
    }

    public FTP(String host) throws UnknownHostException {
        server = InetAddress.getByName(host);
        System.out.println("server = " + server);
        System.out.println("FTP constructor done\n");
        log="server = " + server +'\n';
    }


    private boolean openConnection(InetAddress server,int port) throws IOException {
        InputStream inn;
        OutputStream outt;

        socket = new Socket(server, port);

        System.out.println("CLIENT PORT: "+socket.getLocalPort());
        if (socket == null) {
            return false;
        }
        inn = socket.getInputStream();
        outt = socket.getOutputStream();

        in = new BufferedReader(new InputStreamReader(inn));
        out = new PrintWriter(new OutputStreamWriter(outt), true);

        if (inn == null || outt == null) {
            System.out.println("Failed to open streams to socket.");
            log+="Failed to open streams to socket.\n";
            return false;
        }

        return true;
    }

    public boolean auth(String from, String password)
            throws IOException {
        if (!openConnection(server,FTP_PORT)){
            return false;
        }
        String initialID = in.readLine();
        System.out.println(initialID);
        log+=initialID+'\n';
        System.out.println("user " + from);
        log+="user " + from+'\n';
        out.println("user " + from);
        answer = in.readLine();
        System.out.println(answer);
        log+=answer+'\n';
        System.out.println("pass " + password);
        out.println("pass " + password);
        log+="pass " + password+'\n';
        answer = in.readLine();
        System.out.println(answer);
        log+=answer+'\n';
        //System.out.println("QUIT");
        //out.println("QUIT");

        return true;
    }

    public List getFileList() throws IOException {
        doPASV();
        Socket dataSocket = new Socket(server, datePort);
        out.println("LIST");

        BufferedInputStream input = new BufferedInputStream(dataSocket.getInputStream());
        System.out.println("LIST");
        log+="LIST"+'\n';
        System.out.println(in.readLine());

        byte[] b = IOUtils.readFully(input,-1, true);
        String files=new String(b);
        System.out.println(files);
        input.close();
        List fileList=getFileList(files);
        String resLIST=in.readLine();
        //System.out.println(resLIST);
        log+=resLIST;

        //get List files or folders
        setFlagsList(doSplitString(files));
        return fileList;
    }

    public void quit() throws IOException {
        out.println("quit");
        log+="quit\n";
        System.out.println("quit");
        answer = in.readLine();
        System.out.println(answer);
        log+=answer+'\n';
        //System.out.println("LOG: \n"+log);
        saveToFile(logFile);
    }


    public void deleteFile(String fileName) throws IOException{
        //Delete File
        out.println("dele "+fileName);
        log+="dele "+fileName+'\n';
        System.out.println("dele "+fileName);
        answer = in.readLine();
        System.out.println(answer);
        log+=answer+'\n';
        //Need to Add Delete Directory
    }

    private List doSplitString(String str){
        List<String> list=new ArrayList<>();
        for (String ret:str.split("\n")){
            list.add(ret);
        }
        return list;
    }

    private void setFlagsList(List<String> list){
        //ADD ../ to flags
        flags.clear();
        flags.add(1);
        for (String ret:list){
            if(ret.length()>0) {
                if (ret.charAt(0) == '-') {
                    flags.add(0);
                } else {
                    flags.add(1);
                }
            }
        }
    }

    public List getFileList(String response) {
        List listOfStr=doSplitString(response);
        List fileList=new ArrayList();
        //ADD ../
        fileList.add("../");
        for (Object ret:listOfStr){
            if(getFileName((String)ret)!=null) {
                fileList.add(getFileName((String)ret));
            }
        }
        for (Object ret:fileList){
            System.out.println(ret);
        }
        return fileList;
    }

    private String getFileName(String str){
        if (str.lastIndexOf(" ")<0){
            return null;
        }
        if (str.indexOf("\r")<0){
            return null;
        }
        else
            return str.substring(str.lastIndexOf(" ")+1,str.indexOf("\r"));
    }

    private int getDataPort(String response){
        String port="";
        int res=0;
        for (int i=response.indexOf(')')-1;i>response.substring(0,response.lastIndexOf(',')).lastIndexOf(',');i--){
            if (isDigit(response.charAt(i))) {
                port = port + response.charAt(i);
            }
            else {
                res=res+Integer.parseInt(new StringBuilder(port).reverse().toString());
                port="";
                //System.out.println("res: "+res);
            }
        }
        res=res+Integer.parseInt(new StringBuilder(port).reverse().toString())*256;
        //System.out.println("port: "+Integer.parseInt(new StringBuilder(port).reverse().toString())*256);
        return res;
    }

    public void downloadFile(String fileName, String outputName) throws IOException {
        doPASV();
        Socket dataSocket = new Socket(server, datePort);
        out.println("RETR "+fileName);
        System.out.println("RETR "+fileName);
        log+="RETR "+fileName+'\n';


        BufferedInputStream input = new BufferedInputStream(dataSocket.getInputStream());
        answer=in.readLine();
        System.out.println(answer);
        log+=answer+'\n';

        byte[] b = IOUtils.readFully(input,-1, true);
        String file=new String(b);
        System.out.println(file);
        input.close();

        FileOutputStream stream = new FileOutputStream(outputName);
        try {
            stream.write(b);
        } finally {
            stream.close();
        }

        answer=in.readLine();
        System.out.println(answer);
        log+=answer+'\n';
    }


    public void uploadFile(String fileName) throws IOException {
        doPASV();
        Socket dataSocket = new Socket(server, datePort);
        String path=getCurrentDir();
        path=path+fileName.substring(fileName.lastIndexOf("/"));
        out.println("STOR "+path);
        System.out.println("STOR "+path);
        log+="STOR "+path+'\n';

        BufferedOutputStream output = new BufferedOutputStream(dataSocket.getOutputStream());
        answer=in.readLine();
        System.out.println(answer);
        log+=answer+'\n';

        byte[] b = Files.readAllBytes(new File(fileName).toPath());
        //String file=new String(b);
        output.write(b);
        //System.out.println();
        output.close();

        answer=in.readLine();
        System.out.println(answer);
        log+=answer+'\n';
    }

    private void doPASV() throws IOException {
        System.out.println("PASV");
        log+="PASV"+'\n';
        out.println("PASV");
        answer=in.readLine();
        System.out.println(answer);
        log+=answer+'\n';
        datePort= getDataPort(answer);
    }

    public void renameFileOrDir(String fileName, String outputName) throws IOException {
        //doPASV(); RNFR и RNTO
        System.out.println("RNFR "+fileName);
        log+="RNFR "+fileName+'\n';
        out.println("RNFR "+fileName);
        answer=in.readLine();
        System.out.println(answer);
        log+=answer+'\n';

        System.out.println("RNTO "+outputName);
        log+="RNTO "+outputName+'\n';
        out.println("RNTO "+outputName);
        answer=in.readLine();
        System.out.println(answer);
        log+=answer+'\n';
    }

    public void deleteFolder(String path) throws IOException {
        System.out.println("RMD "+path);
        log+="RMD "+path+'\n';
        out.println("RMD "+path);
        answer=in.readLine();
        System.out.println(answer);
        log+=answer+'\n';
    }

    public String getCurrentDir() throws IOException {
        System.out.println("PWD");
        log+="PWD"+'\n';
        out.println("PWD");
        answer=in.readLine();
        System.out.println(answer);
        log+=answer+'\n';
        return answer.substring(answer.indexOf('"')+1,answer.lastIndexOf('"'));
    }

    public void changeDir(String newDir) throws IOException {
        System.out.println("CWD "+newDir);
        log+="CWD "+newDir+'\n';
        out.println("CWD "+newDir);
        answer=in.readLine();
        System.out.println(answer);
        log+=answer+'\n';
    }

    public void createDir(String newDir) throws IOException {
        System.out.println("MKD "+newDir);
        log+="MKD "+newDir+'\n';
        out.println("MKD "+newDir);
        answer=in.readLine();
        System.out.println(answer);
        log+=answer+'\n';
    }
}
