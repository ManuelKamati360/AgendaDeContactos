// Contando os cards existentes no painel central
const totalCards = document.querySelectorAll('.card-contato').length;

// Localizando a tag onde o total de cards será exibido
const tagTotalCards = document.querySelector('#total-de-contatos');

// Exibindo o total de cards na tag específica
tagTotalCards.textContent = totalCards;

