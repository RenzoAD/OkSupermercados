package com.oksupermercados.vencimientosok.dao;

import android.content.Context;
import android.widget.Toast;

import com.oksupermercados.vencimientosok.connections.DatabaseManager;
import com.oksupermercados.vencimientosok.model.Vencimiento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VencimientoDAO {
    private static final String NAME_TABLE = "OK01.dbo.vencimiento";
    private static final String ID = "id";
    private static final String FECHA_REG = "fec_reg";
    private static final String TIPO_REG = "tip_reg";
    private static final String USUARIO = "usuario";
    private static final String NDOCU = "ndocu";
    private static final String NOM_PROV = "nomprov";
    private static final String CODF = "codf";
    private static final String CANTIDAD = "cant";
    private static final String FECHA_VENC = "fec_venc";
    private static final String ACTIVE = "active";

    private Connection conn;
    private Context context;

    public VencimientoDAO(Context context) {
        this.context = context;
        this.conn = DatabaseManager.getConn(this.context);
    }

    public List<Vencimiento> list(){
        String query = "SELECT * FROM " + NAME_TABLE;
        List<Vencimiento> obj = new ArrayList<>();
        try(PreparedStatement pstm = conn.prepareStatement(query)){
            try(ResultSet rs = pstm.executeQuery()){
                while (rs.next()){
                    obj.add(new Vencimiento(rs.getLong(ID),
                            rs.getTimestamp(FECHA_REG),
                            rs.getString(TIPO_REG),
                            rs.getString(USUARIO),
                            rs.getString(NDOCU),
                            rs.getString(NOM_PROV),
                            rs.getString(CODF),
                            rs.getDouble(CANTIDAD),
                            rs.getDate(FECHA_VENC),
                            rs.getBoolean(ACTIVE))) ;
                }
            }
        } catch (SQLException e) {
            Toast.makeText(context, "Error al cargar datos.", Toast.LENGTH_SHORT).show();
        }
        return obj;
    }
}
