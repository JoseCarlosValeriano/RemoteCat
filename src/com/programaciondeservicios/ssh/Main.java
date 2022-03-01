package com.programaciondeservicios.ssh;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.io.ByteArrayOutputStream;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Session session = null;
        ChannelExec channel = null;
        Scanner scanner = new Scanner(System.in);
        boolean voySalir = true;

        try{
            System.out.printf("Introduce el nombre de usuario:");
            String username = scanner.nextLine();

            System.out.printf("Introduce la contraseña del usuario:");
            String password = scanner.nextLine();

            session = new JSch().getSession(username, "127.0.0.1", 22);
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();


            System.out.println("Conexión remota conectada correctamente");

            while(voySalir){

                System.out.println("Indique 'salir' para abandonar el programa");
                System.out.printf("Indique el nombre del archivo con su extensión:");
                String archivo = scanner.nextLine();

                if(archivo.equals("salir")){
                    voySalir = false;
                    System.out.println("Hasta la próxima!!");
                }else{
                    channel = (ChannelExec) session.openChannel("exec");
                    String comando = "cat /var/log/" + archivo;
                    channel.setCommand(comando);

                    ByteArrayOutputStream responseStream = new ByteArrayOutputStream();
                    channel.setOutputStream(responseStream);
                    channel.connect();

                    while(channel.isConnected()){
                        Thread.sleep(100);
                    }

                    String responseString = new String(responseStream.toByteArray());
                    System.out.println(responseString);
                }



            }







        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (session != null){
                session.disconnect();
            }
            if (channel != null){
                channel.disconnect();
            }
        }



    }
}
