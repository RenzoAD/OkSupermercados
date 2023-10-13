package com.oksupermercados.vencimientosok.connections;
import android.content.Context;
import android.os.StrictMode;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;


public class DatabaseManager {

    public static Connection getConn(Context context) {
        Connection conn = null;
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Class.forName("net.sourceforge.jtds.jdbc.Driver");

            String url = "jdbc:jtds:sqlserver://192.168.44.68;databaseName=OK01;user=sa;password=nava2012;";
            conn = DriverManager.getConnection(url);
            Toast.makeText(context,"Conexión establecida.",Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(context,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
        return conn;
    }

}
