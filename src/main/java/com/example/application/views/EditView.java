package com.example.application.views;

import com.example.application.Settings;
import com.example.application.model.Currency;
import com.example.application.services.ServiceCurrenciesRepository;
import com.example.application.services.ServiceCurrency;
import com.example.application.util.CurrenciesFromFile;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

@PageTitle("Edit")
@Route(value = "/edit")
public class EditView extends VerticalLayout {
    public final ServiceCurrenciesRepository SERVICE_CURRENCIES_REPOSITORY = new ServiceCurrenciesRepository();
    public final ServiceCurrency SERVICE_CURRENCY = new ServiceCurrency();
    private final String LIST_OF_CURRENCIES = "List of currencies";
    private final String ADD = "Add";
    private final String DELETE = "Delete";
    private final String GO_TO_CONVERTER = "Go to Converter";
    private final RouterLink goToMainPage = new RouterLink(GO_TO_CONVERTER, MainView.class);
    private final ComboBox<String> comboBoxListAllCurrencies = new ComboBox<>(LIST_OF_CURRENCIES);
    private final Button buttonAddCurrency = new Button(ADD);
    private final Button buttonDeleteCurrency = new Button(DELETE);
    private final Grid<Currency> GRID = new Grid<>(Currency.class, false);
    private String selectedCurrency;


    public EditView() {

        createGrid();
        createControlGridElement();

        listenAddButton();
        listenComboBoxAllCurrencies();
        listenDeleteButton();

    }

    private void createControlGridElement() {
        HorizontalLayout listButtonAndTextBoxHorizontalLayout = new HorizontalLayout();
        comboBoxListAllCurrencies.setItems(CurrenciesFromFile.getAllCurrenciesFromFile());
        listButtonAndTextBoxHorizontalLayout.setAlignItems(Alignment.BASELINE);
        listButtonAndTextBoxHorizontalLayout.add(buttonAddCurrency, comboBoxListAllCurrencies, buttonDeleteCurrency, goToMainPage);
        add(listButtonAndTextBoxHorizontalLayout);
    }

    private void createGrid() {
        VerticalLayout gridCurrenciesVerticalLayout = new VerticalLayout();
        GRID.removeAllColumns();
        configureGrid();
        gridCurrenciesVerticalLayout.add(GRID);
        add(gridCurrenciesVerticalLayout);

    }

    private void configureGrid() {
        GRID.addColumn(Currency::getName).setHeader("name").setTextAlign(ColumnTextAlign.CENTER).setWidth(Settings.COLUMN_WIDTH_GRID);
        GRID.addThemeVariants(GridVariant.LUMO_COMPACT);
        GRID.addThemeVariants(GridVariant.LUMO_COLUMN_BORDERS);
        GRID.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
        GRID.setWidth(Settings.WIDTH_GRID);
        GRID.setItems(SERVICE_CURRENCY.getListObjCurrencies()); //TODO создаю объект и вызываю метод получения валюты
    }

    private void listenDeleteButton() {
        buttonDeleteCurrency.addClickListener(event -> deleteAndUpdateGrid());
    }

    private void listenComboBoxAllCurrencies() {
        comboBoxListAllCurrencies.addValueChangeListener(event ->
                selectedCurrency = event.getValue());
    }

    private void listenAddButton() {
        buttonAddCurrency.addClickListener(event -> insertAndUpdateGrid());
    }

    private void insertAndUpdateGrid() {
        SERVICE_CURRENCIES_REPOSITORY.addCurrency(selectedCurrency); //TODO создаем сервис репозитория валюты и вызываем метод
        GRID.removeAllColumns();
        configureGrid();
    }

    private void deleteAndUpdateGrid() {
        SERVICE_CURRENCIES_REPOSITORY.deleteCurrency(selectedCurrency); //TODO создаем сервис репозитория валюты и вызываем метод
        GRID.removeAllColumns();
        configureGrid();
    }

}


