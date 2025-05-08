document.addEventListener('DOMContentLoaded', function() {
    const slots = document.querySelectorAll('.slot');
    const colorCircles = document.querySelectorAll('.color-circle');
    const form = document.getElementById('gameForm');

    // Drag and Drop для кружочков
    colorCircles.forEach(circle => {
        circle.addEventListener('dragstart', function(e) {
            e.dataTransfer.setData('text/plain', circle.dataset.color);
            e.dataTransfer.setData('text/background-color', circle.style.backgroundColor);
        });
    });

    // Обработка слотов
    slots.forEach(slot => {
        slot.addEventListener('dragover', function(e) {
            e.preventDefault();
            this.classList.add('drag-over');
        });

        slot.addEventListener('dragleave', function() {
            this.classList.remove('drag-over');
        });

        slot.addEventListener('drop', function(e) {
            e.preventDefault();
            this.classList.remove('drag-over');

            const color = e.dataTransfer.getData('text/plain');
            const backgroundColor = e.dataTransfer.getData('text/background-color');

            // Обновляем вид слота
            this.style.backgroundColor = backgroundColor;
            this.classList.add('filled');

            // Обновляем скрытое поле с значением
            const guessIndex = this.dataset.slot;
            document.getElementById(`guess${guessIndex}`).value = color;
        });
    });

    // Проверка перед отправкой формы
    form.addEventListener('submit', function(e) {
        const inputs = [
            document.getElementById('guess0'),
            document.getElementById('guess1'),
            document.getElementById('guess2'),
            document.getElementById('guess3')
        ];

        if (inputs.some(input => !input.value)) {
            e.preventDefault();
            alert('Пожалуйста, заполните все лунки!');
        }
    });

    // Добавляем возможность очистки слота по двойному клику
    slots.forEach(slot => {
        slot.addEventListener('dblclick', function() {
            this.style.backgroundColor = '';
            this.classList.remove('filled');
            const guessIndex = this.dataset.slot;
            document.getElementById(`guess${guessIndex}`).value = '';
        });
    });
});