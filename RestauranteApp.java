import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

public class RestauranteApp {
    private JFrame frame;
    private JComboBox<Estabelecimento> estabelecimentoComboBox;
    private JComboBox<Prato> pratoComboBox;

    private ArrayList<Estabelecimento> estabelecimentos;
    private ArrayList<Prato> pratos;
    private JFileChooser fileChooser;

    public RestauranteApp() {
        estabelecimentos = new ArrayList<>();
        pratos = new ArrayList<>();
        fileChooser = new JFileChooser();
        initialize();
        show();
    }

    private void initialize() {
        frame = new JFrame("Restaurante App");
        frame.setBounds(100, 100, 400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        frame.getContentPane().add(mainPanel, BorderLayout.CENTER);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JPanel exportImportPanel = new JPanel();
        exportImportPanel.setBorder(BorderFactory.createTitledBorder("Exportar/Importar Estabelecimentos e Pratos"));
        exportImportPanel.setLayout(new GridLayout(4, 2));

        JButton btnExportarEstabelecimentos = new JButton("Exportar Estabelecimentos");
        btnExportarEstabelecimentos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int result = fileChooser.showSaveDialog(frame);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    exportarEstabelecimentos(selectedFile);
                }
            }
        });
        exportImportPanel.add(btnExportarEstabelecimentos);

        JButton btnImportarEstabelecimentos = new JButton("Importar Estabelecimentos");
        btnImportarEstabelecimentos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int result = fileChooser.showOpenDialog(frame);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    importarEstabelecimentos(selectedFile);
                }
            }
        });
        exportImportPanel.add(btnImportarEstabelecimentos);

        JButton btnExportarPratos = new JButton("Exportar Pratos");
        btnExportarPratos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int result = fileChooser.showSaveDialog(frame);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    exportarPratos(selectedFile);
                }
            }
        });
        exportImportPanel.add(btnExportarPratos);

        JButton btnImportarPratos = new JButton("Importar Pratos");
        btnImportarPratos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int result = fileChooser.showOpenDialog(frame);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    importarPratos(selectedFile);
                }
            }
        });
        exportImportPanel.add(btnImportarPratos);

        mainPanel.add(exportImportPanel);

        JPanel selectCreatePanel = new JPanel();
        selectCreatePanel.setBorder(BorderFactory.createTitledBorder("Selecionar/Criar Estabelecimento e Prato"));
        selectCreatePanel.setLayout(new GridLayout(3, 2));

        JLabel lblEstabelecimento = new JLabel("Estabelecimento:");
        selectCreatePanel.add(lblEstabelecimento);

        estabelecimentoComboBox = new JComboBox<>();
        selectCreatePanel.add(estabelecimentoComboBox);

        JLabel lblPrato = new JLabel("Prato:");
        selectCreatePanel.add(lblPrato);

        pratoComboBox = new JComboBox<>();
        selectCreatePanel.add(pratoComboBox);

        JButton btnSelecionar = new JButton("Selecionar");
        btnSelecionar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Estabelecimento estabelecimentoSelecionado = (Estabelecimento) estabelecimentoComboBox.getSelectedItem();
                Prato pratoSelecionado = (Prato) pratoComboBox.getSelectedItem();

                if (estabelecimentoSelecionado != null && pratoSelecionado != null) {
                    // Copia o prato selecionado para o estabelecimento
                    Prato copiaPrato = new Prato(pratoSelecionado.getNome(), pratoSelecionado.getPreco());
                    estabelecimentoSelecionado.adicionarPrato(copiaPrato);

                    JOptionPane.showMessageDialog(frame,
                            "Estabelecimento: " + estabelecimentoSelecionado.getNome() + "\n" +
                                    "Prato: " + copiaPrato.getNome() + "\n" +
                                    "Preço: " + copiaPrato.getPreco(),
                            "Seleção Concluída",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(frame, "Por favor, selecione um estabelecimento e um prato.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        selectCreatePanel.add(btnSelecionar);

        JButton btnCriarEstabelecimento = new JButton("Criar Estabelecimento");
        btnCriarEstabelecimento.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                criarEstabelecimento();
            }
        });
        selectCreatePanel.add(btnCriarEstabelecimento);

        JButton btnCriarPrato = new JButton("Criar Prato");
        btnCriarPrato.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                criarPrato();
            }
        });
        selectCreatePanel.add(btnCriarPrato);

        mainPanel.add(selectCreatePanel);

        JPanel slidePanel = new JPanel();
        slidePanel.setBorder(BorderFactory.createTitledBorder("Slide Interface"));
        slidePanel.setLayout(new GridLayout(1, 1));

        JButton btnSlide = new JButton("Abrir Slide Interface");
        btnSlide.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SlideInterface slideInterface = new SlideInterface(estabelecimentos, new Pedido("nome", "descricao", "endereco"));
                slideInterface.show();
            }
        });
        slidePanel.add(btnSlide);

        mainPanel.add(slidePanel);

        frame.pack();
    }

    private void criarEstabelecimento() {
        String nome = JOptionPane.showInputDialog(frame, "Digite o nome do estabelecimento:");
        String descricao = JOptionPane.showInputDialog(frame, "Digite a descrição do estabelecimento:");
        String endereco = JOptionPane.showInputDialog(frame, "Digite o endereço do estabelecimento:");


        if (nome != null && !nome.isEmpty() && descricao != null && !descricao.isEmpty() && endereco != null && !endereco.isEmpty()) {
            Estabelecimento novoEstabelecimento = new Estabelecimento(nome, descricao, endereco);
            estabelecimentos.add(novoEstabelecimento);
            atualizarComboBoxEstabelecimentos();
        }
    }



    private void criarPrato() {
        String nome = JOptionPane.showInputDialog(frame, "Digite o nome do prato:");
        if (nome != null && !nome.isEmpty()) {
            String precoStr = JOptionPane.showInputDialog(frame, "Digite o preço do prato:");
            try {
                double preco = Double.parseDouble(precoStr);
                Prato novoPrato = new Prato(nome, preco);

                // Adiciona o prato apenas à lista global de pratos
                pratos.add(novoPrato);

                // Atualiza o JComboBox de pratos com a lista global de pratos
                atualizarComboBoxPratos(pratos);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Por favor, digite um valor válido para o preço.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void atualizarComboBoxEstabelecimentos() {
        estabelecimentoComboBox.removeAllItems();
        for (Estabelecimento estabelecimento : estabelecimentos) {
            estabelecimentoComboBox.addItem(estabelecimento);
        }
    }

    // Atualiza o JComboBox de pratos com uma lista de pratos
    private void atualizarComboBoxPratos(ArrayList<Prato> listaPratos) {
        pratoComboBox.removeAllItems();
        for (Prato prato : listaPratos) {
            pratoComboBox.addItem(prato);
        }
    }

    private void exportarEstabelecimentos(File fileToSave) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileToSave))) {
            oos.writeObject(estabelecimentos);
            JOptionPane.showMessageDialog(frame, "Estabelecimentos exportados com sucesso!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Erro ao exportar estabelecimentos.", "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void importarEstabelecimentos(File fileToOpen) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileToOpen))) {
            Object obj = ois.readObject();

            if (obj instanceof ArrayList<?>) {
                ArrayList<Estabelecimento> estabelecimentosImportados = (ArrayList<Estabelecimento>) obj;

                // Limpa a lista local antes de adicionar os estabelecimentos importados
                estabelecimentos.clear();
                estabelecimentos.addAll(estabelecimentosImportados);

                atualizarComboBoxEstabelecimentos();
                JOptionPane.showMessageDialog(frame, "Estabelecimentos importados com sucesso!");
            } else {
                JOptionPane.showMessageDialog(frame, "Formato de arquivo inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(frame, "Erro ao importar estabelecimentos.", "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }



    private void exportarPratos(File file) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(pratos);
            JOptionPane.showMessageDialog(frame, "Pratos exportados com sucesso!");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(frame, "Erro ao exportar pratos.", "Erro", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }


    private void importarPratos(File file) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            ArrayList<Prato> pratosImportados = (ArrayList<Prato>) ois.readObject();

            // Verifica se cada prato importado já existe na lista global antes de adicionar
            for (Prato pratoImportado : pratosImportados) {
                if (!pratos.contains(pratoImportado)) {
                    pratos.add(pratoImportado);
                }
            }

            atualizarComboBoxPratos(pratos);
            JOptionPane.showMessageDialog(frame, "Pratos importados com sucesso!");
        } catch (IOException | ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(frame, "Erro ao importar pratos.", "Erro", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
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

    public static void main(String[] args) {
        RestauranteApp app = new RestauranteApp();
        app.show();
    }
}
