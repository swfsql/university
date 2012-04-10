// factorial
function f(n) {
  return (!n || n==1) ? 1 : (n * f(n-1) );
}

// modelo binomial
var n, p
function b(k) {
  var d = n-k;
  return f(n)/(f(k)*f(d)) * Math.pow(p, k) * Math.pow(1-p, d);
}

/* exemple 2th list, 2th exercise, c) */

n = 15;
p = .4;
b(4) + b(5); // returns 0.31271364801331214, wich is correct

/* exemple 2th list, 3th exercise, c) */

var i, soma;

n = 15;
p = .8;
i = 9;
soma = 0;
while(++i < n+1) soma += b(i); // returns 0.9389485703823368, wich is correct