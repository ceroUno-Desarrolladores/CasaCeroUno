package com.example.casacerouno.Enlace.conex;
// package com.example.testconexion;

import android.content.Context;
import android.net.DhcpInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import com.example.casacerouno.Enlace.msg;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.concurrent.ExecutionException;

public class Conexion {
    private String url;


    private String proto, host, port, dev, acc, val; //protocolo,host,puerto,
    // disopitivo
    // accion
    // valor
    private int status;
    private String user, has ;
    private onPostExecute FuncionAEjecutar;
    private boolean bol_user =false,bol_has=false;
    private MyAsyncTask _MAsync;
    private Context Contexto;
    private WifiManager wifi;


    /*objeto especial para procesar respuestas */
    //private resultado rts;

    public Conexion(Context Contexto) {
        /*
        * TODO: clase de coneccion se requiere :
            url conformada por <protocolo>://<host>:<port>

        */


        // funcion de prueba de coneccion.
        this.Contexto = Contexto;

        // this.setUrl(url);
        // this._MAsync = new MyAsyncTask(host, port, usrhas);

        // host="192.168.155.10";
        port="8181";

        // entre otras cosas buscar la raspberry:



        //
        // prueba exitosa enciende una luz con el usuario leandro morala.
        //
        // this.test();

    }

    public Conexion send(String dev, String acc, String val){
        send(dev,acc,val,new onPostExecute() {
            @Override
            public void recibirTexto(String txt, int est) {
            }
        });
        return this;
    }

    //envia la peticion al remoto
    private Conexion sendExterno(String dev, String acc, String val, onPostExecute EjecutarDespues) {
            this._MAsync = new MyAsyncTask("cerouno.com.ar/app.php?g=" ,"80", this);
            this._MAsync.setUsrhas(this.has);

            _MAsync
                    .setDev(dev)
                    .setAcc(acc)
                    .setVal(val)
                    .execute();
            Log.i("SEND-EXTERNO-->", dev+"-"+acc+"-"+val);
            FuncionAEjecutar = EjecutarDespues;
        return this;
    }

    public Conexion send(String dev, String acc, String val, final onPostExecute EjecutarDespues) {
        // alg oooo par hacer.
        if (_MAsync == null) {
            // primera vez obteniendo informacion de contexto

            if (host==null || host=="" ) {
                msg.echo("buscar raspberry....");
                final String d =dev;
                final String a =acc;
                final String v =val;

                ScanRaspberry("4net-core"
                        , port
                        , "_domotica._tcp",EjecutarDespues, new onScanError() {
                            @Override
                            public void ejecutarFalla(String txt){
                                // sin encontrar la raspberry....
                                sendExterno(d,a,v,EjecutarDespues);
                            }
                });
            }else {
                // no se ejecuta si no tengo el host
                this._MAsync = new MyAsyncTask(host, port, this);
                this._MAsync.setUsrhas(this.has);

                _MAsync
                        .setDev(dev)
                        .setAcc(acc)
                        .setVal(val)
                        .execute();

                FuncionAEjecutar = EjecutarDespues;
            }
        }/*else{
                    Toast.makeText(Contexto
                            , "Hay una tecla funcionando aún."
                            , Toast.LENGTH_LONG).show();
        } */
        // _MAsync.execute();// */
        Log.i("CONEXION SEND -------", dev+" --- "+acc+" --- "+val);
        return this;
    }



    public Conexion setUser(String usuario){
        this.user = usuario;
        bol_user=true;

        return this;
    }
    public Conexion setHash(String hash){
        this.has=hash;
        bol_has=true;

        return this;
    }

    /***
     * return int ( 0=falla no hay conecion)
     * (1=falla no autorizado)
     * (2=ok)
     * */

    public int getStatus(){
        return getStatus(new onPostExecute() {
            @Override
            public void recibirTexto(String txt, int est) {
                // no hay accion asociada al recibir texto
                int estado = est;
            }
        });
    }

    public int getStatus(onPostExecute EjecutarDespues){
        status=0;
        if ( ( bol_user && bol_has ) && ( getWIFIStatus() == 1 ) ){
            status=1;
            /*
            if (_MAsync == null) {
                // primera vez obteniendo informacion de contexto
                ScanRaspberry("4net-core", "8181","_domotica._tcp");
                this._MAsync = new MyAsyncTask(host, port, this);
                this._MAsync.setUsrhas(this.has);
            }

            _MAsync.setDev("GP0")
                    .setAcc("R")
                    .setVal("0")
                    .execute();

            FuncionAEjecutar=EjecutarDespues;
            */
            send("GP0","R","0",EjecutarDespues);

        }
        return status;
    }


    private boolean validarLogin(String rtUsr){
        return user == rtUsr;
    }

    public Conexion setUrl(String url){
        // forma del url:
        // <prot>://<host>:<port>
        String[] datos = url.split(":");
        proto=datos[0];
        host=datos[1].replace("/","");
        port=datos[2];
        this.url = url;
        return this;

        // {proto,host,port} = url.split(':');
    }

