package  
{
	/**
	 * ...
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
		
		public function Bubbles(rm:Number = 1, ro:Number = 0) 
		{
			rMultiplier = rm;
			rOffset = ro;
		}
		
		//									 radius multiplier, radius offset
		public function init(s:String = "1", rm:Number = 1, ro:Number = 0):void
		{
			var b:Bubble = bubbles,
				b2:Bubble = bubbles,
				i:int = -1,
				l:int = s.length;
				
			// modify bubbles
			while ((b = b.next) !== null && ++i < l) 
				b2 = b.draw(Number(s.charAt(i)), rMultiplier, rOffset);
			if (b !== null)
				b.prev = null;
			b = b2; // last modified
			
			// recycle bubbles
			b2 = garbage;
			while (++i < l && (b2 = b2.next) !== null)
			{
				b2.prev = b;
				b = b.next = b2.draw(Number(s.charAt(i)), rMultiplier, rOffset);
			}
			--i;
			if (b2 !== null)
				b2.prev = null;
			
			// create bubbles
			while (++i < l)
			{
				b.next = new Bubble();
				b.next.prev = b;
				b = b.next.draw(Number(s.charAt(i)), rMultiplier, rOffset);
				bubbles.addChild(b);
			}
			--i;
			
			// add bubbles to garbage, prev pointer ignored
			if (i+1 == l)
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
		}
		
	}

}