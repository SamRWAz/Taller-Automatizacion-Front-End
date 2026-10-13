package Atm.task;

import Atm.ui.CatalogoUI;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Clear;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class AgregarAlCarrito implements Task {

    private final String nombreProducto;
    private final String cantidad;

    public AgregarAlCarrito(String nombreProducto, String cantidad) {
        this.nombreProducto = nombreProducto;
        this.cantidad = cantidad;
    }

    public static AgregarAlCarrito elProducto(String nombreProducto, String cantidad) {
        return instrumented(AgregarAlCarrito.class, nombreProducto, cantidad);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                WaitUntil.the(CatalogoUI.INPUT_CANTIDAD_PRODUCTO.of(nombreProducto), isVisible()).forNoMoreThan(10).seconds(),
                Clear.field(CatalogoUI.INPUT_CANTIDAD_PRODUCTO.of(nombreProducto)),
                Enter.theValue(cantidad).into(CatalogoUI.INPUT_CANTIDAD_PRODUCTO.of(nombreProducto)),
                Click.on(CatalogoUI.BOTON_AGREGAR_PRODUCTO.of(nombreProducto))
        );
    }
}
