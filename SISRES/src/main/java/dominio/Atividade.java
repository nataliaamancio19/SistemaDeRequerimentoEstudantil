package dominio;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
public class Atividade extends EntidadeNegocio implements Serializable {

    @NotNull
    @Size(min = 2, max = 60)
    @Pattern(regexp = "\\p{Upper}{1}\\p{Lower}+")
    private String nome;
    
    @NotNull
    @Enumerated(EnumType.STRING)
    private SituacaoAtividade situacao;
    
    @NotNull()
    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_departamento", referencedColumnName = "id")
    private Departamento departamento;

    // Anexos?
   
    public Atividade() { }
    
    public Atividade(String nome, SituacaoAtividade situacao, Departamento departamento, Atividade proximaAtividade)
    {
        this.nome = nome;
        this.situacao = situacao;
        this.departamento = departamento;
    }
   
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    public SituacaoAtividade getSituacao() {
        return situacao;
    }

    public void setSituacao(SituacaoAtividade situacao) {
        this.situacao = situacao;
    }

}

