package beans;

import dominio.AtividadeModelo;
import dominio.Processo;
import excecao.ExcecaoNegocio;
import excecao.MensagemExcecao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.validation.ConstraintViolationException;
import org.primefaces.event.RowEditEvent;
import servico.ProcessoServico;

@ManagedBean(name = "processoeditarBean")
@ViewScoped
public class ProcessoEditarBean implements Serializable
{

    @EJB
    private ProcessoServico ProcessoServico;

    protected List<Processo> processos = new ArrayList<>();

    protected Processo processo;

    protected AtividadeModelo atividade;

    protected String anexarArquivo;

    private List<AtividadeModelo> atividades = new ArrayList<>();

    public ProcessoEditarBean()
    {
        atividade = new AtividadeModelo();
        processo = new Processo();

    }

    public void salvar()
    {
        try {
            processo.setAtividades(atividades);
            ProcessoServico.salvar(processo);

            adicionarMensagem(FacesMessage.SEVERITY_INFO, "Processo cadastrado com Sucesso!");
        }
        catch (ExcecaoNegocio ex) {
            adicionarMensagem(FacesMessage.SEVERITY_WARN, ex.getMessage());
        }
        catch (EJBException ex) {
            if (ex.getCause() instanceof ConstraintViolationException) {
                MensagemExcecao mensagemExcecao = new MensagemExcecao(ex.getCause());
                adicionarMensagem(FacesMessage.SEVERITY_WARN, mensagemExcecao.getMensagem());
            }
        }
        processo = new Processo();
        listar();
    }

    public void editarAtividades() throws ExcecaoNegocio
    {
        if (!processo.getAtividades().isEmpty()) {
            try {
                ProcessoServico.atualizar(processo);
                adicionarMensagem(FacesMessage.SEVERITY_INFO, "Fluxo do processo alterado com Sucesso!");
                listar();
            }
            catch (ExcecaoNegocio ex) {
                adicionarMensagem(FacesMessage.SEVERITY_WARN, ex.getMessage());
            }
            catch (EJBException ex) {
                if (ex.getCause() instanceof ConstraintViolationException) {
                    MensagemExcecao mensagemExcecao = new MensagemExcecao(ex.getCause());
                    adicionarMensagem(FacesMessage.SEVERITY_WARN, mensagemExcecao.getMensagem());
                }
            }
        }
        else {
            adicionarMensagem(FacesMessage.SEVERITY_INFO, "Devem ser adicionadas atividades ao processo!");
        }
        listar();
    }

    public void editar(RowEditEvent event) throws ExcecaoNegocio
    {
        processo = (Processo) event.getObject();
        listar();

        try {
            ProcessoServico.atualizar(processo);
            adicionarMensagem(FacesMessage.SEVERITY_INFO, "Processo alterado com Sucesso!");
            listar();
        }
        catch (ExcecaoNegocio ex) {
            adicionarMensagem(FacesMessage.SEVERITY_WARN, ex.getMessage());
        }
        catch (EJBException ex) {
            if (ex.getCause() instanceof ConstraintViolationException) {
                MensagemExcecao mensagemExcecao = new MensagemExcecao(ex.getCause());
                adicionarMensagem(FacesMessage.SEVERITY_WARN, mensagemExcecao.getMensagem());
            }
        }
        listar();
    }

    public void remover(Processo processo)
    {
        try {
            ProcessoServico.remover(processo);
            adicionarMensagem(FacesMessage.SEVERITY_INFO, "Processo removido com Sucesso!");

        }
        catch (EJBException ex) {
            if (ex.getCause() instanceof ConstraintViolationException) {
                MensagemExcecao mensagemExcecao = new MensagemExcecao(ex.getCause());
                adicionarMensagem(FacesMessage.SEVERITY_WARN, mensagemExcecao.getMensagem());
            }
        }
        listar();
    }

    public void listar()
    {
        processos = ProcessoServico.listar();
    }

    public ProcessoServico getProcessoServico()
    {
        return ProcessoServico;
    }

    public void setProcessoServico(ProcessoServico ProcessoServico)
    {
        this.ProcessoServico = ProcessoServico;
    }

    public List<Processo> getProcessos()
    {
        return processos;
    }

    public void setProcessos(List<Processo> Processos)
    {
        this.processos = Processos;
    }

    public Processo getProcesso()
    {
        if (processo.getAtividades() == null || processo.getAtividades().isEmpty()) {
            processo = (Processo) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("processo_atualizar");
            setAtividades(processo.getAtividades());
        }
        return processo;
    }

    public void setProcesso(Processo Processo)
    {
        this.processo = Processo;
    }

    protected void adicionarMensagem(FacesMessage.Severity severity, String mensagem)
    {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(severity, mensagem, null));
    }

    public void adicionarAtividadeNoProcesso()
    {
        boolean encontrou = false;

        for (AtividadeModelo atividade_atual : this.atividades) {
            if (atividade_atual.getNome().equals(this.atividade.getNome())) {
                encontrou = true;
                adicionarMensagem(FacesMessage.SEVERITY_INFO, "Esse registro já existe, tente outro!");
                break;
            }
        }
        atividade.setAnexarArquivo(return_anexarAquivo());
        if (encontrou == false) {
            this.atividades.add(atividade);
        }
        atividade = new AtividadeModelo();
    }

    public void removerAtividadeDoProcesso(AtividadeModelo atividade)
    {
        for (int i = 0; i < this.atividades.size(); i++) {
            if (this.atividades.get(i).getNome().equals(atividade.getNome())) {
                this.atividades.remove(i);
                adicionarMensagem(FacesMessage.SEVERITY_INFO, "Atividade removida com Sucesso!");
                break;
            }
        }
    }

    public void editarAtividadeLista(AtividadeModelo atividade)
    {   
        atividade.setAnexarArquivo(return_anexarAquivo());
        for (int i = 0; i < this.atividades.size(); i++) {
            if (this.atividades.get(i).getNome().equals(atividade.getNome())) {
                this.atividades.get(i).setNome(atividade.getNome());
                this.atividades.get(i).setDepartamento(atividade.getDepartamento());
                this.atividades.get(i).setAnexarArquivo(atividade.isAnexarArquivo());

                adicionarMensagem(FacesMessage.SEVERITY_INFO, "Atividade alterada com Sucesso!");
                break;
            }
        }
    }

    public AtividadeModelo getAtividade()
    {
        return atividade;
    }

    public void setAtividade(AtividadeModelo atividade)
    {
        this.atividade = atividade;
    }

    public List<AtividadeModelo> getAtividades()
    {
        Processo processo_aux;

        processo_aux = (Processo) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("processo_atualizar");
        if (processo_aux != null) {
            processo = processo_aux;
            atividades = processo.getAtividades();
        }

        return atividades;
    }

    public void setAtividades(List<AtividadeModelo> atividades)
    {
        this.atividades = atividades;
    }

    public boolean return_anexarAquivo()
    {
        if (anexarArquivo.equals("sim")) {
            return true;
        }
        else {
            return false;
        }
    }

    public String visualizar_anexarAquivo(AtividadeModelo atividade_atual)
    {
        if (atividade_atual.isAnexarArquivo()) {
            return "Sim";
        }
        else {
            return "Não";
        }
    }
   
    public String getAnexarArquivo()
    {
        return anexarArquivo;
    }

    public void setAnexarArquivo(String anexarArquivo)
    {
        this.anexarArquivo = anexarArquivo;
    }
}
