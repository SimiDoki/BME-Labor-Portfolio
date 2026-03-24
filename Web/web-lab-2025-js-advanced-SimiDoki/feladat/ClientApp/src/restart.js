import { Player } from './player';

export class Restart {
    constructor(game) {
        this.game = game; 
        this.button = null;
    }

    render() {
        if (this.button) return; 

        this.button = document.createElement('button');
        this.button.innerText = "Restart";
        this.button.className = "btn btn-warning mt-3 w-100"; 
        
        this.button.onclick = () => this.trigger();

        const container = document.querySelector('.guess-container') || document.body;
        container.appendChild(this.button);
    }

    trigger() {
        if (this.button) {
            this.button.remove();
            this.button = null;
        }

        this.game.guesses.clear();

        this.game.timer.clear();
        this.game.timer.render();

        document.getElementById('name-input').value = "";
        document.getElementById('name-input').disabled = false;
        document.getElementById('start-button').disabled = false;
        document.getElementById('name-input').focus();


        this.game.player = new Player();

        this.game.components[1] = this.game.player;

       
        this.game.player.onNameSet.then(() => {
            this.game.start();
        });
    }
}