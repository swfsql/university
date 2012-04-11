// factorial
function f(n) {
  return (!n || n==1) ? 1 : (n * f(n-1) );
}

// binomio
function bin(a, b) {
  return f(a) / (f(b) * f(a-b));
}

// modelo binomial
var n, p
function mb(k) {
  return bin(n, k) * Math.pow(p, k) * Math.pow(1-p, n-k);
}

// modelo poisson
var y;
function poisson (k) {
  return Math.pow (Math.E, -y) * Math.pow (y, k) / f(k);
}

/* exemple 2th list, 3th exercise, c) */

n = 15;
p = .4;
mb (0) + mb (1) + mb (11) + mb (12) + mb (13) + mb (14) + mb (15); // returns 0.014519695605760009

/* exemple 2th list, 4th exercise, c) */

var i, soma;

n = 15;
p = .8;
i = 9;
soma = 0;
while(++i < n+1) soma += mb(i); // returns 0.9389485703823368

/* example 2th list, 7th exercise, c) */

bin(4,1) * bin(5,2) / bin(9,3) + bin(4,2) * bin(5,1) / bin(9,3); // returns 0.8333333333333333

/* example 2th list, 9th exercise, a) */

y = 5;
1 - (poisson (0) + poisson (1) + poisson (2)); // returns 0.8753479805169189
