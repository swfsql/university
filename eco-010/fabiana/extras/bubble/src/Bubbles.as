package  
{
	import flash.filters.BlurFilter;
	/**
	 * bubble list
	 * @author thi
	 */
	public class Bubbles 
	{
		public var next:Bubbles,
				   bubbles:Bubble = new Bubble(), // head
				   garbage:Bubble = new Bubble(), // head
				   //
				   rMultiplier:Number, 
				   rOffset:Number;
		
		public function Bubbles(rm:Number = 1, ro:Number = 1) 
		{
			rMultiplier = rm;
			rOffset = ro;
		}
		
		//									 radius multiplier, radius offset
		public function init(s:String = "1", rm:Number = 1, ro:Number = 1):void
		{
			rMultiplier = rm;
			rOffset = ro;
			
			var b:Bubble = bubbles,
				b2:Bubble = bubbles,
				i:int = -1,
				l:int = s.length;
				
			// modify bubbles
			while ((b = b.next) !== null && ++i < l)
				b2 = b.draw(Number(s.charAt(i)), rMultiplier, rOffset);
			b = b2; // b is the last modified bubble
			
			// recycle bubbles
			b2 = garbage;
			while (++i < l && (b2 = b2.next) !== null)
			{
				garbage.next = b2.next;
				b2.next = null;
				b2.prev = b;
				bubbles.addChild(b2.draw(Number(s.charAt(i)), rMultiplier, rOffset));
				b.next = b2;
				b = b.next;
			}
			--i;
			// b is the last modified bubble
			
			// create bubbles
			while (++i < l)
			{
				b.next = new Bubble();
				b.next.prev = b;
				b = b.next.draw(Number(s.charAt(i)), rMultiplier, rOffset);
				bubbles.addChild(b);
			}
			--i;
			// b is the newest or last modified bubble
			
			// add bubbles to garbage, prev pointer ignored
			if (i == l)
			{
				var b3:Bubble = b2 = b;
				while ((b2 = b2.next) !== null)
				{
					bubbles.removeChild(b2.clear());
					b3 = b2; // last cleared
				}
				b3.next = garbage.next;
				garbage.next = b.next;
				b.next = null;
			}
			
			bubbles.next.prev = null; // dont need to go back to head, when backwards
			
			actual = last = null;
		} // init
		
		private var actual:Bubble, last:Bubble;
		public function sort():String
		{
			var b:Bubble = bubbles,
				//b2:Bubble, // hold last bubble
				next:Bubble,
				s:String = "";
			
			//
			if (actual === null || actual === last )
				actual = bubbles.next;
			
			b = bubbles;
			while ((b = b.next) !== null)
			{
				// finish when we got to the last bubble
				if ((next = b.next) === null)
				{
					_upper = b; // b is the last/up bubble; _upper is used on zooming
					break;
				}
				
				s += String(b.info); // sorted string
			}
			
			b = actual;
			// bubble that will pass
			if((next = b.next) !== null && bubbles.next !== last)
			{
				// comparing color
				
				if (next.next !== last)
				{
					b.comparing();
					next.comparing();
				}
				if (b.prev)
					b.prev.normal();
				
				// you shall not pass
				if (next.info >= b.info)
				{
					b.passing = false;
					b.teta *= .2;
					
					// bubble stops
					if (next === last)
					{
						last = b;
						next.stop();
						b.stop();
					} else
						actual = actual.next;
				} else
				
				// shall pass
				{
					b.passing = true;
					
					// now the angle
					b.teta += next.radius / b.radius * .04 + b.teta * .2;
					//b.teta += .1;
					if (next.passing && next.next)
						b.teta *= 0.8;
					
					// already passed
					if (b.teta > Math.PI)
					{
						// bubble stops
						if (next.next === last)
						{
							last = b;
							b.stop();
							b.passing = false;
						}
						
						//b.teta *= 0.1;
						//b.teta = 0;
						// pointer mess
						next.prev = b.prev;
						b.prev = next;
						b.next = next.next;
						next.next = b;
						// (now works like like b is next; next is b)
						if (next.prev === null)
							bubbles.next = next;
						else
							next.prev.next = next;
						if (b.next)
							b.next.prev = b;
						else
							b = next;
						
					}
				}
			} // 1 bubble
			else
			// bubble stops
			{
				last = b;
				b.stop();
				b.passing = false;
			}
			
			b = _upper;
			// adjust the upper bubble position
			b.x = 0;
			b.y = b.radius;
			
			// move the bubbles
			while (b = b.prev)
			{
				next = b.next;
				
				// passing
				if (b.passing)
				{
					var r:Number = (next.radius + b.radius) / 2; // average radius
					var xc:Number = (b.x + next.x) / 2; // x center
					var yc:Number = (b.y + next.y) / 2; // y center
					var sin:Number = Math.sin(b.teta) * r;
					var cos:Number = Math.cos(b.teta) * r;
					
					b.x = xc + sin;
					b.y = yc + cos;
					
					// move the next bubble
					if (next.next)
					{
						next.x = xc - sin;
						next.y = yc - cos;
						continue;
					}
					
					// move only a little, the last/upper bubble
					{
						next.x += (xc - sin - next.x) * .1;
						next.y += (yc - cos - next.y) * .1;
					}
					continue;
				}
				
				// not passing
				{
					b.x += (next.x - b.x ) * .7 + Math.random() * 1 - .5;
					b.y += (next.y + next.radius + b.radius - b.y ) * .7;
				}
			} // while's movement stuff
			return s;
		} // sort
		
		// adjust the bubbles zoom
		private var _upper:Bubble;
		public function zoom(W:Number, H:Number):Number
		{
			// TODO? fix the width.. bug dont looks bad.
			var _y:Number = _upper.height + 5;
			var _h:Number = bubbles.height / bubbles.scaleY;
			var min:Number = Math.min(W / bubbles.width, (H - _y) / _h);
			bubbles.scaleX = bubbles.scaleY += (min - bubbles.scaleY) * .2;
			return bubbles.scaleY;
		}
		
	}
}