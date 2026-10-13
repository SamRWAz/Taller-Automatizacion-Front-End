package Atm.Question;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.targets.Target;

public class ValidarLogin implements Question<Boolean> {

    private final Target target;

    public ValidarLogin(Target target) {
        this.target = target;
    }

    public static ValidarLogin esVisible(Target target) {
        return new ValidarLogin(target);
    }

    @Override
    public Boolean answeredBy(Actor actor) {
        return target.resolveFor(actor).isVisible();
    }
}
