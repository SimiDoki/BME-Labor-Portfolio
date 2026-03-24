// a globális névtérben deklaráljuk az alábbi változókat és függvényt:
let remainingQuestions, totalQuestions, currentQuestion, correctAnswerIndex, score;
let isGameOver = false;
function getNextQuestion() { 
    currentQuestion++;
    const question = remainingQuestions.pop();
    if (question === undefined) {
        $("#question-category").text("Játék vége");
        $("#question-text").text(`Pontszámod: ${score} / ${totalQuestions}`);
        
        $(".answer").hide();
        $("#next-question").text("Új játék indítása").show();
        
        isGameOver = true;
        return;
    }
    correctAnswerIndex = Math.floor(Math.random() * 4);
    const answers = question.incorrect_answers.slice();
    answers.splice(correctAnswerIndex, 0, question.correct_answer);
    $(".answer .correct, .answer .incorrect, #next-question").hide();

    $(".answer").show();

    $("#start-game-form-section").hide();
    $("#game-section").show();

    $("#current-question-number").text(currentQuestion);
    $("#question-category").text(atob(question.category))
    $("#question-text").text(atob(question.question));

    $(".answer").each((index, element) => {
        const el = $(element);
        el.removeAttr("disabled");
        
        el.find(".answer-text").text(atob(answers[index]));
    });
}

$(() => {
    $(".answer").on("click", function(e) {
        $(".answer").attr("disabled", true);
        const clickedButton = $(this);
        const clickedIndex = clickedButton.data("answer-index");

        if (clickedIndex === correctAnswerIndex) {
            score++;
            clickedButton.find(".correct").show();
        } else {
            clickedButton.find(".incorrect").show();
            $(`.answer[data-answer-index="${correctAnswerIndex}"]`).find(".correct").show();
        }
        $("#next-question").show();
    });

    $("#next-question").on("click", () => {
        if (isGameOver) {
            $("#game-section").hide();
            $("#start-game-form-section").show();
            
            $("#next-question").text("Next question").hide();
            isGameOver = false;
        } else {
            getNextQuestion();
        }
    });

    $("#lets-play-button").on("click", () => {
        $("#lets-play-section, #start-game-form-section").toggle();

        $.get("start-game-form-contents.html").then(html => $("#start-game-form").html(html)
            .on("submit", e => { // közvetlenül a HTML beszúrása után lácolhatjuk a 'submit' eseményre történő feliratkozást
                e.preventDefault(); // a böngésző alapértelmezett működését megállítjuk, amivel újratöltené az oldalt
                $("#start-game-form button[type='submit']").attr("disabled", true); // a Go! gombot letiltjuk, hogy ne lehessen újra API kérést indítani, amíg meg nem érkezett a válasz
                
                const amount = $("[name='trivia_amount']").val();
                const difficulty = $("[name='trivia_difficulty']").val();
                const category = $("[name='trivia_category']").val();
                
                score = 0;

                $.get(`https://opentdb.com/api.php?type=multiple&encode=base64&amount=${amount}&difficulty=${difficulty}&category=${category}`).then(data => {
                    remainingQuestions = data.results;
                    console.log(remainingQuestions);
                    currentQuestion = 0;
                    totalQuestions = remainingQuestions.length;
                    $("#total-questions").text(totalQuestions);
                    $("#start-game-form button[type='submit']").removeAttr("disabled");
                    getNextQuestion();
                });
            }));
    });
});
