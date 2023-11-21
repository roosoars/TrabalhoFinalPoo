import javax.swing.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Pedido {
    private String nomeEstabelecimento;
    private String descricaoEstabelecimento;
    private String enderecoEstabelecimento;
    private List<String> pedidos;

    public Pedido(String nomeEstabelecimento, String descricaoEstabelecimento, String enderecoEstabelecimento) {
        this.nomeEstabelecimento = nomeEstabelecimento;
        this.descricaoEstabelecimento = descricaoEstabelecimento;
        this.enderecoEstabelecimento = enderecoEstabelecimento;
        this.pedidos = new ArrayList<>();
    }

    public void adicionarPedido(Estabelecimento estabelecimento, String nomePrato, double valor) {
        pedidos.add("Estabelecimento: " + estabelecimento.getNome() + "\n" +
                "Descrição: " + estabelecimento.getDescricao() + "\n" +
                "Endereço: " + estabelecimento.getEndereco() + "\n" +
                "Pedido: " + nomePrato + " - R$ " + valor + "\n");
    }

    public void gerarItinerario() {
        JFileChooser fileChooser = new JFileChooser();
        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileChooser.getSelectedFile() + ".txt"))) {
                for (String pedido : pedidos) {
                    writer.write(pedido + "\n");
                }
                JOptionPane.showMessageDialog(null, "Itinerário gerado com sucesso!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
