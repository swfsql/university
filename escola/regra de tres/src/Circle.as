package  
{
	import flash.display.Graphics;
	import flash.display.Sprite;
	/**
	 * ...
	 * @author Thi
	 */
	public class Circle extends Sprite
	{
		private var g:Graphics = this.graphics;
		
		public function Circle() 
		{
			
			
			
		}
		
		public function draw(r:Number = 10):void 
		{
			g.clear();
			g.lineStyle(3, 0xFFFFFF);
			g.beginFill(0xFFFFFF, .9);
			g.drawCircle(0, 0, r);
			
			
		}
		
	}

}