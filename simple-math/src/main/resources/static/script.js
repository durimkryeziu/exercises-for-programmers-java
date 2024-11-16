const firstInput = document.getElementById('firstInput');
const secondInput = document.getElementById('secondInput');
const addition = document.getElementById('addition');
const subtraction = document.getElementById('subtraction');
const multiplication = document.getElementById('multiplication');
const division = document.getElementById('division');
const resetButton = document.getElementById('resetButton');

function formatNumber(num) {
    return num % 1 === 0 ? num : parseFloat(num.toFixed(2));
}

function updateCalculations() {
    const firstNumber = parseFloat(firstInput.value) || 0;
    const secondNumber = parseFloat(secondInput.value) || 0;
    addition.textContent = `${firstNumber} + ${secondNumber} = ${firstNumber + secondNumber}`;
    subtraction.textContent = `${firstNumber} - ${secondNumber} = ${firstNumber - secondNumber}`;
    multiplication.textContent = `${firstNumber} × ${secondNumber} = ${firstNumber * secondNumber}`;
    const quotient = formatNumber(firstNumber / secondNumber);
    division.textContent = secondNumber !== 0 ? `${firstNumber} ÷ ${secondNumber} = ${quotient}`
            : 'Cannot divide by zero!';
}

firstInput.addEventListener('input', updateCalculations);
secondInput.addEventListener('input', updateCalculations);
resetButton.addEventListener('click', () => {
    firstInput.value = 10;
    secondInput.value = 5;
    updateCalculations();
});

updateCalculations();
