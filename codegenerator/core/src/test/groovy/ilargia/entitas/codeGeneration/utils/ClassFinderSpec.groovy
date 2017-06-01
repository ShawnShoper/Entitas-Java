package ilargia.entitas.codeGeneration.utils


import spock.lang.Narrative
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Title

@Narrative("""
Como usuario de la libreria
Quiero poder obtener las clases que existen bajo un package concreto
Para que pueda usarse en la generacion del codigo.
""")
@Title(""" """)
@groovy.transform.TypeChecked
class ClassFinderSpec extends Specification {


    def setupSpec() {


    }


    void 'Buscamos las clases a partir de un paquete base dentro de un jar dentro del classpath'() {

        when: 'buscamos clases recursivamente en un package concreto base'
        List<Class<?>> classes = ClassFinder.findRecursive("org.jboss.forge.roaster.model.util")

        then: 'el resultado de los nombres de contextos debe ser `Core`'
        classes.size() == 97
    }

    void 'Buscamos las clases a partir de un paquete base dentro de un directorio del classpath'() {

        when: 'buscamos clases recursivamente en un package concreto base'
        List<Class<?>> classes = ClassFinder.findRecursive("ilargia.entitas.codeGeneration.data")

        then: 'el resultado de los nombres de contextos debe ser `Core`'
        classes.size() == 5
    }

    void 'Buscamos una clase en concreto dentro del un jar en el classpath'() {

        when:
        Class<?> clazz = ClassFinder.findClass("org.jboss.forge.roaster.model.util.DesignPatterns.class")

        then:
        clazz.getName() == "org.jboss.forge.roaster.model.util.DesignPatterns"
    }

    void 'Buscamos una clase en concreto dentro del classpath'() {

        when:
        Class<?> clazz = ClassFinder.findClass("ilargia.entitas.codeGeneration.data.MethodData.class")

        then:
        clazz.getName() == "ilargia.entitas.codeGeneration.data.MethodData"
    }
}