    public String  receive(){

        String rt= null;
        try {
            rt = _MAsync.get();
        } catch (
                ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // msg.echo("datos recibidos fin de espera.:"+rt);
        try {
            rts = new resultado(new JSONObject(rt));
            //msg.var_dump(rts);

        } catch (
                JSONException e) {
            rts = new resultado(new JSONObject());
            e.printStackTrace();
        }

        return rt;
    }

    /* objeto especial para procesar respuestas inicio */
    private resultado rts;

    /* objeto especial para procesar respuestas fin */

    protected void onPostProcesor(String respuesta) throws JSONException {
        // procesando respuesta recibida.
        receive();
        String rtspost="";
        // deve ejecutar la funcion asignada ( si existiera )
        if (rts.staus.compareToIgnoreCase("ok")==0 ) {
            // validar usuario en cada peticion.
            status = 2;
        }else
            status = 1;

        try{
            if (!(((JSONObject) rts.respuesta).isNull("shell"))) {
                rtspost = ((JSONObject) rts.respuesta).opt("shell").toString();
            }
        } catch (NullPointerException n){
            rtspost = "";
        }

        FuncionAEjecutar.recibirTexto(rtspost, status);

        //_MAsync.
        // destruir el conector.
        _MAsync = null;

    }






    /*
     *
     * clase asincronica de comunicacion de datos.
     *
     * */

    /**
     * codificacion de servicios
     */


    public void sendBroadcast(
            final String Usuario, final String Valor
            , final int puerto
            , final onPostExecute Ejecutor
            ,final onScanError Falla
    ) throws Exception {
        InetAddress respuesta = null;
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    msg.echo("enviando datagrama");
                    InetAddress address = getBroadcastAddress(Contexto);
                    msg.echo("broadcast obtenido.");
                    String Cadena = "u:" + Usuario + ":v:" + Valor;
                    byte[] Buf = Cadena.getBytes();


                    msg.echo("verificando.");
                    DatagramSocket clientSocket = new DatagramSocket();
                    clientSocket.setBroadcast(true);

                    msg.echo("preparando envio.");
                    DatagramPacket sendPacket = new DatagramPacket(
                            Buf
                            , Buf.length
                            , address
                            , puerto);

                    msg.echo("enviado.");
                    clientSocket.send(sendPacket);


                    DatagramPacket peticion = new DatagramPacket(Buf, Buf.length);
                    msg.echo("recepcion:");
                    clientSocket.receive(peticion);
                    String txt = new String(peticion.getData());

                    msg.echo("autorizacion:" + txt);
                    setHash(txt.substring(0, 13));
                    setHost(peticion.getAddress().getHostAddress());

                    msg.echo("host:" + peticion.getAddress().getHostAddress());
                    Ejecutor.recibirTexto("broadcast", 1);
                    clientSocket.close();

                } catch (Exception e) {
                    //Logger.getLogger(MainActivity.class.getName()).log(Level.SEVERE, null, e);
                    msg.echo(e.getMessage());
                    Falla.ejecutarFalla(e.getMessage());
                    //Ejecutor.recibirTexto();
                }
            }
        }).start();

    }


    /**
     * TODO: conexion automatica con la raspberry
     */
    public void ScanRaspberry(String Nombre, String Puerto, String BusquedaBonjour, onPostExecute Ejecutor, onScanError Error) {

        // funcion de buqueda del equipo
        // "_domotica._tcp"

        InetAddress test = null;
        boolean ch = false;
        msg.echo("buscando la raspberry pi.............");

        try {
            // solo envia paquete al broadcast
            sendBroadcast(
                    this.user
                    , this.has
                    , 8182, Ejecutor
                    ,Error);//(int) Integer.getInteger(Puerto));
            ch = true;
            msg.echo("se ha enviado el broadcast");
        } catch (Exception e) {
            e.printStackTrace();
            ch = false;
            Error.ejecutarFalla(e.getMessage());
        }


        msg.echo("fin de la busqueda de la raspberry.");

    }

    public void setHost(String host) {
        // la clave deve coincidir con el has de usuario
        this.host = host;
        status = 2;
    }



        /*

        public void execute(String ... params){

            this.onPostExecute(this.doInBackground(params));

        }

        // * */



    // private class NetworkHelper {


    private int getWIFIStatus(){
        InetAddress ch = null;
        try {
            ch = getBroadcastAddress(Contexto);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (ch!=null)
            return 1;
        else
            return 0;


    }
    /**
     * Método que se encarga de obtener la dirección de broadcast de la subred.
     * @return  InetAddres con la dirección de broadcast.
     */
    private static InetAddress getBroadcastAddress(Context context) throws Exception {

        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        DhcpInfo dhcp = wifi.getDhcpInfo();

        int broadcast = (dhcp.ipAddress & dhcp.netmask) | ~dhcp.netmask;
        byte[] quads = new byte[4];
        for (int k = 0; k < 4; k++)
            quads[k] = (byte) (broadcast >> (k * 8));

        return InetAddress.getByAddress(quads);
    }

    /**
     * Método que se encarga de obtener la dirección ip del dispositivo.
     * @return  InetAddres con la dirección ip del dispositivo.
     */
    private static InetAddress getLocalIpAddress(Context context) throws IOException{

        final WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        final ByteBuffer byteBuffer = ByteBuffer.allocate(4);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        byteBuffer.putInt(wifiManager.getConnectionInfo().getIpAddress());
        InetAddress inetAddress = null;
        try {
            inetAddress = InetAddress.getByAddress(null, byteBuffer.array());
        } catch (Exception e) {
            msg.echo("NetworkHelper"+ e.getMessage() );
        }
        return inetAddress;
    }

    //}

}