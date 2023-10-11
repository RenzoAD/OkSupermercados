package com.oksupermercados.vencimientosok.model;

import java.sql.Date;
import java.sql.Timestamp;

public class Vencimiento {
    private Long id;
    private Timestamp fec_reg;
    private String tip_reg;
    private String usuario;
    private String ndocu;
    private String nomprov;
    private String codf;
    private Double cant;
    private Date fec_venc;
    private Boolean active;

    public Vencimiento() {
    }

    public Vencimiento(Long id, Timestamp fec_reg, String tip_reg, String usuario, String ndocu, String nomprov, String codf, Double cant, Date fec_venc, Boolean active) {
        this.id = id;
        this.fec_reg = fec_reg;
        this.tip_reg = tip_reg;
        this.usuario = usuario;
        this.ndocu = ndocu;
        this.nomprov = nomprov;
        this.codf = codf;
        this.cant = cant;
        this.fec_venc = fec_venc;
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getFec_reg() {
        return fec_reg;
    }

    public void setFec_reg(Timestamp fec_reg) {
        this.fec_reg = fec_reg;
    }

    public String getTip_reg() {
        return tip_reg;
    }

    public void setTip_reg(String tip_reg) {
        this.tip_reg = tip_reg;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getNdocu() {
        return ndocu;
    }

    public void setNdocu(String ndocu) {
        this.ndocu = ndocu;
    }

    public String getNomprov() {
        return nomprov;
    }

    public void setNomprov(String nomprov) {
        this.nomprov = nomprov;
    }

    public String getCodf() {
        return codf;
    }

    public void setCodf(String codf) {
        this.codf = codf;
    }

    public Double getCant() {
        return cant;
    }

    public void setCant(Double cant) {
        this.cant = cant;
    }

    public Date getFec_venc() {
        return fec_venc;
    }

    public void setFec_venc(Date fec_venc) {
        this.fec_venc = fec_venc;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
