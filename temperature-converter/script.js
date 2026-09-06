const temperatureInput = document.getElementById("temperature");
const fromUnit = document.getElementById("fromUnit");
const toUnit = document.getElementById("toUnit");

const convertBtn = document.getElementById("convertBtn");
const swapBtn = document.getElementById("swapBtn");
const clearBtn = document.getElementById("clearBtn");

const result = document.getElementById("result");
const errorMessage = document.getElementById("errorMessage");


// Convert temperature
convertBtn.addEventListener("click", () => {

    errorMessage.textContent = "";

    const value = parseFloat(temperatureInput.value);

    if (isNaN(value)) {
        showError("Please enter a valid temperature.");
        return;
    }

    const from = fromUnit.value;
    const to = toUnit.value;

    // Absolute zero validation
    if (from === "celsius" && value < -273.15) {
        showError("Celsius cannot be below -273.15 °C.");
        return;
    }

    if (from === "fahrenheit" && value < -459.67) {
        showError("Fahrenheit cannot be below -459.67 °F.");
        return;
    }

    if (from === "kelvin" && value < 0) {
        showError("Kelvin cannot be below 0 K.");
        return;
    }

    // Convert input to Celsius
    let celsius;

    if (from === "celsius") {
        celsius = value;
    }
    else if (from === "fahrenheit") {
        celsius = (value - 32) * 5 / 9;
    }
    else {
        celsius = value - 273.15;
    }

    // Convert Celsius to target unit
    let convertedValue;

    if (to === "celsius") {
        convertedValue = celsius;
    }
    else if (to === "fahrenheit") {
        convertedValue = (celsius * 9 / 5) + 32;
    }
    else {
        convertedValue = celsius + 273.15;
    }

    const symbol = getSymbol(to);

    result.textContent =
        `${convertedValue.toFixed(2)} °${symbol}`;
});


// Swap units
swapBtn.addEventListener("click", () => {

    const currentFrom = fromUnit.value;
    const currentTo = toUnit.value;

    fromUnit.value = currentTo;
    toUnit.value = currentFrom;

    result.textContent = "—";
    errorMessage.textContent = "";
});


// Clear everything
clearBtn.addEventListener("click", () => {

    temperatureInput.value = "";

    fromUnit.value = "celsius";
    toUnit.value = "fahrenheit";

    result.textContent = "—";
    errorMessage.textContent = "";

    temperatureInput.focus();
});


// Allow Enter key to convert
temperatureInput.addEventListener("keydown", (event) => {

    if (event.key === "Enter") {
        convertBtn.click();
    }
});


// Get temperature symbol
function getSymbol(unit) {

    if (unit === "celsius") {
        return "C";
    }

    if (unit === "fahrenheit") {
        return "F";
    }

    return "K";
}


// Display error
function showError(message) {

    errorMessage.textContent = message;
    result.textContent = "—";
}