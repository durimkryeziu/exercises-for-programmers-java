const firstInput = document.getElementById('firstInput');
const secondInput = document.getElementById('secondInput');
const addition = document.getElementById('addition');
const subtraction = document.getElementById('subtraction');
const multiplication = document.getElementById('multiplication');
const division = document.getElementById('division');
const resetButton = document.getElementById('resetButton');
const validationMessage = document.getElementById('validationMessage');

const INVALID_INPUT_MESSAGE = 'Enter whole numbers greater than or equal to zero.';

function formatNumber(num) {
    return num % 1 === 0 ? num : parseFloat(num.toFixed(2));
}

function parseWholeNumber(input) {
    const value = input.value.trim();

    if (value === '') {
        return null;
    }

    const number = Number(value);
    return Number.isSafeInteger(number) && number >= 0 ? number : null;
}

function clearCalculations() {
    addition.textContent = '';
    subtraction.textContent = '';
    multiplication.textContent = '';
    division.textContent = '';
}

function updateInputValidity(firstNumber, secondNumber) {
    firstInput.setAttribute('aria-invalid', firstNumber === null ? 'true' : 'false');
    secondInput.setAttribute('aria-invalid', secondNumber === null ? 'true' : 'false');
}

function updateCalculations() {
    const firstNumber = parseWholeNumber(firstInput);
    const secondNumber = parseWholeNumber(secondInput);
    updateInputValidity(firstNumber, secondNumber);

    if (firstNumber === null || secondNumber === null) {
        validationMessage.textContent = INVALID_INPUT_MESSAGE;
        clearCalculations();
        return;
    }

    validationMessage.textContent = '';
    addition.textContent = `${firstNumber} + ${secondNumber} = ${formatNumber(firstNumber + secondNumber)}`;
    subtraction.textContent = `${firstNumber} - ${secondNumber} = ${formatNumber(firstNumber - secondNumber)}`;
    multiplication.textContent = `${firstNumber} × ${secondNumber} = ${formatNumber(firstNumber * secondNumber)}`;
    division.textContent = secondNumber !== 0 ? `${firstNumber} ÷ ${secondNumber} = ${formatNumber(firstNumber / secondNumber)}`
            : 'Cannot divide by zero!';
}

firstInput.addEventListener('input', updateCalculations);
secondInput.addEventListener('input', updateCalculations);
resetButton.addEventListener('click', () => {
    firstInput.value = '10';
    secondInput.value = '5';
    updateCalculations();
});

updateCalculations();
