function f(n) {
  if ((n == 0) || (n == 1))
    return 1
   else {
      result = (n * f(n-1) )
      return result
   }
}

var n
var p

function b(k) {
  var d = n-k;
  return f(n)/(f(k)*f(d)) * Math.pow(p, k) * Math.pow(1-p, d);
}

/* example 2th list, 2th exercise, c) */

n = 15
p = .4
b(4) + b(5) // returns 0.31271364801331214, wich is correct