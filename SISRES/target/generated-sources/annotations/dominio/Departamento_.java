package dominio;

import dominio.Responsavel;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-10-12T17:05:04")
@StaticMetamodel(Departamento.class)
public class Departamento_ extends EntidadeNegocio_ {

    public static volatile SingularAttribute<Departamento, String> sigla;
    public static volatile SingularAttribute<Departamento, String> nome;
    public static volatile SingularAttribute<Departamento, Responsavel> responsavel;

}