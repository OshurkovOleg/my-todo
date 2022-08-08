package com.example.application.views;

import com.example.application.client.ProcessingConvert;
import com.example.application.exceptions.ConverterException;
import com.example.application.services.ServiceCurrenciesRepository;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

import java.io.IOException;
import java.math.BigDecimal;

@PageTitle("Convert currencies")
@Route(value = "")
public class MainView extends VerticalLayout {
    public final ServiceCurrenciesRepository SERVICE_CURRENCIES_REPOSITORY = new ServiceCurrenciesRepository();
    private final String ERROR_CONVERT_CURRENCY = "Error convert currency";
    private final String AMOUNT = "Amount";
    private final String START = "Start";
    private final String FROM = "From";
    private final String IN = "In";
    private final String GO_TO_EDIT = "Go to Edit";
    RouterLink goToEditPage = new RouterLink(GO_TO_EDIT, EditView.class);
    private final TextField TEXT_FIELD_AMOUNT_CURRENCY = new TextField(AMOUNT);
    private final Button BUTTON_START_CONVERTER = new Button(START);
    private final ComboBox<String> COMBOBOX_FROM_CURRENCY = new ComboBox<>(FROM);
    private final ComboBox<String> COMBOBOX_IN_CURRENCY = new ComboBox<>(IN);
    private String startCurrency;
    private String resultCurrency;
    private BigDecimal amountToConvert;
    private String conversionResult;

    public MainView() {

        add(goToEditPage);

        getRequestParametersLayout();
        getStartButtonLayout();

        listenComboBoxFromCurrency();
        listenComboBoxInCurrency();
        listenTextFieldAmountCurrency();
        listenButtonStart();
    }

    private void getStartButtonLayout() {
        HorizontalLayout startButtonLayout = new HorizontalLayout();
        initLayoutObjInCenter(startButtonLayout);
        startButtonLayout.add(BUTTON_START_CONVERTER);
        add(startButtonLayout);
    }

    private void getRequestParametersLayout() {
        HorizontalLayout requestParametersLayout = new HorizontalLayout();
        initLayoutObjInCenter(requestParametersLayout);
        requestParametersLayout.add(COMBOBOX_FROM_CURRENCY, COMBOBOX_IN_CURRENCY, TEXT_FIELD_AMOUNT_CURRENCY);
        initComboBoxInAndFromCurrencies();
        add(requestParametersLayout);
    }

    private static void initLayoutObjInCenter(HorizontalLayout requestParametersLayout) {
        requestParametersLayout.setWidthFull();
        requestParametersLayout.setJustifyContentMode(JustifyContentMode.CENTER);
    }

    private void initComboBoxInAndFromCurrencies() {
        COMBOBOX_FROM_CURRENCY.setItems(SERVICE_CURRENCIES_REPOSITORY.getListCurrencies());
        COMBOBOX_IN_CURRENCY.setItems(SERVICE_CURRENCIES_REPOSITORY.getListCurrencies());
    }

    private void listenComboBoxFromCurrency() {
        COMBOBOX_FROM_CURRENCY.addValueChangeListener(event -> startCurrency = event.getValue());
    }

    private void listenComboBoxInCurrency() {
        COMBOBOX_IN_CURRENCY.addValueChangeListener(event -> resultCurrency = event.getValue());
    }

    private void listenTextFieldAmountCurrency() {
        TEXT_FIELD_AMOUNT_CURRENCY.addValueChangeListener(event ->
                amountToConvert = BigDecimal.valueOf(Double.parseDouble(event.getValue())));
    }

    private void listenButtonStart() {
        BUTTON_START_CONVERTER.addClickListener(event -> {
            try {
                conversionResult = ProcessingConvert.startConvert(startCurrency, resultCurrency, amountToConvert); //TODO нужно ли создавать по аналогии с сервисным слоем
                Notification.show(conversionResult);
            } catch (IOException e) {
                throw new ConverterException(ERROR_CONVERT_CURRENCY + e);
            }
        });
    }

}
