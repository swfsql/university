package  
{
	import flash.display.BlendMode;
	import flash.display.Graphics;
	import flash.display.Sprite;
	import flash.filters.GlowFilter;
	/**
	 * é uma bolha.
	 * @author thi
	 */
	public class Bubble extends Sprite
	{
		public var next:Bubble;
		public var prev:Bubble;
		public var info:Number;
		
		public var vel:Number;
		public var radius:Number;
		public var teta:Number = 0;
		
		public var passing:Boolean;
		
		public function Bubble() 
		{
			var glow:GlowFilter = new GlowFilter(0x707090, 1, 6, 6, 5, 3, false, false);
			//this.filters = [glow];
			//this.blendMode = BlendMode.HARDLIGHT;
			
		}
		
		public function draw():void
		{
			// preferi fazer vetorial mesmo, sem cpyPixel pra um bmData dpois.
			
			var g:Graphics = this.graphics;
			g.clear();
			//g.lineStyle(.5 , 0x101020, .1);
			g.beginFill(0x707090, .5);
			g.drawCircle(0, 0, radius);
			g.endFill();
			g = null;
		}
		
		public function resize(W:Number, H:Number):void
		{
			
			
		}
		
	}

}