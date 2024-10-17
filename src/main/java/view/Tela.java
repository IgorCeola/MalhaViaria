package view;

import controller.ControllerTela;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;


// Restante do código...

public class Tela extends javax.swing.JFrame {
    
    String caminho;
    private int qtdcarros;
    private double tempo;
   
	private final ControllerTela controller;

	// Adicionando um modelo de tabela
	 // Modelo da tabela
            private void inicializacaoMalha() {
        // Definindo o modelo de tabela com dados e colunas
        DefaultTableModel tableModel = new DefaultTableModel(
        );
        jTable1.setModel(tableModel); // Atualiza a tabela com o modelo
    }

    private void updateTable() {
        jTable1.repaint(); // Atualiza a exibição da tabela
    }

	/**
	 * Creates new form Tela
	 */
	public Tela() {
		this.controller = new ControllerTela();
		initComponents();
		inicializacaoMalha();
	}


	// Buscando o arquivo e retornando o texto do mesmo.
	public void SelecionaArquivo() {
		JFileChooser chooser = new JFileChooser();
		String caminho = "";
		int resultado = chooser.showOpenDialog(null);
		if (chooser.APPROVE_OPTION == resultado) {
			File arquivo = chooser.getSelectedFile();
			System.out.println(arquivo.getAbsolutePath());
			caminho = arquivo.getAbsolutePath();
		}
		          setCaminho(caminho);
	}

    public String getCaminho() {
        return caminho;
    }

    public void setCaminho(String caminho) {
        this.caminho = caminho;
    }

    public int getQtdcarros() {
        return qtdcarros;
    }

    public void setQtdcarros(int qtdcarros) {
        this.qtdcarros = qtdcarros;
    }

    public double getTempo() {
        return tempo;
    }

    public void setTempo(double tempo) {
        this.tempo = tempo;
    }
     
    
    
	@SuppressWarnings("unchecked")
	private void initComponents() {

		btnIniciar = new javax.swing.JButton();
		btnEncerrar = new javax.swing.JButton();
		qtdVeiculo = new javax.swing.JSpinner();
		jLabel1 = new javax.swing.JLabel();
		Intervalo = new javax.swing.JSpinner();
		jLabel2 = new javax.swing.JLabel();
		btnPausar = new javax.swing.JButton();
		btnBuscar = new javax.swing.JButton();
		selectMapInput = new javax.swing.JTextField();
		jTable1 = new javax.swing.JTable(); // Criando a JTable
		JScrollPane scrollPane = new JScrollPane(jTable1); // Adicionando a tabela ao JScrollPane

		selectMapInput.setEditable(false);

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		btnIniciar.setText("Iniciar");
		btnIniciar.addActionListener(new java.awt.event.ActionListener() {
                    
                        
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnIniciarActionPerformed(evt);
			}
		});

		btnEncerrar.setText("Encerrar");
		btnEncerrar.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnEncerrarActionPerformed(evt);
			}
		});

		jLabel1.setText("Quantidade de Veiculos");

		jLabel2.setText("Intervalo");

		btnPausar.setText("Pausar");

		btnBuscar.setText("Buscar");
		btnBuscar.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnBuscarActionPerformed(evt);
			}
		});

		// Definindo o layout e adicionando os componentes
		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
			.addGroup(layout.createSequentialGroup()
				.addGap(30, 30, 30)
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
					.addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE) // Adicionando a JTable
					.addGroup(layout.createSequentialGroup()
						.addComponent(qtdVeiculo, javax.swing.GroupLayout.PREFERRED_SIZE, 79,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(18, 18, 18)
						.addComponent(jLabel1)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 65, Short.MAX_VALUE)
						.addComponent(btnBuscar)
						.addGap(18, 18, 18)
						.addComponent(btnIniciar, javax.swing.GroupLayout.PREFERRED_SIZE, 67,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(18, 18, 18)
						.addComponent(btnPausar)
						.addGap(18, 18, 18)
						.addComponent(btnEncerrar)))
				.addGap(26, 26, 26)));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
			.addGroup(layout.createSequentialGroup()
				.addGap(17, 17, 17)
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
					.addComponent(btnIniciar)
					.addComponent(btnEncerrar)
					.addComponent(qtdVeiculo, javax.swing.GroupLayout.PREFERRED_SIZE,
							javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
					.addComponent(jLabel1)
					.addComponent(btnPausar)
					.addComponent(btnBuscar))
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
				.addComponent(scrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE) // Definindo o tamanho da JTable
				.addContainerGap(402, Short.MAX_VALUE)));

		pack();
	}

        
        // ao Iniciar pega a qtd de carros e o tempo 
	private void btnIniciarActionPerformed(java.awt.event.ActionEvent evt) {
		          setQtdcarros(qtdVeiculo.getComponentCount());
                          setTempo(Intervalo.getComponentCount());
                          
	}

	private void btnEncerrarActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO add your handling code here:
	}

        
        // busca o arquivo
	private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {
                                   
           SelecionaArquivo();

	}


	public static void main(String args[]) {
		/* Set the Nimbus look and feel */
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception ex) {
			java.util.logging.Logger.getLogger(Tela.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}

		/* Create and display the form */
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new Tela().setVisible(true);
			}
		});
	}

	// Variables declaration - do not modify
	private javax.swing.JSpinner Intervalo;
	private javax.swing.JButton btnBuscar;
	private javax.swing.JButton btnEncerrar;
	private javax.swing.JButton btnIniciar;
	private javax.swing.JButton btnPausar;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JSpinner qtdVeiculo;
	private javax.swing.JTextField selectMapInput;
	private javax.swing.JTable jTable1; // Declarando a JTable
	// End of variables declaration
}
