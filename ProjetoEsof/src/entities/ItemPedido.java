package entities;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ItemPedido {

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    private Integer idPedido;
    private Integer idFilme;
    private Integer quantidade;
    private String dataVencimentoAluguel;

    public ItemPedido(Integer idPedido, Integer idFilme) {
        this.idPedido = idPedido;
        this.idFilme = idFilme;
        this.quantidade = 1;

        iniciaParametrosSistema();
    }

    public Integer getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Integer idPedido) {
        this.idPedido = idPedido;
    }

    public Integer getIdFilme() {
        return idFilme;
    }

    public void setIdFilme(Integer idFilme) {
        this.idFilme = idFilme;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public String getDataVencimentoAluguel() {
        return dataVencimentoAluguel;
    }

    public void iniciaParametrosSistema() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(cal.getTime());
        cal.add(Calendar.DAY_OF_MONTH, 30);
        this.dataVencimentoAluguel = sdf.format(cal.getTime());
    }

}
