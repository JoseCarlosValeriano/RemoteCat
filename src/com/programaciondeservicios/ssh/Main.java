package com.programaciondeservicios.ssh;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Session session = null;
        ChannelExec channel = null;
        Scanner scanner = new Scanner(System.in);

        try{
            System.out.printf("Introduce el nombre de usuario:");
            String username = scanner.nextLine();

            System.out.printf("Introduce la contraseña del usuario:");
            String password = scanner.nextLine();

            session = new JSch().getSession(username, "127.0.0.1", 22);
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();
            channel = (ChannelExec) session.openChannel("exec");

            System.out.println("Conexión remota conectada correctamente");
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
