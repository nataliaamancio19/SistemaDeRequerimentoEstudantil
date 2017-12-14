package dominio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@SequenceGenerator(name = "PROCESSO_SEQUENCE", sequenceName = "PROCESSO_SEQUENCE", allocationSize = 1, initialValue = 1)
public class Processo extends EntidadeNegocio implements Serializable {

    @NotNull(message = "O nome deve ser preenchido.")
    @Size(min = 2, max = 40, message= "O nome deve conter entre 2 à 40 caracteres.")
    @Pattern(regexp = "[A-Za-zà-úÀ-Ú ]+", message = "O nome deve conter apenas letras.")
    private String nome;

    @NotNull(message = "O responsável deve ser preenchido.")
    @OneToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_responsavel", referencedColumnName = "id")
    private Responsavel responsavel;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataDeCriacao;

    @NotNull(message = "O processo deve conter atividades.")
    @OneToMany(mappedBy = "processo", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AtividadeModelo> atividades;
    
    @NotNull
    private int duracaoMaximaEmDias;
    
    public Processo()
    {
        atividades = new ArrayList<>();
    }
    
    public Date getDataDeCriacao() {
        return dataDeCriacao;
    }

    public void setDataDeCriacao(Date dataDeCriacao) {
        this.dataDeCriacao = dataDeCriacao;
    }
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Responsavel getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(Responsavel responsavel) {
        
        this.responsavel = responsavel;
    }

    public List<AtividadeModelo> getAtividades() {
      
        return atividades;
    }

    public void addAtividade(AtividadeModelo atividade)
    {
        atividade.setProcesso(this);
        atividades.add(atividade);
    }
    
    public void setAtividades(List<AtividadeModelo> atividades) 
    {
        for (AtividadeModelo atividade_atual : atividades)
        {
            addAtividade(atividade_atual);
        }
    }

    public int getDuracaoMaximaEmDias() {
        return duracaoMaximaEmDias;
    }

    public void setDuracaoMaximaEmDias(int duracaoMaximaEmDias) {
        this.duracaoMaximaEmDias = duracaoMaximaEmDias;
    }
}
