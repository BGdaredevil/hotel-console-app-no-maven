package com.hotels.forms;

import com.hotels.utils.Color;
import com.hotels.utils.Validators;

import java.util.function.Function;

public class FormDataItem<T> {
    private T value = null;
    private boolean isValid = true;

    private Function<T, Boolean> validator = null;

    private String validationMessage = null;

    public FormDataItem(T initValue, Function<T, Boolean> validator, String validationMessage) {
        this.value = initValue;
        this.validator = validator;
        this.validationMessage = validationMessage;
    }

    public boolean isValid() {
        this.isValid = validator.apply(this.value);
        return isValid ;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
        this.isValid = this.validator.apply(value);
    }

    public String getState(boolean hideValue) {
        String color = this.isValid ? "green" : "red";
        String value = hideValue ? "*".repeat(String.valueOf(this.value).length()) : String.valueOf(this.value);
        String message = this.validationMessage.isEmpty() ? "Invalid input!" : value + ": " + this.validationMessage;

        return Color.color(color, this.isValid ? value : message);

    }
}
