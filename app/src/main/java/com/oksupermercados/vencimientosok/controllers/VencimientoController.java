package com.oksupermercados.vencimientosok.controllers;

import android.content.Context;
import com.oksupermercados.vencimientosok.dao.VencimientoDAO;
import com.oksupermercados.vencimientosok.model.Vencimiento;
import java.util.List;

public class VencimientoController {

    private VencimientoDAO vencimientoDAO;

    public VencimientoController(Context context) {
        vencimientoDAO = new VencimientoDAO(context);
    }

    public List<Vencimiento> list(){
        return vencimientoDAO.list();
    }

}
