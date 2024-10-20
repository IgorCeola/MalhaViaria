package tabelaMalha;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;

import gerenciador.GerenciadorSimulacao;
import model.Carro;
import model.Posicao;
import constantes.ConstantesCelula;

public class RenderizadorCelulaMalha extends DefaultTableCellRenderer{

	private Border border;
    private GerenciadorSimulacao gerenciador;
    
    public RenderizadorCelulaMalha() {
        this.border = BorderFactory.createLineBorder(Color.BLACK);
        this.gerenciador = GerenciadorSimulacao.getInstance();
    }
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        
        ConstantesCelula celula = (ConstantesCelula) value;
        
        setBackground(celula.toColor());
        setFont(new Font("Dialog", Font.BOLD, 14));
        setBorder(border);
        
        Carro carro = gerenciador.getCarro(new Posicao(row, column));
        if (carro != null) {
            setFont(new Font("Serif", Font.BOLD, 20));
            setText(""+carro.id());
            setForeground(carro.getColor());
        } else {
            setFont(new Font("Serif", Font.BOLD, 18));
            setForeground(Color.GRAY);
        }
        
        //Teste
        Dimension dimension = new Dimension(30, 30);
        setPreferredSize(dimension);
        setSize(dimension);
        setMaximumSize(dimension);
        setMinimumSize(dimension);
        
        setHorizontalAlignment(SwingConstants.CENTER);
        setVerticalAlignment(SwingConstants.CENTER);
        return component;
    }
}
