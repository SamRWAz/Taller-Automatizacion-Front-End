package Atm.task;

import Atm.ui.CatalogoUI;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isClickable;

public class IrAlCarrito implements Task {

    public static IrAlCarrito ahora() {
        return instrumented(IrAlCarrito.class);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                WaitUntil.the(CatalogoUI.ENLACE_CARRITO, isClickable()).forNoMoreThan(10).seconds(),
                Click.on(CatalogoUI.ENLACE_CARRITO)
        );
    }
}
