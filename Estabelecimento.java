import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.io.Serializable;

class Estabelecimento implements Serializable {
    private String nome;
    private String descricao;
    private String endereco;
    private boolean ofereceCafeDaManha;
    private boolean ofereceAlmoco;
    private boolean ofereceJantar;
    private ArrayList<Prato> pratos;

    public Estabelecimento(String nome, String descricao, String endereco) {
        this.nome = nome;
        this.descricao = descricao;
        this.endereco = endereco;
        this.pratos = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getEndereco() {
        return endereco;
    }

    public boolean ofereceCafeDaManha() {
        return ofereceCafeDaManha;
    }

    public boolean ofereceAlmoco() {
        return ofereceAlmoco;
    }

    public boolean ofereceJantar() {
        return ofereceJantar;
    }

    public ArrayList<Prato> getPratos() {
        return pratos;
    }


    public void adicionarPrato(Prato prato) {
        // Verifica se o prato já existe na lista de pratos
        if (!pratos.contains(prato)) {
            pratos.add(prato);
        } else {
            System.out.println("O prato já existe no cardápio.");
        }
    }

    @Override
    public String toString() {
        return nome;
    }
}
