import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SlideInterface {
    private JFrame frame;
    private JLabel lblNome;
    private JLabel lblDescricao;
    private JLabel lblEndereco;
    private JButton btnProximo;
    private JButton btnAnterior;
    private JButton btnCardapio;
    private JButton btnItinerario;
    private JPanel cardapioPanel;
    private ArrayList<Estabelecimento> estabelecimentos;
    private int indiceAtual;
    private Estabelecimento estabelecimentoAtual;
    private Pedido pedido;

    public SlideInterface(ArrayList<Estabelecimento> estabelecimentos, Pedido pedido) {
        this.estabelecimentos = estabelecimentos;
        this.pedido = pedido;
        this.indiceAtual = 0;
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Slide Interface");
        frame.setBounds(100, 100, 600, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        frame.getContentPane().add(panel, BorderLayout.CENTER);
        panel.setLayout(new BorderLayout());

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));

        lblNome = new JLabel("Nome do Restaurante");
        lblNome.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblNome.setFont(new Font("Arial", Font.BOLD, 20));
        infoPanel.add(lblNome);

        lblDescricao = new JLabel("Descrição: ");
        lblDescricao.setAlignmentX(Component.CENTER_ALIGNMENT);
        infoPanel.add(lblDescricao);

        lblEndereco = new JLabel("Endereço: ");
        lblEndereco.setAlignmentX(Component.CENTER_ALIGNMENT);
        infoPanel.add(lblEndereco);

        panel.add(infoPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        btnAnterior = new JButton("Anterior");
        btnAnterior.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mostrarEstabelecimentoAnterior();
            }
        });
        buttonPanel.add(btnAnterior);

        btnProximo = new JButton("Próximo");
        btnProximo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mostrarProximoEstabelecimento();
            }
        });
        buttonPanel.add(btnProximo);

        btnCardapio = new JButton("Cardápio");
        btnCardapio.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mostrarCardapio();
            }
        });
        buttonPanel.add(btnCardapio);

        btnItinerario = new JButton("Gerar Itinerário");
        btnItinerario.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                pedido.gerarItinerario();
            }
        });

        buttonPanel.add(btnItinerario);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        mostrarEstabelecimentoAtual();
    }

    private void mostrarEstabelecimentoAtual() {
        if (!estabelecimentos.isEmpty()) {
            estabelecimentoAtual = estabelecimentos.get(indiceAtual);
            lblNome.setText(estabelecimentoAtual.getNome());
            lblDescricao.setText("Descrição: " + estabelecimentoAtual.getDescricao());
            lblEndereco.setText("Endereço: " + estabelecimentoAtual.getEndereco());
        } else {
            lblNome.setText("Nenhum estabelecimento disponível");
            lblDescricao.setText("");
            lblEndereco.setText("");
        }
    }

    private void mostrarEstabelecimentoAnterior() {
        if (!estabelecimentos.isEmpty()) {
            indiceAtual = (indiceAtual - 1 + estabelecimentos.size()) % estabelecimentos.size();
            mostrarEstabelecimentoAtual();
        }
    }

    private void mostrarProximoEstabelecimento() {
        if (!estabelecimentos.isEmpty()) {
            indiceAtual = (indiceAtual + 1) % estabelecimentos.size();
            mostrarEstabelecimentoAtual();
        }
    }

    private void mostrarCardapio() {
        if (!estabelecimentos.isEmpty()) {
            if (estabelecimentoAtual != null) {
                ArrayList<Prato> pratos = estabelecimentoAtual.getPratos();
                JPanel panelCardapio = new JPanel();
                panelCardapio.setLayout(new GridLayout(pratos.size(), 2));

                for (Prato prato : pratos) {
                    JCheckBox checkBox = new JCheckBox(prato.getNome() + " - R$ " + prato.getPreco());
                    panelCardapio.add(checkBox);
                }

                int result = JOptionPane.showConfirmDialog(null, panelCardapio, "Selecione os pratos desejados", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    for (Component component : panelCardapio.getComponents()) {
                        if (component instanceof JCheckBox) {
                            JCheckBox checkBox = (JCheckBox) component;
                            if (checkBox.isSelected()) {
                                String[] pratoInfo = checkBox.getText().split(" - R\\$ ");
                                String nomePrato = pratoInfo[0];
                                double precoPrato = Double.parseDouble(pratoInfo[1]);
                                pedido.adicionarPedido(estabelecimentoAtual, nomePrato, precoPrato);
                            }
                        }
                    }
                }
            }
        }
    }

    public void show() {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
