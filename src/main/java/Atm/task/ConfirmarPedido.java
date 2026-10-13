package Atm.task;

import Atm.ui.CheckoutUI;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isClickable;

public class ConfirmarPedido implements Task {

    public static ConfirmarPedido sinDatos() {
        return instrumented(ConfirmarPedido.class);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                WaitUntil.the(CheckoutUI.BOTON_CONFIRMAR, isClickable()).forNoMoreThan(10).seconds(),
                Click.on(CheckoutUI.BOTON_CONFIRMAR)
        );
    }
}
