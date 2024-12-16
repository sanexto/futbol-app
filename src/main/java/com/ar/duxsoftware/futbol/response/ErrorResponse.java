package com.ar.duxsoftware.futbol.response;

public class ErrorResponse {
    private String mensaje;
    private Integer codigo;

    public ErrorResponse() {}

    public ErrorResponse(final String mensaje, final Integer codigo) {
        this.mensaje = mensaje;
        this.codigo = codigo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(final String mensaje) {
        this.mensaje = mensaje;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(final Integer codigo) {
        this.codigo = codigo;
    }
}
