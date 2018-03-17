package servico;

import dominio.Responsavel;
import excecao.ExcecaoNegocio;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import static javax.ejb.TransactionAttributeType.REQUIRED;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import static javax.ejb.TransactionManagementType.CONTAINER;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

@Stateless
@TransactionAttribute(REQUIRED)
@TransactionManagement(CONTAINER)
public class ResponsavelServico extends Servico
{

    public void salvar(Responsavel responsavel) throws ExcecaoNegocio
    {
        if (chegaExistencia(responsavel) == false) {
            em.persist(responsavel);
        }
        else {
            throw new ExcecaoNegocio(ExcecaoNegocio.OBJETO_EXISTENTE);
        }
    }

    public List<Responsavel> listar()
    {
        em.flush();
        return em.createQuery("select r from Responsavel r", Responsavel.class).getResultList();
    }

    public Responsavel getResponsavelByEmail(String email)
    {
        Responsavel responsavel;
        TypedQuery<Responsavel> query;

        try {
            query = em.createQuery("select r from Responsavel r where r.email = ?1", Responsavel.class);
            query.setParameter(1, email);
            responsavel = query.getSingleResult();
        }
        catch (NoResultException e) {
            responsavel = null;
        }

        return responsavel;
    }
    
     public String buscar_grupo_responsavel(String email)
    {
        String grupoAtual = "nenhum";
        Responsavel responsavel = getResponsavelByEmail(email);

        if (responsavel != null) {

            switch (responsavel.getTitulo()) {

                case Administrador:
                    return "administrador";
                case Aluno:
                    return "aluno";
                default:
                    return "servidor";

            }
        }

        return grupoAtual;
    }

    public boolean chegaExistencia(Responsavel responsavel)
    {
        TypedQuery<Responsavel> query;

        if (responsavel.getId() == null) {
            query = em.createQuery("select r from Responsavel r where r.email = ?1", Responsavel.class
            );
            query.setParameter(1, responsavel.getEmail());

        }
        else {
            query = em.createQuery("select r from Responsavel r where r.email = ?1 and r.id != ?2", Responsavel.class
            );
            query.setParameter(1, responsavel.getEmail());
            query.setParameter(2, responsavel.getId());
        }

        List<Responsavel> responsaveis = query.getResultList();

        return !responsaveis.isEmpty();
    }

    public void remover(Responsavel responsavel)
    {
        Responsavel r = (Responsavel) em.find(Responsavel.class,
                 responsavel.getId());
        em.remove(r);
    }

    public void atualizar(Responsavel responsavel) throws ExcecaoNegocio
    {
        em.flush();
        if (chegaExistencia(responsavel) == false) {
            em.merge(responsavel);
        }
        else {
            throw new ExcecaoNegocio(ExcecaoNegocio.OBJETO_EXISTENTE);
        }
    }

    public List<Responsavel> listar_nao_aprovados_lider()
    {
        em.flush();

        return em.createQuery("select r from Responsavel r where r.aprovado = false and r.lider = true ", Responsavel.class
        ).getResultList();
    }

    public List<Responsavel> listar_nao_aprovados(Responsavel usuario_logado)
    {
        em.flush();
        TypedQuery<Responsavel> query;

        query
                = em.createQuery("select r from Responsavel r where r.aprovado = false", Responsavel.class
                );
       
        List<Responsavel> responsaveis = query.getResultList();

        return responsaveis;

    }

}
