package Atm.Question;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.targets.Target;

public class ElEstado implements Question<Boolean> {

    private final Target target;

    public ElEstado(Target target) {
        this.target = target;
    }

    public static ElEstado esVisible(Target target) {
        return new ElEstado(target);
    }

    @Override
    public Boolean answeredBy(Actor actor) {
        return target.resolveFor(actor).isVisible();
    }
}
