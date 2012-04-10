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

/* exemple 2th list, 2th exercise, c) */

n = 15;
p = .4;
mb(4) + mb(5); // returns 0.31271364801331214

/* exemple 2th list, 3th exercise, c) */

var i, soma;

n = 15;
p = .8;
i = 9;
soma = 0;
while(++i < n+1) soma += mb(i); // returns 0.9389485703823368

/* example 2th list, 7th exercise, c) */

bin(4,1) * bin(5,2) / bin(9,3) + bin(4,2) * bin(5,1) / bin(9,3); // returns 0.8333333333333333