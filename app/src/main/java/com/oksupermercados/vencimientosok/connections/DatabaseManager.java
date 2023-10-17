package com.oksupermercados.vencimientosok.connections;
import android.content.Context;
import android.os.StrictMode;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;


public class DatabaseManager {
    private static Connection conn;

    public static Connection getConn(Context context) {

        if (conn == null){
            try {
                Class.forName("net.sourceforge.jtds.jdbc.Driver");
                String url = "jdbc:jtds:sqlserver://192.168.124.136;databaseName=OK01;user=sa;password=nava2012;";
                conn = DriverManager.getConnection(url);
                Toast.makeText(context, "Conexión establecida.", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        return conn;
    }

}
