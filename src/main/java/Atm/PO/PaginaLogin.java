package Atm.PO;

import Atm.ui.LoginUI;
import net.serenitybdd.screenplay.targets.Target;

public class PaginaLogin {
    public static final Target usuario = LoginUI.CAMPO_USUARIO;
    public static final Target password = LoginUI.CAMPO_CLAVE;
    public static final Target botonIniciarSesion = LoginUI.BOTON_INGRESAR;
    public static final Target mensajeError = LoginUI.MENSAJE_ERROR;
}
