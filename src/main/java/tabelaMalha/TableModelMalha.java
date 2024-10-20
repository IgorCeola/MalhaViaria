package tabelaMalha;

import gerenciador.GerenciadorSimulacao;
import model.Malha;

import javax.swing.table.AbstractTableModel;

import constantes.ConstantesCelula;

public class TableModelMalha extends AbstractTableModel{

	private Malha malha;
    
    public TableModelMalha() {
        this.malha = GerenciadorSimulacao.getInstance().getMalha();
    }

    @Override
    public int getRowCount() {
        return malha.linhas();
    }

    @Override
    public int getColumnCount() {
        return malha.colunas();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
    	ConstantesCelula cell = malha.get(rowIndex, columnIndex);
        return cell;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return ConstantesCelula.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }
}
