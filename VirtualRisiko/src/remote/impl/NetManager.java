package remote.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class NetManager {

    private static final String URL = "http://www.showmyip.com/simple/";

    public static String findMeExternalIP(){

    String ip = null;

    try {

        URL page = new URL(URL);

        URLConnection yc = page.openConnection();

        BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));

        ip = in.readLine();

        in.close();

    } catch (IOException ex) {

        return null;

    }

    return ip;

    }

    public static void main(String[] args){

        NetManager netManager = new NetManager();
        System.out.println("IP esterno: "+netManager.findMeExternalIP());
    }
}