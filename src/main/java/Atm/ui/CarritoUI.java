package Atm.ui;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public class CarritoUI {
    public static final Target TITULO_CARRITO = Target.the("título de la página del carrito")
            .located(By.tagName("h1"));

    public static final Target FILAS_CARRITO = Target.the("filas de productos en el carrito")
            .located(By.cssSelector("tr.linea-carrito"));

    public static final Target MENSAJE_CARRITO = Target.the("mensaje informativo del carrito")
            .located(By.cssSelector("[data-testid='mensaje-carrito']"));

    public static final Target CARRITO_VACIO = Target.the("mensaje de carrito vacío")
            .located(By.cssSelector("[data-testid='carrito-vacio']"));

    public static final Target CAMPO_CUPON = Target.the("campo de texto para código de cupón")
            .located(By.name("cupon"));

    public static final Target BOTON_APLICAR_CUPON = Target.the("botón aplicar cupón")
            .located(By.id("btn-aplicar-cupon"));

    public static final Target MENSAJE_CUPON = Target.the("mensaje de resultado de cupón")
            .located(By.id("mensaje-cupon"));

    public static final Target CUPON_APLICADO = Target.the("etiqueta de cupón aplicado")
            .located(By.cssSelector("[data-testid='cupon-aplicado']"));

    public static final Target FILA_DESCUENTO = Target.the("fila de descuento en resumen")
            .located(By.cssSelector(".fila-descuento"));

    public static final Target VALOR_DESCUENTO = Target.the("valor monetario del descuento")
            .located(By.cssSelector("[data-testid='descuento']"));

    public static final Target SUBTOTAL = Target.the("subtotal de la compra")
            .located(By.id("subtotal"));

    public static final Target TOTAL_PARCIAL = Target.the("total parcial")
            .located(By.cssSelector(".total-parcial"));

    public static final Target BOTON_IR_A_PAGAR = Target.the("botón ir a pagar")
            .located(By.cssSelector(".btn-pagar"));

    public static final Target BOTON_QUITAR_CUPON = Target.the("botón quitar cupón")
            .located(By.cssSelector("[data-testid='quitar-cupon']"));
}
