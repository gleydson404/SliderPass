package vsgdev.com.br.sliderpass;

import android.util.Log;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPSenderThread extends Thread{
    private static UDPSenderThread instance;
    InetAddress server;
    boolean running;
    byte[] buffer;
    DatagramPacket packet;

    public static UDPSenderThread getInstance(){
        if (instance == null){
            instance = new UDPSenderThread();
        }
        return instance;
    }

    private UDPSenderThread(){
        this.running = true;
    }

    public void setServer(InetAddress server){
        this.server = server;
    }

    public void setBuffer(final byte[] buffer){
        this.buffer = buffer;
    }

    @Override
    public void run() {
        Log.i("UDPSenderThread", "starting...");
        while (running){
            if (buffer != null){
                try {
                    packet = new DatagramPacket(buffer, buffer.length, server, 9876);
                    DatagramSocket datagramSocket = new DatagramSocket();
                    datagramSocket.send(packet);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                buffer = null;
            }
        }
        Log.i("UDPSenderThread", "stopping...");
    }
}
