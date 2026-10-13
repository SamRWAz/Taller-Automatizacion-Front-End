package Atm.task;

import Atm.ui.CarritoUI;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isClickable;

public class IrACheckout implements Task {

    public static IrACheckout ahora() {
        return instrumented(IrACheckout.class);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                WaitUntil.the(CarritoUI.BOTON_IR_A_PAGAR, isClickable()).forNoMoreThan(10).seconds(),
                Click.on(CarritoUI.BOTON_IR_A_PAGAR)
        );
    }
}
