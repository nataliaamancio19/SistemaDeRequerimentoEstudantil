package dominio;

import dominio.Responsavel;
import dominio.TiposUnidadesOrganizacionais;
import dominio.UnidadeOrganizacional;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-03-04T01:01:09")
@StaticMetamodel(UnidadeOrganizacional.class)
public class UnidadeOrganizacional_ extends EntidadeNegocio_ {

    public static volatile SingularAttribute<UnidadeOrganizacional, UnidadeOrganizacional> mae;
    public static volatile SingularAttribute<UnidadeOrganizacional, TiposUnidadesOrganizacionais> tiposUnidadesOrganizacionais;
    public static volatile SingularAttribute<UnidadeOrganizacional, String> sigla;
    public static volatile SingularAttribute<UnidadeOrganizacional, String> nome;
    public static volatile SingularAttribute<UnidadeOrganizacional, Responsavel> responsavel;

}