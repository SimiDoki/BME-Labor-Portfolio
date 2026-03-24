class Todo {
    constructor(name, state) {
        this.name = name;
        this.state = state;
    }
}

const todos = JSON.parse(localStorage.getItem("todos")) || [];   // Az oldal betöltődésekor a TODO lista egy üres tömb.
const states = ["active", "inactive", "done"];
const tabs = ["all"].concat(states);    // "all" + a state tömb elemei a fülek.

// Aktuálisan kiválasztott fül.
let currentTab = "all";

console.log(tabs);

x0
const form = document.getElementById("new-todo-form");
const input = document.getElementById("new-todo-title");

form.onsubmit = function(event) {
    // Meggátoljuk az alapértelmezett működést, ami frissítené az oldalt.
    event.preventDefault(); 

    // Csak akkor adjuk a tömbhöz ha a szögegdobozban érvényes érték van.
    // Ekvivalens ezzel: if (input && input.value && input.value.length) 
    // vagy if (input != null && input.value != null && input.value.length > 0)
    if (input?.value?.length) { 
        // Az új todo-t aktív állapotban hozunk létre.
        todos.push(new Todo(input.value, "active")); 
        // Kiürítjük az inputot
        input.value = ""; 

        // TODO: újrarajzolni a listát
        renderTodos();
        saveTodos();
    }
}

class Button {
    constructor(action, icon, type, title) {
        this.action = action;
        this.icon = icon;
        this.type = type;
        this.title = title;
    }
}

// A gombokat reprezentáló modell objektumok tömbje
const buttons = [
    new Button("done", "check", "success", "Mark as done"),
    new Button("active", "plus", "secondary", "Mark as active"),
    // Az objektumot dinamikusan is kezelhetjük, ekkor nem a konstruktorral példányosítjuk
    { action: "inactive", icon: "minus", type: "secondary", title: "Mark as inactive" },
    { action: "up", icon: "arrow-up", type: "secondary", title: "Move up" },
    { action: "down", icon: "arrow-down", type: "secondary", title: "Move down" },
    new Button("remove", "trash", "danger", "Remove"),
];


function createElementFromHTML(html) {
    const virtualElement = document.createElement("div");
    virtualElement.innerHTML = html;

    return virtualElement.childElementCount == 1 
        ? virtualElement.firstChild 
        : virtualElement.children;
}

function renderTodos() {
    // Megkeressük a konténert, ahová az elemeket tesszük
    const todoList = document.getElementById("todo-list"); 
    // A jelenleg a DOM-ban levő to-do elemeket töröljük
    todoList.innerHTML = ""; 

    const filtered = todos.filter(function(todo){ return todo.state === currentTab || currentTab === "all"; });

    // Bejárjuk a jelenlegi todo elemeket.
    for(let todo of filtered)
    {
        const row = createElementFromHTML(
            `<div class="row">
                <div class="col d-flex p-0">
                    <a class="list-group-item flex-grow-1" href="#">
                        ${todo.name}
                    </a>
                    <div class="btn-group action-buttons"></div>
                </div>
            </div>`);

        // A gomb modellek alapján legyártjuk a DOM gombokat.
        for(let button of buttons)
        {
            const btn = createElementFromHTML(
                `<button class="btn btn-outline-${button.type} fas fa-${button.icon} " title="${button.title}"></button>`
            );

            if (todo.state === button.action) // azt a gombot letiljuk, amilyen állapotban van egy elem
                btn.disabled = true;
            
            if (currentTab !== "all" ){
                if (button.action === "up" || button.action === "down"){
                    continue;
                }
            }
            const index = todos.indexOf(todo);
            if(index === 0 && button.action === "up"){
                btn.disabled = true;
            }
            if(index === todos.length - 1 && button.action === "down"){
                btn.disabled = true;
            }
            // TODO: a gomb klikk eseményének kezelése
            btn.onclick = () => { 
                // Törlést külön kell kezelni.
                if (button.action === "remove") { 
                    // Megerősítés után kiveszünk a 'todo'-adik elemtől 1 elemet a todos tömbből.
                    if (confirm("Are you sure you want to delete the todo titled '" + todo.name + "'?")) { 
                        todos.splice(todos.indexOf(todo), 1);
                        renderTodos();
                        saveTodos();
                    }
                }
                else if (button.action === "up") {
                    if (index > 0 ) {
                        // Kicseréljük a két elemet a tömbben
                        [todos[index - 1], todos[index]] = [todos[index], todos[index - 1]];
                        renderTodos();
                        saveTodos();
                    }
                }
                else if (button.action === "down") {
                    if (index < todos.length - 1) {
                        // Kicseréljük a két elemet a tömbben
                        [todos[index + 1], todos[index]] = [todos[index], todos[index + 1]];
                        renderTodos();
                        saveTodos();
                    }
                }
                else { 
                    // Ha nem törlés, akkor átállítjuk a kiválasztott todo állapotát a gomb állapotára.
                    todo.state = button.action;
                    renderTodos();
                    saveTodos();
                }
            }

            // A virtuális elem gomb konténerébe beletesszük a gombot
            row.querySelector(".action-buttons").appendChild(btn); 
        }

        // Az összeállított HTML-t a DOM-ban levő todo-list ID-jú elemhez fűzzük.
        todoList.appendChild(row); 
    }
    document.querySelector(".todo-tab[data-tab-name='all'] .badge").innerHTML = todos.length || "";

    for (let state of states)
        document.querySelector(`.todo-tab[data-tab-name='${state}'] .badge`).innerHTML = todos.filter(t => t.state === state).length || "";

}

// Kezdeti állapot kirajzolása.
renderTodos(); 



function selectTab(type) {
    // Beállítjuk az újonnan kiválasztott fület.
    currentTab = type; 

    for (let tab of document.getElementsByClassName("todo-tab")) {
        // Az összes fülről levesszük az `.active` osztályt.
        tab.classList.remove("active"); 

        // Ha éppen ez a fül a kiválasztott, visszatesszük az `.active` osztályt.
        if (tab.getAttribute("data-tab-name") == type) 
            tab.classList.add("active");
    }

    // Újrarajzolunk mindent.
    renderTodos();
}

// App indulásakor (oldal betöltéskor) kiválasztjuk az "all" fület.
selectTab("all"); 



function saveTodos() {
    const todosJSON = JSON.stringify(todos);
    localStorage.setItem("todos", todosJSON);
}