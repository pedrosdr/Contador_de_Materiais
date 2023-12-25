package gui;

import application.Program;
import entities.Material;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import utils.Utils;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class MainViewController
{
    // Nodes
    @FXML private Label lblSeparator;
    @FXML private Label lblSource;
    @FXML private Label lblTarget;
    @FXML private TextField txtSeparator;
    @FXML private TextField txtSource;
    @FXML private TextField txtTarget;
    @FXML private Button btnSource;
    @FXML private Button btnTarget;
    @FXML private Button btnExecute;

    // Events
    @FXML
    private void btnSource_Action(ActionEvent e)
    {
        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV files", "*.csv"));
        File file = chooser.showOpenDialog(Program.getStage());
        txtSource.setText(file.getAbsolutePath());
    }

    @FXML
    private void btnTarget_Action(ActionEvent e)
    {
        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV files", "*.csv"));
        File file = chooser.showSaveDialog(Program.getStage());
        txtTarget.setText(file.getAbsolutePath());
    }

    @FXML
    private void btnExecute_Action(ActionEvent e)
    {
        if(Utils.stringNullOrEmpty(txtSeparator.getText()) ||
           Utils.stringNullOrEmpty(txtSource.getText()) ||
           Utils.stringNullOrEmpty(txtTarget.getText()))
        {
            Utils.showAlert("Preencha todos os campos", "Atenção", Alert.AlertType.WARNING);
            return;
        }

        List<Material> materials = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new FileReader(txtSource.getText())))
        {
            String line = reader.readLine();
            while ((line = reader.readLine()) != null)
            {
                String[] props = line.split(txtSeparator.getText());
                String name = props[0].toLowerCase();
                Double value = Double.parseDouble(props[1].replace(',', '.'));
                String unit = props[2].toLowerCase();
                materials.add(new Material(name, value, unit));
            }
        }
        catch (IOException ex){
            Utils.showAlert("Cheque se todos os campos foram preenchidos corretamente", "Algo deu errado", Alert.AlertType.WARNING);
            return;
        }

        List<String> newMaterialNames = materials.stream()
                .map(Material::getName)
                .collect(Collectors.toSet())
                .stream()
                .sorted()
                .toList();

        try(BufferedWriter writer = new BufferedWriter(new FileWriter(txtTarget.getText(), false)))
        {
            String sep = txtSeparator.getText();
            writer.write("Material" + sep + "Quantidade" + sep + "Unidade\n");

            for(String materialName : newMaterialNames)
            {
                List<Material> sameTypeMaterials = materials.stream()
                        .filter(m -> m.getName().equals(materialName))
                        .collect(Collectors.toList());

                String name = sameTypeMaterials.get(0).getName();
                String value = sameTypeMaterials.stream()
                        .map(Material::getValue)
                        .reduce(0.0, Double::sum)
                        .toString()
                        .replace('.', ',');
                String unit = sameTypeMaterials.get(0).getUnit();

                writer.write(name + sep + value + sep + unit + "\n");
            }
        }
        catch (IOException ex){
            Utils.showAlert("Cheque se todos os campos foram preenchidos corretamente", "Algo deu errado", Alert.AlertType.WARNING);
        }

        Utils.showAlert("Arquivo CSV criado com sucesso!", "Sucesso", Alert.AlertType.INFORMATION);
    }
}
