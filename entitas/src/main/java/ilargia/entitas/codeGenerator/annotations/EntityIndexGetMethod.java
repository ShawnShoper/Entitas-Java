package ilargia.entitas.codeGenerator.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;


@Retention(RetentionPolicy.SOURCE)
@Target({METHOD})
public @interface EntityIndexGetMethod {

}