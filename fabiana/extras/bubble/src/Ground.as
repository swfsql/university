package  
{
	import flash.display.Graphics;
	import flash.display.Sprite;
	import flash.filters.GlowFilter;
	import flash.geom.Matrix;
	/**
	 * back/fore ground.
	 * @author thi
	 */
	public class Ground 
	{
		public var back:Sprite,
				   front:Sprite;
		
		public function Ground() 
		{
			back = new Sprite();
			front = new Sprite();
			back.cacheAsBitmap = true;
			front.cacheAsBitmap = true;
		}
		
		public function resize(W:Number, H:Number):void
		{
			var g:Graphics = back.graphics;
			g.clear();
			g.moveTo(0, 0);
			
			var colors:Array = [0xC4E6F5, 0x435268];
			var alphas:Array = [1, 1];
			var ratios:Array = [0, 255];
			var matrix:Matrix = new Matrix();
			matrix.createGradientBox(W, H, 90*Math.PI/180, 0, 0);
			g.beginGradientFill("linear", colors, alphas, ratios, matrix);
			g.drawRect(0, 0, W, H);
			g.endFill();
			
			var glow:GlowFilter = new GlowFilter(0, .3, 256, 296, 1, 2, true);
			back.filters = [glow];
			
			
		}
		
	}

}