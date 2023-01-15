const QUESTION_TYPES_URL = '/survey/api/get-question-types';
const SUBMIT_URL = '/survey/api/submit';
const SUCCESS_URL = '/survey/success'

let questionId = 1;
let questionTypes = [];

init();

function init() {
    const addQuestionButton = document.getElementById('add-question');
    addQuestionButton.onclick = addQuestion;

    const submitFormButton = document.getElementById('submit-button');
    submitFormButton.onclick = submitForm;

    fetchQuestionTypes()
        .then(types => {
            types.forEach(type => questionTypes.push(type));
        });
}


async function fetchQuestionTypes() {
    return fetch(QUESTION_TYPES_URL)
        .then(response => {
            return response.json();
        });
}

async function submitForm(e) {
    e.preventDefault();

    const surveyTitle = document.getElementById('title-input').value;
    if (surveyTitle === "") {
        alert("Survey title must be filled out!");
        return false;
    }

    const questionsContainer = document.getElementById('questions');
    const questions = questionsContainer.childNodes;

    const jsonMap = {'title': surveyTitle, 'questions': []};

    const isValid = extractQuestions(questions, jsonMap);

    if (isValid) {
        const submitFormButton = document.getElementById('submit-button');
        submitFormButton.disabled = true;

        const token = document.getElementById('csrf_token').getAttribute('content');

        fetch(SUBMIT_URL, {
            method: 'POST',
            headers: {
                'X-CSRF-TOKEN': token,
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(jsonMap),
        })
            .then((response) => {
                if (!response.ok) {
                    throw new Error("ERROR");
                }
                return response.json();
            })
            .then(surveyId => window.location.replace(SUCCESS_URL+`/${surveyId}`))
            .catch(error => console.error(error));
    }
}

function extractQuestions(questions, jsonMap) {
    try {
        questions.forEach(question => putQuestionToMap(jsonMap, question));
        return true;
    } catch (e) {
        alert(e)
        return false;
    }
}

function putQuestionToMap(map, question) {
    const selector = question.querySelector('select');
    const questionType = questionTypes[selector.selectedIndex];
    const questionPrompt = question.querySelector('.prompt').value;

    if (questionPrompt === "") {
        throw 'Prompt must be filled out!';
    }

    const questionMap = {
        'type': questionType,
        'prompt': questionPrompt
    };

    if (questionType === "MULTIPLE_CHOICES") {
        const possibleAnswers = [];
        question.querySelectorAll('input[name="possible-answer"]')
            .forEach(answerNode => {
                if (answerNode.value === "") {
                    throw 'Possible answer must be filled out!';
                }
                possibleAnswers.push(answerNode.value)
            });

        questionMap['answers'] = possibleAnswers;
    }

    map['questions'].push(questionMap);
}


function addQuestion() {
    const allQuestions = document.getElementById("questions");
    allQuestions.append(createQuestion("SIMPLE", questionId++));
}

function createQuestion(questionType, id) {
    const question = document.createElement('div');
    question.id = `q${id}`;

    const selector = createTypeSelector(id);
    const questionBody = createQuestionBody(questionType, id);
    question.append(selector, questionBody);

    return question;
}

function createQuestionBody(questionType, id) {
    const questionBody = document.createElement('div');
    questionBody.classList.add('qbody');

    const inputBox = document.createElement('div');
    inputBox.classList.add('input-group', 'mb-3');

    const questionPrompt = document.createElement('span');
    questionPrompt.classList.add('input-group-text');
    questionPrompt.innerText = "Question";

    const questionInput = document.createElement('input');
    questionInput.name = 'questionPrompt' + id;
    questionInput.classList.add('form-control', 'prompt');
    questionInput.type = "text";
    questionInput.required = true;

    inputBox.append(questionPrompt, questionInput);
    questionBody.append(inputBox);

    if (questionType === "SCALE") {
        createScaleQuestion(questionBody, id);
    } else if (questionType === "MULTIPLE_CHOICES") {
        createMultipleChoiceQuestion(questionBody, id);
    } else {
        createSimpleQuestion(questionBody, id)
    }

    return questionBody;
}

function createMultipleChoiceQuestion(questionBody, id) {
    const multipleAnswersBox = document.createElement('div');
    multipleAnswersBox.classList.add('btn-group-vertical', 'answer-group');

    addAnswerBox(multipleAnswersBox);

    const addAnswerButton = document.createElement('button');
    addAnswerButton.classList.add('btn', 'btn-primary');
    addAnswerButton.type = 'button'
    addAnswerButton.innerText = 'Add Answer'
    addAnswerButton.onclick = function () {
        addAnswerBox(multipleAnswersBox);
    };

    questionBody.append(multipleAnswersBox, addAnswerButton);
}

function addAnswerBox(multipleAnswersBox) {
    const answerBox = document.createElement('div');
    answerBox.classList.add('input-group', 'flex-nowrap');

    const dotSymbol = document.createElement('span');
    dotSymbol.classList.add('input-group-text');
    dotSymbol.innerText = '●';

    const answerInput = document.createElement('input');
    answerInput.classList.add('form-control');
    answerInput.name = 'possible-answer';
    answerInput.type = 'text';
    answerInput.required = true;

    answerBox.append(dotSymbol, answerInput);
    multipleAnswersBox.append(answerBox);
}

function createScaleQuestion(questionBody, id) {
    const scalesBox = document.createElement('div');
    scalesBox.classList.add('btn-group');

    for (let i = 1; i <= 5; i++) {
        const checkbox = document.createElement('input');
        checkbox.classList.add('btn-check');
        checkbox.type = 'checkbox';
        checkbox.id = `q${id}check${i}`;

        const label = document.createElement('label');
        label.classList.add('btn', 'btn-outline-primary');
        label.setAttribute('for', `q${id}check${i}`);
        label.innerText = i.toString();

        scalesBox.append(checkbox, label);
    }

    questionBody.append(scalesBox);
}

function createSimpleQuestion(questionBody, id) {
    const answerInput = document.createElement('input');
    answerInput.name = 'answer' + id;
    answerInput.classList.add('form-control', 'prompt');
    answerInput.type = "text";
    answerInput.placeholder = "Answer";

    questionBody.append(answerInput);
}

function createTypeSelector(id) {
    const selector = document.createElement('select');
    selector.id = `q${id}selector`;
    selector.onchange = selectorChanged;

    questionTypes.forEach(type => {
       const option = document.createElement('option');
       option.value = type;
       option.innerText = type;

       selector.append(option);
    });

    return selector;
}

function selectorChanged(event) {
    const selector = event.target;
    const questionBox = selector.parentElement;
    const id = questionBox.id;

    const options = selector.querySelectorAll('option');
    const selectedType = options[selector.selectedIndex].innerText;

    const questionBody = createQuestionBody(selectedType, id);

    questionBox.removeChild(questionBox.querySelector('.qbody'));
    questionBox.append(questionBody);
}