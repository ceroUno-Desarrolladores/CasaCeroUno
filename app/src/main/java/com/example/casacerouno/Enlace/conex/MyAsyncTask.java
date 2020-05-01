package com.example.casacerouno.Enlace.conex;

import android.os.AsyncTask;
import android.util.Base64;

import com.example.casacerouno.Enlace.msg;

import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class MyAsyncTask extends AsyncTask<String, Void, String> {

    String host, port, headget, response;
    private int contadorSend = 0;
    String usrhas, dev, acc, val;
    // private onPostExecute FuncionPost;
    private Conexion padre;
    IOException ioException;
    boolean working = false;

    MyAsyncTask(String host, String port, Conexion padre) {
        super();

        this.host = host;
        this.port = port;
        // this.usrhas=usr;
        this.padre = padre;
        this.response = "";
        this.ioException = null;

    }
    public String executePost(){
        // protected String doInBackground(String... params) {
        this.working=true;
        // construyendo el header :
        headget=headGet(host,port,usrhas,dev,acc,val);
        msg.echo("MyAsinc: enviando \r\n"+headget);
        //msg.var_dump(this);
        /*al enviar la ejecuion a segundo plano */
        StringBuilder sb = new StringBuilder();
        try {

            Socket socket = new Socket( host, Integer.parseInt(port));
            OutputStream out = socket.getOutputStream();
            msg.echo("MyAsinc: write-"+contadorSend+"--");
            out.write(headget.getBytes());
            contadorSend++;
            InputStream in = socket.getInputStream();

            /*lectura de respuesta*/
            byte[] buf = new byte[1024];
            int nbytes;
            while ((nbytes = in.read(buf)) != -1) {
                sb.append(new String(buf, 0, nbytes));
            }

            //msg.echo("MyAsinc: READ \r\n"+sb.toString());
            socket.close();

        } catch(IOException e) {
            this.ioException = e;
            this.working=false;
            msg.echo("MyAsinc: falla" + e.getMessage());
            return "{}";
        }
        this.working=false;
        return headRequest( sb.toString() );
        // sb.toString();
    }
    @Override
    protected String doInBackground(String... params) {
        // protected String doInBackground(String... params) {
        this.working=true;
        // construyendo el header :
        headget=headGet(host,port,usrhas,dev,acc,val);
        msg.echo("MyAsinc: enviando \r\n"+headget);
        //msg.var_dump(this);
        /*al enviar la ejecuion a segundo plano */
        StringBuilder sb = new StringBuilder();
        try {

            Socket socket = new Socket( host, Integer.parseInt(port));
            OutputStream out = socket.getOutputStream();
            msg.echo("MyAsinc: write-"+contadorSend+"--");
            out.write(headget.getBytes());
            contadorSend++;
            InputStream in = socket.getInputStream();

            /*lectura de respuesta*/
            byte[] buf = new byte[1024];
            int nbytes;
            while ((nbytes = in.read(buf)) != -1) {
                sb.append(new String(buf, 0, nbytes));
            }

            //msg.echo("MyAsinc: READ \r\n"+sb.toString());
            socket.close();

        } catch(IOException e) {
            this.ioException = e;
            this.working=false;
            msg.echo("MyAsinc: falla" + e.getMessage());
            return "{}";
        }
        this.working=false;
        return headRequest( sb.toString() );
        // sb.toString();
    }
    @Override
    protected void onPostExecute(String result) {
        /*al terminar la ejcucion recibe la respuesta */
        if (this.ioException != null) {
            this.response="error";
        } else {
            // this.textView.setText(result);

            this.response=result;
        }


        //msg.echo("MyAsinc: request-brute:"+result);
        msg.echo("MyAsinc: responde: --\r\n"+response );
        // this.button.setEnabled(true);
        try {
            padre.onPostProcesor(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getResponse(){

        return this.response;
    }


    public String getUsrhas() { return usrhas;}

    public void setUsrhas(String usrhas) {
        this.usrhas = usrhas;

    }
    private String headGet(String host,String port, String usr,String dev,String acc,String val) {
        // cambiamos desitno a la accion necesaria:
        String rt = usr + '/' + dev + '/' + acc + '/' + val;
        // codifcamos cabecera y datos a base64

        String dataToSend = String.format(
                "GET /%s HTTP/1.1\r\n"
                        +"Host: %s:%s\r\n"
                        +"User-Agent: Midori/1.0 \r\n"
                        +"Gecko/20100101 \r\n"
                        +"Accept: */* \r\n"
                        +"Connection: keep-alive \r\n\r\n"
                // encriptacion de datos:
                ,encript(rt)
                // ,proto
                ,host
                ,port
        );


        return dataToSend;
        //return rt;
    }
        private String encript(String t){

            return Base64.encodeToString(t.getBytes(), Base64.CRLF);

        }

        public String headRequest(String code){
            // recibimos el resultado y lo convertimos a texto.
            String rt=code;
            byte[] data = Base64.decode(code, Base64.CRLF);

            rt= new String(data, StandardCharsets.UTF_8);

            // salido de tipo cadena:
            return rt;
        }


    public String getDev() {return dev;}

    public MyAsyncTask setDev(String dev) {this.dev = dev; return this ;}

    public String getAcc() {return acc;}

    public MyAsyncTask setAcc(String acc) {this.acc = acc; return this; }

    public String getVal() {return val;}

    public MyAsyncTask setVal(String val) {this.val = val; return this; }


}

