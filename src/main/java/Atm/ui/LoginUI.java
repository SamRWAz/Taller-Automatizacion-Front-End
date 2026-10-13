package Atm.ui;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public class LoginUI {
    public static final Target CAMPO_USUARIO = Target.the("campo de usuario")
            .located(By.id("usuario"));

    public static final Target CAMPO_CLAVE = Target.the("campo de contraseña")
            .located(By.name("clave"));

    public static final Target BOTON_INGRESAR = Target.the("botón de ingreso")
            .located(By.cssSelector("button[type='submit']"));

    public static final Target MENSAJE_ERROR = Target.the("mensaje de error de autenticación")
            .located(By.cssSelector("[data-testid='error-login']"));
}
