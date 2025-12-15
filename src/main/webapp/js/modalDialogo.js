// Botões de manipulação do modal...
const btnAdd = document.querySelector("#btn-adicionar-contato");
const btnDeletar = document.querySelectorAll(".btn-deletar-contato");
const btnEditar = document.querySelectorAll(".btn-editar-contato");

// Função para abrir o modal...
function abrirModal(id) {
    const modal = document.querySelector(id);
    modal.showModal();
    
    // Evento dos botões "Cancelar"...
    const btnCancelar = modal.querySelector(".btn-cancelar");
    btnCancelar.onclick = function() {
        modal.close();
    };
}

// Evento do botão "Add +"...
btnAdd.onclick = function() {
    abrirModal(".modal#adicionar");
}

// Evento do botão "Editar contato"...
btnEditar.forEach(btn => { 
    btn.onclick = function() { 
        abrirModal(".modal#editar");
    };
});

// Evento do botão "Deletar contato"...
btnDeletar.forEach(btn => { 
    btn.onclick = function() { 
        abrirModal(".modal#deletar");
    };
});
