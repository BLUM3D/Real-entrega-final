const op1 = document.getElementById('senhaChoice1');
const op2 = document.getElementById('senhaChoice2');
const op3 = document.getElementById('senhaChoice3');
const op4 = document.getElementById('senhaChoice4');

var f1 = document.getElementById("f1");
var f2 = document.getElementById("f2");
var f3 = document.getElementById("f3");
var f4 = document.getElementById("f4");

op1.addEventListener('click', function() {
  f1.classList.remove('disabled');
  f2.classList.add('disabled');
  f3.classList.add('disabled');
  f4.classList.add('disabled');
});

op2.addEventListener('click', function() {
  f2.classList.remove('disabled');
  f1.classList.add('disabled');
  f3.classList.add('disabled');
  f4.classList.add('disabled');
});

op3.addEventListener('click', function() {
  f3.classList.remove('disabled');
  f2.classList.add('disabled');
  f1.classList.add('disabled');
  f4.classList.add('disabled');
})

op4.addEventListener('click', function() {
  f4.classList.remove('disabled');
  f2.classList.add('disabled');
  f1.classList.add('disabled');
  f3.classList.add('disabled');
})