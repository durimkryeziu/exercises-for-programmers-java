const lengthInput = document.getElementById('lengthInput');
const widthInput = document.getElementById('widthInput');
const dimensionsDisplay = document.getElementById('dimensions');
const squareFeetDisplay = document.getElementById('squareFeet');
const squareMetersDisplay = document.getElementById('squareMeters');

const CONVERSION_FACTOR = 0.09290304;

function formatSquareMeters(value) {
    return value.toFixed(3);
}

function formatSquareFeet(value) {
    return value % 1 === 0 ? value : value.toFixed(2);
}

function updateCalculations() {
    const length = Number.parseFloat(lengthInput.value) || 0;
    const width = Number.parseFloat(widthInput.value) || 0;

    if (length === 0 || width === 0) {
        dimensionsDisplay.textContent = '';
        squareFeetDisplay.textContent = '';
        squareMetersDisplay.textContent = '';
        return;
    }

    const squareFeet = length * width;
    const squareMeters = squareFeet * CONVERSION_FACTOR;

    dimensionsDisplay.textContent = `You entered dimensions of ${length} feet by ${width} feet.`;
    squareFeetDisplay.textContent = formatSquareFeet(squareFeet);
    squareMetersDisplay.textContent = formatSquareMeters(squareMeters);
}

lengthInput.addEventListener('input', updateCalculations);
widthInput.addEventListener('input', updateCalculations);

updateCalculations();
