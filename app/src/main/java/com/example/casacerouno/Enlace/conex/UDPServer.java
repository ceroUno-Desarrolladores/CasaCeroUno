package com.example.casacerouno.Enlace.conex;

import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UDPServer {
    //servicio de escucha en un puerto.
    private DatagramSocket udpSocket;
    private int port;
    public String host,key;
    private String msg;
    // private Activity activity;

    public UDPServer( int port) throws IOException {
        this.port = port;
        this.udpSocket = new DatagramSocket(this.port);
        /* udpSocket .*/
        //this.activity = activity;
        this.host="";
    }

    public void listen() throws Exception {

        // String msg;

        while (true) {

            byte[] buf = new byte[256];
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            // blocks until a packet is received
            udpSocket.receive(packet);
            // convirtiendo el datagrama en string recibido.
            msg = new String(packet.getData()).trim();
                /*
                if (msg.equals("Okkkk")) {
                    msg = "Message from " + packet.getAddress().getHostAddress() + ": " + msg;
                    final String finalMsg = msg;
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(activity, finalMsg, Toast.LENGTH_SHORT).show();
                        }
                    });

                }
                */
            Log.e("UDP_SERVER", "Message from "
                    + packet.getAddress().getHostAddress()
                    + ": " + msg);
            host=packet.getAddress().getHostAddress();
            key=msg;
        }


    }

}