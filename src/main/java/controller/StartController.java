package controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import javax.swing.JFrame;

import gerenciador.GerenciadorSimulacao;
import model.Malha;
import seletorArquivos.SeletorArquivos;
import constantes.ModoSelecionado;
import view.TelaMalha;

public class StartController {
	private File file;
    private ModoSelecionado modoSelecionado;
    
    public String handleSelectFile(JFrame view) {
        this.file = SeletorArquivos.loadFileSelector(view).getSelectedFile();
        if (file != null && file.exists()) {
            return file.getAbsolutePath();
        }
        return "";
    }

    public void handleModoSelecionado(ModoSelecionado modoSelecionado) {
        this.modoSelecionado = modoSelecionado;
    }

    public void handleConfirm() {
        if (modoSelecionado == null) {
            SeletorArquivos.message("Nenhum gerenciador de threads selecionado!");
        } else if (file != null && file.exists()) {
            try {
                GerenciadorSimulacao gerenciador = GerenciadorSimulacao.getInstance();
                gerenciador.setModo(modoSelecionado.toClass());

                String conteudo = Files.readString(file.toPath());
                Malha world = Malha.from(conteudo);
                System.out.println(world.toString() + '\n');

                gerenciador.setMalha(world);

                SeletorArquivos.init(new TelaMalha());
            } catch (IOException ex) {
                SeletorArquivos.message("Erro na leitura: " + ex.getMessage());
            }
        } else {
            SeletorArquivos.message("Nenhum arquivo selecionado");
        }
    }
}
