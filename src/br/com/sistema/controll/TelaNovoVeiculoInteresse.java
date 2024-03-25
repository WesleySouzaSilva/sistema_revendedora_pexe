package br.com.sistema.controll;

import java.math.BigDecimal;
import java.util.Optional;

import br.com.sistema.conexao.Conexao;
import br.com.sistema.filtros.FiltroFlutuante;
import br.com.sistema.filtros.FiltroFone;
import br.com.sistema.listeners.ListenerFormatarFone;
import br.com.sistema.listeners.ListenerParaMaiusculas;
import br.com.sistema.listeners.ListenerValorMinMax;
import br.com.sistema.model.ClienteCrm;
import br.com.sistema.model.dao.PessoaDAO;
import br.com.sistema.model.dao.VeiculoDAO;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class TelaNovoVeiculoInteresse {

	@FXML
	private Button btnCancelar;

	@FXML
	private Button btnConfirmar;

	@FXML
	private ComboBox<String> cmbCategoria;

	@FXML
	private ComboBox<String> cmbMarca;

	@FXML
	private TextField txtCliente;

	@FXML
	private TextField txtNome;

	@FXML
	private TextField txtTelefone;

	@FXML
	private TextField txtValorFinal;

	@FXML
	private TextField txtValorInicio;

	private PessoaDAO pessoaDAO = null;
	private VeiculoDAO veiculoDAO = null;
	private Conexao conexao;

	public void initialize() {
		comboBoxCategoriaVeiculo();
		comboBoxMarcaVeiculo();

		txtNome.textProperty().addListener(new ListenerParaMaiusculas(txtNome));
		txtCliente.textProperty().addListener(new ListenerParaMaiusculas(txtCliente));
		txtValorInicio.addEventFilter(KeyEvent.KEY_TYPED, new FiltroFlutuante(9, false));
		txtValorInicio.focusedProperty().addListener(new ListenerValorMinMax(txtValorInicio, 0, 100000));
		txtValorFinal.addEventFilter(KeyEvent.KEY_TYPED, new FiltroFlutuante(9, false));
		txtValorFinal.focusedProperty().addListener(new ListenerValorMinMax(txtValorFinal, 0, 100000));
		txtTelefone.addEventFilter(KeyEvent.KEY_TYPED, new FiltroFone());
		txtTelefone.focusedProperty().addListener(new ListenerFormatarFone(txtTelefone));

		btnConfirmar.setOnAction(e -> {
			confirmar();
		});

		btnCancelar.setOnAction(e -> {
			cancelar();
		});

		cmbCategoria.setOnAction(e -> {
			comboBoxMarcaVeiculo();
		});

		cmbCategoria.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				// TODO Auto-generated method stub
				if (event.getCode() == KeyCode.ENTER) {
					cmbMarca.requestFocus();
				}

			}
		});

		txtCliente.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				// TODO Auto-generated method stub
				if (event.getCode() == KeyCode.ENTER) {
					txtTelefone.requestFocus();
				}

			}
		});

		txtTelefone.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				// TODO Auto-generated method stub
				if (event.getCode() == KeyCode.ENTER) {
					cmbMarca.requestFocus();
				}

			}
		});

		cmbMarca.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				// TODO Auto-generated method stub
				if (event.getCode() == KeyCode.ENTER) {
					txtNome.requestFocus();
				}

			}
		});

		txtNome.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				// TODO Auto-generated method stub
				if (event.getCode() == KeyCode.ENTER) {
					txtValorInicio.requestFocus();
				}

			}
		});

		txtValorInicio.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				// TODO Auto-generated method stub
				if (event.getCode() == KeyCode.ENTER) {
					txtValorFinal.requestFocus();
				}

			}
		});

		txtValorFinal.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				// TODO Auto-generated method stub
				if (event.getCode() == KeyCode.ENTER) {
					confirmar();
				}

			}
		});

	}

	private void confirmar() {
		String nomeCliente = null, veiculo = null, tipo = null, marca = null, telefone = null;
		BigDecimal valorInicio = null;
		BigDecimal valorFinal = null;

		if (txtCliente.getText().isEmpty()) {
			ValidationFields.checkEmptyFields(txtCliente);
			Alert dlg = new Alert(AlertType.ERROR);
			dlg.setContentText("Preencha o campo Nome Cliente!");
			dlg.showAndWait();
			txtCliente.requestFocus();
			return;

		} else {

			nomeCliente = txtCliente.getText();
		}

		if (txtTelefone.getText().isEmpty()) {
			ValidationFields.checkEmptyFields(txtTelefone);
			Alert dlg = new Alert(AlertType.ERROR);
			dlg.setContentText("Preencha o campo Telefone!");
			dlg.showAndWait();
			txtNome.requestFocus();
			return;

		} else {

			telefone = txtTelefone.getText();
		}

		if (cmbCategoria.getValue() == null) {
			ValidationFields.checkEmptyFields(cmbCategoria);
			Alert dlg = new Alert(AlertType.ERROR);
			dlg.setContentText("Selecione a categoria Veiculo!");
			dlg.showAndWait();
			cmbCategoria.requestFocus();
			return;

		} else {

			tipo = cmbCategoria.getValue().toString();

		}

		if (cmbMarca.getValue() == null) {
			ValidationFields.checkEmptyFields(cmbMarca);
			Alert dlg = new Alert(AlertType.ERROR);
			dlg.setContentText("Selecione a marca Veiculo!");
			dlg.showAndWait();
			cmbMarca.requestFocus();
			return;

		} else {

			marca = cmbMarca.getValue().toString();

		}

		if (txtNome.getText().isEmpty()) {
			ValidationFields.checkEmptyFields(txtNome);
			Alert dlg = new Alert(AlertType.ERROR);
			dlg.setContentText("Preencha o campo Nome Veiculo!");
			dlg.showAndWait();
			txtNome.requestFocus();
			return;

		} else {

			veiculo = txtNome.getText();

		}

		if (txtValorInicio.getText().isEmpty()) {
			ValidationFields.checkEmptyFields(txtValorInicio);
			Alert dlg = new Alert(AlertType.ERROR);
			dlg.setContentText("Preencha o campo Valor Inicio!");
			dlg.showAndWait();
			txtValorInicio.requestFocus();
			return;

		} else {
			valorInicio = new BigDecimal(txtValorInicio.getText().replace(",", "."));

		}

		if (txtValorFinal.getText().isEmpty()) {
			ValidationFields.checkEmptyFields(txtValorFinal);
			Alert dlg = new Alert(AlertType.ERROR);
			dlg.setContentText("Preencha o campo Valor Final!");
			dlg.showAndWait();
			txtValorFinal.requestFocus();
			return;

		} else {
			BigDecimal valor = new BigDecimal(txtValorFinal.getText().replace(",", "."));
			if (valorInicio.compareTo(valor) == 1) {
				ValidationFields.checkEmptyFields(txtValorFinal);
				Alert dlg = new Alert(AlertType.ERROR);
				dlg.setContentText("O VALOR FINAL esta menor que o VALOR DE INICIO!\nValor Inicio : "
						+ txtValorInicio.getText() + "\nValor Final : " + txtValorFinal.getText()
						+ "\nNão é possivel continuar, altere o valor para prosseguir!");
				dlg.showAndWait();
				txtValorFinal.requestFocus();
				return;

			} else {
				valorFinal = new BigDecimal(txtValorFinal.getText().replace(",", "."));
			}

		}

		ClienteCrm cliente = new ClienteCrm(null, nomeCliente, telefone, tipo, marca, veiculo, valorInicio, valorFinal);

		ButtonType sim = new ButtonType("SIM", ButtonBar.ButtonData.YES);
		ButtonType nao = new ButtonType("NÃO", ButtonBar.ButtonData.NO);
		Alert alert = new Alert(AlertType.INFORMATION,
				"Deseja realmente cadastrar um novo cliente com interesse em veiculo ?", sim, nao);

		Optional<ButtonType> result = alert.showAndWait();

		if (result.get().equals(sim)) {
			this.conexao = new Conexao();
			this.pessoaDAO = new PessoaDAO(conexao);
			boolean sucesso = pessoaDAO.inserirClienteCrm(cliente);
			conexao.fecharConexao();
			if (sucesso) {
				Alert aler = new Alert(AlertType.INFORMATION);
				aler.setTitle("Confirmação de INCLUSÃO");
				aler.setHeaderText("Cliente interessado salvo com sucesso!");
				aler.showAndWait();
				voltarTela();
			}
		}

	}

	private void cancelar() {
		Alert alerta = new Alert(AlertType.CONFIRMATION);
		alerta.setTitle("Confirmação de Cancelamento");
		alerta.setHeaderText("Você quer mesmo cancelar ? ");
		alerta.setContentText("Todos os dados preenchidos serão perdidos \nVocê tem certeza?");
		Optional<ButtonType> escolha = alerta.showAndWait();

		if (escolha.get() == ButtonType.OK) {
			Stage stage = (Stage) btnCancelar.getScene().getWindow();
			stage.close();
		} else {

		}

	}

	private void voltarTela() {
		Stage stage = (Stage) btnCancelar.getScene().getWindow();
		stage.close();
	}

	private void comboBoxCategoriaVeiculo() {
		cmbCategoria.getItems().clear();
		this.conexao = new Conexao();
		this.veiculoDAO = new VeiculoDAO(conexao);
		cmbCategoria.getItems().addAll(veiculoDAO.listarCategoriaMarca());
		conexao.fecharConexao();
	}

	private void comboBoxMarcaVeiculo() {
		this.conexao = new Conexao();
		this.veiculoDAO = new VeiculoDAO(conexao);
		cmbMarca.getItems().clear();
		if (cmbCategoria.getSelectionModel().getSelectedItem() != null) {
			cmbMarca.getItems()
					.addAll(veiculoDAO.listarModeloMarca(cmbCategoria.getSelectionModel().getSelectedItem()));
		}
		conexao.fecharConexao();
	}

}
