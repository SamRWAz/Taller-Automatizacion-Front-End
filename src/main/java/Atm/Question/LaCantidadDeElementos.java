package Atm.Question;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.targets.Target;

public class LaCantidadDeElementos implements Question<Integer> {

    private final Target target;

    public LaCantidadDeElementos(Target target) {
        this.target = target;
    }

    public static LaCantidadDeElementos visibles(Target target) {
        return new LaCantidadDeElementos(target);
    }

    @Override
    public Integer answeredBy(Actor actor) {
        return target.resolveAllFor(actor).size();
    }
}
