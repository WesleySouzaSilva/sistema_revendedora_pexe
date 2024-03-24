package br.com.sistema.controll;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import br.com.sistema.conexao.Conexao;
import br.com.sistema.filtros.FiltroChassis;
import br.com.sistema.filtros.FiltroFlutuante;
import br.com.sistema.filtros.FiltroInteiro;
import br.com.sistema.listeners.ListenerParaMaiusculas;
import br.com.sistema.listeners.ListenerValorMinMax;
import br.com.sistema.model.Veiculo;
import br.com.sistema.model.dao.VeiculoDAO;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class TelaEditarVeiculo {

	@FXML
	private Button btnCancelar;

	@FXML
	private Button btnConfirmar;

	@FXML
	private ComboBox<String> cmbCategoria;

	@FXML
	private ComboBox<String> cmbFinanciamento;

	@FXML
	private ComboBox<String> cmbLeilao;

	@FXML
	private ComboBox<String> cmbMarca;

	@FXML
	private ComboBox<String> cmbTipoEntrada;

	@FXML
	private TextField txtAnoModelo;

	@FXML
	private TextField txtCor;

	@FXML
	private TextField txtKm;

	@FXML
	private TextField txtNome;

	@FXML
	private TextField txtPlaca;

	@FXML
	private TextField txtRenavam;

	@FXML
	private TextField txtValorEntrada;

	@FXML
	private TextField txtValorVenda;

	@FXML
	private TextField txtValorFipe;

	private Conexao conexao = Principal.getConexao();
	private VeiculoDAO veiculoDAO = null;

	public void initialize() {
		comboBoxAlternativas();
		comboBoxCategoriaVeiculo();
		listarDados();
		comboBoxTipoEntradaVeiculo();

		txtRenavam.addEventFilter(KeyEvent.KEY_TYPED, new FiltroInteiro(11));
		txtCor.textProperty().addListener(new ListenerParaMaiusculas(txtCor));
		txtKm.addEventFilter(KeyEvent.KEY_TYPED, new FiltroInteiro(11));
		txtNome.textProperty().addListener(new ListenerParaMaiusculas(txtNome));
		txtValorEntrada.addEventFilter(KeyEvent.KEY_TYPED, new FiltroFlutuante(9, false));
		txtValorEntrada.focusedProperty().addListener(new ListenerValorMinMax(txtValorEntrada, 0, 100000));
		txtValorVenda.addEventFilter(KeyEvent.KEY_TYPED, new FiltroFlutuante(9, false));
		txtValorVenda.focusedProperty().addListener(new ListenerValorMinMax(txtValorVenda, 0, 100000));
		txtValorFipe.addEventFilter(KeyEvent.KEY_TYPED, new FiltroFlutuante(9, false));
		txtValorFipe.focusedProperty().addListener(new ListenerValorMinMax(txtValorFipe, 0, 100000));
		txtPlaca.addEventFilter(KeyEvent.KEY_TYPED, new FiltroChassis(7));
		txtPlaca.textProperty().addListener(new ListenerParaMaiusculas(txtPlaca));

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
					txtPlaca.requestFocus();
				}

			}
		});

		txtPlaca.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				// TODO Auto-generated method stub
				if (event.getCode() == KeyCode.ENTER) {
					txtAnoModelo.requestFocus();
				}

			}
		});

		txtAnoModelo.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				// TODO Auto-generated method stub
				if (event.getCode() == KeyCode.ENTER) {
					txtRenavam.requestFocus();
				}

			}
		});

		txtRenavam.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				// TODO Auto-generated method stub
				if (event.getCode() == KeyCode.ENTER) {
					txtCor.requestFocus();
				}

			}
		});

		txtCor.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				// TODO Auto-generated method stub
				if (event.getCode() == KeyCode.ENTER) {
					txtKm.requestFocus();
				}

			}
		});

		txtKm.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				// TODO Auto-generated method stub
				if (event.getCode() == KeyCode.ENTER) {
					cmbLeilao.requestFocus();
				}

			}
		});

		cmbLeilao.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				// TODO Auto-generated method stub
				if (event.getCode() == KeyCode.ENTER) {
					cmbFinanciamento.requestFocus();
				}

			}
		});

		cmbFinanciamento.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				// TODO Auto-generated method stub
				if (event.getCode() == KeyCode.ENTER) {
					cmbTipoEntrada.requestFocus();
				}

			}
		});

		cmbTipoEntrada.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				// TODO Auto-generated method stub
				if (event.getCode() == KeyCode.ENTER) {
					txtValorEntrada.requestFocus();
				}

			}
		});

		txtValorEntrada.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				// TODO Auto-generated method stub
				if (event.getCode() == KeyCode.ENTER) {
					txtValorVenda.requestFocus();
				}

			}
		});

		txtValorVenda.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				// TODO Auto-generated method stub
				if (event.getCode() == KeyCode.ENTER) {
					txtValorFipe.requestFocus();
				}

			}
		});

		txtValorFipe.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				// TODO Auto-generated method stub
				if (event.getCode() == KeyCode.ENTER) {
					confirmar();
				}

			}
		});

		btnCancelar.setOnAction(e -> {
			cancelar();
		});

		btnConfirmar.setOnAction(e -> {
			confirmar();
		});
	}

	public void listarDados() {
		TelaHome tela = new TelaHome();
		this.veiculoDAO = Principal.getVeiculoDAO();
		for (Veiculo v : veiculoDAO.listarTodosId(tela.getIdVeiculo())) {
			txtNome.setText(v.getVeiculo());
			txtPlaca.setText(v.getPlaca());
			txtRenavam.setText(v.getRenavam());
			txtCor.setText(v.getCor());
			txtKm.setText(v.getKm());
			txtAnoModelo.setText(v.getAno_modelo());
			txtValorEntrada.setText(v.getValorEntradaFormatado().replace(".", "").replace(",", "."));
			txtValorVenda.setText(v.getValorVendaFormatado().replace(".", "").replace(",", "."));
			cmbCategoria.setValue(v.getCategoria());
			cmbMarca.setValue(v.getMarca());
			cmbFinanciamento.setValue(v.getFinanciamento());
			cmbLeilao.setValue(v.getSinistroRs());
			cmbTipoEntrada.setValue(v.getSituacao());
			txtValorFipe.setText(v.getValorFipeFormatado().replace(".", "").replace(",", "."));

		}
		conexao.fecharConexao();

	}

	public void confirmar() {
		TelaHome tela = new TelaHome();
		String veiculo = null, ano_modelo = null, marca = null, placa = null, renavam = null, cor = null,
				categoria = null, sinistro = null, financiamento = null, situacao = null, km = null;
		BigDecimal valorEntrada = null, valorVenda = null, valorFipe = null;

		if (cmbCategoria.getValue() == null) {
			ValidationFields.checkEmptyFields(cmbCategoria);
			Alert dlg = new Alert(AlertType.ERROR);
			dlg.setContentText("Selecione a categoria Veiculo!");
			dlg.showAndWait();
			cmbCategoria.requestFocus();
			return;

		} else {

			categoria = cmbCategoria.getValue().toString();

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

		if (txtPlaca.getText().isEmpty()) {
			placa = new String();
		} else {

			String validar = txtPlaca.getText();
			System.out.println("validar placa : " + validar);

			if (validaPlaca(validar)) {

			} else {
				Alert dlg = new Alert(AlertType.WARNING);
				dlg.setContentText("Placa digitada não e válida!!!");
				dlg.showAndWait();
				txtPlaca.requestFocus();
				return;
			}

			placa = txtPlaca.getText();

		}

		if (txtRenavam.getText().isEmpty()) {
			renavam = new String();

		} else {
			renavam = txtRenavam.getText();

		}

		if (txtCor.getText().isEmpty()) {
			ValidationFields.checkEmptyFields(txtCor);
			Alert dlg = new Alert(AlertType.ERROR);
			dlg.setContentText("Preencha o campo Cor!");
			dlg.showAndWait();
			txtCor.requestFocus();
			return;

		} else {
			cor = txtCor.getText();

		}

		if (txtKm.getText().isEmpty() || txtKm.getText() == null) {
			ValidationFields.checkEmptyFields(txtKm);
			Alert dlg = new Alert(AlertType.ERROR);
			dlg.setContentText("Preencha o campo KM!");
			dlg.showAndWait();
			txtKm.requestFocus();
			return;

		} else {
			km = txtKm.getText();

		}

		if (txtAnoModelo.getText().isEmpty()) {
			ValidationFields.checkEmptyFields(txtAnoModelo);
			Alert dlg = new Alert(AlertType.ERROR);
			dlg.setContentText("Preencha o campo Ano/Modelo!");
			dlg.showAndWait();
			txtAnoModelo.requestFocus();
			return;

		} else {
			ano_modelo = txtAnoModelo.getText();

		}

		if (cmbLeilao.getValue() == null) {
			ValidationFields.checkEmptyFields(cmbLeilao);
			Alert dlg = new Alert(AlertType.ERROR);
			dlg.setContentText("Selecione se possui Leilão/RS");
			dlg.showAndWait();
			cmbLeilao.requestFocus();
			return;

		} else {

			sinistro = cmbLeilao.getValue();

		}

		if (cmbFinanciamento.getValue() == null) {
			ValidationFields.checkEmptyFields(cmbFinanciamento);
			Alert dlg = new Alert(AlertType.ERROR);
			dlg.setContentText("Selecione se possui Financiamento");
			dlg.showAndWait();
			cmbFinanciamento.requestFocus();
			return;

		} else {

			financiamento = cmbFinanciamento.getValue();

		}

		if (cmbTipoEntrada.getValue() == null) {
			ValidationFields.checkEmptyFields(cmbTipoEntrada);
			Alert dlg = new Alert(AlertType.ERROR);
			dlg.setContentText("Selecione o tipo de entrada");
			dlg.showAndWait();
			cmbTipoEntrada.requestFocus();
			return;

		} else {

			situacao = cmbTipoEntrada.getValue();

		}

		if (txtValorEntrada.getText().isEmpty()) {
			ValidationFields.checkEmptyFields(txtValorEntrada);
			Alert dlg = new Alert(AlertType.ERROR);
			dlg.setContentText("Preencha o campo Valor Entrada!");
			dlg.showAndWait();
			txtValorEntrada.requestFocus();
			return;

		} else {
			valorEntrada = new BigDecimal(txtValorEntrada.getText().replace(",", "."));

		}

		if (txtValorVenda.getText().isEmpty()) {
			ValidationFields.checkEmptyFields(txtValorVenda);
			Alert dlg = new Alert(AlertType.ERROR);
			dlg.setContentText("Preencha o campo Valor Venda!");
			dlg.showAndWait();
			txtValorVenda.requestFocus();
			return;

		} else {
			BigDecimal valor = new BigDecimal(txtValorVenda.getText().replace(",", "."));
			if (valorEntrada.compareTo(valor) == 1) {
				ValidationFields.checkEmptyFields(txtValorVenda);
				Alert dlg = new Alert(AlertType.ERROR);
				dlg.setContentText("O VALOR DE VENDA esta menor que o VALOR DE ENTRADA!\nValor Entrada : "
						+ txtValorEntrada.getText() + "\nValor Venda : " + txtValorVenda.getText()
						+ "\nNão é possivel continuar, altere o valor para prosseguir!");
				dlg.showAndWait();
				txtValorVenda.requestFocus();
				return;

			} else {
				valorVenda = new BigDecimal(txtValorVenda.getText().replace(",", "."));
			}

		}

		if (txtValorFipe.getText().isEmpty()) {
			ValidationFields.checkEmptyFields(txtValorFipe);
			Alert dlg = new Alert(AlertType.ERROR);
			dlg.setContentText("Preencha o campo Valor Fipe!");
			dlg.showAndWait();
			txtValorFipe.requestFocus();
			return;

		} else {
			valorFipe = new BigDecimal(txtValorFipe.getText().replace(",", "."));

		}

		Veiculo veiculo2 = new Veiculo(tela.getIdVeiculo(), veiculo, marca, ano_modelo, renavam, placa, cor, situacao,
				null, null, null, null, null, null, valorEntrada, valorVenda, null, sinistro, financiamento, categoria,
				km, valorFipe, null, null, null);
		Alert alerta = new Alert(AlertType.CONFIRMATION);
		alerta.setTitle("Confirmação de EDIÇÃO");
		alerta.setHeaderText("Você quer mesmo atualizar os dados do Veiculo ? ");
		alerta.setContentText("O veiculo '" + txtNome.getText() + "' será atualizado!" + "\nVocê tem certeza?");
		Optional<ButtonType> escolha = alerta.showAndWait();

		if (escolha.get() == ButtonType.OK) {
			this.veiculoDAO = Principal.getVeiculoDAO();
			boolean sucesso = veiculoDAO.atualizar(veiculo2);
			System.out.println("valor boolean :" + sucesso);
			if (sucesso) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Confirmação de EDIÇÃO");
				alert.setHeaderText("Dados do Veiculo atualizado com sucesso!");
				alert.showAndWait();
			}
			conexao.fecharConexao();
			voltarTela();

		}

	}

	public void comboBoxAlternativas() {

		String nome = new String("SIM");
		String cpfCnpj = new String("NAO");

		ArrayList<String> listaBusca = new ArrayList<>();
		listaBusca.add(nome);
		listaBusca.add(cpfCnpj);
		cmbFinanciamento.getItems().addAll(listaBusca);
		cmbLeilao.getItems().addAll(listaBusca);

	}

	public void comboBoxTipoEntradaVeiculo() {

		String nome = new String("ESTOQUE");
		String cpfCnpj = new String("CONSIGNADO");

		ArrayList<String> listaBusca = new ArrayList<>();
		listaBusca.add(nome);
		listaBusca.add(cpfCnpj);
		cmbTipoEntrada.getItems().addAll(listaBusca);

	}

	public void comboBoxCategoriaVeiculo() {
		cmbCategoria.getItems().clear();
		this.veiculoDAO = Principal.getVeiculoDAO();
		cmbCategoria.getItems().addAll(veiculoDAO.listarCategoriaMarca());
		conexao.fecharConexao();
	}

	public void comboBoxMarcaVeiculo() {
		this.veiculoDAO = Principal.getVeiculoDAO();
		cmbMarca.getItems().clear();
		if (cmbCategoria.getSelectionModel().getSelectedItem() != null) {
			cmbMarca.getItems()
					.addAll(veiculoDAO.listarModeloMarca(cmbCategoria.getSelectionModel().getSelectedItem()));
		}
		conexao.fecharConexao();
	}

	public void cancelar() {
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

	public void voltarTela() {
		Stage stage = (Stage) btnCancelar.getScene().getWindow();
		stage.close();
	}

	public boolean validaPlaca(String placa) {
		boolean result = false;

		Pattern pattern = Pattern.compile("[A-Z]{3}[0-9]{1}[A-Z]{1}[0-9]{2}|[A-Z]{3}\\-[0-9]{4}");
		Matcher mat = pattern.matcher(placa);
		if (!mat.matches()) {
			result = false;
		} else {
			result = true;

		}
		return result;

	}

}
