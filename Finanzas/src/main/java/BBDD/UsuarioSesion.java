package BBDD;

//@author AlejandroGpublic

public class UsuarioSesion {
private static Usuario usuarioactual;

    private static String mesSeleccionado;


public static Usuario getUsuariosesion() {
    return usuarioactual;
}

public static void setUsuariosesion(Usuario usuario) {
    UsuarioSesion.usuarioactual = usuario;

}
    public static void setMesSeleccionado(String mes) {
        mesSeleccionado = mes;
    }

    public static String getMesSeleccionado() {
        return mesSeleccionado;
    }

}
