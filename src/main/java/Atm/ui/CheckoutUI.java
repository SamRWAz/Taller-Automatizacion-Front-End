package Atm.ui;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public class CheckoutUI {
    public static final Target CAMPO_NOMBRE = Target.the("campo nombre completo")
            .located(By.id("nombreCompleto"));

    public static final Target CAMPO_CORREO = Target.the("campo correo electrónico")
            .located(By.name("correo"));

    public static final Target CAMPO_DIRECCION = Target.the("campo dirección")
            .located(By.cssSelector("[data-testid='direccion']"));

    public static final Target CAMPO_CIUDAD = Target.the("campo ciudad")
            .located(By.name("ciudad"));

    public static final Target CAMPO_TELEFONO = Target.the("campo teléfono")
            .located(By.name("telefono"));

    public static final Target BOTON_CONFIRMAR = Target.the("botón confirmar pedido")
            .located(By.id("btn-confirmar"));

    public static final Target MENSAJE_CHECKOUT = Target.the("mensaje general de checkout")
            .located(By.cssSelector("[data-testid='mensaje-checkout']"));

    public static final Target ERROR_CAMPO = Target.the("mensaje de error del campo {0}")
            .locatedBy("//span[@class='error-campo'][@data-campo='{0}']");
}
